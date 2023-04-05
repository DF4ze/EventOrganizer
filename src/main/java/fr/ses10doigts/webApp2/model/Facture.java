package fr.ses10doigts.webApp2.model;

import java.util.HashSet;
import java.util.Set;

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
    private Set<Paiement>  paiements  = new HashSet<>();
    @OneToMany
    private Set<Reduction> reductions = new HashSet<>();
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

    public Set<Paiement> getPaiements() {
	return paiements;
    }

    public void setPaiements(Set<Paiement> paiements) {
	this.paiements = paiements;
    }

    public Set<Reduction> getReductions() {
	return reductions;
    }

    public void setReductions(Set<Reduction> reductions) {
	this.reductions = reductions;
    }

    public boolean isPaye() {
	return paye;
    }

    public void setPaye(boolean paye) {
	this.paye = paye;
    }

}
