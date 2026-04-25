package com.learning.employee_service.mappers;

import com.learning.employee_service.dto.EmployeeDetailsDto;
import com.learning.employee_service.dto.PostDetailsDto;
import com.learning.employee_service.entities.Post;
import com.learning.employee_service.io.EmployeeAdminPatchDto;
import com.learning.employee_service.io.PostCreateRequest;
import com.learning.employee_service.io.PostPatchRequest;
import com.learning.employee_service.io.PostResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PostMapper {
    PostDetailsDto mapCreateRequestToDetailsDto(PostCreateRequest postCreateRequest);
    Post mapDetailsDtoToEntity(PostDetailsDto postDetailsDto);
    PostDetailsDto mapEntityToDetailsDto(Post post);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapPatchRequestToDetailsDto(PostPatchRequest postPatchRequest,
                                 @MappingTarget PostDetailsDto postDetailsDto);
    PostResponse mapDetailsDtoToResponse(PostDetailsDto postDetailsDto);
}
