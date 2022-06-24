package com.cnpm.workingspace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceDto {
    private Integer serviceId;
    private String serviceName;
    private String icon;
}
