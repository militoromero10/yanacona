package com.yanacona.converters;

import com.yanacona.dtos.DepartmentDto;
import com.yanacona.entities.Department;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DepartmentConverterTest {

    @InjectMocks
    DepartmentConverter converter;

    @Test
    void convert() {
        Department department = new Department();
        long id = 1L;
        String dep_name = "dep name";
        String daneCode = "001";
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime now1 = LocalDateTime.now();
        int version = 1;

        department.setId(id);
        department.setName(dep_name);
        department.setDaneCode(daneCode);
        department.setModifiedAt(now);
        department.setCreatedAt(now1);
        department.setVersion(version);
        DepartmentDto departmentDto = DepartmentDto.builder().id(id).name(dep_name).daneCode(daneCode).modifiedAt(now).createdAt(now1).version(version).build();

        Department returned = converter.convert(departmentDto);

        assertEquals(department, returned);
    }
}