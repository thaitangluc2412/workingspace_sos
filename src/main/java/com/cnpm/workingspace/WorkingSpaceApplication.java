package com.cnpm.workingspace;

import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.cnpm.workingspace.configuration.CloudinaryConfig;
import com.cnpm.workingspace.dao.ReservationDao;
import com.cnpm.workingspace.dto.ReservationDto;
import com.cnpm.workingspace.service.ReservationService;
import com.cnpm.workingspace.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;


@SpringBootApplication
public class WorkingSpaceApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(WorkingSpaceApplication.class, args);
    }

    @Autowired
    ReservationDao reservationDao;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("list : ");
        List<Integer> a=reservationDao.getReservationAmountPerMonth();
        for(int x:a){
            System.out.print(x+" ");
        }
    }
}
