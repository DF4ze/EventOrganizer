package fr.ses10doigts.webApp2.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Souhait;
import fr.ses10doigts.webApp2.model.table.SouhaitsTable;
import fr.ses10doigts.webApp2.repository.SouhaitRepository;

@Service
public class SouhaitsService {

    @Autowired
    private SouhaitRepository souhaitRepo;

    @Autowired
    private ParticipantService	participantService;

    @Autowired
    private CeremonieService  ceremServ;

    private static final Logger	logger = LoggerFactory.getLogger(SouhaitsService.class);

    public List<Souhait> findAllByParticipant(Participant participant) {
	return souhaitRepo.findByParticipantAndActif(participant, true);
    }

    public List<SouhaitsTable> getAllSouhaitsTable() {
	List<Participant> parts = participantService.getAllActiveParticipants();
	List<SouhaitsTable> souhaits = new ArrayList<>();

	for (Participant participant : parts) {
	    SouhaitsTable payLoad = buildSouhaitsTables(participant);
	    souhaits.add(payLoad);
	}

	return souhaits;
    }

    public SouhaitsTable buildSouhaitsTables(Participant participant) {
	SouhaitsTable spl = new SouhaitsTable();

	Set<Souhait> souhaits = participant.getSouhaits();
	Set<String> souhaitsTxt = new HashSet<>();

	for (Souhait souhait : souhaits) {
	    if (souhait.isActif()) {
		souhaitsTxt.add(souhait.getCeremonie().getNom());
	    }
	}
	spl.prenom = participant.getPrenom() + " " + participant.getNom();
	spl.participantId = participant.getId();
	spl.souhaits = new ArrayList<>(souhaitsTxt);
	if (participant.getQuestionnaire() != null) {
	    spl.questionnaireId = participant.getQuestionnaire().getId();
	}

	return spl;
    }

    public Souhait buildSouhaitFromCeremonieName(String souhaitTxt, Participant participant) {
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

    public void delete(long id) {
	souhaitRepo.deleteById(id);
    }

    public Souhait save(Souhait souhait) {
	return souhaitRepo.save(souhait);
    }

}
