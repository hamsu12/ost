package com.example.ost.service;

import com.example.ost.domain.track.LikedTrack;
import com.example.ost.model.Comment;
import com.example.ost.repository.CommentRepository;
import com.example.ost.repository.LikedTrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepo;
    private final LikedTrackRepository likedTrackRepo;

    public CommentService(CommentRepository commentRepo, LikedTrackRepository likedTrackRepo) {
        this.commentRepo = commentRepo;
        this.likedTrackRepo = likedTrackRepo;
    }

    public Comment addComment(String trackId, String content) {
        Comment comment = new Comment(trackId, content);
        return commentRepo.save(comment);
    }

    public List<Comment> getComments(String trackId) {
        return commentRepo.findByTrackId(trackId);
    }

    public Comment updateComment(Long id, String newContent) {
        Comment c = commentRepo.findById(id).orElseThrow();
        c.setContent(newContent);
        return commentRepo.save(c);
    }

    public void deleteComment(Long id) {
        commentRepo.deleteById(id);
    }

    public LikedTrack likeTrack(String trackId) {
        LikedTrack liked = new LikedTrack(trackId);
        return likedTrackRepo.save(liked);
    }
}
