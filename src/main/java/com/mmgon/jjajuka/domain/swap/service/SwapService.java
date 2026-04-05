package com.mmgon.jjajuka.domain.swap.service;

import com.mmgon.jjajuka.domain.member.entity.Member;
import com.mmgon.jjajuka.domain.member.repository.MemberRepository;
import com.mmgon.jjajuka.domain.notification.service.NotificationService;
import com.mmgon.jjajuka.domain.schedule.entity.Schedule;
import com.mmgon.jjajuka.domain.schedule.repository.ScheduleRepository;
import com.mmgon.jjajuka.domain.swap.dto.request.SwapCreateRequest;
import com.mmgon.jjajuka.domain.swap.dto.request.SwapDecisionRequest;
import com.mmgon.jjajuka.domain.swap.dto.response.SwapAdminResponse;
import com.mmgon.jjajuka.domain.swap.dto.response.SwapCreateResponse;
import com.mmgon.jjajuka.domain.swap.dto.response.SwapDecisionResponse;
import com.mmgon.jjajuka.domain.swap.dto.response.SwapResponse;
import com.mmgon.jjajuka.domain.swap.dto.response.SwapSentResponse;
import com.mmgon.jjajuka.domain.swap.entity.Swap;
import com.mmgon.jjajuka.domain.swap.exception.SwapErrorCode;
import com.mmgon.jjajuka.domain.swap.exception.SwapException;
import com.mmgon.jjajuka.domain.swap.repository.SwapRepository;
import com.mmgon.jjajuka.domain.swap.service.dto.DiscordWebhookRequest;
import com.mmgon.jjajuka.domain.vacancy.entity.ReplacementCandidate;
import com.mmgon.jjajuka.domain.vacancy.entity.Vacancy;
import com.mmgon.jjajuka.domain.vacancy.event.VacancyCreatedEvent;
import com.mmgon.jjajuka.domain.vacancy.repository.ReplacementCandidateRepository;
import com.mmgon.jjajuka.domain.vacancy.repository.VacancyRepository;
import com.mmgon.jjajuka.global.enums.ScheduleStatus;
import com.mmgon.jjajuka.global.enums.SwapStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SwapService {

    private final SwapRepository swapRepository;
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final NotificationService notificationService;
    private final DiscordNotificationService discordNotificationService;
    private final VacancyRepository vacancyRepository;
    private final ReplacementCandidateRepository candidateRepository;
    private final ApplicationEventPublisher eventPublisher;

    public List<SwapAdminResponse> getAdminSwapList() {
        return swapRepository.findAllWithDetails()
                .stream()
                .map(SwapAdminResponse::from)
                .toList();
    }

    public List<Swap> findAll() {
        return swapRepository.findAll();
    }

    public Swap findById(Integer id) {
        return swapRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Swap not found: " + id));
    }

    @Transactional
    public SwapCreateResponse createSwapRequest(SwapCreateRequest request) {
        Member target = memberRepository.findById(request.getTargetMemberId())
                .orElseThrow(() -> new SwapException(SwapErrorCode.TARGET_MEMBER_NOT_FOUND));

        Schedule requesterSchedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new SwapException(SwapErrorCode.SCHEDULE_NOT_FOUND));

/*
        if (requesterSchedule.getStatus() != ScheduleStatus.CANCELED) {
            throw new SwapException(SwapErrorCode.SCHEDULE_NOT_CANCELLED);
        }

        if (requesterSchedule.getWorkDate().isBefore(LocalDate.now())) {
            throw new SwapException(SwapErrorCode.SCHEDULE_IN_PAST);
        }

        swapRepository.findByRequesterScheduleIdAndStatus(request.getScheduleId(), SwapStatus.PENDING)
                .ifPresent(existingSwap -> {
                    throw new SwapException(SwapErrorCode.SWAP_ALREADY_EXISTS);
                });
*/

        Member requester = requesterSchedule.getMember();

/*
        if (requester.getId().equals(target.getId())) {
            throw new SwapException(SwapErrorCode.CANNOT_SWAP_WITH_SELF);
        }
*/

        Swap swap = Swap.createSwapRequest(requester, target, requesterSchedule);
        Swap savedSwap = swapRepository.save(swap);

        try {
            DiscordWebhookRequest message = discordNotificationService.sendSwapRequestNotification(savedSwap);
            //notificationService.createSwapNotification(savedSwap, message);
        } catch (Exception e) {
            log.error("Failed to send Discord notification, but swap was created successfully", e);
        }

        return SwapCreateResponse.success();
    }

    public List<SwapSentResponse> getSentSwaps(Integer loginMemberId) {
        return swapRepository.findSentSwapsByMemberId(loginMemberId)
                .stream()
                .map(SwapSentResponse::from)
                .toList();
    }

    public List<SwapResponse> getReceivedSwaps(Integer loginMemberId) {
        return swapRepository.findReceivedSwapsByMemberId(loginMemberId)
                .stream()
                .map(SwapResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public SwapResponse getReceivedSwap(Integer swapId, Integer loginMemberId) {
        Swap swap = swapRepository.findById(swapId)
                .orElseThrow(() -> new SwapException(SwapErrorCode.SWAP_NOT_FOUND));

        if (!swap.getTarget().getId().equals(loginMemberId)) {
            throw new SwapException(SwapErrorCode.UNAUTHORIZED);
        }

        return SwapResponse.from(swap);
    }

    @Transactional
    public SwapDecisionResponse respondToSwap(
            Integer swapId,
            Integer loginMemberId,
            SwapDecisionRequest request
    ) {
        Swap swap = swapRepository.findById(swapId)
                .orElseThrow(() -> new SwapException(SwapErrorCode.SWAP_NOT_FOUND));

        if (!swap.getTarget().getId().equals(loginMemberId)) {
            throw new SwapException(SwapErrorCode.UNAUTHORIZED);
        }

        if (request.getSwapStatus() == SwapStatus.PENDING) {
            throw new SwapException(SwapErrorCode.INVALID_SWAP_STATUS);
        }

        Schedule requesterSchedule = swap.getRequesterSchedule();

        Vacancy vacancy = vacancyRepository
                .findByScheduleIdAndMemberId(requesterSchedule.getId(), swap.getRequester().getId())
                .stream()
                .findFirst()
                .orElse(null);

        ReplacementCandidate replacementCandidate = null;
        if (vacancy != null) {
            replacementCandidate = candidateRepository
                    .findByVacancyIdAndCandidateMemberId(vacancy.getId(), loginMemberId)
                    .stream()
                    .findFirst()
                    .orElse(null);
        }

        if (request.getSwapStatus() == SwapStatus.REJECTED) {
            swap.reject();

            if (vacancy != null) {
                vacancy.reject();
            }

            if (replacementCandidate != null) {
                replacementCandidate.reject();
            }

            return SwapDecisionResponse.rejected(swap);
        }

        if (request.getTargetScheduleId() == null) {
            throw new SwapException(SwapErrorCode.TARGET_SCHEDULE_REQUIRED);
        }

        Schedule targetSchedule = scheduleRepository.findById(request.getTargetScheduleId())
                .orElseThrow(() -> new SwapException(SwapErrorCode.TARGET_SCHEDULE_NOT_FOUND));

        if (!targetSchedule.getMember().getId().equals(loginMemberId)) {
            throw new SwapException(SwapErrorCode.TARGET_SCHEDULE_NOT_BELONG_TO_TARGET);
        }

        if (targetSchedule.getStatus() != ScheduleStatus.ACTIVE) {
            throw new SwapException(SwapErrorCode.INVALID_SCHEDULE);
        }

        if (requesterSchedule.getStatus() != ScheduleStatus.ACTIVE
                && requesterSchedule.getStatus() != ScheduleStatus.VACANCY_PENDING
                && requesterSchedule.getStatus() != ScheduleStatus.SWAP_PENDING) {
            throw new SwapException(SwapErrorCode.INVALID_SCHEDULE);
        }

        if (requesterSchedule.getId().equals(targetSchedule.getId())) {
            throw new SwapException(SwapErrorCode.INVALID_SCHEDULE);
        }

        Member requesterMember = requesterSchedule.getMember();
        Member targetMember = targetSchedule.getMember();

        requesterSchedule.changeMember(targetMember);
        targetSchedule.changeMember(requesterMember);

        requesterSchedule.changeStatus(ScheduleStatus.ACTIVE);
        targetSchedule.changeStatus(ScheduleStatus.ACTIVE);

        swap.accept(targetSchedule);

        if (vacancy != null) {
            vacancy.accept();
            eventPublisher.publishEvent(new VacancyCreatedEvent(this, vacancy));
        }

        if (replacementCandidate != null) {
            replacementCandidate.accept();
        }

        return SwapDecisionResponse.accepted(swap);
    }
}
