package com.cihanozmen.permissionmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleErrorDetailsDto {

    private int status;
    private String message;
    private String path;

}
