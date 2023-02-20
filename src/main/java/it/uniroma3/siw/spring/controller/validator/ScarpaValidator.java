package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Scarpa;
import it.uniroma3.siw.spring.service.BrandService;
import it.uniroma3.siw.spring.service.ScarpaService;

@Component
public class ScarpaValidator implements Validator{
	
	@Autowired
	private ScarpaService scarpaService;
	
	@Autowired
	private BrandService brandService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Scarpa.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.scarpaService.alreadyExist(((Scarpa)target).getNome())) {
			errors.reject("duplicate.scarpa");
		}
		
		if(((Scarpa)target).getNome().trim().isEmpty()) {
			errors.rejectValue("nome", "required");
		}
		else {
			if(((Scarpa)target).getNome().length()<2 || ((Scarpa)target).getNome().length()>20) {
				errors.rejectValue("nome", "size");
			}
		}
		
		
		if(((Scarpa)target).getDescrizione().trim().isEmpty()) {
			errors.rejectValue("descrizione", "required");
		}
		else {
			if(((Scarpa)target).getDescrizione().length()<2 || ((Scarpa)target).getDescrizione().length()>100) {
				errors.rejectValue("descrizione", "size");
			}
		}
		
		
		if(((Scarpa)target).getColore().trim().isEmpty()) {
			errors.rejectValue("colore", "required");
		}
		else {
			if(((Scarpa)target).getColore().length()<2 || ((Scarpa)target).getColore().length()>20) {
				errors.rejectValue("colore","size");
			}
		}
		
		
		
		if(((Scarpa)target).getTaglia() == null) {
			errors.rejectValue("taglia", "required");
		}
		else {
			if(((Scarpa)target).getTaglia()<35 || ((Scarpa)target).getTaglia()>45) {
				errors.rejectValue("taglia", "size");
			}
		}
		
		if(((Scarpa)target).getBrand().getNome().trim().isEmpty()) {
			errors.reject("required.scarpa.brand");
		}
		else {
			if(!this.brandService.alreadyExist(((Scarpa)target).getBrand().getNome())) {
				errors.rejectValue("brand","invalid");
			}
		}
		
	}

}
