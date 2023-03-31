package fr.ses10doigts.webApp2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Souhait;
import fr.ses10doigts.webApp2.service.CeremonieService;
import fr.ses10doigts.webApp2.service.ParticipantService;

@RestController
@RequestMapping("/api")
public class AjaxController {
    @Autowired
    private ParticipantService partService;
    @Autowired
    private CeremonieService   ceremService;

    @GetMapping("/switchSouhait")
    public boolean switchSouhait(@RequestParam("ceremId") long ceremId, @RequestParam("partId") long partId) {

	Participant participant = partService.getParticipant(partId);
	if (participant == null) {
	    return false;
	}

	// Si souhait présent, on le supprime
	boolean found = false;
	for (Souhait souhait : participant.getSouhaits()) {
	    if (souhait.getCeremonie().getId().equals(ceremId)) {
		found = true;
		participant.getSouhaits().remove(souhait);

		break;
	    }
	}

	// s'il n'est pas présent, on le créé
	if (!found) {
	    Ceremonie ceremonie = ceremService.getCeremonie(ceremId);
	    if (ceremonie == null) {
		return false;
	    }
	    Souhait souhait = new Souhait();
	    souhait.setCeremonie(ceremonie);
	    souhait.setParticipant(participant);

	    participant.getSouhaits().add(souhait);

	}
	partService.save(participant);

	return true;
    }
}
