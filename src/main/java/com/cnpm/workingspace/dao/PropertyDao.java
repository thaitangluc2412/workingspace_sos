package com.cnpm.workingspace.dao;

import com.cnpm.workingspace.dto.PropertyDto;
import com.cnpm.workingspace.model.Property;

import java.util.List;

public interface PropertyDao {
    List<Property> getPropertyByCityTypeName(String city, int typeId, String name);
}
