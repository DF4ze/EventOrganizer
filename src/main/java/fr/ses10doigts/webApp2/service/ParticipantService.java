package fr.ses10doigts.webApp2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.payload.SouhaitsPayLoad;
import fr.ses10doigts.webApp2.repository.ParticipantRepository;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository partRepo;

    @Autowired
    private SouhaitsService	  souhaitService;

    public List<Participant> getAllParticipants() {
	List<Participant> participants = partRepo.findAll();

	return participants;
    }

    public List<SouhaitsPayLoad> getAllSouhaits() {
	List<Participant> parts = getAllParticipants();
	List<SouhaitsPayLoad> souhaits = new ArrayList<>();

	for (Participant participant : parts) {
	    SouhaitsPayLoad payLoad = souhaitService.buildSouhaitsPaylLoads(participant);
	    souhaits.add(payLoad);
	}

	return souhaits;
    }

    public Participant getParticipant(long id) {
	return partRepo.findById(id).orElse(null);
    }

    public void save(Participant participant) {
	partRepo.save(participant);
    }

    public void delete(long id) {
	partRepo.deleteById(id);
    }
}
