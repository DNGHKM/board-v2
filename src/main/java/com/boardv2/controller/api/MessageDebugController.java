package com.boardv2.controller.api;

import com.boardv2.dto.WriteRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class MessageDebugController {
    private final MessageSource messageSource;

    @GetMapping("/debug/message")
    public String test(@RequestParam String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }


    @PostMapping("/testMapping")
    public ResponseEntity<?> write(@Valid @ModelAttribute WriteRequestDTO dto, BindingResult bindingResult, Locale locale) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println("🔹 field: " + error.getField());
                System.out.println("🔹 code: " + error.getCode());
                System.out.println("🔹 defaultMessage: " + error.getDefaultMessage());
                System.out.println("🔹 codes: " + Arrays.toString(error.getCodes()));

                for (String code : error.getCodes()) {
                    try {
                        String resolved = messageSource.getMessage(code, error.getArguments(), locale);
                        System.out.println("✅ found in MessageSource: " + code + " → " + resolved);
                        break;
                    } catch (Exception e) {
                        System.out.println("❌ not found in MessageSource: " + code);
                    }
                }
                System.out.println("──────────────────────────────");
            }
        }

        return ResponseEntity.badRequest().build();
    }

}