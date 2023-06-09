package fr.ses10doigts.webApp2.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Display;
import fr.ses10doigts.webApp2.model.Facture;
import fr.ses10doigts.webApp2.model.Note;
import fr.ses10doigts.webApp2.model.Participant;
import fr.ses10doigts.webApp2.model.Questionnaire;
import fr.ses10doigts.webApp2.model.Souhait;
import fr.ses10doigts.webApp2.model.payload.CeremoniePayload;
import fr.ses10doigts.webApp2.model.payload.NotePayLoad;
import fr.ses10doigts.webApp2.model.payload.PaiementPayload;
import fr.ses10doigts.webApp2.model.payload.ParticipantPayload;
import fr.ses10doigts.webApp2.model.payload.ParticipationPayload;
import fr.ses10doigts.webApp2.model.payload.QuestionnairePayload;
import fr.ses10doigts.webApp2.model.payload.ReducPayload;
import fr.ses10doigts.webApp2.model.table.FactureTable;
import fr.ses10doigts.webApp2.model.table.ParticipationsByCeremonieTable;
import fr.ses10doigts.webApp2.model.table.ParticipationsTable;
import fr.ses10doigts.webApp2.model.table.SouhaitsTable;
import fr.ses10doigts.webApp2.security.model.Role;
import fr.ses10doigts.webApp2.security.model.User;
import fr.ses10doigts.webApp2.security.model.payload.request.LoginRequest;
import fr.ses10doigts.webApp2.security.model.payload.request.SignupRequest;
import fr.ses10doigts.webApp2.security.repository.RoleRepository;
import fr.ses10doigts.webApp2.security.service.IAuthenticationFacade;
import fr.ses10doigts.webApp2.service.CeremonieService;
import fr.ses10doigts.webApp2.service.FactureService;
import fr.ses10doigts.webApp2.service.NoteService;
import fr.ses10doigts.webApp2.service.ParticipantService;
import fr.ses10doigts.webApp2.service.ParticipationService;
import fr.ses10doigts.webApp2.service.QuestionnaireService;
import fr.ses10doigts.webApp2.service.SouhaitsService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class MainController {
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private RoleRepository	  roleRepo;
    @Autowired
    private QuestionnaireService  questService;
    @Autowired
    private ParticipantService	  partService;
    @Autowired
    private CeremonieService	  ceremService;
    @Autowired
    private SouhaitsService	  souhaitService;
    @Autowired
    private NoteService		  noteService;
    @Autowired
    private ParticipationService  participationService;
    @Autowired
    private FactureService	  factureService;

    private static final Logger	  logger = LoggerFactory.getLogger(MainController.class);



    /**** Main page section ****/

    @GetMapping("/")
    public String home(Model model) {
	User user = null;

	String view = "home";
	try {
	    user = authenticationFacade.getConnectedUser();
	    model.addAttribute("loggued", true);
	    model.addAttribute("username", user.getUsername());

	    List<Participant> participants = partService.getAllActiveParticipants();
	    model.addAttribute("participants", participants);

	    List<Ceremonie> ceremonies = ceremService.getAllActivesCeremoniesByDisplay(Display.SOUHAIT);
	    model.addAttribute("ceremonies", ceremonies);

	    Map<String, Integer> partCerem = new HashMap<>();
	    for (Ceremonie ceremonie : ceremonies) {
		for (Participant participant : participants) {
		    for (Souhait souhait : participant.getSouhaits()) {
			if (souhait.getCeremonie().getNom().equals(ceremonie.getNom())) {
			    if (partCerem.containsKey(ceremonie.getNom())) {
				int val = partCerem.get(ceremonie.getNom()) + 1;
				partCerem.put(ceremonie.getNom(), val);
			    } else {
				partCerem.put(ceremonie.getNom(), 1);
			    }
			}
		    }
		}
	    }
	    model.addAttribute("souhaitsParCeremonie", partCerem);

	    List<SouhaitsTable> souhaitsPayLoads = new ArrayList<>();
	    for (Participant participant : participants) {
		SouhaitsTable paylLoad = souhaitService.buildSouhaitsTables(participant);
		souhaitsPayLoads.add(paylLoad);
	    }
	    model.addAttribute("souhaits", souhaitsPayLoads);

	    logger.debug("Nombre de participants : " + participants.size());

	} catch (RuntimeException e) {
	    LoginRequest loginRequest = new LoginRequest();
	    model.addAttribute("loginRequest", loginRequest);
	    view = "login";
	}

	return view;
    }

    @GetMapping("/home")
    public String homeParam(Model model) {
	return home(model);
    }

    @GetMapping("/login")
    public String login(Model model) {
	LoginRequest loginRequest = new LoginRequest();
	model.addAttribute("loginRequest", loginRequest);

	return "login";
    }

    /**** Questionnaire section ****/

    @GetMapping("/questionnaire")
    public String questionnaire(Model model) {
	QuestionnairePayload q = new QuestionnairePayload();
	model.addAttribute(q);
	return "questionnaire";
    }

    @GetMapping("/sent")
    public String questionnaireSent(Model model) {
	QuestionnairePayload q = new QuestionnairePayload();
	model.addAttribute(q);
	return "questionnaireSent";
    }

    @PostMapping(value = "/questionnaire", params = "action=validate")
    public ModelAndView saveConfig(@ModelAttribute QuestionnairePayload dto) {
	logger.info("Formulaire : " + dto);

	questService.saveQuestionnaire(dto);

	return new ModelAndView("redirect:/sent");
    }

    /**** Questionnaire Admin section ****/
    @GetMapping("/questRead")
    @PreAuthorize("hasRole('ADMIN')")
    public String questionnaireLecture(Model model) {
	List<Questionnaire> allQuestionnaires = questService.getAllQuestionnaires();

	model.addAttribute("questionnaires", allQuestionnaires);
	model.addAttribute("search", null);

	return "questRead";
    }

    @GetMapping("/questRead{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView questionnaireById(@PathVariable("id") long id) {
	List<Questionnaire> allQuestionnaires = questService.getAllQuestionnaires();
	Questionnaire questionnaire = questService.getQuestionnaire(id);

	ModelAndView modelAndView = new ModelAndView("questRead");
	modelAndView.addObject("questionnaires", allQuestionnaires);
	modelAndView.addObject("search", questionnaire);

	return modelAndView;
    }

    /**** NavBar section ****/
    @GetMapping("/navbar")
    @PreAuthorize("hasRole('ADMIN')")
    public String navbar(Model model) {

	return "navbar";
    }

    /**** Participants section ****/
    @GetMapping("/participant")
    @PreAuthorize("hasRole('ADMIN')")
    public String participants(Model model) {
	List<Participant> allParticipants = partService.getAllParticipants();
	Participant participant = null;
	NotePayLoad npl = new NotePayLoad();
	ParticipantPayload pp = new ParticipantPayload();

	model.addAttribute("participants", allParticipants);
	model.addAttribute("search", participant);
	model.addAttribute("notePayload", npl);
	model.addAttribute("participantPayload", pp);

	return "participant";
    }

    @GetMapping("/participant{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView participantById(@PathVariable("id") long id) {
	List<Participant> allParticipants = partService.getAllParticipants();
	Participant participant = partService.getParticipant(id);

	NotePayLoad npl = new NotePayLoad();
	npl.idParticipant = id;

	ParticipantPayload pp = new ParticipantPayload();

	ModelAndView modelAndView = new ModelAndView("participant");
	modelAndView.addObject("participants", allParticipants);
	modelAndView.addObject("search", participant);
	modelAndView.addObject("notePayload", npl);
	modelAndView.addObject("participantPayload", pp);

	return modelAndView;
    }

    @PostMapping(value = "/participant", params = "action=addParticipant")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addParticipant(@ModelAttribute ParticipantPayload dto) {
	Participant participant = new Participant();
	participant.setEmail(dto.email);
	participant.setNom(dto.nom);
	participant.setPrenom(dto.prenom);
	participant.setTel(dto.tel);
	participant.setUrgence(dto.urgence);
	participant.setPrenoms(dto.prenoms);
	participant.setNaissance(dto.naissance);
	Facture f = new Facture();
	f.setParticipant(participant);
	participant.setFacture(f);

	partService.save(participant);

	ParticipantPayload pp = new ParticipantPayload();
	List<Participant> allParticipants = partService.getAllParticipants();
	NotePayLoad npl = new NotePayLoad();

	ModelAndView modelAndView = new ModelAndView("participant");
	modelAndView.addObject("participants", allParticipants);
	modelAndView.addObject("search", participant);
	modelAndView.addObject("notePayload", npl);
	modelAndView.addObject("participantPayload", pp);

	return modelAndView;
    }

    @GetMapping("/deleteParticipant{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView deleteParticipant(@PathVariable("id") long id) {

	partService.delete(id);

	ParticipantPayload pp = new ParticipantPayload();
	List<Participant> allParticipants = partService.getAllParticipants();
	NotePayLoad npl = new NotePayLoad();

	ModelAndView modelAndView = new ModelAndView("participant");
	modelAndView.addObject("participants", allParticipants);
	modelAndView.addObject("search", null);
	modelAndView.addObject("notePayload", npl);
	modelAndView.addObject("participantPayload", pp);

	return modelAndView;
    }

    @GetMapping("/desactiverParticipant{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView desactiverParticipant(@PathVariable("id") long id) {

	Participant participant = partService.getParticipant(id);
	participant = partService.desactiver(participant);

	List<Participant> allParticipants = partService.getAllParticipants();
	ParticipantPayload pp = new ParticipantPayload();
	NotePayLoad npl = new NotePayLoad();

	ModelAndView modelAndView = new ModelAndView("participant");
	modelAndView.addObject("participants", allParticipants);
	modelAndView.addObject("search", null);
	modelAndView.addObject("notePayload", npl);
	modelAndView.addObject("participantPayload", pp);

	return modelAndView;
    }

    @GetMapping("/activerParticipant{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView activerParticipant(@PathVariable("id") long id) {

	Participant participant = partService.getParticipant(id);
	participant = partService.activer(participant);

	List<Participant> allParticipants = partService.getAllParticipants();
	ParticipantPayload pp = new ParticipantPayload();
	NotePayLoad npl = new NotePayLoad();

	ModelAndView modelAndView = new ModelAndView("participant");
	modelAndView.addObject("participants", allParticipants);
	modelAndView.addObject("search", null);
	modelAndView.addObject("notePayload", npl);
	modelAndView.addObject("participantPayload", pp);

	return modelAndView;
    }

    /**** Note section ******/

    @PostMapping(value = "/participant", params = "action=addNote")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addNote(@ModelAttribute NotePayLoad dto) {
	Participant participant = partService.getParticipant(dto.getIdParticipant());

	Note note = new Note();
	note.setParticipant(participant);
	note.setText(dto.texte);
	note.setWriter(authenticationFacade.getConnectedUser());
	note.setDate(new Date());
	if (participant.getNotes() == null) {
	    participant.setNotes(new ArrayList<>());
	}
	participant.getNotes().add(note);

	partService.save(participant);

	ParticipantPayload pp = new ParticipantPayload();
	List<Participant> allParticipants = partService.getAllParticipants();
	dto.texte = "";

	ModelAndView modelAndView = new ModelAndView("participant");
	modelAndView.addObject("participants", allParticipants);
	modelAndView.addObject("search", participant);
	modelAndView.addObject("notePayload", dto);
	modelAndView.addObject("participantPayload", pp);

	return modelAndView;
    }

    @GetMapping("/deleteNote{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView deleteNote(@PathVariable("id") long id) {

	Note note = noteService.getNote(id);
	for (Note search : note.getParticipant().getNotes()) {
	    if (search.getId().equals(id)) {
		note.getParticipant().getNotes().remove(search);
		break;
	    }
	}
	partService.save(note.getParticipant());
	noteService.deleteNote(id);

	ParticipantPayload pp = new ParticipantPayload();
	List<Participant> allParticipants = partService.getAllParticipants();
	NotePayLoad npl = new NotePayLoad();

	ModelAndView modelAndView = new ModelAndView("participant");
	modelAndView.addObject("participants", allParticipants);
	modelAndView.addObject("search", note.getParticipant());
	modelAndView.addObject("notePayload", npl);
	modelAndView.addObject("participantPayload", pp);

	return modelAndView;
    }

    /**** Ceremonie section ******/
    @GetMapping("/ceremonie")
    @PreAuthorize("hasRole('ADMIN')")
    public String ceremonies(Model model) {
	List<Ceremonie> ceremonies = ceremService.getAllCeremoniesByDisplay(Display.CEREMONIE);

	CeremoniePayload pp = new CeremoniePayload();

	model.addAttribute("ceremonies", ceremonies);
	model.addAttribute("ceremoniePayload", pp);

	return "ceremonie";
    }

    @GetMapping("/desactiverCeremonie{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView desactiverCeremonie(@PathVariable("id") long id) {

	ceremService.setActif(id, false);

	List<Ceremonie> ceremonies = ceremService.getAllCeremoniesByDisplay(Display.CEREMONIE);
	CeremoniePayload pp = new CeremoniePayload();

	ModelAndView modelAndView = new ModelAndView("ceremonie");
	modelAndView.addObject("ceremonies", ceremonies);
	modelAndView.addObject("ceremoniePayload", pp);

	return modelAndView;
    }

    @GetMapping("/activerCeremonie{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView activerCeremonie(@PathVariable("id") long id) {

	ceremService.setActif(id, true);

	List<Ceremonie> ceremonies = ceremService.getAllCeremoniesByDisplay(Display.CEREMONIE);
	CeremoniePayload pp = new CeremoniePayload();

	ModelAndView modelAndView = new ModelAndView("ceremonie");
	modelAndView.addObject("ceremonies", ceremonies);
	modelAndView.addObject("ceremoniePayload", pp);

	return modelAndView;
    }

    @GetMapping("/deleteCeremonie{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView deleteCeremonie(@PathVariable("id") long id) {

	ceremService.delete(id);

	List<Ceremonie> ceremonies = ceremService.getAllCeremoniesByDisplay(Display.CEREMONIE);
	CeremoniePayload pp = new CeremoniePayload();

	ModelAndView modelAndView = new ModelAndView("ceremonie");
	modelAndView.addObject("ceremonies", ceremonies);
	modelAndView.addObject("ceremoniePayload", pp);

	return modelAndView;
    }

    @PostMapping(value = "/ceremonie", params = "action=addCeremonie")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addCeremonie(@ModelAttribute CeremoniePayload dto) {
	Ceremonie cerem = new Ceremonie();
	cerem.setDisplay(Display.BOTH);
	cerem.setJour(dto.jour);
	cerem.setNom(dto.nom);
	cerem.setPrix(dto.prix);
	cerem.setType(dto.type);
	cerem.setOrdre(dto.ordre);

	ceremService.save(cerem);

	List<Ceremonie> ceremonies = ceremService.getAllCeremoniesByDisplay(Display.CEREMONIE);
	CeremoniePayload pp = new CeremoniePayload();

	ModelAndView modelAndView = new ModelAndView("ceremonie");
	modelAndView.addObject("ceremonies", ceremonies);
	modelAndView.addObject("ceremoniePayload", pp);

	return modelAndView;
    }

    /**** Participation section ****/

    @GetMapping("/participation")
    public String participation(Model model) {
	ParticipationPayload pp = new ParticipationPayload();
	List<Ceremonie> ceremonies = ceremService.getAllActivesCeremoniesByDisplay(Display.CEREMONIE);
	List<ParticipationsTable> participations = participationService.getAllParticipationsTable();
	List<ParticipationsByCeremonieTable> partByCerem = participationService.getAllParticipationByCeremonieTables();

	model.addAttribute("search", null);
	model.addAttribute("participationPayload", pp);
	model.addAttribute("ceremonies", ceremonies);
	model.addAttribute("participations", participations);
	model.addAttribute("partByCerem", partByCerem);

	return "participation";
    }

    @GetMapping("/participation{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView participationById(@PathVariable("id") long id) {
	ParticipationPayload pp = new ParticipationPayload();
	List<Ceremonie> ceremonies = ceremService.getAllActivesCeremoniesByDisplay(Display.CEREMONIE);
	List<ParticipationsTable> participations = participationService.getAllParticipationsTable();
	FactureTable search = factureService.buildFactureTableFromParticipant(id);

	ReducPayload reduc = new ReducPayload();
	reduc.idParticipant = search.idParticipant;
	PaiementPayload paiement = new PaiementPayload();
	paiement.idParticipant = search.idParticipant;

	ModelAndView modelAndView = new ModelAndView("participation");
	modelAndView.addObject("search", search);
	modelAndView.addObject("participationPayload", pp);
	modelAndView.addObject("ceremonies", ceremonies);
	modelAndView.addObject("participations", participations);
	modelAndView.addObject("reducPayload", reduc);
	modelAndView.addObject("paiementPayload", paiement);

	return modelAndView;
    }

    @PostMapping(value = "/participation", params = "action=addReduc")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addReduc(@ModelAttribute ReducPayload dto) {
	ParticipationPayload pp = new ParticipationPayload();
	List<Ceremonie> ceremonies = ceremService.getAllActivesCeremoniesByDisplay(Display.CEREMONIE);
	List<ParticipationsTable> participations = participationService.getAllParticipationsTable();

	if (dto.valeur != 0) {
	    factureService.addReduction(dto);
	}

	FactureTable search = factureService.buildFactureTableFromParticipant(dto.idParticipant);
	PaiementPayload paiement = new PaiementPayload();
	paiement.idParticipant = search.idParticipant;

	ModelAndView modelAndView = new ModelAndView("participation");
	modelAndView.addObject("search", search);
	modelAndView.addObject("participationPayload", pp);
	modelAndView.addObject("ceremonies", ceremonies);
	modelAndView.addObject("participations", participations);
	modelAndView.addObject("reducPayload", dto);
	modelAndView.addObject("paiementPayload", paiement);

	return modelAndView;
    }

    @PostMapping(value = "/participation", params = "action=addPaiement")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addPaiement(@ModelAttribute PaiementPayload dto) {
	ParticipationPayload pp = new ParticipationPayload();
	List<Ceremonie> ceremonies = ceremService.getAllActivesCeremoniesByDisplay(Display.CEREMONIE);
	List<ParticipationsTable> participations = participationService.getAllParticipationsTable();

	if (dto.valeur != 0) {
	    factureService.addPaiement(dto);
	}

	FactureTable search = factureService.buildFactureTableFromParticipant(dto.idParticipant);
	PaiementPayload paiement = new PaiementPayload();
	paiement.idParticipant = search.idParticipant;

	ModelAndView modelAndView = new ModelAndView("participation");
	modelAndView.addObject("search", search);
	modelAndView.addObject("participationPayload", pp);
	modelAndView.addObject("ceremonies", ceremonies);
	modelAndView.addObject("participations", participations);
	modelAndView.addObject("reducPayload", dto);
	modelAndView.addObject("paiementPayload", paiement);

	return modelAndView;
    }

    @GetMapping("/deleteReduc{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView deleteReduc(@PathVariable("id") long id) {
	ParticipationPayload pp = new ParticipationPayload();
	List<Ceremonie> ceremonies = ceremService.getAllActivesCeremoniesByDisplay(Display.CEREMONIE);
	List<ParticipationsTable> participations = participationService.getAllParticipationsTable();

	Facture facture = factureService.deleteReduction(id);
	FactureTable search = factureService.buildFactureTableFromParticipant(facture.getParticipant().getId());

	ReducPayload reduc = new ReducPayload();
	reduc.idParticipant = search.idParticipant;
	PaiementPayload paiement = new PaiementPayload();
	paiement.idParticipant = search.idParticipant;

	ModelAndView modelAndView = new ModelAndView("participation");
	modelAndView.addObject("search", search);
	modelAndView.addObject("participationPayload", pp);
	modelAndView.addObject("ceremonies", ceremonies);
	modelAndView.addObject("participations", participations);
	modelAndView.addObject("reducPayload", reduc);
	modelAndView.addObject("paiementPayload", paiement);

	return modelAndView;
    }

    @GetMapping("/deletePaiement{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView deletePaiement(@PathVariable("id") long id) {
	ParticipationPayload pp = new ParticipationPayload();
	List<Ceremonie> ceremonies = ceremService.getAllActivesCeremoniesByDisplay(Display.CEREMONIE);
	List<ParticipationsTable> participations = participationService.getAllParticipationsTable();

	Facture facture = factureService.deletePaiement(id);
	FactureTable search = factureService.buildFactureTableFromParticipant(facture.getParticipant().getId());

	ReducPayload reduc = new ReducPayload();
	reduc.idParticipant = search.idParticipant;
	PaiementPayload paiement = new PaiementPayload();
	paiement.idParticipant = search.idParticipant;

	ModelAndView modelAndView = new ModelAndView("participation");
	modelAndView.addObject("search", search);
	modelAndView.addObject("participationPayload", pp);
	modelAndView.addObject("ceremonies", ceremonies);
	modelAndView.addObject("participations", participations);
	modelAndView.addObject("reducPayload", reduc);
	modelAndView.addObject("paiementPayload", paiement);

	return modelAndView;
    }


    /**** Test section ****/
    @GetMapping("/register")
    public String register(Model model) {
	SignupRequest signUpRequest = new SignupRequest();

	List<Role> roles = roleRepo.findAll();

	model.addAttribute("signUpRequest", signUpRequest);
	model.addAttribute("allRoles", roles);

	return "register";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
	return "User Content. Hello " + authenticationFacade.getConnectedUser();
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String moderatorAccess() {
	return "Moderator Board. Hello " + authenticationFacade.getAuthentication().getName();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
	return "Admin Board. Hello " + authenticationFacade.getAuthentication().getName();
    }
}