package com.cnpm.workingspace.repository;

import com.cnpm.workingspace.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    List<Image> getByImageStorageId(int id);
}