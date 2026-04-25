package com.learning.employee_service.dao;

import com.learning.employee_service.dto.PostDetailsDto;

import java.util.List;

public interface PostDao {
    List<PostDetailsDto> getAllPosts();

    PostDetailsDto getPost(Long id);

    PostDetailsDto createOrUpdatePost(PostDetailsDto detailsDto);

    void deletePost(Long id);

    Boolean postExistsById(Long id);
}
