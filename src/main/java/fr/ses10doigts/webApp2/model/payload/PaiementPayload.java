package fr.ses10doigts.webApp2.model.payload;

import fr.ses10doigts.webApp2.model.TypePaiement;

public class PaiementPayload {

    public long		idParticipant;
    public int		valeur;
    public TypePaiement	moyenPaiement;
    public String	note;

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

    public TypePaiement getMoyenPaiement() {
	return moyenPaiement;
    }

    public void setMoyenPaiement(TypePaiement moyenPaiement) {
	this.moyenPaiement = moyenPaiement;
    }

    public String getNote() {
	return note;
    }

    public void setNote(String note) {
	this.note = note;
    }

}
