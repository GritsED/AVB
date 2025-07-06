package org.example.users.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserDto {
    @Size(min = 2, max = 150)
    String firstname;
    @Size(min = 2, max = 150)
    String lastname;
    @Size(min = 11, max = 20)
    String phone;
    @Positive
    Long companyId;
}
