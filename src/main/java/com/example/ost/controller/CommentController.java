package com.example.ost.controller;

import com.example.ost.domain.track.LikedTrack;
import com.example.ost.model.Comment;
import com.example.ost.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    // 댓글 추가
    @PostMapping("/comment")
    public Comment add(
            @RequestParam String trackId,
            @RequestParam String content
    ) {
        return service.addComment(trackId, content);
    }

    // 특정 곡 댓글 리스트 가져오기
    @GetMapping("/comments")
    public List<Comment> get(@RequestParam String trackId) {
        return service.getComments(trackId);
    }

    // 댓글 수정
    @PutMapping("/comment/{id}")
    public Comment edit(
            @PathVariable Long id,
            @RequestParam String content
    ) {
        return service.updateComment(id, content);
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteComment(id);
        return "deleted";
    }

    // 좋아요 등록
    @PostMapping("/like")
    public LikedTrack like(@RequestParam String trackId) {
        return service.likeTrack(trackId);
    }
}
