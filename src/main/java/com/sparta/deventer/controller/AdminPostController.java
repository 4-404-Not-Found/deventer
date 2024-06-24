package com.sparta.deventer.controller;

import com.sparta.deventer.dto.*;
import com.sparta.deventer.security.UserDetailsImpl;
import com.sparta.deventer.service.AdminPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/posts")
@RequiredArgsConstructor
public class AdminPostController {

    private final AdminPostService adminPostService;

    // 공지글 생성
    @PutMapping("/{postId}/notice")
    public ResponseEntity<PostResponseDto> createNoticePost(
            @RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PostResponseDto postResponseDto = adminPostService.createNoticePost(postRequestDto,
            userDetails.getUser());
        return ResponseEntity.ok().body(postResponseDto);
    }

    // 공지글 수정
    @PutMapping("/notice/{postId}")
    public ResponseEntity<PostResponseDto> updateNoticePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequestDto updatePostRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PostResponseDto postResponseDto = adminPostService.updateNoticePost(postId,
            updatePostRequestDto, userDetails.getUser());
        return ResponseEntity.ok(postResponseDto);
    }

    // 어드민 권한으로 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteAnyPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        adminPostService.deleteUserPost(postId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
   //카테고리 이동
   @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> moveCategory(
         @PathVariable Long postId,
         @RequestBody MoveCategoryRequestDto moveCategoryRequestDto,
         @AuthenticationPrincipal UserDetailsImpl userDetails) {

     PostResponseDto postResponseDto = adminPostService.moveCategory(postId, moveCategoryRequestDto,
         userDetails.getUser());
        return ResponseEntity.ok(postResponseDto);
    }

}