package ru.maximister.bank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VerifyDto(
        @NotBlank(message = "field 'email' is mandatory : it can not be blank")
        @Email(message = "field 'email' must be well formated as 'example@mail.com'")
        String email,

        @NotBlank(message = "field 'code' is mandatory : it can not be blank")
        @Size(message = "size of field 'code' must be equals to 6", min = 6, max = 6)
        String code
) {
}
