package fr.ses10doigts.webApp2.model.payload;

import java.util.Map;
import java.util.Set;

import fr.ses10doigts.webApp2.model.Paiement;
import fr.ses10doigts.webApp2.model.Reduction;

public class FactureTable {

    public Long			idParticipant;
    public String		nomParticipant;

    public Set<String>		nomCeremonies;
    public Map<String, Integer>	qtes;
    public Map<String, Integer>	prix;
    public Integer		total;
    public Integer		totalPaiement;
    public Integer		totalReduction;

    public Set<Paiement>	paiements;
    public Set<Reduction>	reductions;
    public Boolean		paye;

    public Long getIdParticipant() {
	return idParticipant;
    }

    public void setIdParticipant(Long idParticipant) {
	this.idParticipant = idParticipant;
    }

    public Map<String, Integer> getQtes() {
	return qtes;
    }

    public void setQtes(Map<String, Integer> qtes) {
	this.qtes = qtes;
    }

    public Integer getTotal() {
	return total;
    }

    public Integer getTotalPaiement() {
	return totalPaiement;
    }

    public void setTotalPaiement(Integer totalPaiement) {
	this.totalPaiement = totalPaiement;
    }

    public Integer getTotalReduction() {
	return totalReduction;
    }

    public void setTotalReduction(Integer totalReduction) {
	this.totalReduction = totalReduction;
    }

    public void setTotal(Integer total) {
	this.total = total;
    }

    public String getNomParticipant() {
	return nomParticipant;
    }

    public void setNomParticipant(String nomParticipant) {
	this.nomParticipant = nomParticipant;
    }

    public Set<String> getNomCeremonies() {
	return nomCeremonies;
    }

    public void setNomCeremonies(Set<String> nomCeremonies) {
	this.nomCeremonies = nomCeremonies;
    }

    public Map<String, Integer> getPrix() {
	return prix;
    }

    public void setPrix(Map<String, Integer> prix) {
	this.prix = prix;
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

    public Boolean getPaye() {
	return paye;
    }

    public void setPaye(Boolean paye) {
	this.paye = paye;
    }

}
