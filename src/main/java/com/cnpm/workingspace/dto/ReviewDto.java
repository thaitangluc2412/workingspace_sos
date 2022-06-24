package com.cnpm.workingspace.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDto {
    private Integer reviewId;
    private Integer customerId;
    private Integer propertyId;
    private String rating;
    private String content;
    private LocalDateTime createDate;
}
