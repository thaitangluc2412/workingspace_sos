package com.cnpm.workingspace.dao;

import com.cnpm.workingspace.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PriceDao {
    List<Price> getPriceOrder(String nameCol, String sort);
}
