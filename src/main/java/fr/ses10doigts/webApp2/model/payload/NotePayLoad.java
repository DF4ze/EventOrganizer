package fr.ses10doigts.webApp2.model.payload;

public class NotePayLoad {
    public long	  id;
    public long	  idParticipant;
    public String texte;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public long getIdParticipant() {
	return idParticipant;
    }

    public void setIdParticipant(long idParticipant) {
	this.idParticipant = idParticipant;
    }

    public String getTexte() {
	return texte;
    }

    public void setTexte(String texte) {
	this.texte = texte;
    }

}
