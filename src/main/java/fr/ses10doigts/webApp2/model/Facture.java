package fr.ses10doigts.webApp2.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long	   id;
    @OneToOne
    private Participant	   participant;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Paiement> paiements;
    @OneToMany
    private List<Reduction> reductions;
    private boolean	   paye	= false;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Participant getParticipant() {
	return participant;
    }

    public void setParticipant(Participant participant) {
	this.participant = participant;
    }

    public List<Paiement> getPaiements() {
	return paiements;
    }

    public void setPaiements(List<Paiement> paiements) {
	this.paiements = paiements;
    }

    public List<Reduction> getReductions() {
	return reductions;
    }

    public void setReductions(List<Reduction> reductions) {
	this.reductions = reductions;
    }

    public boolean isPaye() {
	return paye;
    }

    public void setPaye(boolean paye) {
	this.paye = paye;
    }

}
