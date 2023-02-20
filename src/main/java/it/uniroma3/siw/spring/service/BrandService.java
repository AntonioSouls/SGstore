package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Brand;
import it.uniroma3.siw.spring.repository.BrandRepository;

@Service
public class BrandService {
	
	@Autowired
	private BrandRepository brandRepository;
	
	public List<Brand>findAll(){
		List<Brand> listaBrand = new ArrayList<>();
		for(Brand b : brandRepository.findAll()) {
			listaBrand.add(b);
		}
		return listaBrand;
	}
	
	public Brand findById(Long idBrand) {
		Brand brand = brandRepository.findById(idBrand).get();
		return brand;
	}
	
	public Brand findByNome(String nomeBrand) {
		Brand brand = brandRepository.findByNome(nomeBrand).get();
		return brand;
	}
	
	@Transactional
	public void add(Brand brand) {
		brandRepository.save(brand);
	}
	
	@Transactional
	public void remove(Brand brand) {
		brandRepository.delete(brand);
	}
	
	public List<Brand> otherBrands (Long idBrand){
		List<Brand> listaBrand = this.findAll();
		List<Brand> otherBrands = new ArrayList<>();
		for(Brand b : listaBrand) {
			if(b.getId() != idBrand) {
				otherBrands.add(b);
			}
		}
		return otherBrands;
	}
	
	public boolean alreadyExist(String nome) {
		List<Brand> listaBrand= this.findAll();
		for(Brand b : listaBrand) {
			if(b.getNome().equals(nome)) {
				return true;
			}
		}
		return false;
	}

}
