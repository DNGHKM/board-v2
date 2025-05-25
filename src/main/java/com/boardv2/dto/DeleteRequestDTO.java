package com.boardv2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class DeleteRequestDTO {
    @NotBlank(message = "비밀번호를 입력하세요.")
    @Length(min = 4, max = 16, message = "비밀번호는 4~16자여야 합니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{}\\[\\]:;\"'<>,.?/]).{4,16}$",
            message = "비밀번호는 영문/숫자/특수문자를 모두 포함해야 합니다."
    )
    private String password;
}
