package com.boardv2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.boardv2.constant.ValidationConstant.*;

@Getter
@Builder
public class ModifyRequestDTO {
    @NotBlank(message = "{NotBlank.boardEngName}")
    private String boardEngName;

    @NotBlank(message = "{NotBlank.writer}")
    @Length(min = WRITER_MIN_LENGTH, max = WRITER_MAX_LENGTH, message = "{Length.writer}")
    private String writer;

    @NotBlank(message = "{NotBlank.password}")
    @Length(min = PW_MIN_LENGTH, max = PW_MAX_LENGTH, message = "{Length.password}")
    @Pattern(regexp = PW_REGEX, message = "{Pattern.password}")
    private String password;

    @NotBlank(message = "{NotBlank.subject}")
    @Length(min = SUBJECT_MIN_LENGTH, max = SUBJECT_MAX_LENGTH, message = "{Length.subject}")
    private String subject;

    @NotBlank(message = "{NotBlank.content}")
    @Length(min = CONTENT_MIN_LENGTH, max = CONTENT_MAX_LENGTH, message = "{Length.content}")
    private String content;

    //보존되는 저장파일명 목록(uuid.확장자)
    private List<String> preserveFilenames;

    //새로 업로드 된 파일 목록
    private List<MultipartFile> files;
}
