package com.vision.haksa.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(Model model) {
        // 메인 페이지에 필요한 데이터를 모델에 추가
        return "main";
    }
    @GetMapping("/erd")
    public String erd() {
        return "erd";
    }

}