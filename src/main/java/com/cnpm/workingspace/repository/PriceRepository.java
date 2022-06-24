package com.cnpm.workingspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cnpm.workingspace.model.Price;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price,Integer> {
}
