package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.spring.controller.validator.BrandValidator;
import it.uniroma3.siw.spring.model.Brand;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.service.BrandService;
import it.uniroma3.siw.spring.service.CredentialsService;

@Controller
public class BrandController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private BrandValidator brandValidator;

	@GetMapping("/visualizzaListaBrand")
	public String getListaBrand(Model model) {
		model.addAttribute("brands",brandService.findAll());
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "/admin/listaBrandAdmin.html";
        }
		return "listaBrand.html";
	}
	
	@GetMapping("/admin/inseriscibrand")
	public String inserisciBrandForm(Model model) {
		Brand b = new Brand();
		b.setListaScarpe(new ArrayList<>());
		model.addAttribute("brand",b);
		return "/admin/addBrandForm.html";
	}
	
	@PostMapping("/addBrand")
	public String inserisciBrand(@Valid @ModelAttribute(value="brand")Brand brand, BindingResult bindingResult, Model model) {
		this.brandValidator.validate(brand, bindingResult);
		if(!bindingResult.hasErrors()) {
			brandService.add(brand);
			model.addAttribute("brand",brand);
			return "Brand.html";
		}
		return "/admin/addBrandForm.html";
	}
	
	@GetMapping("/brand/{id}")
	public String visualizzaBrand(@PathVariable("id")Long idBrand, Model model) {
		Brand brand = brandService.findById(idBrand);
		model.addAttribute("brand", brand);
		return "Brand.html";
	}
	
	@GetMapping("/admin/rimuovibrand/{id}")
	public String rimuoviBrand(@PathVariable("id")Long idBrand, Model model) {
		Brand b = brandService.findById(idBrand);
		brandService.remove(b);
		model.addAttribute("brands", brandService.findAll());
		return "/admin/listaBrandAdmin.html";
	}
}
