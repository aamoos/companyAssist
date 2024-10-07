package com.companyAssist.repository

import com.companyAssist.entity.Member
import com.companyAssist.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long>