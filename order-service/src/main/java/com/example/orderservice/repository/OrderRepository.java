package com.example.orderservice.repository;

import com.example.orderservice.model.OrderEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByOrderId(final String orderId);
    List<OrderEntity> findByUserId(final String userId);
}
