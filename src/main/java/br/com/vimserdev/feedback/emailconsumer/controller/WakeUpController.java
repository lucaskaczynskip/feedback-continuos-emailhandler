package br.com.vimserdev.feedback.emailconsumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/up")
public class WakeUpController {

    @GetMapping
    public String wakeUp() {
        return "Tá bom, tá bom, já acordei!";
    }
}
