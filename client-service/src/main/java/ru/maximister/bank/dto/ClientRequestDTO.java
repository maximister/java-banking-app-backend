package ru.maximister.bank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.maximister.bank.enums.Gender;
import ru.maximister.bank.util.validation.AgeMinimum;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ClientRequestDTO {
    @NotBlank(message = "field 'firstname' is mandatory: it can not be blank")
    private String firstname;

    @NotBlank(message = "field 'lastname' is mandatory: it can not be blank")
    private String lastname;

    @NotNull(message = "field 'dateOfBirth' is mandatory: it can not be null")
    @AgeMinimum(min = 18, message = "Customer must be at least 18 years old")
    private LocalDate dateOfBirth;

    @NotBlank(message = "field 'email' is mandatory: it can not be blank")
    @Email(message = "field 'email' is not well formated")
    private String email;
}