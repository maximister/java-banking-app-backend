package ru.maximister.bank.dto;

import lombok.*;
import ru.maximister.bank.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ClientResponseDTO {
    private String id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String email;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;
}