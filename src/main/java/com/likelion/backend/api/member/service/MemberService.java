package com.likelion.backend.api.member.service;

import com.likelion.backend.api.member.dto.request.MemberLoginRequestDTO;
import com.likelion.backend.api.member.dto.request.MemberSignupRequestDTO;
import com.likelion.backend.api.member.dto.response.MemberLoginResponseDTO;
import com.likelion.backend.api.member.dto.response.MemberMypageResponseDTO;
import com.likelion.backend.api.member.dto.response.MemberSignupResponseDTO;
import com.likelion.backend.api.member.entity.Member;
import com.likelion.backend.api.member.repository.MemberRepository;
import com.likelion.backend.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // 회원가입
    public MemberSignupResponseDTO signup(MemberSignupRequestDTO requestDTO) {

        if (memberRepository.existsByEmailAndIsDeleted(requestDTO.email(), false)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member member = Member.createMember(
                requestDTO.email(),
                passwordEncoder.encode(requestDTO.password()),
                requestDTO.name(),
                requestDTO.department()
        );

        Member saved = memberRepository.save(member);

        log.info("[Member] 회원가입 성공 - email: {}", saved.getEmail());

        return MemberSignupResponseDTO.from(saved);
    }

    // 로그인
    @Transactional(readOnly = true)
    public MemberLoginResponseDTO login(MemberLoginRequestDTO requestDTO) {

        Member member = memberRepository.findByEmailAndIsDeleted(requestDTO.email(), false)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(requestDTO.password(), member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtProvider.generateAccessToken(
                member.getId(),
                member.getEmail(),
                member.getRole()
        );

        log.info("[Member] 로그인 성공 - email: {}", member.getEmail());

        return MemberLoginResponseDTO.of(accessToken);
    }

    // 내 정보 조회(마이페이지)
    @Transactional(readOnly = true)
    public MemberMypageResponseDTO getMypage(Member member) {
        return MemberMypageResponseDTO.from(member);
    }
}
