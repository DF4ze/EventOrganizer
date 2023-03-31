package fr.ses10doigts.webApp2.model.payload;

public class ParticipantPayload {

    public String prenom;
    public String nom;
    public String tel;
    public String urgence;
    public String email;

    public String getPrenom() {
	return prenom;
    }

    public void setPrenom(String prenom) {
	this.prenom = prenom;
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
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

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

}
