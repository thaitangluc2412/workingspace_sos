package com.cnpm.workingspace.dao;

import com.cnpm.workingspace.model.Price;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class HibernatePriceDao implements PriceDao{

    private EntityManager entityManager;

    @Autowired
    HibernatePriceDao(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Price> getPriceOrder(String nameCol, String sort) {
        String GET_PRICE_ORDER_BY = "SELECT * FROM price ORDER BY " + nameCol + " " + sort;

        Session session = entityManager.unwrap(Session.class);
        return session.createNativeQuery(GET_PRICE_ORDER_BY, Price.class).getResultList();
    }
}
