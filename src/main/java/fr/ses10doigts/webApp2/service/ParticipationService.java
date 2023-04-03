package fr.ses10doigts.webApp2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Participation;
import fr.ses10doigts.webApp2.model.Souhait;
import fr.ses10doigts.webApp2.repository.ParticipationRepository;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepo;

    public List<Participation> getAllParticipations() {
	List<Participation> list = participationRepo.findAll();

	return list;
    }

    public Participation buildParticipationFromSouhait(Souhait souhait) {
	Participation p = new Participation();
	p.setCeremonie(souhait.getCeremonie());
	p.setParticipant(souhait.getParticipant());
	p.setPrix(souhait.getCeremonie().getPrix());
	p.setSouhait(souhait);

	return p;
    }
}
