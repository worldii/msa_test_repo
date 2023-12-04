package com.example.catalogservice.controller;


import static org.springframework.http.HttpStatus.OK;

import com.example.catalogservice.model.CatalogEntity;
import com.example.catalogservice.service.CatalogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog-service")
public class CatalogController {

    private final CatalogService catalogService;
    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Catalog Service";
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<CatalogEntity>> getAllCatalogs() {
        final List<CatalogEntity> catalogs = catalogService.getAllCatalogs();

        return new ResponseEntity<>(catalogs, OK);
    }

}
