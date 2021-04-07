package com.yanacona.services.impl;

import com.yanacona.converters.DepartmentConverter;
import com.yanacona.converters.DepartmentDtoConverter;
import com.yanacona.dtos.DepartmentDto;
import com.yanacona.entities.Department;
import com.yanacona.exceptions.NotFoundException;
import com.yanacona.repositories.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository repository;

    @Mock
    private DepartmentConverter converter;

    @Mock
    private DepartmentDtoConverter dtoConverter;

    @InjectMocks
    private DepartmentServiceImpl service;

    Department dep1, dep2;
    DepartmentDto depDto1, depDto2;

    InOrder inOrder;

    @BeforeEach
    void setUp() {
        dep1 = new Department();
        dep1.setId(1L);
        dep1.setName("department 1");
        dep1.setDaneCode("001");

        dep2 = new Department();
        dep2.setId(2L);
        dep2.setName("department 2");
        dep2.setDaneCode("002");

        depDto1 = DepartmentDto.builder().id(1L).name("deparment 1").daneCode("001").build();
        depDto2 = DepartmentDto.builder().id(2L).name("deparment 2").daneCode("002").build();

        inOrder = inOrder(repository, converter, dtoConverter);
    }


    @Test
    @DisplayName("Find all")
    void findAll() {
        Set<DepartmentDto> deps = Set.of(depDto1,depDto2);

        given(repository.findAll()).willReturn(List.of(dep1,dep2));
        given(dtoConverter.convert(any(Department.class))).willReturn(depDto1,depDto2);

        Set<DepartmentDto> returned = service.findAll();

        int times = deps.size();
        assertAll("full test case",
                () -> assertNotNull(returned, "Must be not null"),
                () -> assertFalse(returned.isEmpty(), "Must not be empty"),
                () -> assertEquals(deps.size(), returned.size(), "they must be equals the size"),
                () -> then(repository).should(inOrder).findAll(),
                () -> then(dtoConverter).should(inOrder, times(times)).convert(any(Department.class)),
                () -> verifyNoMoreInteractions(repository, dtoConverter),
                () -> verifyNoInteractions(converter)
        );
    }

    @Test
    @DisplayName("Find by name positive case")
    void findByName() {
        given(repository.findByName(anyString())).willReturn(Optional.of(dep1));
        given(dtoConverter.convert(any(Department.class))).willReturn(depDto1);

        Optional<DepartmentDto> returned = service.findByName(depDto1.getName());
        assertTrue(returned.isPresent(), "Must be present value");

        DepartmentDto get = returned.get();
        assertAll("full test case",
                () -> assertNotNull(returned, "Failed service with null returned value"),
                () -> assertNotNull(get, "Response myst be not null"),
                () -> assertEquals(depDto1.getName(), get.getName(),"Must be equals"),
                () -> then(repository).should(inOrder).findByName(anyString()),
                () -> then(dtoConverter).should(inOrder).convert(any(Department.class)),
                () -> verifyNoMoreInteractions(repository, dtoConverter),
                () -> verifyNoInteractions(converter)
        );
    }

    @Test
    @DisplayName("Not found by name negative case")
    void notFoundByName() {
        given(repository.findByName(anyString())).willThrow(new NotFoundException("boom!"));
        assertAll("Would throws not found exception",
                () -> assertThrows(NotFoundException.class, () -> service.findByName(depDto1.getName())),
                () -> then(repository).should(inOrder).findByName(anyString()),
                () -> verifyNoMoreInteractions(repository),
                () -> verifyNoInteractions(converter, dtoConverter)
        );
    }

    @Test
    @DisplayName("Find by daneCode positive case")
    void findByDaneCode() {
        given(repository.findByDaneCode(anyString())).willReturn(Optional.of(dep1));
        given(dtoConverter.convert(any(Department.class))).willReturn(depDto1);

        Optional<DepartmentDto> returned = service.findByDaneCode(depDto1.getDaneCode());
        assertTrue(returned.isPresent(), "Must be present value");

        DepartmentDto get = returned.get();
        assertAll("full test case",
                () -> assertNotNull(returned, "Failed service with null returned value"),
                () -> assertNotNull(get, "Response myst be not null"),
                () -> assertEquals(depDto1.getDaneCode(), get.getDaneCode(),"Must be equals"),
                () -> then(repository).should(inOrder).findByDaneCode(anyString()),
                () -> then(dtoConverter).should(inOrder).convert(any(Department.class)),
                () -> verifyNoMoreInteractions(repository, dtoConverter),
                () -> verifyNoInteractions(converter)
        );
    }

    @Test
    @DisplayName("Not found by daneCode negative case")
    void notFoundByDaneCode() {
        given(repository.findByDaneCode(anyString())).willThrow(new NotFoundException("boom!"));
        assertAll("Would throws not found exception",
                () -> assertThrows(NotFoundException.class, () -> service.findByDaneCode(depDto1.getDaneCode())),
                () -> then(repository).should(inOrder).findByDaneCode(anyString()),
                () -> verifyNoMoreInteractions(repository),
                () -> verifyNoInteractions(converter, dtoConverter)
        );
    }

    @Test
    @DisplayName("save new department")
    void save() {
        dep1.setId(null);
        depDto1.setId(null);

        Department saved = new Department();
        saved.setId(1L);
        saved.setName(dep1.getName());
        saved.setDaneCode(dep1.getDaneCode());

        DepartmentDto toReturn = DepartmentDto.builder().id(saved.getId()).name(saved.getName()).daneCode(saved.getDaneCode()).build();

        given(converter.convert(any(DepartmentDto.class))).willReturn(dep1);
        given(repository.save(any(Department.class))).willReturn(saved);
        given(dtoConverter.convert(any(Department.class))).willReturn(toReturn);

        Optional<DepartmentDto> returned = service.save(depDto1);
        assertTrue(returned.isPresent());

        DepartmentDto value = returned.get();
        assertAll("Save new department test cases",
                () -> assertNotNull(value, "Failed service"),
                () -> assertNotNull(value.getId(), "Must be not null"),
                () -> then(converter).should(inOrder).convert(any(DepartmentDto.class)),
                () -> then(repository).should(inOrder).save(any(Department.class)),
                () -> then(dtoConverter).should(inOrder).convert(any(Department.class)),
                () -> verifyNoMoreInteractions(repository, converter, dtoConverter)
        );
    }

    @Test
    @DisplayName("update department")
    void saveDepartmentWithId() {
        Department saved = new Department();
        saved.setId(1L);
        saved.setName("New name");
        saved.setDaneCode("New dane code");

        DepartmentDto toReturn = DepartmentDto.builder().id(saved.getId()).name(saved.getName()).daneCode(saved.getDaneCode()).build();

        given(converter.convert(any(DepartmentDto.class))).willReturn(dep1);
        given(repository.save(any(Department.class))).willReturn(saved);
        given(dtoConverter.convert(any(Department.class))).willReturn(toReturn);

        Optional<DepartmentDto> returned = service.save(depDto1);
        assertTrue(returned.isPresent());

        DepartmentDto value = returned.get();
        assertAll("Save new department test cases",
                () -> assertNotNull(value, "Failed service"),
                () -> assertNotNull(value.getId(), "Must be not null"),
                () -> assertEquals(toReturn.getId(), value.getId(), "ID must be equals"),
                () -> assertEquals(toReturn.getName(), value.getName(), "NAME must be equals"),
                () -> assertEquals(toReturn.getDaneCode(), value.getDaneCode(), "DaneCode must be equals"),
                () -> then(converter).should(inOrder).convert(any(DepartmentDto.class)),
                () -> then(repository).should(inOrder).save(any(Department.class)),
                () -> then(dtoConverter).should(inOrder).convert(any(Department.class)),
                () -> verifyNoMoreInteractions(repository, converter, dtoConverter)
        );
    }

    @Test
    @DisplayName("Delete")
    void delete() {
        given(repository.findById(anyLong())).willReturn(Optional.of(dep1));
        willDoNothing().given(repository).delete(any(Department.class));

        service.delete(depDto1);
        assertAll("Deleting department",
                () -> then(repository).should(inOrder).findById(anyLong()),
                () -> then(repository).should(inOrder).delete(any(Department.class)),
                () -> verifyNoMoreInteractions(repository),
                () -> verifyNoInteractions(converter, dtoConverter)
        );
    }

    @Test
    @DisplayName("Not deleted, throws exception")
    void notDeleted() {
        given(repository.findById(anyLong())).willThrow(new NotFoundException("Boom!"));
        assertAll("Deleting department",
                () -> assertThrows(NotFoundException.class, () -> service.delete(depDto1)),
                () -> then(repository).should(inOrder).findById(anyLong()),
                () -> verifyNoMoreInteractions(repository),
                () -> verifyNoInteractions(converter, dtoConverter)
        );
    }

    @Test
    @DisplayName("Delete by id")
    void deleteById() {
        given(repository.findById(anyLong())).willReturn(Optional.of(dep1));
        willDoNothing().given(repository).deleteById(anyLong());

        service.deleteById(depDto1.getId());
        assertAll("Deleting department by id",
                () -> then(repository).should(inOrder).findById(anyLong()),
                () -> then(repository).should(inOrder).deleteById(anyLong()),
                () -> verifyNoMoreInteractions(repository),
                () -> verifyNoInteractions(converter, dtoConverter)
        );
    }

    @Test
    @DisplayName("Not delete by id, throws exception")
    void notDeleteById() {
        given(repository.findById(anyLong())).willThrow(new NotFoundException("Boom!"));
        assertAll("Not deleted department by id",
                () -> assertThrows( NotFoundException.class, () -> service.deleteById(depDto1.getId())),
                () -> then(repository).should(inOrder).findById(anyLong()),
                () -> verifyNoMoreInteractions(repository),
                () -> verifyNoInteractions(converter, dtoConverter)
        );
    }

    @Test
    @DisplayName("Find by id")
    void findById() {
        given(repository.findById(anyLong())).willReturn(Optional.of(dep1));
        given(dtoConverter.convert(any(Department.class))).willReturn(depDto1);

        Optional<DepartmentDto> returned = service.findById(depDto1.getId());
        assertTrue(returned.isPresent());

        DepartmentDto actual = returned.get();
        assertAll("Full test case find by id",
                () -> assertNotNull(actual, "Failed service"),
                () -> assertEquals(depDto1.getId(), actual.getId(), "ID must be equals"),
                () -> then(repository).should(inOrder).findById(anyLong()),
                () -> then(dtoConverter).should(inOrder).convert(any(Department.class)),
                () -> verifyNoMoreInteractions(repository, dtoConverter),
                () -> verifyNoInteractions(converter)
        );
    }
    @Test
    @DisplayName("Not found by id")
    void notFoundById() {
        given(repository.findById(anyLong())).willThrow(new NotFoundException("boom!"));
        assertAll("Full test case find by id",
                () -> assertThrows(NotFoundException.class, () -> service.findById(depDto1.getId())),
                () -> then(repository).should(inOrder).findById(anyLong()),
                () -> verifyNoMoreInteractions(repository),
                () -> verifyNoInteractions(converter, dtoConverter)
        );
    }

}