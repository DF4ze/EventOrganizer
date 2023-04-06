package fr.ses10doigts.webApp2.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Facture;
import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Participation;
import fr.ses10doigts.webApp2.model.Questionnaire;
import fr.ses10doigts.webApp2.model.Souhait;
import fr.ses10doigts.webApp2.model.payload.QuestionnairePayload;
import fr.ses10doigts.webApp2.repository.ParticipantRepository;
import fr.ses10doigts.webApp2.repository.QuestionnaireRepository;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireRepository qRepository;

    @Autowired
    private ParticipantRepository   pRepository;

    @Autowired
    private SouhaitsService	    souhaitsService;

    @Autowired
    private ParticipationService    participationService;


    private static final Logger	    logger = LoggerFactory.getLogger(QuestionnaireService.class);



    public Boolean saveQuestionnaire(QuestionnairePayload qp) {
	// Participant
	Participant participant = pRepository.findByNomAndPrenom(qp.nom, qp.prenom).orElse(null);
	if (participant == null) {
	    participant = new Participant();
	    participant.setEmail(qp.email);
	    participant.setNom(qp.nom);
	    participant.setPrenom(qp.prenom);
	    participant.setTel(qp.telephone);
	    participant.setUrgence(qp.urgence);
	    participant.setPrenoms(qp.prenoms);
	    participant.setNaissance(qp.naissance);
	    Facture f = new Facture();
	    f.setParticipant(participant);
	    participant.setFacture(f);
	}

	// Questionnaire
	Questionnaire questionnaire = new Questionnaire();
	questionnaire.setDefi(qp.defi);
	questionnaire.setEmail(qp.email);
	questionnaire.setExperience(qp.experience);
	questionnaire.setIntention(qp.intention);
	questionnaire.setNom(qp.nom);
	questionnaire.setPrenom(qp.prenom);
	questionnaire.setSante(qp.sante);
	questionnaire.setTelephone(qp.telephone);
	questionnaire.setUrgence(qp.urgence);
	questionnaire.setRemarques(qp.remarques);
	questionnaire.setPrenoms(qp.prenoms);
	questionnaire.setNaissance(qp.naissance);


	// Souhaits & Participations
	Set<Souhait> souhaits = new HashSet<>();
	Set<Participation> participations = new HashSet<>();

	List<String> nonCerem = new ArrayList<>();
	nonCerem.add("nom");
	nonCerem.add("prenom");
	nonCerem.add("defi");
	nonCerem.add("email");
	nonCerem.add("experience");
	nonCerem.add("intention");
	nonCerem.add("sante");
	nonCerem.add("telephone");
	nonCerem.add("urgence");
	nonCerem.add("remarques");
	nonCerem.add("naissance");
	nonCerem.add("prenoms");

	Class<QuestionnairePayload> plc = QuestionnairePayload.class;
	Field[] declaredFields = plc.getDeclaredFields();
	for (Field field : declaredFields) {
	    String souhaitTxt = null;
	    if( !nonCerem.contains( field.getName() )){
		try {
		    souhaitTxt = (String) field.get(qp);

		} catch (IllegalArgumentException | IllegalAccessException e) {
		    logger.debug("Unable to retrieve data in " + field.getName() + " field");
		}

		if (souhaitTxt != null) {
		    Souhait souhait = souhaitsService.buildSouhaitFromCeremonieName(souhaitTxt, participant);

		    souhaits.add(souhait);

		    Participation participation = participationService.buildParticipationFromSouhait(souhait);
		    participations.add(participation);
		}
	    }
	}


	questionnaire.setParticipant(participant);
	participant.setQuestionnaire(questionnaire);
	participant.setSouhaits(souhaits);
	participant.setParticipations(participations);

	pRepository.save(participant);

	return true;
    }


    public Questionnaire getQuestionnaire(long id) {
	Optional<Questionnaire> q = qRepository.findById(id);
	return q.orElse(null);
    }

    public List<Questionnaire> getAllQuestionnaires() {
	List<Questionnaire> findAll = qRepository.findByActivParticipant();
	return findAll;
    }
}
