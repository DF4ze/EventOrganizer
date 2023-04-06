package fr.ses10doigts.webApp2.model.payload;

public class ReducPayload {

    public long	  idParticipant;
    public int	  valeur;
    public String note;

    public long getIdParticipant() {
	return idParticipant;
    }

    public void setIdParticipant(long idParticipant) {
	this.idParticipant = idParticipant;
    }

    public int getValeur() {
	return valeur;
    }

    public void setValeur(int valeur) {
	this.valeur = valeur;
    }

    public String getNote() {
	return note;
    }

    public void setNote(String note) {
	this.note = note;
    }

}
