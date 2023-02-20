package it.uniroma3.siw.spring.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Brand {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String nome;
	
	@Column(nullable = false)
	private String descrizione;
	
	@OneToMany(mappedBy = "brand", cascade = {CascadeType.REMOVE})
	private List<Scarpa> listaScarpe;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
	public List<Scarpa> getListaScarpe() {
		return listaScarpe;
	}
	public void setListaScarpe(List<Scarpa> listaScarpe) {
		this.listaScarpe = listaScarpe;
	}
}
