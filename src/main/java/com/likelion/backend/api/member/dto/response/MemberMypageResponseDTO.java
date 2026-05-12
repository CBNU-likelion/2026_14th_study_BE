package com.likelion.backend.api.member.dto.response;

import com.likelion.backend.api.member.entity.Member;

public record MemberMypageResponseDTO(
        Long id,
        String email,
        String name,
        String department
) {
    public static MemberMypageResponseDTO from(Member member) {
        return new MemberMypageResponseDTO(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getDepartment()
        );
    }
}
