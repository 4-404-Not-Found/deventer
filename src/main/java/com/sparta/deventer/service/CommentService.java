package com.sparta.deventer.service;

import com.sparta.deventer.dto.CommentRequestDto;
import com.sparta.deventer.dto.CommentResponseDto;
import com.sparta.deventer.entity.Comment;
import com.sparta.deventer.entity.Post;
import com.sparta.deventer.entity.User;
import com.sparta.deventer.repository.CommentRepository;
import com.sparta.deventer.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {

        Post post = validPostId(requestDto.getPostId());

        Comment comment = new Comment(post, user, requestDto.getContent());

        log.info("Creating comment {}", comment.getUser().getUsername());

        commentRepository.save(comment);
        return new CommentResponseDto(comment);

    }

    @Transactional
    public CommentResponseDto updateComment(Long userId, CommentRequestDto requestDto,
            Long postId) {
        Comment comment = validCommentId(postId);

        validUserId(comment, userId);

        comment.update(requestDto.getContent());

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long userId, Long postId) {
        Comment comment = validCommentId(postId);

        validUserId(comment, userId);

        commentRepository.delete(comment);
    }


    public Comment validCommentId(Long Id) {
        return commentRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을수 없습니다"));
    }

    public void validUserId(Comment comment, Long userId) {
        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
        }
    }

    public Post validPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
    }

}
