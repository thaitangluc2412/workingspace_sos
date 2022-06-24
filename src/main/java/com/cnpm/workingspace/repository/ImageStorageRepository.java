package com.cnpm.workingspace.repository;

import com.cnpm.workingspace.model.ImageStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageStorageRepository extends JpaRepository<ImageStorage, Integer> {
}