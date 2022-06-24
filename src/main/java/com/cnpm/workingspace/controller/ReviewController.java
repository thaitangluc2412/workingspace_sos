package com.cnpm.workingspace.controller;

import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.dto.ReviewDto;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.cnpm.workingspace.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<?> getAll() {
        List<ReviewDto> reviewDtos = reviewService.getAll();
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, reviewDtos), HttpStatus.OK);
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        ReviewDto reviewDto = reviewService.getById(id);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, reviewDto), HttpStatus.OK);
    }

    @PostMapping("/reviews")
    public ResponseEntity<?> insert(@RequestBody ReviewDto reviewDto) {
        reviewService.insert(reviewDto);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<?> update(@RequestBody ReviewDto reviewDto, @PathVariable int id) {
        boolean isSuccess = reviewService.update(reviewDto, id);
        if (isSuccess) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        reviewService.delete(id);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
    }

    @GetMapping("/properties/{propertyId}/reviews")
    public ResponseEntity<?> getReviewsByPropertyId(@PathVariable int propertyId) {
        List<ReviewDto> reviewDtos = reviewService.getByPropertyId(propertyId);
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, reviewDtos), HttpStatus.OK);
    }
}
