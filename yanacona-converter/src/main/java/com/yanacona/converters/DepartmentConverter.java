package com.yanacona.converters;

import com.yanacona.dtos.DepartmentDto;
import com.yanacona.entities.Department;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverter implements Converter<DepartmentDto, Department> {

    @Override
    public Department convert(DepartmentDto departmentDto) {
        Department department = new Department();

        if(departmentDto == null ) return  department;

        department.setId(departmentDto.getId());
        department.setName(departmentDto.getName());
        department.setDaneCode(departmentDto.getDaneCode());
        department.setCreatedAt(departmentDto.getCreatedAt());
        department.setModifiedAt(departmentDto.getModifiedAt());
        department.setVersion(departmentDto.getVersion());
        return department;
    }
}
