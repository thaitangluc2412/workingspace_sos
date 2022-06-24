package com.cnpm.workingspace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.ConstructorParameters;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageDto {
    private Integer id;
    private String url;
    private String thumbnail;
}
