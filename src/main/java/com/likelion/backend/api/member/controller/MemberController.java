package com.likelion.backend.api.member.controller;

import com.likelion.backend.api.member.dto.request.MemberLoginRequestDTO;
import com.likelion.backend.api.member.dto.request.MemberSignupRequestDTO;
import com.likelion.backend.api.member.dto.response.MemberLoginResponseDTO;
import com.likelion.backend.api.member.dto.response.MemberMypageResponseDTO;
import com.likelion.backend.api.member.dto.response.MemberSignupResponseDTO;
import com.likelion.backend.api.member.entity.Member;
import com.likelion.backend.api.member.service.MemberService;
import com.likelion.backend.global.response.ApiResponse;
import com.likelion.backend.global.response.SuccessStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원(Member)", description = "회원 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<MemberSignupResponseDTO>> signup(
            @RequestBody MemberSignupRequestDTO requestDTO
    ) {
        MemberSignupResponseDTO responseDTO = memberService.signup(requestDTO);

        return ApiResponse.success(SuccessStatus.SUCCESS_MEMBER_REGISTRATION, responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberLoginResponseDTO>> login(
            @RequestBody MemberLoginRequestDTO requestDTO
    ) {
        MemberLoginResponseDTO responseDTO = memberService.login(requestDTO);

        return ApiResponse.success(SuccessStatus.SUCCESS_MEMBER_LOGIN, responseDTO);
    }

    @GetMapping("/mypage")
    public ResponseEntity<ApiResponse<MemberMypageResponseDTO>> getMypage(
            @AuthenticationPrincipal(expression = "member") Member member
    ) {
        MemberMypageResponseDTO responseDTO = memberService.getMypage(member);

        return ApiResponse.success(SuccessStatus.SUCCESS_MEMBER_MYPAGE_GET, responseDTO);
    }
}
