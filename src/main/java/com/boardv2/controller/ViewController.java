package com.boardv2.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boards")
@AllArgsConstructor
public class ViewController {

    @GetMapping("/{boardEngName}/list")
    public String list() {
        return "list";
    }

    @GetMapping("/{boardEngName}/write")
    public String writePage() {
        return "write";
    }

    @GetMapping("/{boardEngName}/view/{postId}")
    public String viewPage() {
        return "view";
    }

    @GetMapping("/{boardEngName}/modify/{postId}")
    public String modifyPage() {
        return "modify";
    }
}
