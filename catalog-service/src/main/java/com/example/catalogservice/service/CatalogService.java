package com.example.catalogservice.service;

import com.example.catalogservice.model.CatalogEntity;
import com.example.catalogservice.repository.CatalogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;
    public List<CatalogEntity> getAllCatalogs() {
        List<CatalogEntity> catalogs = catalogRepository.findAll();
        return catalogs;
    }
}
