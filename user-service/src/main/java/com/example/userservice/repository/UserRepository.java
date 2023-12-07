package com.example.userservice.repository;

import com.example.userservice.domain.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserId(String userId);

    List<UserEntity> findAll();

    Optional<UserEntity> findByEmail(String username);
}
