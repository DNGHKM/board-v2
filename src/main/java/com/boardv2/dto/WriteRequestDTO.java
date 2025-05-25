package com.boardv2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.boardv2.constant.ValidationConstant.*;

@Getter
@Builder

/*
 * 게시글 작성 요청을 위한 DTO.
 *
 * 원래는 어노테이션에 메시지 키를 명시하지 않아도,
 * 스프링이 검증 실패 시 FieldError.code를 기반으로 messageSource에서
 * 적절한 메시지를 찾아 응답에 반영해 줄 것으로 기대했으나,
 *
 * 결과:
 *   - ✅ Thymeleaf 환경에서는 메시지 코드(`error.code`)를 사용해 자동으로 메시지 해석이 잘 작동함
 *   - ❌ 하지만 REST API(JSON 응답)에서는 messageSource에서 메시지를 찾아도
 *       FieldError.defaultMessage에는 적용되지 않고,
 *       기본 메시지("공백일 수 없습니다", "길이가 4~16 사이여야 합니다" 등)가 그대로 응답됨
 *
 * ⚠️ 따라서 메시지를 별도 파일(errors.properties)에 관리하면서도 API 응답에서도 커스텀 메시지를 정확히 응답하기 위해
 *     각 제약 어노테이션의 message 속성에 `{메시지_키}`를 명시적으로 선언함
 */
public class WriteRequestDTO {

    @NotBlank(message = "{NotBlank.boardEngName}")
    private String boardEngName;

    @NotNull(message = "{NotNull.categoryId}")
    private Long categoryId;

    @NotBlank(message = "{NotBlank.writer}")
    @Length(min = WRITER_MIN_LENGTH, max = WRITER_MAX_LENGTH, message = "{Length.writer}")
    private String writer;

    @NotBlank(message = "{NotBlank.password}")
    @Length(min = PW_MIN_LENGTH, max = PW_MAX_LENGTH, message = "{Length.password}")
    @Pattern(regexp = PW_REGEX, message = "{Pattern.password}")
    private String password;

    @NotBlank(message = "{NotBlank.passwordCheck}")
    @Length(min = PW_MIN_LENGTH, max = PW_MAX_LENGTH, message = "{Length.passwordCheck}")
    @Pattern(regexp = PW_REGEX, message = "{Pattern.passwordCheck}")
    private String passwordCheck;

    @NotBlank(message = "{NotBlank.subject}")
    @Length(min = SUBJECT_MIN_LENGTH, max = SUBJECT_MAX_LENGTH, message = "{Length.subject}")
    private String subject;

    @NotBlank(message = "{NotBlank.content}")
    @Length(min = CONTENT_MIN_LENGTH, max = CONTENT_MAX_LENGTH, message = "{Length.content}")
    private String content;

    private List<MultipartFile> files;
}
