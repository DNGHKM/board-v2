package com.boardv2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootTest
class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    void 메시지키_확인() {
        String key = "Length.password";
        System.out.println(messageSource.getMessage(key, null, Locale.KOREAN));
        System.out.println(messageSource.getMessage(key, null, Locale.getDefault()));
        System.out.println(messageSource.getMessage(key, null, Locale.US));
    }
}
