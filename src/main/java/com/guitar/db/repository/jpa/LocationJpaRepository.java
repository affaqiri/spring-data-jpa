package com.guitar.db.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guitar.db.model.Location;

public interface LocationJpaRepository extends JpaRepository<Location, Long> {

	List<Location> findByStateLike(String state);
	
	List<Location> findByStateStartingWith(String state);
	
	List<Location> findByStateStartingWithIgnoreCase(String state);
	
	Location findFirstByStateStartingWithIgnoreCase(String state);
	
	List<Location> findByStateNotLike(String state);
	
	List<Location> findByStateNotLikeOrderByStateAsc(String state);
	
	/**
	 * It is strange why i m forced to pass two parameters for an or condition.
	 * The same parameter must be used for the query.
	 */
	List<Location> findByStateOrCountry(String value, String value2);

	/**
	 * Same as findByStateOrCountry
	 */
	List<Location> findByStateIsOrCountryEquals(String value, String value2);
	
	List<Location> findByStateAndCountry(String state, String country);
	
	List<Location> findByStateNot(String state);

}