package com.learning.employee_service.services;

import com.learning.employee_service.dao.PostDao;
import com.learning.employee_service.dto.PostDetailsDto;
import com.learning.employee_service.entities.Employee;
import com.learning.employee_service.exceptions.ResourceNotFoundException;
import com.learning.employee_service.io.PostCreateRequest;
import com.learning.employee_service.io.PostPatchRequest;
import com.learning.employee_service.io.PostResponse;
import com.learning.employee_service.mappers.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostDao postDao;

    private final PostMapper postMapper;

    @Override
    public PostResponse createPost(PostCreateRequest postCreateRequest) {
        PostDetailsDto postDetailsDto = postMapper.mapCreateRequestToDetailsDto(postCreateRequest);
        Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postDetailsDto.setEmployee(employee);
        postDetailsDto = postDao.createOrUpdatePost(postDetailsDto);
        return postMapper.mapDetailsDtoToResponse(postDetailsDto);
    }

    @Override
    public PostResponse getPost(Long id) {
        if(!postDao.postExistsById(id))
            throw new ResourceNotFoundException("Post not found with Id: "+ id);
        PostDetailsDto postDetailsDto = postDao.getPost(id);
        return postMapper.mapDetailsDtoToResponse(postDetailsDto);
    }

    @Override
    public List<PostResponse> getPosts() {
        List<PostDetailsDto> postDetailsDtoList =  postDao.getAllPosts();
        return postDetailsDtoList
                .stream()
                .map(postMapper::mapDetailsDtoToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponse updatePost(Long id, PostPatchRequest postPatchRequest) {
        if(!postDao.postExistsById(id))
            throw new ResourceNotFoundException("Post not found with Id: "+ id);
        PostDetailsDto postDetailsDto = postDao.getPost(id);
        Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!Objects.equals(employee.getId(), postDetailsDto.getEmployee().getId()))
            throw new AccessDeniedException("Employee not allowed to update");
        postMapper.mapPatchRequestToDetailsDto(postPatchRequest, postDetailsDto);
        postDetailsDto = postDao.createOrUpdatePost(postDetailsDto);
        return postMapper.mapDetailsDtoToResponse(postDetailsDto);
    }

    @Override
    public void deletePost(Long id) {
        if(!postDao.postExistsById(id))
            throw new ResourceNotFoundException("Post not found with Id: "+ id);
        postDao.deletePost(id);
    }
}
