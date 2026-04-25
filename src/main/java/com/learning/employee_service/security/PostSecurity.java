package com.learning.employee_service.security;

import com.learning.employee_service.dao.PostDao;
import com.learning.employee_service.dto.PostDetailsDto;
import com.learning.employee_service.entities.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSecurity {

    private final PostDao postDao;

    public boolean isOwnerOfPost(Long postId){
        Employee employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDetailsDto postDetailsDto = postDao.getPost(postId);
        return postDetailsDto.getEmployee().getId().equals(employee.getId());
    }
}
