package ru.loginov.dev.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manufacturers")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "manufacturer_name", length = 50, nullable = false)
    private String manufacturerName;

    @Column(name = "manufacturer_description", nullable = false)
    private String manufacturerDescription;

    @Column(length = 50, nullable = false)
    private String country;

    @OneToMany(mappedBy = "manufacturer")
    private List<Product> products = new ArrayList<>();

    public Manufacturer() {}

    public Manufacturer(String manufacturerName, String manufacturerDescription, String country) {
        this.manufacturerName = manufacturerName;
        this.manufacturerDescription = manufacturerDescription;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerDescription() {
        return manufacturerDescription;
    }

    public void setManufacturerDescription(String manufacturerDescription) {
        this.manufacturerDescription = manufacturerDescription;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Product> getProducts() {
        return products;
    }
}
