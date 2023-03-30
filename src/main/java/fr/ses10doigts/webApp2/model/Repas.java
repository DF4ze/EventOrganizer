package fr.ses10doigts.webApp2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Repas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long      id;
    @Enumerated(EnumType.STRING)
    private TypeRepas type;
    private Integer   prix;


    private boolean   actif = true;

    public boolean isActif() {
	return actif;
    }

    public void setActif(boolean actif) {
	this.actif = actif;
    }

    public TypeRepas getType() {
	return type;
    }

    public void setType(TypeRepas type) {
	this.type = type;
    }

    public Integer getPrix() {
	return prix;
    }

    public void setPrix(Integer prix) {
	this.prix = prix;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }


}
