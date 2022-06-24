package com.cnpm.workingspace.dao;

import com.cnpm.workingspace.dto.PropertyDto;
import com.cnpm.workingspace.model.Property;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class HibernatePropertyDao implements PropertyDao{

    private EntityManager entityManager;

    @Autowired
    HibernatePropertyDao(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Property> getPropertyByCityTypeName(String city, int typeId, String name) {
        boolean check = false;
        String GET_PROPERTY_BY_CITY_TYPE_NAME = "SELECT * FROM property";
        Session session = entityManager.unwrap(Session.class);

        if (!city.isEmpty()) {
            GET_PROPERTY_BY_CITY_TYPE_NAME += " WHERE city LIKE '%" + city + "%'";
            check = true;
        }
        if (typeId > 0){
            if (check){
                GET_PROPERTY_BY_CITY_TYPE_NAME += " AND property_type_id = " + typeId;
            } else {
                GET_PROPERTY_BY_CITY_TYPE_NAME += " WHERE property_type_id = " + typeId;
                check = true;
            }
        }
        if (!name.isEmpty()){
            if (check){
                GET_PROPERTY_BY_CITY_TYPE_NAME += " AND property_name LIKE '%" + name + "%'";
            } else {
                GET_PROPERTY_BY_CITY_TYPE_NAME += " WHERE property_name LIKE '%" + name + "%'";
            }
        }
        return session.createNativeQuery(GET_PROPERTY_BY_CITY_TYPE_NAME, Property.class).getResultList();
    }
}
