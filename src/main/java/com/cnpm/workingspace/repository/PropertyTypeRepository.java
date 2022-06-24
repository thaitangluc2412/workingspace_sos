package com.cnpm.workingspace.repository;

import com.cnpm.workingspace.model.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyTypeRepository extends JpaRepository<PropertyType,Integer> {
}
