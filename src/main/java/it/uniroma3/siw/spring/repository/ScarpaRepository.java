package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Scarpa;

public interface ScarpaRepository extends CrudRepository<Scarpa, Long>{
	
	public List<Scarpa> findByBrand_id(Long brand_id);

}
