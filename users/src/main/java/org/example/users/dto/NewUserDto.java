package org.example.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewUserDto {
    @NotBlank
    @Size(min = 2, max = 150)
    String firstname;
    @NotBlank
    @Size(min = 2, max = 150)
    String lastname;
    @NotBlank
    @Size(min = 11, max = 20)
    String phone;
    @NotNull
    @Positive
    Long companyId;
}
