package com.yanacona.repositories;

import com.yanacona.entities.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//        ( properties = {
//        "spring.datasource.driver-class-name=org.h2.Driver",
//        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
//        "spring.datasource.username=sa",
//        "spring.datasource.password=",
//        "spring.datasource.schema=schema.sql",
//        "spring.datasource.data=data.sql",
//        "spring.jpa.hibernate.ddl-auto=create-drop"
//})
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    Department dep1, dep2;

    @BeforeEach
    void setUp() {
        dep1 = new Department();
        dep1.setId(1L);
        dep1.setName("Dep1");
        dep1.setDaneCode("001");
        dep1.setVersion(1);
        dep1.setCreatedAt(LocalDateTime.now());
        dep1.setModifiedAt(LocalDateTime.now());

        dep2 = new Department();
        dep2.setId(2L);
        dep2.setName("Dep2");
        dep2.setDaneCode("002");
        dep2.setVersion(1);
        dep2.setCreatedAt(LocalDateTime.now());
        dep2.setModifiedAt(LocalDateTime.now());
        departmentRepository.saveAll(Set.of(dep1,dep2));

    }

    @Test
    void findAll(){
        List<Department> res = departmentRepository.findAll();
        assertAll( "find all test",
                () -> assertThat(res).isNotEmpty(),
                () -> assertThat(res).hasSize(2)
        );
    }

    @Test
    @Disabled
    void findByIdPositive(){
        Long ID = 1l;
        Optional<Department> opt = departmentRepository.findById(ID);

        opt.ifPresentOrElse(
                dep -> assertAll("find by name test",
                        () -> assertThat(dep).isNotNull(),
                        () -> assertThat(dep.getId()).isEqualTo(ID)
                ),
                () -> fail("Department not found!")
        );
    }

    @Test
    void findByIdNegative(){
        Long ID = 5l;
        Optional<Department> opt = departmentRepository.findById(ID);

        assertAll( "find by name test",
                () -> assertThat(opt).isNotPresent()
        );
    }

    @Test
    void findByNamePositive(){
        String NAME = "Dep1";
        Optional<Department> depOpt = departmentRepository.findByName(NAME);
        depOpt.ifPresentOrElse(
                dep -> assertAll( "find by name test",
                        () -> assertThat(dep).isNotNull(),
                        () -> assertThat(dep.getName()).isEqualTo(NAME)
                ),
                () -> fail("Department not found!")
        );
    }

    @Test
    void findByNameNegative(){
        String NAME = "NOT_FOUND";
        Optional<Department> depOpt = departmentRepository.findByName(NAME);

        assertAll( "find by name test",
                () -> assertThat(depOpt).isNotPresent()
        );
    }

    @Test
    void findByDaneCodePositive(){
        String DANE_CODE = "001";
        Optional<Department> depOpt = departmentRepository.findByDaneCode(DANE_CODE);
        depOpt.ifPresentOrElse(
                dep -> assertAll( "find by name test",
                        () -> assertThat(dep).isNotNull(),
                        () -> assertThat(dep.getDaneCode()).isEqualTo(DANE_CODE)
                ),
                () -> fail("Department not found!")
        );
    }

    @Test
    void findByDaneCodeNegative(){
        String DANE_CODE = "NOT_EXIST";
        Optional<Department> depOpt = departmentRepository.findByDaneCode(DANE_CODE);
        assertAll( "find by name test",
                () -> assertThat(depOpt).isNotPresent()
        );
    }

    @Test
    void savePositive(){
        String NAME = "Dep1";
        String DANE_CODE = "001";
        int VERSION = 1;
        Department department = new Department();
        department.setName(NAME);
        department.setDaneCode(DANE_CODE);
        department.setModifiedAt(LocalDateTime.now());
        department.setCreatedAt(LocalDateTime.now());
        department.setVersion(VERSION);

        Department saved = departmentRepository.save(department);
        List<Department> deps = departmentRepository.findAll();

        assertAll( "find by name test",
                () -> assertThat(saved).isNotNull(),
                () -> assertThat(saved.getName()).isEqualTo(NAME),
                () -> assertThat(saved.getDaneCode()).isEqualTo(DANE_CODE),
                () -> assertThat(saved.getVersion()).isEqualTo(VERSION),
                () -> assertThat(deps).isNotNull(),
                () -> assertThat(deps).isNotEmpty(),
                () -> assertThat(deps).hasSize(3)
        );
    }

    @Test
    void saveNegative(){
        Long ID = 1l;
        Department department = new Department();
        department.setId(ID);
        assertAll( "find by name test",
                () -> assertThrows(Exception.class, () -> departmentRepository.save(department))
        );
    }

    @Test
    @Disabled
    void update(){
        Long ID = 1l;
        Department find = departmentRepository.findById(ID).orElseThrow();

        String NAME = "Department1";
        String DANE_CODE = "999999";
        find.setName(NAME);
        find.setDaneCode(DANE_CODE);

        Department saved = departmentRepository.save(find);
        List<Department> deps = departmentRepository.findAll();

        assertAll( "find by name test",
                () -> assertThat(saved).isNotNull(),
                () -> assertThat(saved.getName()).isEqualTo(NAME),
                () -> assertThat(saved.getDaneCode()).isEqualTo(DANE_CODE),
                () -> assertThat(saved.getVersion()).isEqualTo(2),
                () -> assertThat(deps).isNotNull(),
                () -> assertThat(deps).isNotEmpty(),
                () -> assertThat(deps).hasSize(2)
        );
    }

    @Test
    void deletePositive(){
        Long ID = 1l;
        Department find = departmentRepository.findById(ID).orElseThrow();

        departmentRepository.delete(find);
        List<Department> deps = departmentRepository.findAll();
        Optional<Department> notFound = departmentRepository.findById(ID);

        notFound.ifPresentOrElse(
                dep -> fail("no debe encontrarlo, esta eliminado"),
                () -> assertAll(
                        () -> assertThat(notFound).isNotPresent(),
                        () -> assertThat(deps).isNotNull(),
                        () -> assertThat(deps).isNotEmpty(),
                        () -> assertThat(deps).hasSize(1)
                )
        );
    }

    @Test
    void deleteNegative(){
        Long ID = 9l;
        Department department = new Department();
        department.setId(ID);

        departmentRepository.delete(department);
        List<Department> deps = departmentRepository.findAll();
        assertAll("test",
                () -> assertThat(deps).isNotNull(),
                () -> assertThat(deps).isNotEmpty(),
                () -> assertThat(deps).hasSize(2)
            );

    }

    @Test
    @Disabled
    void deleteByIdPositive(){
        Long ID = 1l;
        departmentRepository.deleteById(ID);
        List<Department> deps = departmentRepository.findAll();
        Optional<Department> notFound = departmentRepository.findById(ID);
        notFound.ifPresentOrElse(
                dep -> fail("Department not found"),
                () -> assertAll( "find by name test",
                        () -> assertThat(notFound).isNotPresent(),
                        () -> assertThat(deps).isNotNull(),
                        () -> assertThat(deps).isNotEmpty(),
                        () -> assertThat(deps).hasSize(1)
                )
        );
    }

    @Test
    void deleteByIdNegative(){
        Long ID = 5l;
        assertAll( "find by name test",
                () -> assertThrows(Exception.class, () -> departmentRepository.deleteById(ID))
        );
    }

    @AfterEach
    void tearDown() {
    }

}