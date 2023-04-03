package fr.ses10doigts.webApp2.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long	id;
    private String	nom;
    private String	prenom;
    private String	email;
    private String	telephone;
    private String	urgence;

    @Column(length = 50000)
    private String	intention;
    @Column(length = 50000)
    private String	experience;
    @Column(length = 50000)
    private String	sante;
    @Column(length = 50000)
    private String	defi;
    @Column(length = 50000)
    private String	remarques;
    private String	prenoms;
    private Date	naissance;

    @OneToOne
    private Participant	participant;

    private boolean	actif = true;

    public boolean isActif() {
	return actif;
    }

    public void setActif(boolean actif) {
	this.actif = actif;
    }

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

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public String getPrenom() {
	return prenom;
    }

    public void setPrenom(String prenom) {
	this.prenom = prenom;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getTelephone() {
	return telephone;
    }

    public void setTelephone(String telephone) {
	this.telephone = telephone;
    }

    public String getUrgence() {
	return urgence;
    }

    public void setUrgence(String urgence) {
	this.urgence = urgence;
    }

    public String getIntention() {
	return intention;
    }

    public void setIntention(String intention) {
	this.intention = intention;
    }

    public String getExperience() {
	return experience;
    }

    public void setExperience(String experience) {
	this.experience = experience;
    }

    public String getSante() {
	return sante;
    }

    public void setSante(String sante) {
	this.sante = sante;
    }

    public String getDefi() {
	return defi;
    }

    public void setDefi(String defi) {
	this.defi = defi;
    }

    public String getRemarques() {
	return remarques;
    }

    public void setRemarques(String remarques) {
	this.remarques = remarques;
    }

    public String getPrenoms() {
	return prenoms;
    }

    public void setPrenoms(String prenoms) {
	this.prenoms = prenoms;
    }

    public Date getNaissance() {
	return naissance;
    }

    public void setNaissance(Date naissance) {
	this.naissance = naissance;
    }

}
