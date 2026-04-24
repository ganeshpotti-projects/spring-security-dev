package com.learning.employee_service.mappers;

import com.learning.employee_service.dto.EmployeeDetailsDto;
import com.learning.employee_service.entities.Employee;
import com.learning.employee_service.io.EmployeeAdminPatchDto;
import com.learning.employee_service.io.EmployeeCreateDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface EmployeeMapper {

    EmployeeDetailsDto mapCreateDtoToDetailsDto(EmployeeCreateDto dto);

    EmployeeDetailsDto mapEntityToDetailsDto(Employee employee);

    Employee mapDetailsDtoToEntity(EmployeeDetailsDto employeeDetailsDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapPatchDtoToDetailsDto(EmployeeAdminPatchDto patchDto,
                               @MappingTarget EmployeeDetailsDto employeeDetailsDto);
}

