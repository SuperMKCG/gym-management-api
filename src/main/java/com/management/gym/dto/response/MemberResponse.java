package com.management.gym.dto.response;

import java.time.LocalDate;

public record MemberResponse(Long idMember,
                             String name,
                             String email,
                             String phone,
                             LocalDate registrationDate,
                             Boolean active,
                             Long planId) {
}
