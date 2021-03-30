package com.yanacona.controllers;

import com.yanacona.dtos.DepartmentDto;
import com.yanacona.services.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Set<DepartmentDto> getAll(){
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentDto getById(@PathVariable("id") Long idDep) {
        return departmentService.findById(idDep).orElseThrow(RuntimeException::new);
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentDto getByName(@PathVariable("name") String depName) {
        return departmentService.findByName(depName).orElseThrow(RuntimeException::new);
    }

    @GetMapping("/{dane}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentDto getByDaneCode(@PathVariable("dane") String daneCode) {
        return departmentService.findByDaneCode(daneCode).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto save(DepartmentDto departmentDto){
        return departmentService.save(departmentDto).orElseThrow(RuntimeException::new);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long idDep){
        departmentService.deleteById(idDep);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(DepartmentDto departmentDto){
        departmentService.delete(departmentDto);
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentDto update(@PathVariable("id") Long idDep, DepartmentDto departmentDto){
        return departmentService.update(idDep, departmentDto).orElseThrow(RuntimeException::new);
    }

}
