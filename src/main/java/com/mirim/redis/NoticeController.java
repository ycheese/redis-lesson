package com.mirim.redis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeController {

    private final NoticeService noticeService;
    private final FakeNoticeDb db;

    public NoticeController(NoticeService noticeService, FakeNoticeDb db) {
        this.noticeService = noticeService;
        this.db = db;
    }

    @GetMapping("/notice")
    public Notice notice(@RequestParam Long id) {
        return noticeService.find(id);
    }

    @GetMapping("/notice/db-count")
    public int dbCount() {
        return db.getCallCount();
    }
}
