package com.cnpm.workingspace.repository;

import com.cnpm.workingspace.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    final String GET_PROPERTY_BY_CITY = "SELECT * FROM property WHERE city LIKE %:city%";

    @Query(value = GET_PROPERTY_BY_CITY, nativeQuery = true)
    List<Property> getPropertyByCity(String city);

    List<Property> getByCustomerCustomerId(Integer customerId);

    @Modifying
    @Query(value = "update Property p set p.roomQuantity = p.roomQuantity + 1 where p.propertyId =:propertyId")
    void increaseRoomQuantity(int propertyId);

    @Modifying
    @Query(value = "UPDATE property\n" +
                   "SET rating = (SELECT avg(r.rating)\n" +
                   "              FROM (SELECT rating\n" +
                   "                    FROM review\n" +
                   "                    WHERE property_id = :property_id) r)\n" +
                   "WHERE property_id = :property_id", nativeQuery = true)
    void updateRating(@Param("property_id") int propertyId);

    @Query(value = "SELECT count(property_id) as total FROM property", nativeQuery = true)
    int countTotalProperty();
}