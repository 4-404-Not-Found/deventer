package com.sparta.deventer.controller;

import com.sparta.deventer.dto.PostRequestDto;
import com.sparta.deventer.dto.PostResponseDto;
import com.sparta.deventer.dto.PostWithCommentsResponseDto;
import com.sparta.deventer.dto.UpdatePostRequestsDto;
import com.sparta.deventer.security.UserDetailsImpl;
import com.sparta.deventer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostWithCommentsResponseDto> getCommentList(@PathVariable Long postId) {
        PostWithCommentsResponseDto responseDto = postService.getPostDetail(postId);
        return ResponseEntity.ok().body(responseDto);
    }

    // 게시글 생성
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PostResponseDto postResponseDto =  postService.createPost(postRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(postResponseDto);
    }
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts(
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        List<PostResponseDto> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }
    //카테고리 별 게시글 조회
    @GetMapping(params = "category")
    public ResponseEntity<List<PostResponseDto>> getPostsByCategory(
            @RequestParam Long category,
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        List<PostResponseDto> posts = postService.getPostsByCategory(category,pageable);
        return ResponseEntity.ok(posts);
    }


    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequestsDto updatePostRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PostResponseDto postResponseDto = postService.updatePost(postId, updatePostRequestDto,
                userDetails.getUser());
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());
        return new ResponseEntity<>("게시글이 삭제 되었습니다.", HttpStatus.OK);
    }
}