package com.mirim.redis;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

    // TODO: [세션] 설계 카드의 '키 이름' 앞부분으로 바꾸기 (뒤에 로그인한 사람 이름이 붙음 → note:s2399)
    private static final String PREFIX = "note:";
    private final StringRedisTemplate redisTemplate;

    @Value("${server.port:8080}")
    private String port;

    public NoteController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/note/write")
    public String write(@RequestParam String text, HttpSession session) {
        Object user = session.getAttribute("user");   // 지난 시간 /login이 넣어 둔 것
        if (user == null) {
            return "login first  server=" + port;
        }
        redisTemplate.opsForList().leftPush(PREFIX + user, text);   // LPUSH
        return "saved  server=" + port;
    }

    @GetMapping("/note/list")
    public String list(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return "login first  server=" + port;
        }
        List<String> notes = redisTemplate.opsForList().range(PREFIX + user, 0, 4);   // LRANGE, 최근 5개
        return "server=" + port + "  user=" + user + "  notes=" + notes;
    }
}
