package fr.ses10doigts.webApp2.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ceremonie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long	  id;
    @Column(unique = true)
    private String	  nom;
    @Enumerated(EnumType.STRING)
    private TypeCeremonie type;
    private Integer	  prix;
    private Date	  jour;

    @Enumerated(EnumType.STRING)
    private Display	  display;
    private boolean	  actif	= true;


    public boolean isActif() {
	return actif;
    }

    public void setActif(boolean actif) {
	this.actif = actif;
    }




    public Display getDisplay() {
	return display;
    }

    public void setDisplay(Display display) {
	this.display = display;
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public TypeCeremonie getType() {
	return type;
    }

    public void setType(TypeCeremonie type) {
	this.type = type;
    }

    public Integer getPrix() {
	return prix;
    }

    public void setPrix(Integer prix) {
	this.prix = prix;
    }

    public Date getJour() {
	return jour;
    }

    public void setJour(Date jour) {
	this.jour = jour;
    }

    @Override
    public String toString() {
	return "Ceremonie [id=" + id + ", nom=" + nom + "]";
    }

}
