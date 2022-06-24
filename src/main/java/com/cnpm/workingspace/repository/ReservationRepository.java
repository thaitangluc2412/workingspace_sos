package com.cnpm.workingspace.repository;

import com.cnpm.workingspace.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> getByCustomerCustomerId(int customerId);

    @Query(value = "select IFNULL(sum(total), 0) as total\n" +
            "from reservation \n" +
            "where reservation_status_id = 2;", nativeQuery = true)
    double countTotalProfit();
}
