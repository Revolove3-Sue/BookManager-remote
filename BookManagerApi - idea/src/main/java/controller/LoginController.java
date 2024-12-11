package com.rabbiter.bms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public String login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        // 从 session 获取验证码
        String captchaFromSession = (String) session.getAttribute("captcha");

        // 校验验证码
        if (captchaFromSession == null || !captchaFromSession.equalsIgnoreCase(loginRequest.getCaptcha())) {
            return "验证码错误";
        }

        // 其他登录逻辑
        return "登录成功";
    }
}
