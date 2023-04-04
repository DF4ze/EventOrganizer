package fr.ses10doigts.webApp2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Participation;
import fr.ses10doigts.webApp2.model.Souhait;
import fr.ses10doigts.webApp2.model.payload.ParticipationsTable;
import fr.ses10doigts.webApp2.repository.ParticipationRepository;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepo;
    @Autowired
    private ParticipantService	    participantService;
    @Autowired
    private CeremonieService	    ceremonieService;

    public List<Participation> getAllParticipations() {
	List<Participation> list = participationRepo.findAll();

	return list;
    }

    public Participation buildParticipationFromSouhait(Souhait souhait) {
	Participation p = new Participation();

	p.setParticipant(souhait.getParticipant());
	p.setPrix(souhait.getCeremonie().getPrix());
	p.setSouhait(souhait);
	p.setCeremonie(souhait.getCeremonie());
	p.setQuantite(1);

	if (souhait.getCeremonie().getNom().equals("Tambours")) {
	    Ceremonie ceremonie = ceremonieService.getByName("Tambours 1");
	    p.setCeremonie(ceremonie);

	} else if (souhait.getCeremonie().getNom().equals("Kambo x1")) {
	    Ceremonie ceremonie = ceremonieService.getByName("Kambo Mardi");
	    p.setCeremonie(ceremonie);

	} else if (souhait.getCeremonie().getNom().equals("Kambo x2")) {
	    Ceremonie ceremonie = ceremonieService.getByName("Kambo Mardi");
	    p.setCeremonie(ceremonie);
	    p.setQuantite(2);

	} else if (souhait.getCeremonie().getNom().equals("Kambo x3")) {
	    Ceremonie ceremonie = ceremonieService.getByName("Kambo Mardi");
	    p.setCeremonie(ceremonie);
	    p.setQuantite(3);

	}

	return p;
    }

    public List<ParticipationsTable> getAllParticipationsTable() {
	List<Participant> participants = participantService.getAllActiveParticipants();
	List<ParticipationsTable> table = new ArrayList<>();

	for (Participant participant : participants) {
	    ParticipationsTable pt = new ParticipationsTable();
	    pt.idParticipant = participant.getId();
	    pt.nomParticipant = participant.getPrenom() + " " + participant.getNom();

	    List<String> nomPart = new ArrayList<>();
	    Map<String, Integer> prices = new HashMap<>();
	    Map<String, Integer> qtes = new HashMap<>();
	    Map<String, Boolean> fait = new HashMap<>();
	    int total = 0;
	    for (Participation participation : participant.getParticipations()) {

		nomPart.add(participation.getCeremonie().getNom());
		prices.put(participation.getCeremonie().getNom(), participation.getPrix());
		qtes.put(participation.getCeremonie().getNom(), participation.getQuantite());
		fait.put(participation.getCeremonie().getNom(), participation.isFait());
		total += participation.getQuantite() * participation.getPrix();
	    }
	    pt.nomCeremonies = nomPart;
	    pt.prix = prices;
	    pt.qte = qtes;
	    pt.fait = fait;
	    pt.total = total;

	    table.add(pt);
	}

	return table;
    }

    public void save(Participation p) {
	participationRepo.save(p);
    }

    public void delete(long partId) {
	participationRepo.deleteById(partId);
    }
}
