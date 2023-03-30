package fr.ses10doigts.webApp2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Souhait {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long	id;
    @ManyToOne
    private Participant	participant;
    @OneToOne
    private Ceremonie	ceremonie;


    private boolean	actif = true;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public boolean isActif() {
	return actif;
    }

    public void setActif(boolean actif) {
	this.actif = actif;
    }


    public Participant getParticipant() {
	return participant;
    }

    public void setParticipant(Participant participant) {
	this.participant = participant;
    }

    public Ceremonie getCeremonie() {
	return ceremonie;
    }

    public void setCeremonie(Ceremonie ceremonie) {
	this.ceremonie = ceremonie;
    }



}
