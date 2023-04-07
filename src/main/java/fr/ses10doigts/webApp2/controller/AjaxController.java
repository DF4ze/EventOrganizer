package fr.ses10doigts.webApp2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Participation;
import fr.ses10doigts.webApp2.model.Souhait;
import fr.ses10doigts.webApp2.service.CeremonieService;
import fr.ses10doigts.webApp2.service.ParticipantService;
import fr.ses10doigts.webApp2.service.ParticipationService;
import fr.ses10doigts.webApp2.service.SouhaitsService;

@RestController
@RequestMapping("/api")
public class AjaxController {
    @Autowired
    private ParticipantService	 partService;
    @Autowired
    private CeremonieService	 ceremService;
    @Autowired
    private ParticipationService participationService;
    @Autowired
    private SouhaitsService	 souhaitService;

    private static final Logger	logger = LoggerFactory.getLogger(AjaxController.class);


    @GetMapping("/switchSouhait")
    public boolean switchSouhait(@RequestParam("ceremId") long ceremId, @RequestParam("partId") long partId) {

	Participant participant = partService.getParticipant(partId);
	if (participant == null) {
	    return false;
	}

	// Si souhait présent, on le supprime
	boolean isSouhaitFounded = false;
	Souhait souhaitFounded = null;
	for (Souhait souhait : participant.getSouhaits()) {
	    if (souhait.getCeremonie().getId().equals(ceremId)) {
		isSouhaitFounded = true;
		souhaitFounded = souhait;
		participant.getSouhaits().remove(souhait);

		break;
	    }
	}
	// et on supprime la participation qui allait avec
	Long deletedParticipationId = null;
	if (souhaitFounded != null) {
	    for (Participation participation : participant.getParticipations()) {
		if (participation.getSouhait() != null && participation.getSouhait().equals(souhaitFounded)
			&& !participation.isFait()) {
		    participant.getParticipations().remove(participation);
		    deletedParticipationId = participation.getId();
		    break;
		}
	    }
	}

	// s'il n'est pas présent, on créé
	if (!isSouhaitFounded) {
	    Ceremonie ceremonie = ceremService.getCeremonie(ceremId);
	    if (ceremonie == null) {
		return false;
	    }

	    // Le souhait
	    Souhait souhait = new Souhait();
	    souhait.setCeremonie(ceremonie);
	    souhait.setParticipant(participant);
	    souhaitService.save(souhait);
	    participant.getSouhaits().add(souhait);

	    // La participation
	    Participation existingPart = null;
	    for (Participation participation : participant.getParticipations()) {
		if (participation.getCeremonie().getId() == ceremId) {
		    existingPart = participation;
		    break;
		}
	    }

	    Participation participation = existingPart;
	    if (existingPart == null) {
		participation = participationService.buildParticipationFromSouhait(souhait);

		participant.getParticipations().add(participation);
	    } else {
		participation.setSouhait(souhait);
	    }

	    participationService.save(participation);
	}
	partService.save(participant);

	if (deletedParticipationId != null) {
	    participationService.delete(deletedParticipationId);
	}
	if (souhaitFounded != null) {
	    souhaitService.delete(souhaitFounded.getId());
	}

	return true;
    }

    @GetMapping("/saveParticipation")
    public boolean saveParticipation(
	    @RequestParam("ceremId") long ceremId, @RequestParam("partId") long partId, @RequestParam("qte") int qte,
	    @RequestParam("prix") int prix
	    ) {

	logger.debug("received : ceremid : " + ceremId + " partId : " + partId + " qte : " + qte + " prix : " + prix);

	Participant participant = partService.getParticipant(partId);
	if (participant == null) {
	    return false;
	}

	Participation participation = null;
	// Est-ce que nous avons déjà une participation pour cette cérémonie?
	for (Participation p : participant.getParticipations()) {
	    if (p.getCeremonie().getId().equals(ceremId)) {
		participation = p;
		break;
	    }
	}

	// Pas de participation pour cette cérémonie, alors on la crée
	if (participation == null) {
	    participation = new Participation();
	    Ceremonie ceremonie = ceremService.getCeremonie(ceremId);
	    if (ceremonie == null) {
		return false;
	    }

	    participation.setCeremonie(ceremonie);
	    participation.setParticipant(participant);
	    participant.getParticipations().add(participation);
	    ceremonie.getParticipations().add(participation);
	    participation = participationService.save(participation);
	    ceremService.save(participation.getCeremonie());
	}

	// Si la participation n'est pas vérouillée
	if (!participation.isFait()) {
	    // QTE == 0 : On supprime
	    if (qte == 0) {
		// Il faut retirer la participation de la cérémonie
		for (Participation p : participation.getCeremonie().getParticipations()) {
		    if (p.getId() == participation.getId()) {
			participation.getCeremonie().getParticipations().remove(p);
			break;
		    }
		}
		ceremService.save(participation.getCeremonie());

		// la retirer du participant
		participant.getParticipations().remove(participation);
		// puis on peut la retirer de la db
		participationService.delete(participation.getId());

		// On retire le souhait associé
		if (participation.getSouhait() != null) {
		    long souhaitId = participation.getSouhait().getId();
		    participant.getSouhaits().remove(participation.getSouhait());
		    participation.setSouhait(null);
		    participant = partService.save(participant);
		    souhaitService.delete(souhaitId);
		}


	    } else {
		participation.setPrix(prix);
		participation.setQuantite(qte);

	    }
	}

	partService.save(participant);


	return true;
    }

    @GetMapping("/validateParticipation")
    public boolean validateParticipation(@RequestParam("ceremId") long ceremId, @RequestParam("partId") long partId) {

	Participant participant = partService.getParticipant(partId);
	if (participant == null) {
	    return false;
	}

	boolean done = false;
	for (Participation p : participant.getParticipations()) {
	    if (p.getCeremonie().getId().equals(ceremId)) {
		p.setFait(!p.isFait());
		participationService.save(p);
		done = true;
		break;
	    }
	}

	if (!done) {
	    return false;
	}

	return true;
    }
}
