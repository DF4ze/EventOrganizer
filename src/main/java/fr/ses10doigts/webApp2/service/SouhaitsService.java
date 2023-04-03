package fr.ses10doigts.webApp2.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Souhait;
import fr.ses10doigts.webApp2.model.payload.SouhaitsPayLoad;
import fr.ses10doigts.webApp2.repository.SouhaitRepository;

@Service
public class SouhaitsService {

    @Autowired
    private SouhaitRepository souhaitRepo;

    @Autowired
    private CeremonieService  ceremServ;

    private static final Logger	logger = LoggerFactory.getLogger(SouhaitsService.class);

    public List<Souhait> findAllByParticipant(Participant participant) {
	return souhaitRepo.findByParticipant(participant);
    }

    public SouhaitsPayLoad buildSouhaitsPaylLoads(Participant participant) {
	SouhaitsPayLoad spl = new SouhaitsPayLoad();

	List<Souhait> souhaits = participant.getSouhaits();
	List<String> souhaitsTxt = new ArrayList<String>();

	for (Souhait souhait : souhaits) {
	    souhaitsTxt.add(souhait.getCeremonie().getNom());
	}
	spl.prenom = participant.getPrenom() + " " + participant.getNom();
	spl.participantId = participant.getId();
	spl.souhaits = souhaitsTxt;
	if (participant.getQuestionnaire() != null) {
	    spl.questionnaireId = participant.getQuestionnaire().getId();
	}

	return spl;
    }

    public Souhait buildSouhaitFromPayLoad(String souhaitTxt, Participant participant) {
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

}
