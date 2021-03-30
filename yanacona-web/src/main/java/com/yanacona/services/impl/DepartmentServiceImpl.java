package com.yanacona.services.impl;

import com.yanacona.converters.DepartmentConverter;
import com.yanacona.converters.DepartmentDtoConverter;
import com.yanacona.dtos.DepartmentDto;
import com.yanacona.entities.Department;
import com.yanacona.repositories.DepartmentRepository;
import com.yanacona.services.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentConverter departmentConverter;
    private final DepartmentDtoConverter departmentDtoConverter;


    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentConverter departmentConverter, DepartmentDtoConverter departmentDtoConverter) {
        this.departmentRepository = departmentRepository;
        this.departmentConverter = departmentConverter;
        this.departmentDtoConverter = departmentDtoConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<DepartmentDto> findAll() {
        Set<DepartmentDto> deps = new HashSet<>();
        departmentRepository.findAll()
                .stream()
                .map(departmentDtoConverter::convert)
                .forEach(deps::add);
        return deps;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentDto> findByName(String name) {
        Department department = departmentRepository.findByName(name).orElseThrow(RuntimeException::new);
        return Optional.of(departmentDtoConverter.convert(department));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentDto> findByDaneCode(String code) {
        Department department = departmentRepository.findByDaneCode(code).orElseThrow(RuntimeException::new);
        return Optional.of(departmentDtoConverter.convert(department));
    }

    @Override
    public Optional<DepartmentDto> save(DepartmentDto departmentDto) {
        Department department = departmentConverter.convert(departmentDto);
        Department saved = departmentRepository.save(department);
        return Optional.of(departmentDtoConverter.convert(saved));
    }

    @Override
    public void delete(DepartmentDto departmentDto) {
        Department found = departmentRepository.findById(departmentDto.getId()).orElseThrow(RuntimeException::new);
        departmentRepository.delete(found);
    }

    @Override
    public void deleteById(Long id) {
        departmentRepository.findById(id).orElseThrow(RuntimeException::new);
        departmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentDto> findById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(RuntimeException::new);
        return Optional.of(departmentDtoConverter.convert(department));
    }

    @Override
    public Optional<DepartmentDto> update(Long id, DepartmentDto departmentDto) {
        Department found = departmentRepository.findById(id).orElseThrow(RuntimeException::new);
        Department department = departmentConverter.convert(departmentDto);
        department.setCreatedAt(found.getCreatedAt());
        department.setModifiedAt(LocalDateTime.now());
        Department saved = departmentRepository.save(department);
        return Optional.of(departmentDtoConverter.convert(saved));
    }
}
