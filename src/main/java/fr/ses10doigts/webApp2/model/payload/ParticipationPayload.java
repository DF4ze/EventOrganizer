package fr.ses10doigts.webApp2.model.payload;

import fr.ses10doigts.webApp2.model.Participant;

public class ParticipationPayload {

    public Participant participant;
    public String ceremonie;
    public int	  prix;

    public Participant getParticipant() {
	return participant;
    }

    public void setParticipant(Participant participant) {
	this.participant = participant;
    }

    public String getCeremonie() {
	return ceremonie;
    }

    public void setCeremonie(String ceremonie) {
	this.ceremonie = ceremonie;
    }

    public int getPrix() {
	return prix;
    }

    public void setPrix(int prix) {
	this.prix = prix;
    }

}
