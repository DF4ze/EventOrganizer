package fr.ses10doigts.webApp2.model.payload;

import java.util.List;
import java.util.Map;

public class ParticipationsTable {

    public String		nomParticipant;
    public long			idParticipant;
    public List<String>		nomCeremonies;
    public Map<String, Integer>	qte;
    public Map<String, Integer>	prix;
    public Map<String, Boolean>	fait;
    public Integer		total;

    public String getNomParticipant() {
	return nomParticipant;
    }

    public void setNomParticipant(String nomParticipant) {
	this.nomParticipant = nomParticipant;
    }

    public long getIdParticipant() {
	return idParticipant;
    }

    public void setIdParticipant(long idParticipant) {
	this.idParticipant = idParticipant;
    }

    public List<String> getNomCeremonies() {
	return nomCeremonies;
    }

    public void setNomCeremonies(List<String> nomCeremonies) {
	this.nomCeremonies = nomCeremonies;
    }

    public Map<String, Integer> getPrix() {
	return prix;
    }

    public void setPrix(Map<String, Integer> prix) {
	this.prix = prix;
    }

    public Map<String, Integer> getQte() {
	return qte;
    }

    public void setQte(Map<String, Integer> qte) {
	this.qte = qte;
    }

    public Map<String, Boolean> getFait() {
	return fait;
    }

    public void setFait(Map<String, Boolean> fait) {
	this.fait = fait;
    }

    public Integer getTotal() {
	return total;
    }

    public void setTotal(Integer total) {
	this.total = total;
    }
}
