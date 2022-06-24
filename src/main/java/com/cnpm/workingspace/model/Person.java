package com.cnpm.workingspace.model;

import javax.persistence.*;

@Entity
@Table(name = "Persons")
public class Person {
    @Id
    @Column(name = "PersonID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "LastName")
    String lastName;
    @Column(name = "FirstName")
    String firstName;
    @Column(name = "Address")
    String address;
    @Column(name = "City")
    String city;

    public Person() {
    }

    public Person(int id, String lastName, String firstName, String address, String city) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
