package com.learning.employee_service.controllers;

import com.learning.employee_service.dto.PostDetailsDto;
import com.learning.employee_service.io.PostCreateRequest;
import com.learning.employee_service.io.PostPatchRequest;
import com.learning.employee_service.io.PostResponse;
import com.learning.employee_service.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return ResponseEntity.ok(postService.getPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping
    @Secured({"ROLE_GRADE_1", "ROLE_GRADE_2", "ROLE_GRADE_3", "ROLE_GRADE_4","ROLE_GRADE_5", "ROLE_GUEST"})
    public ResponseEntity<PostResponse> createPost(@RequestBody PostCreateRequest createRequest){
        return ResponseEntity.ok(postService.createPost(createRequest));
    }

    @PatchMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole('GRADE_1','GRADE_2','GRADE_3','GRADE_4','GRADE_5','GUEST') " +
                    "and hasAuthority('POST_UPDATE') " +
                    "and @postSecurity.isOwnerOfPost(#id)"
    )
        public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody PostPatchRequest postPatchRequest){
        return ResponseEntity.ok(postService.updatePost(id, postPatchRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
