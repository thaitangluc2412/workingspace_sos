package com.cnpm.workingspace.service;

import com.cnpm.workingspace.dto.ReviewDto;
import com.cnpm.workingspace.model.Customer;
import com.cnpm.workingspace.model.Property;
import com.cnpm.workingspace.model.Review;
import com.cnpm.workingspace.repository.CustomerRepository;
import com.cnpm.workingspace.repository.PropertyRepository;
import com.cnpm.workingspace.repository.ReviewRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PropertyRepository propertyRepository;


    ModelMapper toEntityMapper;
    ModelMapper toDtoMapper;

    public ReviewServiceImpl(ModelMapper toEntityMapper, ModelMapper toDtoMapper) {
        Converter<Integer, Customer> customerConverter = context -> customerRepository.getById(context.getSource());
        Converter<Integer, Property> propertyConverter = context -> propertyRepository.getById(context.getSource());
        this.toEntityMapper = toEntityMapper;
        this.toEntityMapper.createTypeMap(ReviewDto.class, Review.class)
                           .addMappings(mapper -> mapper.using(customerConverter).map(ReviewDto::getCustomerId, Review::setCustomer))
                           .addMappings(mapper -> mapper.using(propertyConverter).map(ReviewDto::getPropertyId, Review::setProperty));

        this.toDtoMapper = toDtoMapper;
        this.toDtoMapper.createTypeMap(Review.class, ReviewDto.class)
                        .addMappings(mapper -> mapper.map(review -> review.getCustomer().getCustomerId(), ReviewDto::setCustomerId))
                        .addMappings(mapper -> mapper.map(review -> review.getProperty().getPropertyId(), ReviewDto::setPropertyId));
    }

    @Override
    public List<ReviewDto> getAll() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(review -> toDtoMapper.map(review, ReviewDto.class)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getById(int id) {
        Review review = reviewRepository.getById(id);
        return toDtoMapper.map(review, ReviewDto.class);
    }

    @Transactional
    @Override
    public void insert(ReviewDto reviewDto) {
        Review review = toEntityMapper.map(reviewDto, Review.class);
        reviewRepository.save(review);
        propertyRepository.updateRating(review.getProperty().getPropertyId());
    }

    @Override
    public boolean update(ReviewDto reviewDto, int id) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            reviewRepository.save(review.get());
            return true;
        }
        return false;
    }

    @Override
    public void delete(int id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<ReviewDto> getByPropertyId(int propertyId) {
        List<Review> reviews = reviewRepository.getByPropertyPropertyId(propertyId);
        return reviews.stream()
                      .map(review -> toDtoMapper.map(review, ReviewDto.class))
                      .collect(Collectors.toList());
    }
}
