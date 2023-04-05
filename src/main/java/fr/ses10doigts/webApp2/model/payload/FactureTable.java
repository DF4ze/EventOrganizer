package fr.ses10doigts.webApp2.model.payload;

import java.util.Map;

import fr.ses10doigts.webApp2.model.Paiement;
import fr.ses10doigts.webApp2.model.Reduction;

public class FactureTable {

    public Long			  idParticipant;
    public String		  nomParticipant;
    public Map<String, Paiement>  paiements;
    public Map<String, Reduction> reductions;
    public Boolean		  paye;

    public Long getIdParticipant() {
	return idParticipant;
    }

    public void setIdParticipant(Long idParticipant) {
	this.idParticipant = idParticipant;
    }

    public String getNomParticipant() {
	return nomParticipant;
    }

    public void setNomParticipant(String nomParticipant) {
	this.nomParticipant = nomParticipant;
    }

    public Map<String, Paiement> getPaiements() {
	return paiements;
    }

    public void setPaiements(Map<String, Paiement> paiements) {
	this.paiements = paiements;
    }

    public Map<String, Reduction> getReductions() {
	return reductions;
    }

    public void setReductions(Map<String, Reduction> reductions) {
	this.reductions = reductions;
    }

    public Boolean getPaye() {
	return paye;
    }

    public void setPaye(Boolean paye) {
	this.paye = paye;
    }

}
