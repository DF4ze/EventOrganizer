package fr.ses10doigts.webApp2.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Participant;
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
    private CeremonieService	    ceremServ;

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


	// Souhaits
	List<Souhait> souhaits = new ArrayList<>();

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
	    }
	    Souhait souhait = buildSouhaitFromPayLoad(souhaitTxt, participant);
	    souhaits.add(souhait);
	}


	questionnaire.setParticipant(participant);
	participant.setQuestionnaire(questionnaire);
	participant.setSouhaits(souhaits);

	pRepository.save(participant);

	return true;
    }

    private Souhait buildSouhaitFromPayLoad(String souhaitTxt, Participant participant) {
	Souhait souhait = null;
	if (souhaitTxt != null) {
	    Ceremonie ceremonie = ceremServ.getByName(souhaitTxt);
	    if (ceremonie != null) {
		souhait = new Souhait();
		souhait.setCeremonie(ceremonie);
		souhait.setParticipant(participant);
	    } else {
		logger.debug("No ceremonie for name : " + souhaitTxt);
	    }
	}
	return souhait;
    }

    public Questionnaire getQuestionnaire(long id) {
	Optional<Questionnaire> q = qRepository.findById(id);
	return q.orElse(null);
    }

    public List<Questionnaire> getAllQuestionnaires() {
	List<Questionnaire> findAll = qRepository.findAll();
	return findAll;
    }
}
