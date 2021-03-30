package com.yanacona.services;

import com.yanacona.dtos.DepartmentDto;

import java.util.Optional;
import java.util.Set;

public interface DepartmentService {
    Set<DepartmentDto> findAll();
    Optional<DepartmentDto> findByName(String name) ;
    Optional<DepartmentDto> findByDaneCode(String code);
    Optional<DepartmentDto> save(DepartmentDto departmentDto);
    Optional<DepartmentDto> update(Long id, DepartmentDto departmentDto);
    void delete(DepartmentDto departmentDto);
    void deleteById(Long id);
    Optional<DepartmentDto> findById(Long id);

}
