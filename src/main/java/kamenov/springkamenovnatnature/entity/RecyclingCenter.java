package kamenov.springkamenovnatnature.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class RecyclingCenter extends BaseEntity{
    @Column(name = "name",nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String address;

    public RecyclingCenter() {
    }

    public String getName() {
        return name;
    }

    public RecyclingCenter setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity() {
        return city;
    }

    public RecyclingCenter setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public RecyclingCenter setAddress(String address) {
        this.address = address;
        return this;
    }
}
