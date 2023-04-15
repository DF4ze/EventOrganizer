package fr.ses10doigts.webApp2.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Participation;
import fr.ses10doigts.webApp2.model.Souhait;
import fr.ses10doigts.webApp2.repository.ParticipantRepository;
import fr.ses10doigts.webApp2.repository.ParticipationRepository;
import fr.ses10doigts.webApp2.repository.SouhaitRepository;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository partRepo;
    @Autowired
    private ParticipationRepository participationRepository;
    @Autowired
    private SouhaitRepository	    souhaitRepository;


    public List<Participant> getAllParticipants() {
	List<Participant> participants = partRepo.findAll();

	return participants;
    }

    public List<Participant> getAllActiveParticipants() {
	List<Participant> participants = partRepo.findAllByActif();

	return participants;
    }



    public Participant getParticipant(long id) {
	return partRepo.findById(id).orElse(null);
    }

    public Participant save(Participant participant) {
	return partRepo.save(participant);
    }

    public void delete(long id) {
	Participant p = partRepo.findById(id).orElse(null);
	p.getParticipations().clear();
	Set<Souhait> souhaits = p.getSouhaits();
	for (Souhait souhait : souhaits) {
	    souhaitRepository.delete(souhait);
	}
	p.getSouhaits().clear();
	partRepo.save(p);
	partRepo.flush();


	partRepo.deleteById(id);
    }

    public Participant desactiver(Participant p) {
	p.setActif(false);

	for (Souhait s : p.getSouhaits()) {
	    s.setActif(false);
	}

	for (Participation part : p.getParticipations()) {
	    part.setActif(false);
	}

	return save(p);
    }

    public Participant activer(Participant p) {
	p.setActif(true);

	for (Souhait s : p.getSouhaits()) {
	    s.setActif(true);
	}

	for (Participation part : p.getParticipations()) {
	    part.setActif(true);
	}

	return save(p);
    }
}
