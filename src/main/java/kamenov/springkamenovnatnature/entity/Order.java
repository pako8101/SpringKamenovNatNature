package kamenov.springkamenovnatnature.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    @Column(name = "customer_name",nullable = false,unique = true)
    private String customerName;
    @Column(name = "customer_address",nullable = false,unique = true)
    private String customerAddress;
    @OneToMany
    private List<Product> products;

    public Order() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public Order setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public Order setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Order setProducts(List<Product> products) {
        this.products = products;
        return this;
    }
}
