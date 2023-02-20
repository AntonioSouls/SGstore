package it.uniroma3.siw.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Brand;

public interface BrandRepository extends CrudRepository<Brand, Long>{
	
	public Optional<Brand> findByNome (String nome);

}
