package fr.ses10doigts.webApp2.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Facture;
import fr.ses10doigts.webApp2.model.Note;
import fr.ses10doigts.webApp2.model.Paiement;
import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Participation;
import fr.ses10doigts.webApp2.model.Reduction;
import fr.ses10doigts.webApp2.model.payload.PaiementPayload;
import fr.ses10doigts.webApp2.model.payload.ReducPayload;
import fr.ses10doigts.webApp2.model.table.FactureTable;
import fr.ses10doigts.webApp2.repository.FactureRepository;
import fr.ses10doigts.webApp2.repository.PaiementRepository;
import fr.ses10doigts.webApp2.repository.ReductionRepository;
import fr.ses10doigts.webApp2.security.service.IAuthenticationFacade;

@Service
public class FactureService {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private ParticipantService	  partServ;
    @Autowired
    private FactureRepository	  factureRepository;
    @Autowired
    private ReductionRepository	  reducRepository;
    @Autowired
    private PaiementRepository	  paieRepository;

    public Facture getFactureByParticipant(long particpantId) {
	Participant participant = partServ.getParticipant(particpantId);
	return getFactureByParticipant(participant);
    }



    public Facture getFactureByParticipant(Participant participant) {
	return participant.getFacture();
    }


    public FactureTable buildFactureTableFromParticipant(Participant participant) {
	FactureTable ft = new FactureTable();
	ft.idParticipant = participant.getId();
	ft.nomParticipant = participant.getPrenom()+" "+participant.getNom();

	Set<String> nomsCerem = new HashSet<>();
	Map<String, Integer> qtes = new HashMap<>();
	Map<String, Integer> prix = new HashMap<>();
	int total = 0;
	for (Participation p : participant.getParticipations()) {
	    if (p.isActif()) {
		nomsCerem.add(p.getCeremonie().getNom());
		qtes.put(p.getCeremonie().getNom(), p.getQuantite());
		prix.put(p.getCeremonie().getNom(), p.getPrix());
		total += p.getPrix() * p.getQuantite();
	    }
	}
	ft.nomCeremonies = nomsCerem;
	ft.prix = prix;
	ft.qtes = qtes;
	ft.total = total;

	ft.paiements = participant.getFacture().getPaiements();
	ft.reductions = participant.getFacture().getReductions();
	ft.totalPaiement = 0;
	for (Paiement paie : ft.paiements) {
	    ft.totalPaiement += paie.getValeur();
	}

	ft.totalReduction = 0;
	for (Reduction reduc : ft.reductions) {
	    ft.totalReduction += reduc.getValeur();
	}
	return ft;
    }

    public FactureTable buildFactureTableFromParticipant(long participantId) {
	Participant participant = partServ.getParticipant(participantId);
	return buildFactureTableFromParticipant(participant);
    }

    public void addReduction(ReducPayload dto) {
	Participant participant = partServ.getParticipant(dto.idParticipant);

	Reduction reduc = new Reduction();
	reduc.setValeur(dto.valeur);
	reduc.setFacture(participant.getFacture());
	Note note = new Note();
	note.setDate(new Date());
	note.setText(dto.note);
	note.setWriter(authenticationFacade.getConnectedUser());
	reduc.setNote(note);

	participant.getFacture().getReductions().add(reduc);
	partServ.save(participant);
    }

    public void addPaiement(PaiementPayload dto) {
	Participant participant = partServ.getParticipant(dto.idParticipant);

	Paiement paiement = new Paiement();
	paiement.setMoyen(dto.moyenPaiement);
	paiement.setValeur(dto.valeur);
	paiement.setFacture(participant.getFacture());

	Note note = new Note();
	note.setDate(new Date());
	note.setText(dto.note);
	note.setWriter(authenticationFacade.getConnectedUser());

	paiement.setNote(note);

	participant.getFacture().getPaiements().add(paiement);
	partServ.save(participant);
    }

    public Facture deleteReduction(long idReduc) {
	Reduction redu = reducRepository.findById(idReduc).orElse(null);
	Facture f = redu.getFacture();

	for (Reduction r : f.getReductions()) {
	    if (r.getId() == idReduc) {
		f.getReductions().remove(r);
		break;
	    }
	}

	factureRepository.save(f);

	return f;
    }

    public Facture deletePaiement(long idPaie) {
	Paiement paie = paieRepository.findById(idPaie).orElse(null);
	Facture f = paie.getFacture();

	for (Paiement p : f.getPaiements()) {
	    if (p.getId() == idPaie) {
		f.getPaiements().remove(p);
		break;
	    }
	}

	factureRepository.save(f);
	return f;
    }

}
