package com.yanacona.converters;

import com.yanacona.dtos.DepartmentDto;
import com.yanacona.entities.Department;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DepartmentDtoConverter implements Converter<Department, DepartmentDto> {
    @Override
    public DepartmentDto convert(Department department) {
        DepartmentDto departmentDto = DepartmentDto.builder().build();

        if(department == null ) return  departmentDto;

        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        departmentDto.setDaneCode(department.getDaneCode());
        departmentDto.setCreatedAt(department.getCreatedAt());
        departmentDto.setModifiedAt(department.getModifiedAt());
        departmentDto.setVersion(department.getVersion());

        return departmentDto;
    }
}
