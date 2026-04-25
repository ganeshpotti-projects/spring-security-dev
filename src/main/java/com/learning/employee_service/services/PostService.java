package com.learning.employee_service.services;

import com.learning.employee_service.dto.PostDetailsDto;
import com.learning.employee_service.io.PostCreateRequest;
import com.learning.employee_service.io.PostPatchRequest;
import com.learning.employee_service.io.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostCreateRequest postCreateRequest);
    PostResponse getPost(Long id);
    List<PostResponse> getPosts();
    PostResponse updatePost(Long id, PostPatchRequest postPatchRequest);
    void deletePost(Long id);
}
