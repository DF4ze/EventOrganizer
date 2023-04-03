package fr.ses10doigts.webApp2.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

/**
 * @author df4ze
 *
 */
@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long		id;
    private String		nom;
    private String		prenom;
    private String		email;
    private String		tel;
    private String		urgence;
    private String		prenoms;
    private Date		naissance;

    @OneToOne(cascade = CascadeType.ALL)
    private Questionnaire	questionnaire;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Note>		notes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Souhait>	souhaits;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Participation>	participations;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Repas>		repas;

    private boolean		actif = true;

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

    public String getTel() {
	return tel;
    }

    public void setTel(String tel) {
	this.tel = tel;
    }

    public String getUrgence() {
	return urgence;
    }

    public void setUrgence(String urgence) {
	this.urgence = urgence;
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

    public Questionnaire getQuestionnaire() {
	return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
	this.questionnaire = questionnaire;
    }

    public List<Note> getNotes() {
	return notes;
    }

    public void setNotes(List<Note> notes) {
	this.notes = notes;
    }

    public List<Souhait> getSouhaits() {
	return souhaits;
    }

    public void setSouhaits(List<Souhait> souhaits) {
	this.souhaits = souhaits;
    }

    public List<Participation> getParticipations() {
	return participations;
    }

    public void setParticipations(List<Participation> participations) {
	this.participations = participations;
    }

    public List<Repas> getRepas() {
	return repas;
    }

    public void setRepas(List<Repas> repas) {
	this.repas = repas;
    }

}
