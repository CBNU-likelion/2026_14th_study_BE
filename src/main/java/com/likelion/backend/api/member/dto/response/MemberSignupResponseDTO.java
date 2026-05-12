package com.likelion.backend.api.member.dto.response;

import com.likelion.backend.api.member.entity.Member;

public record MemberSignupResponseDTO(

        Long id,
        String email,
        String name,
        String department
) {
    public static MemberSignupResponseDTO from(Member member) {
        return new MemberSignupResponseDTO(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getDepartment()
        );
    }
}
