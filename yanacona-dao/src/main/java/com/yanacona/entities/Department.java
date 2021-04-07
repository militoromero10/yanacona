package com.yanacona.entities;

import com.yanacona.util.DateProcessor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Size(max = 250)
    private String name;

    @NotNull
    @Size(max = 4)
    @Column(name = "dane_code")
    private String daneCode;

    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
    private LocalDateTime modifiedAt;

    @Version
    private int version;

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", daneCode='" + daneCode + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if( o == null || getClass()!=o.getClass())
            return false;
        Department department = (Department) o;
        return Objects.equals(id, department.id)  &&
                Objects.equals(name, department.getName()) &&
                Objects.equals(daneCode, department.getDaneCode()) &&
                Objects.equals(modifiedAt, department.getModifiedAt()) &&
                Objects.equals(createdAt, department.getCreatedAt()) &&
                Objects.equals(version, department.getVersion());

    }
}
