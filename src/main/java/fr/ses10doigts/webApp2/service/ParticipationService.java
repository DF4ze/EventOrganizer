package fr.ses10doigts.webApp2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Display;
import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Participation;
import fr.ses10doigts.webApp2.model.Souhait;
import fr.ses10doigts.webApp2.model.table.ParticipationsByCeremonieTable;
import fr.ses10doigts.webApp2.model.table.ParticipationsTable;
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

	Ceremonie ceremonie = souhait.getCeremonie();
	p.setParticipant(souhait.getParticipant());
	p.setPrix(souhait.getCeremonie().getPrix());
	p.setSouhait(souhait);
	p.setCeremonie(ceremonie);
	p.setQuantite(1);

	if (souhait.getCeremonie().getNom().equals("Tambour")) {
	    ceremonie = ceremonieService.getByName("Tambour 33");
	    p.setCeremonie(ceremonie);

	} else if (souhait.getCeremonie().getNom().equals("Kambo x1")) {
	    ceremonie = ceremonieService.getByName("Kambo Mardi");
	    p.setCeremonie(ceremonie);

	} else if (souhait.getCeremonie().getNom().equals("Kambo x2")) {
	    ceremonie = ceremonieService.getByName("Kambo Mardi");
	    p.setCeremonie(ceremonie);
	    p.setQuantite(2);

	} else if (souhait.getCeremonie().getNom().equals("Kambo x3")) {
	    ceremonie = ceremonieService.getByName("Kambo Mardi");
	    p.setCeremonie(ceremonie);
	    p.setQuantite(3);

	}

	ceremonie.getParticipations().add(p);
	p = participationRepo.save(p);
	ceremonieService.save(ceremonie);

	return p;
    }

    public ParticipationsTable getParticipationTable(Participant participant) {
	ParticipationsTable pt = new ParticipationsTable();
	pt.idParticipant = participant.getId();
	pt.nomParticipant = participant.getPrenom() + " " + participant.getNom();

	pt.idFacture = participant.getFacture().getId();


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

	return pt;
    }

    public ParticipationsTable getParticipationTable(long id) {
	Participant participant = participantService.getParticipant(id);

	return getParticipationTable(participant);
    }

    public List<ParticipationsTable> getAllParticipationsTable() {
	List<Participant> participants = participantService.getAllActiveParticipants();
	List<ParticipationsTable> table = new ArrayList<>();

	for (Participant participant : participants) {
	    ParticipationsTable pt = getParticipationTable(participant);
	    table.add(pt);
	}

	return table;
    }

    public List<ParticipationsByCeremonieTable> getAllParticipationByCeremonieTables() {
	ArrayList<ParticipationsByCeremonieTable> table = new ArrayList<>();

	List<Ceremonie> ceremonies = ceremonieService.getAllActivesCeremoniesByDisplay(Display.CEREMONIE);
	for (Ceremonie cerem : ceremonies) {
	    ParticipationsByCeremonieTable pct = getParticipationByCeremonieTable(cerem);
	    table.add(pct);
	}

	return table;
    }

    public ParticipationsByCeremonieTable getParticipationByCeremonieTable(Ceremonie cerem) {
	ParticipationsByCeremonieTable pct = new ParticipationsByCeremonieTable();

	pct.nomCeremonie = cerem.getNom();
	pct.quantite = 0;
	pct.nbParticipants = 0;
	pct.nbParticipantsConfirmes = 0;
	pct.total = 0;
	pct.totalConfirmes = 0;
	for (Participation p : cerem.getParticipations()) {
	    if (p.isActif()) {
		pct.nbParticipants++;
		pct.total += p.getPrix() * p.getQuantite(); // TODO quid Kambo
		pct.quantite += p.getQuantite();
		if (p.isFait()) {
		    pct.nbParticipantsConfirmes++;
		    pct.totalConfirmes += p.getPrix() * p.getQuantite(); // TODO quid Kambo
		}
	    }
	}

	return pct;
    }

    public Participation save(Participation p) {
	return participationRepo.save(p);
    }

    public void delete(long partId) {
	Participation participation = participationRepo.findById(partId).orElse(null);
	List<Participation> participations = participation.getCeremonie().getParticipations();
	for (Participation p : participations) {
	    if (p.getId() == partId) {
		participations.remove(p);
		break;
	    }
	}
	ceremonieService.save(participation.getCeremonie());

	participationRepo.deleteById(partId);
    }

    public Participation getParticipation(long id) {
	return participationRepo.findById(id).orElse(null);
    }



}
