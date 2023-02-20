package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Brand;
import it.uniroma3.siw.spring.service.BrandService;

@Component
public class BrandValidator implements Validator{
	
	@Autowired
	public BrandService brandService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Brand.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		if(brandService.alreadyExist(((Brand)target).getNome())) {
			errors.reject("duplicate.brand");
		}
		
		if(((Brand)target).getNome().trim().isEmpty()) {
			errors.rejectValue("nome", "required");
		}
		else {
			if(((Brand)target).getNome().length()<2 || ((Brand)target).getNome().length()>20) {
				errors.rejectValue("nome", "size");
			}
		}
		
		if(((Brand)target).getDescrizione().trim().isEmpty()) {
			errors.rejectValue("descrizione", "required");
		}
		else {
			if(((Brand)target).getDescrizione().length()<2 || ((Brand)target).getDescrizione().length()>20) {
				errors.rejectValue("descrizione", "size");
			}
		}
	}

}
