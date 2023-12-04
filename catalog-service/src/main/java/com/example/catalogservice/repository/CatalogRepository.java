package com.example.catalogservice.repository;

import com.example.catalogservice.model.CatalogEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {

    List<CatalogEntity> findAll();
}
