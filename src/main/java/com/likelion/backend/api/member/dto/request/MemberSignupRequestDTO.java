package com.likelion.backend.api.member.dto.request;

public record MemberSignupRequestDTO(

        String email,

        String password,

        String name,

        String department
) {
}
