package com.yanacona.dtos;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentDtoTest {

    DepartmentDto dep = DepartmentDto.builder().build();

    @Test
    void testIdPositive(){
        Long ID = 1L;
        dep.setId(ID);
        assertEquals(ID, dep.getId());
    }

    @Test
    void testIdNegative(){
        Long ID = 1L;
        Long idFail = 99L;
        dep.setId(ID);
        assertNotEquals(idFail, dep.getId());
    }

    @Test
    void testNamePositive(){
        String NAME = "some name";
        dep.setName(NAME);
        assertEquals(NAME, dep.getName());
    }

    @Test
    void testNameNegative(){
        String NAME = "some name";
        String nameFail = "differentName";
        dep.setName(NAME);
        assertNotEquals(nameFail, dep.getName());
    }

    @Test
    void testDaneCodePositive(){
        String DANE_CODE = "001";
        dep.setDaneCode(DANE_CODE);
        assertEquals(DANE_CODE, dep.getDaneCode());
    }

    @Test
    void testDaneCodeNegative(){
        String DANE_CODE = "001";
        String DANE_CODE_F = "015";
        dep.setName(DANE_CODE);
        assertNotEquals(DANE_CODE_F, dep.getName());
    }

    @Test
    void testVersionPositive(){
        int VERSION = 1;
        dep.setVersion(VERSION);
        assertEquals(VERSION, dep.getVersion());
    }

    @Test
    void testVersionNegative(){
        int VERSION = 1;
        int VERSION_F = 2;
        dep.setVersion(VERSION);
        assertNotEquals(VERSION_F, dep.getVersion());
    }

    @Test
    void testModifiedAtPositive(){
        LocalDateTime MODIFIED = LocalDateTime.now();
        dep.setModifiedAt(MODIFIED);
        assertEquals(MODIFIED, dep.getModifiedAt());
    }

    @Test
    void testModifiedAtNegative(){
        LocalDateTime MODIFIED = LocalDateTime.now();
        LocalDateTime MODIFIED_F = LocalDateTime.now().plus(1, ChronoUnit.SECONDS);
        dep.setModifiedAt(MODIFIED);
        assertNotEquals(MODIFIED_F, dep.getModifiedAt());
    }

    @Test
    void testCreatedAtPositive(){
        LocalDateTime CREATED = LocalDateTime.now();
        dep.setCreatedAt(CREATED);
        assertEquals(CREATED, dep.getCreatedAt());
    }

    @Test
    void testCreatedAtNegative(){
        LocalDateTime CREATED = LocalDateTime.now();
        LocalDateTime CREATED_F = LocalDateTime.now().plus(1, ChronoUnit.SECONDS);
        dep.setCreatedAt(CREATED);
        assertNotEquals(CREATED_F, dep.getCreatedAt());
    }

}