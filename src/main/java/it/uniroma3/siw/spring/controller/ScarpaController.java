package it.uniroma3.siw.spring.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.spring.controller.validator.ScarpaValidator;
import it.uniroma3.siw.spring.model.Brand;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Scarpa;
import it.uniroma3.siw.spring.service.BrandService;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.ScarpaService;

@Controller
public class ScarpaController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private ScarpaService scarpaService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ScarpaValidator scarpaValidator;

	@GetMapping("/visualizzaListaScarpe")
	public String getListaScarpe(Model model) {
		model.addAttribute("scarpe",scarpaService.findAll());
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "/admin/listaScarpeAdmin.html";
        }
		return "listaScarpe.html";
	}
	
	@GetMapping("/scarpeDelBrand/{id}")
	public String visualizzaBuffetPerChef(@PathVariable("id")Long idBrand,Model model) {
		Brand brand = brandService.findById(idBrand);
		model.addAttribute("scarpe",scarpaService.findScarpePerBrand(idBrand));
		model.addAttribute("brand",brand);
		return "listaScarpe.html";
	}
	
	@GetMapping("/admin/inserisciscarpa")
	public String inserisciScarpaForm(Model model) {
		Scarpa s = new Scarpa();
		s.setBrand(new Brand());
		model.addAttribute("scarpa",s);
		return "admin/addScarpaForm.html";
	}
	
	@PostMapping("/addScarpa")
	public String inserisciBrand(@Valid @ModelAttribute(value="scarpa")Scarpa scarpa,BindingResult bindingResult, Model model) {
		this.scarpaValidator.validate(scarpa, bindingResult);
		if(!bindingResult.hasErrors()) {
			Brand b = brandService.findByNome(scarpa.getBrand().getNome());
			scarpa.setBrand(b);
			scarpaService.add(scarpa);
			model.addAttribute("scarpa",scarpa);
			return "scarpa.html";
		}
		return "admin/addScarpaForm.html";
	}
	
	@GetMapping("/scarpa/{id}")
	public String visualizzaScarpa(@PathVariable("id") Long idScarpa, Model model) {
		Scarpa scarpa = scarpaService.findById(idScarpa);
		model.addAttribute("scarpa", scarpa);
		return "scarpa.html";
	}
	
	@GetMapping("/admin/rimuoviscarpa/{id}")
	public String rimuoviScarpa(@PathVariable("id") Long idScarpa, Model model) {
		Scarpa scarpa = scarpaService.findById(idScarpa);
		scarpaService.remove(scarpa);
		model.addAttribute("scarpe",scarpaService.findAll());
		return "/admin/listaScarpeAdmin.html";
	}
	
}
