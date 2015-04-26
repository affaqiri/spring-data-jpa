package com.guitar.db.repository.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guitar.db.model.Model;

public interface ModelJpaRepository extends JpaRepository<Model, Long> {
	
	List<Model> findByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal lowest, BigDecimal highest);
	
	List<Model> findByModelTypeNameIn(List<String> types);
	
	/**
	 * Example of a JPQL not treated like other query DSL methods.
	 * 
	 * %:wood% like syntax is advanced jpql enhancement. In normal jpql we can
	 * not do this.
	 */
	@Query("select m from Model m where m.price >= :lowest and m.price <= :highest and m.woodType like %:wood%")
	List<Model> queryByPriceRangeAndWoodType(@Param("lowest") BigDecimal lowest,
											 @Param("highest") BigDecimal highestttttttt,
											 @Param("wood") String wood);
	
	/**
	 * The error is just STS related cause it treats it as Query DSL.
	 * 
	 * The namedQuery respects EntityName.findSomething so we can call directly
	 * findSomething
	 */
 	List<Model> findAllModelsByType(@Param("name") String type);
 	
	/**
	 * The following method is equivalent to the above method except it lets us
	 * to have any name we want for the method.
	 * 
	 * @param name
	 * @return
	 */
// 	@Query("Model.findAllModelsByType")
// 	List<Model> rechercherAllModelsByType(@Param("name") String type);
	
} 