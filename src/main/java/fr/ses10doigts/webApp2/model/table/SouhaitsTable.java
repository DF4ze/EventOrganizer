package fr.ses10doigts.webApp2.model.table;

import java.util.List;

public class SouhaitsTable {

    public String	prenom;
    public Long		participantId;
    public List<String>	souhaits;
    public Long		questionnaireId;

    public String getPrenom() {
	return prenom;
    }

    public void setPrenom(String prenom) {
	this.prenom = prenom;
    }

    public Long getParticipantId() {
	return participantId;
    }

    public void setParticipantId(Long participantId) {
	this.participantId = participantId;
    }

    public List<String> getSouhaits() {
	return souhaits;
    }

    public void setSouhaits(List<String> souhaits) {
	this.souhaits = souhaits;
    }

    public Long getQuestionnaireId() {
	return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
	this.questionnaireId = questionnaireId;
    }

}
