package com.likelion.backend.api.member.dto.request;

public record MemberLoginRequestDTO(

        String email,

        String password
) {
}
