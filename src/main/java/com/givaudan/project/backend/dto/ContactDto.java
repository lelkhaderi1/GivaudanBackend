package com.givaudan.project.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ContactDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

}
