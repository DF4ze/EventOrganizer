package fr.ses10doigts.webApp2.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Facture;
import fr.ses10doigts.webApp2.model.Paiement;
import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Participation;
import fr.ses10doigts.webApp2.model.Reduction;
import fr.ses10doigts.webApp2.model.payload.FactureTable;

@Service
public class FactureService {

    @Autowired
    private ParticipantService partServ;

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
	    nomsCerem.add(p.getCeremonie().getNom());
	    qtes.put(p.getCeremonie().getNom(), p.getQuantite());
	    prix.put(p.getCeremonie().getNom(), p.getPrix());
	    total += p.getPrix();
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
}
