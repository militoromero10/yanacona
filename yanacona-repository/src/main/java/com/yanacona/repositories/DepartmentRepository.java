package com.yanacona.repositories;

import com.yanacona.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("select d from Department d where d.name = :name")
    Optional<Department> findByName(@Param("name") String nm);

    @Query("select d from Department d where d.daneCode = :dane")
    Optional<Department> findByDaneCode(@Param("dane") String daneCode);

}
