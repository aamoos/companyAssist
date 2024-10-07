package com.companyAssist.controller

import com.companyAssist.dto.LoginDto
import com.companyAssist.dto.MemberDtoRequest
import com.companyAssist.exception.BaseResponse
import com.companyAssist.jwt.TokenInfo
import com.companyAssist.service.MemberService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/member")
@RestController
class MemberController(
    private val memberService: MemberService,
) {
    /** 회원가입 */
    @RequestMapping("/signup")
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val resultMsg: String = memberService.signUp(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }

    /** 로그인 */
    @RequestMapping("/login")
    fun login(@RequestBody @Valid loginDto: LoginDto): BaseResponse<TokenInfo> {
        val tokenInfo = memberService.login(loginDto)
        return BaseResponse(data = tokenInfo)
    }
}