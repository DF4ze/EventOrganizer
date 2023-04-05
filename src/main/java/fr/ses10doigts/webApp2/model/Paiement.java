package fr.ses10doigts.webApp2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long	 id;
    private int		 somme = 0;
    @OneToOne
    private Note	 note;
    private TypePaiement moyen = TypePaiement.LIQUIDE;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public int getSomme() {
	return somme;
    }

    public void setSomme(int somme) {
	this.somme = somme;
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

}
