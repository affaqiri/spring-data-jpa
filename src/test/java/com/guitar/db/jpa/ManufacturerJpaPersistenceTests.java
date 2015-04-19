package com.guitar.db.jpa;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.guitar.db.model.Manufacturer;
import com.guitar.db.repository.jpa.ManufacturerJpaRepository;

@ContextConfiguration(locations={"classpath:com/guitar/db/applicationTests-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ManufacturerJpaPersistenceTests {
	
	@Autowired
	private ManufacturerJpaRepository manufacturerJpaRepository;

	@Test
	public void testGetManufacturersFoundedBeforeDate() throws Exception {
		List<Manufacturer> mans = manufacturerJpaRepository.findByFoundedDateBefore(new Date());
		assertEquals(2, mans.size());
	}
	
	@Test
	public void testTrueFalse() throws Exception {
		List<Manufacturer> mans = manufacturerJpaRepository.findByActiveTrue();
		assertEquals("Fender Musical Instruments Corporation", mans.get(0).getName());
		
		 mans = manufacturerJpaRepository.findByActiveFalse();
		 assertEquals("Gibson Guitar Corporation", mans.get(0).getName());
	}
	
//
//	@Test
//	public void testGetManufactureByName() throws Exception {
//		Manufacturer m = manufacturerJpaRepository.getManufacturerByName("Fender");
//		assertEquals("Fender Musical Instruments Corporation", m.getName());
//	}
//
//	@Test
//	public void testGetManufacturersThatSellModelsOfType() throws Exception {
//		List<Manufacturer> mans = manufacturerJpaRepository.getManufacturersThatSellModelsOfType("Semi-Hollow Body Electric");
//		assertEquals(1, mans.size());
//	}
	
}