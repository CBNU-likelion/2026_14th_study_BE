package com.likelion.backend.api.member.dto.response;


public record MemberLoginResponseDTO(
        String accessToken
) {
    public static MemberLoginResponseDTO of(String accessToken) {
        return new MemberLoginResponseDTO(accessToken);
    }
}
