package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Scarpa;
import it.uniroma3.siw.spring.repository.ScarpaRepository;

@Service
public class ScarpaService {
	
	@Autowired
	private ScarpaRepository scarpaRepository;

	public List<Scarpa>findAll(){
		List<Scarpa> listaScarpe = new ArrayList<>();
		for(Scarpa s : scarpaRepository.findAll()) {
			listaScarpe.add(s);
		}
		return listaScarpe;
	}
	
	public List<Scarpa> findScarpePerBrand(Long brand_id){
		List<Scarpa> listaBuffetPerChef = new ArrayList<Scarpa>();
		for(Scarpa s: scarpaRepository.findByBrand_id(brand_id)) {
			listaBuffetPerChef.add(s);
		}
		return listaBuffetPerChef;
	}
	
	public Scarpa findById(Long idScarpa) {
		return scarpaRepository.findById(idScarpa).get();
	}
	
	@Transactional
	public void add(Scarpa scarpa) {
		scarpaRepository.save(scarpa);
	}
	
	@Transactional
	public void remove(Scarpa scarpa) {
		scarpaRepository.delete(scarpa);
	}
	
	public boolean alreadyExist(String nome) {
		List<Scarpa> listaScarpe= this.findAll();
		for(Scarpa s : listaScarpe) {
			if(s.getNome().equals(nome)) {
				return true;
			}
		}
		return false;
	}
}
