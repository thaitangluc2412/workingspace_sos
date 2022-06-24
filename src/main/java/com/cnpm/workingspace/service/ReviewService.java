package com.cnpm.workingspace.service;

import com.cnpm.workingspace.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> getAll();
    ReviewDto getById(int id);
    void insert(ReviewDto reviewDto);
    boolean update(ReviewDto reviewDto, int id);
    void delete(int id);
    List<ReviewDto> getByPropertyId(int propertyId);
}
