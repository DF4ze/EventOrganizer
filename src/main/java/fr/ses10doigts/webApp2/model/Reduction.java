package fr.ses10doigts.webApp2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Reduction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long   id;
    private String nom;
    private int	   valeur = 0;
    @OneToOne
    private Note   note;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public int getValeur() {
	return valeur;
    }

    public void setValeur(int valeur) {
	this.valeur = valeur;
    }

    public Note getNote() {
	return note;
    }

    public void setNote(Note note) {
	this.note = note;
    }

}
