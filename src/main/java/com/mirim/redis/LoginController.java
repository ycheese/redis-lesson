package com.mirim.redis;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 실습 편의상 비밀번호 없이 "이름만 대면 로그인"
@RestController
public class LoginController {

    @Value("${server.port:8080}")
    private String port;

    @GetMapping("/login")
    public String login(@RequestParam String name, HttpSession session) {
        session.setAttribute("user", name);
        return "login ok  user=" + name + "  server=" + port;
    }

    @GetMapping("/me")
    public String me(HttpSession session) {
        Object user = session.getAttribute("user");
        return "server=" + port + "  user=" + (user == null ? "(not logged in)" : user);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();            // 로그아웃 = 열쇠 반납
        return "bye  server=" + port;
    }
}
