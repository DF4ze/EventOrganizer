package fr.ses10doigts.webApp2.model.table;

import java.util.List;
import java.util.Map;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Paiement;
import fr.ses10doigts.webApp2.model.Reduction;

public class FactureTable {

    public Long			idParticipant;
    public String		nomParticipant;

    public List<Ceremonie>	nomCeremonies;
    public Map<String, Integer>	qtes;
    public Map<String, Integer>	prix;
    public Integer		total;
    public Integer		totalPaiement;
    public Integer		totalReduction;

    public List<Paiement>	paiements;
    public List<Reduction>	reductions;
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

    public List<Ceremonie> getNomCeremonies() {
	return nomCeremonies;
    }

    public void setNomCeremonies(List<Ceremonie> nomCeremonies) {
	this.nomCeremonies = nomCeremonies;
    }

    public Map<String, Integer> getPrix() {
	return prix;
    }

    public void setPrix(Map<String, Integer> prix) {
	this.prix = prix;
    }

    public List<Paiement> getPaiements() {
	return paiements;
    }

    public void setPaiements(List<Paiement> paiements) {
	this.paiements = paiements;
    }

    public List<Reduction> getReductions() {
	return reductions;
    }

    public void setReductions(List<Reduction> reductions) {
	this.reductions = reductions;
    }

    public Boolean getPaye() {
	return paye;
    }

    public void setPaye(Boolean paye) {
	this.paye = paye;
    }

}
