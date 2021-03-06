package com.guitar.db.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.guitar.db.model.Location;
import com.guitar.db.repository.jpa.LocationJpaRepository;

@ContextConfiguration(locations = { "classpath:com/guitar/db/applicationTests-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class LocationJpaPersistenceTests {
	
	@Autowired
	private LocationJpaRepository locationJpaRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void testJpaFind() {
		List<Location> locations = locationJpaRepository.findAll();
		assertNotNull(locations);
	}
	
	@Test
	public void testJpaAnd() {
		List<Location> locations = locationJpaRepository.findByStateAndCountry("Utah", "United States");
		assertNotNull(locations);
		
		assertEquals("Utah", locations.get(0).getState());
	}
	
	@Test
	public void testJpaOr() {
		List<Location> locations = locationJpaRepository.findByStateOrCountry("Utah", "Utah");
		assertNotNull(locations);
		
		assertEquals("Utah", locations.get(0).getState());
	}
	
	@Test
	public void testJpaOrWithIsEquals() {
		List<Location> locations = locationJpaRepository.findByStateIsOrCountryEquals("Utah", "Utah");
		assertNotNull(locations);
		
		assertEquals("Utah", locations.get(0).getState());
	}
	
	@Test
	public void testJpaNot() {
		List<Location> locations = locationJpaRepository.findByStateNot("Utah");
		assertNotNull(locations);
		
		assertNotSame("Utah", locations.get(0).getState());
	}
	
	@Test
	@Transactional
	public void testSaveAndGetAndDelete() throws Exception {
		Location location = new Location();
		location.setCountry("Canada");
		location.setState("British Columbia");
		location = locationJpaRepository.save(location);

		// clear the persistence context so we don't return the previously
		// cached location object
		// this is a test only thing and normally doesn't need to be done in
		// prod code
		entityManager.clear();

		Location otherLocation = locationJpaRepository.findOne(location.getId());
		assertEquals("Canada", otherLocation.getCountry());
		assertEquals("British Columbia", otherLocation.getState());

		// delete BC location now
		locationJpaRepository.delete(otherLocation);
	}

	@Test
	public void testFindWithLike() throws Exception {
		List<Location> locs = locationJpaRepository.findByStateLike("New%");
		assertEquals(4, locs.size());
		
		locs = locationJpaRepository.findByStateStartingWithIgnoreCase("NeW%");
		assertEquals(4, locs.size());
		
		/**
		 * No need for % at the end of parameter value
		 */
		locs = locationJpaRepository.findByStateStartingWith("New");
		assertEquals(4, locs.size());
		
		locs = locationJpaRepository.findByStateNotLike("New%");
		assertEquals(46, locs.size());
		
		locs = locationJpaRepository.findByStateNotLikeOrderByStateAsc("New%");
		locs.forEach((location) -> {
			System.out.println(location.getState());
		});
		
		Location loc = locationJpaRepository.findFirstByStateStartingWithIgnoreCase("a");
		assertEquals("Alabama", loc.getState());
	}

	@Test
	@Transactional
	// note this is needed because we will get a lazy load exception unless we
	// are in a tx
	public void testFindWithChildren() throws Exception {
		Location arizona = locationJpaRepository.findOne(3L);
		assertEquals("United States", arizona.getCountry());
		assertEquals("Arizona", arizona.getState());

		assertEquals(1, arizona.getManufacturers().size());

		assertEquals("Fender Musical Instruments Corporation", arizona.getManufacturers().get(0).getName());
	}
	
}