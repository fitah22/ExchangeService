package com.s305089.software.trade.dao;

import com.s305089.software.trade.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends CrudRepository<Order, Integer> {
    List<Order> findByActiveTrue();
}
