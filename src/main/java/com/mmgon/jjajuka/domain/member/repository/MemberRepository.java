package com.mmgon.dutyflow.domain.member.repository;

import com.mmgon.dutyflow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
