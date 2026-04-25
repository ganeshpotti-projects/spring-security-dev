package com.learning.employee_service.dao;

import com.learning.employee_service.dto.PostDetailsDto;
import com.learning.employee_service.entities.Post;
import com.learning.employee_service.exceptions.ResourceNotFoundException;
import com.learning.employee_service.mappers.PostMapper;
import com.learning.employee_service.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostDaoImpl implements PostDao{

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    @Override
    public List<PostDetailsDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts
                .stream()
                .map(postMapper::mapEntityToDetailsDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDetailsDto getPost(Long id) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with Id: "+id));
        return postMapper.mapEntityToDetailsDto(post);
    }

    @Override
    public PostDetailsDto createOrUpdatePost(PostDetailsDto detailsDto) {
        Post post = postMapper.mapDetailsDtoToEntity(detailsDto);
        post =  postRepository.save(post);
        return postMapper.mapEntityToDetailsDto(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Boolean postExistsById(Long id) {
        return postRepository.existsById(id);
    }
}
