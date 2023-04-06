package fr.ses10doigts.webApp2.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long	 id;
    private int		 valeur	= 0;
    @OneToOne(cascade = CascadeType.ALL)
    private Note	 note;
    private TypePaiement moyen = TypePaiement.LIQUIDE;
    @ManyToOne
    private Facture	 facture;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
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

    public TypePaiement getMoyen() {
	return moyen;
    }

    public void setMoyen(TypePaiement moyen) {
	this.moyen = moyen;
    }

    public Facture getFacture() {
	return facture;
    }

    public void setFacture(Facture facture) {
	this.facture = facture;
    }



}
