package fr.ses10doigts.webApp2.model.payload;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class QuestionnairePayload {

    public String nom;
    public String prenom;
    public String email;
    public String telephone;
    public String urgence;

    public String intention;
    public String experience;
    public String sante;
    public String defi;

    public String temazcalMardi;

    public String tambour;
    public String mineraux;
    public String rome;
    public String chant;

    public String prepaIntention;
    public String grandMereJeudi;

    public String princesse;
    public String grandMereVendredi;

    public String ninos;

    public String hommage;
    public String temazcalCloture;

    public String grenouille1;
    public String grenouille2;
    public String grenouille3;

    public String remarques;

    public String prenoms;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date	  naissance;


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

    public String getTemazcalMardi() {
	return temazcalMardi;
    }

    public void setTemazcalMardi(String temazcalMardi) {
	this.temazcalMardi = temazcalMardi;
    }

    public String getTambour() {
	return tambour;
    }

    public void setTambour(String tambour) {
	this.tambour = tambour;
    }

    public String getMineraux() {
	return mineraux;
    }

    public void setMineraux(String mineraux) {
	this.mineraux = mineraux;
    }

    public String getRome() {
	return rome;
    }

    public void setRome(String rome) {
	this.rome = rome;
    }

    public String getChant() {
	return chant;
    }

    public void setChant(String chant) {
	this.chant = chant;
    }

    public String getPrepaIntention() {
	return prepaIntention;
    }

    public void setPrepaIntention(String prepaIntention) {
	this.prepaIntention = prepaIntention;
    }

    public String getGrandMereJeudi() {
	return grandMereJeudi;
    }

    public void setGrandMereJeudi(String grandMereJeudi) {
	this.grandMereJeudi = grandMereJeudi;
    }

    public String getPrincesse() {
	return princesse;
    }

    public void setPrincesse(String princesse) {
	this.princesse = princesse;
    }

    public String getGrandMereVendredi() {
	return grandMereVendredi;
    }

    public void setGrandMereVendredi(String grandMereVendredi) {
	this.grandMereVendredi = grandMereVendredi;
    }

    public String getNinos() {
	return ninos;
    }

    public void setNinos(String ninos) {
	this.ninos = ninos;
    }

    public String getHommage() {
	return hommage;
    }

    public void setHommage(String hommage) {
	this.hommage = hommage;
    }

    public String getTemazcalCloture() {
	return temazcalCloture;
    }

    public void setTemazcalCloture(String temazcalCloture) {
	this.temazcalCloture = temazcalCloture;
    }

    public String getGrenouille1() {
	return grenouille1;
    }

    public void setGrenouille1(String grenouille1) {
	this.grenouille1 = grenouille1;
    }

    public String getGrenouille2() {
	return grenouille2;
    }

    public void setGrenouille2(String grenouille2) {
	this.grenouille2 = grenouille2;
    }

    public String getGrenouille3() {
	return grenouille3;
    }

    public void setGrenouille3(String grenouille3) {
	this.grenouille3 = grenouille3;
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

    @Override
    public String toString() {
	return "QuestionnairePayload [nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", telephone="
		+ telephone + ", urgence=" + urgence + ", intention=" + intention + ", experience=" + experience
		+ ", sante=" + sante + ", defi=" + defi + ", temazcalMardi=" + temazcalMardi + ", tambour=" + tambour
		+ ", mineraux=" + mineraux + ", rome=" + rome + ", chant=" + chant + ", prepaIntention="
		+ prepaIntention + ", grandMereJeudi=" + grandMereJeudi + ", princesse=" + princesse
		+ ", grandMereVendredi=" + grandMereVendredi + ", ninos=" + ninos + ", hommage=" + hommage
		+ ", temazcalCloture=" + temazcalCloture + ", grenouille1=" + grenouille1 + ", grenouille2="
		+ grenouille2 + ", grenouille3=" + grenouille3 + ", remarques=" + remarques + "]";
    }


}
