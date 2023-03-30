package fr.ses10doigts.webApp2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long	id;

    private Integer	prix;
    @ManyToOne
    private Participant	participant;
    @OneToOne
    private Souhait	souhait;
    @OneToOne
    private Ceremonie	ceremonie;

    private boolean	actif = true;


    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Integer getPrix() {
	return prix;
    }

    public void setPrix(Integer prix) {
	this.prix = prix;
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

    public Souhait getSouhait() {
	return souhait;
    }

    public void setSouhait(Souhait souhait) {
	this.souhait = souhait;
    }

    public Ceremonie getCeremonie() {
	return ceremonie;
    }

    public void setCeremonie(Ceremonie ceremonie) {
	this.ceremonie = ceremonie;
    }




}
