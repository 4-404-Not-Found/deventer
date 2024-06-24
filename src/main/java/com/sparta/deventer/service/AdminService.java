package com.sparta.deventer.service;

import com.sparta.deventer.dto.MoveCategoryRequestDto;
import com.sparta.deventer.dto.PostRequestDto;
import com.sparta.deventer.dto.PostResponseDto;
import com.sparta.deventer.dto.UpdatePostRequestDto;
import com.sparta.deventer.entity.Category;
import com.sparta.deventer.entity.Post;
import com.sparta.deventer.entity.User;
import com.sparta.deventer.enums.UserRole;
import com.sparta.deventer.exception.CategoryNotFoundException;
import com.sparta.deventer.exception.PostNotFoundException;
import com.sparta.deventer.exception.UserNotFoundException;
import com.sparta.deventer.repository.CategoryRepository;
import com.sparta.deventer.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;


    private void checkAdmin(User user) {
        if (user.getRole() != UserRole.ADMIN) {
            throw new UserNotFoundException("관리자 권한이 필요합니다.");
        }
    }

    // 공지글 생성
    public PostResponseDto createNoticePost(PostRequestDto postRequestDto, User admin) {
        checkAdmin(admin);

        Category category = categoryRepository.findByTopic(postRequestDto.getCategoryTopic())
                .orElseThrow(() -> new CategoryNotFoundException("카테고리를 찾을 수 없습니다."));

        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContent(), admin, category);
        post.setNotice(true); // 공지글로 설정
        postRepository.save(post);
        return new PostResponseDto(post);
    }
}