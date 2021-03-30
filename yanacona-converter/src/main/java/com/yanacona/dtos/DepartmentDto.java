package com.yanacona.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DepartmentDto {

    private Long id;
    private String name;
    private String daneCode;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int version;

}
