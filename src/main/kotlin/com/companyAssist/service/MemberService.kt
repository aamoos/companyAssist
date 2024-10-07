package com.companyAssist.service

import com.companyAssist.dto.LoginDto
import com.companyAssist.dto.MemberDtoRequest
import com.companyAssist.entity.Member
import com.companyAssist.entity.MemberRole
import com.companyAssist.enum.ROLE
import com.companyAssist.exception.InvalidInputException
import com.companyAssist.jwt.JwtTokenProvider
import com.companyAssist.jwt.TokenInfo
import com.companyAssist.repository.MemberRepository
import com.companyAssist.repository.MemberRoleRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    /** 회원가입 **/
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if(member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다")
        }

        member = memberDtoRequest.toEntity()
        memberRepository.save(member)

        val memberRole: MemberRole = MemberRole(null, ROLE.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료 되었습니다."
    }

    /** 로그인 -> 토큰 발행 */
    fun login(loginDto: LoginDto): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.loginId, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }

}