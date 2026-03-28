package com.mmgon.jjajuka.domain.member.repository;

import com.mmgon.jjajuka.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
