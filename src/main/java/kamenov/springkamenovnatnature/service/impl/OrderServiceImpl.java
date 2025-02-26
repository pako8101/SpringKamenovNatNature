package kamenov.springkamenovnatnature.service.impl;

import kamenov.springkamenovnatnature.entity.Order;
import kamenov.springkamenovnatnature.repositories.OrderRepository;
import kamenov.springkamenovnatnature.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }
@Override
    public Order createOrder(Order order) {
        return repository.save(order);
    }
}
