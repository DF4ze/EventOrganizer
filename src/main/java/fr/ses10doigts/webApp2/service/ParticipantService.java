package fr.ses10doigts.webApp2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Participation;
import fr.ses10doigts.webApp2.model.Souhait;
import fr.ses10doigts.webApp2.repository.ParticipantRepository;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository partRepo;


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
