package fr.ses10doigts.webApp2.model.table;

public class ParticipationsByCeremonieTable {

    public String  nomCeremonie;
    public Integer quantite;
    public Integer nbParticipants;
    public Integer nbParticipantsConfirmes;
    public Integer total;
    public Integer totalConfirmes;

    public String getNomCeremonie() {
	return nomCeremonie;
    }

    public void setNomCeremonie(String nomCeremonie) {
	this.nomCeremonie = nomCeremonie;
    }

    public Integer getQuantite() {
	return quantite;
    }

    public void setQuantite(Integer quantite) {
	this.quantite = quantite;
    }

    public Integer getNbParticipants() {
	return nbParticipants;
    }

    public void setNbParticipants(Integer nbParticipants) {
	this.nbParticipants = nbParticipants;
    }

    public Integer getNbParticipantsConfirmes() {
	return nbParticipantsConfirmes;
    }

    public void setNbParticipantsConfirmes(Integer nbParticipantsConfirmes) {
	this.nbParticipantsConfirmes = nbParticipantsConfirmes;
    }

    public Integer getTotal() {
	return total;
    }

    public void setTotal(Integer total) {
	this.total = total;
    }

    public Integer getTotalConfirmes() {
	return totalConfirmes;
    }

    public void setTotalConfirmes(Integer totalConfirmes) {
	this.totalConfirmes = totalConfirmes;
    }

}
