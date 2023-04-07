package fr.ses10doigts.webApp2.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Display;
import fr.ses10doigts.webApp2.model.TypeCeremonie;
import fr.ses10doigts.webApp2.security.model.User;
import fr.ses10doigts.webApp2.security.service.IAuthenticationFacade;
import fr.ses10doigts.webApp2.service.CeremonieService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestApiController {
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private CeremonieService	  ceremServ;

    @GetMapping("/all")
    public String allAccess() {
	User user = null;
	try {
	    user = authenticationFacade.getConnectedUser();
	} catch (RuntimeException e) {
	}
	return "Public Content. " + (user != null ? user.toString() : "");
    }

    @GetMapping("/registerCeremonies")
    public String registerCeremonies() {

	List<Ceremonie> cerems = new ArrayList<>();

	Ceremonie temMar = new Ceremonie();
	temMar.setActif(true);
	temMar.setJour(new GregorianCalendar(2023, Calendar.MAY, 2).getTime());
	temMar.setPrix(20);
	temMar.setType(TypeCeremonie.TEMAZCAL);
	temMar.setNom("Temaz Mardi");
	temMar.setDisplay(Display.BOTH);
	temMar.setOrdre(0);
	cerems.add(temMar);

	Ceremonie tambour1 = new Ceremonie();
	tambour1.setActif(true);
	tambour1.setJour(new GregorianCalendar(2023, Calendar.MAY, 3).getTime());
	tambour1.setPrix(120);
	tambour1.setType(TypeCeremonie.TAMBOUR1);
	tambour1.setNom("Tambour 33");
	tambour1.setDisplay(Display.CEREMONIE);
	tambour1.setOrdre(10);
	cerems.add(tambour1);

	Ceremonie tambour2 = new Ceremonie();
	tambour2.setActif(true);
	tambour2.setJour(new GregorianCalendar(2023, Calendar.MAY, 3).getTime());
	tambour2.setPrix(150);
	tambour2.setType(TypeCeremonie.TAMBOUR2);
	tambour2.setNom("Tambour 36");
	tambour2.setDisplay(Display.CEREMONIE);
	tambour2.setOrdre(20);
	cerems.add(tambour2);

	Ceremonie tambour3 = new Ceremonie();
	tambour3.setActif(true);
	tambour3.setJour(new GregorianCalendar(2023, Calendar.MAY, 3).getTime());
	tambour3.setPrix(150);
	tambour3.setType(TypeCeremonie.TAMBOUR3);
	tambour3.setNom("Tambour 38");
	tambour3.setDisplay(Display.CEREMONIE);
	tambour3.setOrdre(30);
	cerems.add(tambour3);

	Ceremonie tambourSouhait = new Ceremonie();
	tambourSouhait.setActif(true);
	tambourSouhait.setJour(new GregorianCalendar(2023, Calendar.MAY, 3).getTime());
	tambourSouhait.setPrix(150);
	tambourSouhait.setType(TypeCeremonie.TAMBOUR1);
	tambourSouhait.setNom("Tambour");
	tambourSouhait.setDisplay(Display.SOUHAIT);
	tambourSouhait.setOrdre(40);
	cerems.add(tambourSouhait);

	Ceremonie mineraux = new Ceremonie();
	mineraux.setActif(true);
	mineraux.setJour(new GregorianCalendar(2023, Calendar.MAY, 3).getTime());
	mineraux.setPrix(20);
	mineraux.setType(TypeCeremonie.MINERAUX);
	mineraux.setNom("Mineraux");
	mineraux.setDisplay(Display.BOTH);
	mineraux.setOrdre(50);
	cerems.add(mineraux);

	Ceremonie rome = new Ceremonie();
	rome.setActif(true);
	rome.setJour(new GregorianCalendar(2023, Calendar.MAY, 3).getTime());
	rome.setPrix(20);
	rome.setType(TypeCeremonie.ROME);
	rome.setNom("Cercle Romé");
	rome.setDisplay(Display.BOTH);
	rome.setOrdre(60);
	cerems.add(rome);

	Ceremonie chants = new Ceremonie();
	chants.setActif(true);
	chants.setJour(new GregorianCalendar(2023, Calendar.MAY, 3).getTime());
	chants.setPrix(20);
	chants.setType(TypeCeremonie.CHANT);
	chants.setNom("Cercle Chants");
	chants.setDisplay(Display.BOTH);
	chants.setOrdre(70);
	cerems.add(chants);

	Ceremonie prepa = new Ceremonie();
	prepa.setActif(true);
	prepa.setJour(new GregorianCalendar(2023, Calendar.MAY, 4).getTime());
	prepa.setPrix(0);
	prepa.setType(TypeCeremonie.PREPA_INTENTIONS);
	prepa.setNom("Prepa intent");
	prepa.setDisplay(Display.BOTH);
	prepa.setOrdre(80);
	cerems.add(prepa);

	Ceremonie gmJeudi = new Ceremonie();
	gmJeudi.setActif(true);
	gmJeudi.setJour(new GregorianCalendar(2023, Calendar.MAY, 4).getTime());
	gmJeudi.setPrix(120);
	gmJeudi.setType(TypeCeremonie.GRANDMERE);
	gmJeudi.setNom("GM Jeudi");
	gmJeudi.setDisplay(Display.BOTH);
	gmJeudi.setOrdre(90);
	cerems.add(gmJeudi);

	Ceremonie gmVend = new Ceremonie();
	gmVend.setActif(true);
	gmVend.setJour(new GregorianCalendar(2023, Calendar.MAY, 5).getTime());
	gmVend.setPrix(120);
	gmVend.setType(TypeCeremonie.GRANDMERE);
	gmVend.setNom("GM Vendredi");
	gmVend.setDisplay(Display.BOTH);
	gmVend.setOrdre(100);
	cerems.add(gmVend);

	Ceremonie wilka = new Ceremonie();
	wilka.setActif(true);
	wilka.setJour(new GregorianCalendar(2023, Calendar.MAY, 5).getTime());
	wilka.setPrix(120);
	wilka.setType(TypeCeremonie.PRINCESSE);
	wilka.setNom("Wilka");
	wilka.setDisplay(Display.BOTH);
	wilka.setOrdre(110);
	cerems.add(wilka);

	Ceremonie ninos = new Ceremonie();
	ninos.setActif(true);
	ninos.setJour(new GregorianCalendar(2023, Calendar.MAY, 6).getTime());
	ninos.setPrix(120);
	ninos.setType(TypeCeremonie.NINOS);
	ninos.setNom("Ninos");
	ninos.setDisplay(Display.BOTH);
	ninos.setOrdre(120);
	cerems.add(ninos);

	Ceremonie homm = new Ceremonie();
	homm.setActif(true);
	homm.setJour(new GregorianCalendar(2023, Calendar.MAY, 7).getTime());
	homm.setPrix(120);
	homm.setType(TypeCeremonie.HOMMAGE);
	homm.setNom("Hom Lieu");
	homm.setDisplay(Display.BOTH);
	homm.setOrdre(130);
	cerems.add(homm);

	Ceremonie temDim = new Ceremonie();
	temDim.setActif(true);
	temDim.setJour(new GregorianCalendar(2023, Calendar.MAY, 7).getTime());
	temDim.setPrix(20);
	temDim.setType(TypeCeremonie.TEMAZCAL);
	temDim.setNom("Temaz Dim");
	temDim.setDisplay(Display.BOTH);
	temDim.setOrdre(140);
	cerems.add(temDim);

	Ceremonie gre1 = new Ceremonie();
	gre1.setActif(true);
	gre1.setJour(null);
	gre1.setPrix(50);
	gre1.setType(TypeCeremonie.GRENOUILLE1);
	gre1.setNom("Kambo x1");
	gre1.setDisplay(Display.SOUHAIT);
	gre1.setOrdre(150);
	cerems.add(gre1);

	Ceremonie gre2 = new Ceremonie();
	gre2.setActif(true);
	gre2.setJour(null);
	gre2.setPrix(100);
	gre2.setType(TypeCeremonie.GRENOUILLE2);
	gre2.setNom("Kambo x2");
	gre2.setDisplay(Display.SOUHAIT);
	gre2.setOrdre(160);
	cerems.add(gre2);

	Ceremonie gre3 = new Ceremonie();
	gre3.setActif(true);
	gre3.setJour(null);
	gre3.setPrix(120);
	gre3.setType(TypeCeremonie.GRENOUILLE3);
	gre3.setNom("Kambo x3");
	gre3.setDisplay(Display.SOUHAIT);
	gre3.setOrdre(170);
	cerems.add(gre3);

	Ceremonie greMar = new Ceremonie();
	greMar.setActif(true);
	greMar.setJour(new GregorianCalendar(2023, Calendar.MAY, 2).getTime());
	greMar.setPrix(50);
	greMar.setType(TypeCeremonie.GRENOUILLE1);
	greMar.setNom("Kambo Mardi");
	greMar.setDisplay(Display.CEREMONIE);
	greMar.setOrdre(180);
	cerems.add(greMar);

	Ceremonie greMer = new Ceremonie();
	greMer.setActif(true);
	greMer.setJour(new GregorianCalendar(2023, Calendar.MAY, 3).getTime());
	greMer.setPrix(50);
	greMer.setType(TypeCeremonie.GRENOUILLE1);
	greMer.setNom("Kambo Mercredi");
	greMer.setDisplay(Display.CEREMONIE);
	greMer.setOrdre(190);
	cerems.add(greMer);

	Ceremonie greJeu = new Ceremonie();
	greJeu.setActif(true);
	greJeu.setJour(new GregorianCalendar(2023, Calendar.MAY, 4).getTime());
	greJeu.setPrix(50);
	greJeu.setType(TypeCeremonie.GRENOUILLE1);
	greJeu.setNom("Kambo Jeudi");
	greJeu.setDisplay(Display.CEREMONIE);
	greJeu.setOrdre(200);
	cerems.add(greJeu);

	Ceremonie greVen = new Ceremonie();
	greVen.setActif(true);
	greVen.setJour(new GregorianCalendar(2023, Calendar.MAY, 5).getTime());
	greVen.setPrix(50);
	greVen.setType(TypeCeremonie.GRENOUILLE1);
	greVen.setNom("Kambo Vendredi");
	greVen.setDisplay(Display.CEREMONIE);
	greVen.setOrdre(210);
	cerems.add(greVen);

	Ceremonie greSam = new Ceremonie();
	greSam.setActif(true);
	greSam.setJour(new GregorianCalendar(2023, Calendar.MAY, 6).getTime());
	greSam.setPrix(50);
	greSam.setType(TypeCeremonie.GRENOUILLE1);
	greSam.setNom("Kambo Samedi");
	greSam.setDisplay(Display.CEREMONIE);
	greSam.setOrdre(220);
	cerems.add(greSam);

	Ceremonie greDim = new Ceremonie();
	greDim.setActif(true);
	greDim.setJour(new GregorianCalendar(2023, Calendar.MAY, 7).getTime());
	greDim.setPrix(50);
	greDim.setType(TypeCeremonie.GRENOUILLE1);
	greDim.setNom("Kambo Dimanche");
	greDim.setDisplay(Display.CEREMONIE);
	greDim.setOrdre(230);
	cerems.add(greDim);

	Ceremonie repas = new Ceremonie();
	repas.setActif(true);
	repas.setJour(null);
	repas.setPrix(15);
	repas.setType(TypeCeremonie.REPAS);
	repas.setNom("Repas");
	repas.setDisplay(Display.CEREMONIE);
	repas.setOrdre(240);
	cerems.add(repas);

	Ceremonie dej = new Ceremonie();
	dej.setActif(true);
	dej.setJour(null);
	dej.setPrix(10);
	dej.setType(TypeCeremonie.REPAS_DEJ);
	dej.setNom("Déjeuné");
	dej.setDisplay(Display.CEREMONIE);
	dej.setOrdre(250);
	cerems.add(dej);

	Ceremonie lieu = new Ceremonie();
	lieu.setActif(true);
	lieu.setJour(null);
	lieu.setPrix(15);
	lieu.setType(TypeCeremonie.LIEU);
	lieu.setNom("Lieu");
	lieu.setDisplay(Display.CEREMONIE);
	lieu.setOrdre(260);
	cerems.add(lieu);

	String msg = "cérémonie enregistrée en base de données";
	if (!ceremServ.saveAll(cerems)) {
	    msg = "Cérémonies non mises à jour car elles sont déjà présentes : vider la table";
	}

	return msg;
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