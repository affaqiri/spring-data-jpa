package com.guitar.db.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guitar.db.model.Manufacturer;

public interface ManufacturerJpaRepository extends JpaRepository<Manufacturer, Long>{
		
}