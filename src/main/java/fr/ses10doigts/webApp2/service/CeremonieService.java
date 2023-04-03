package fr.ses10doigts.webApp2.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.webApp2.model.Ceremonie;
import fr.ses10doigts.webApp2.model.Display;
import fr.ses10doigts.webApp2.repository.CeremonieRepository;

@Service
public class CeremonieService {

    @Autowired
    private CeremonieRepository ceremRepo;

    public boolean saveAll(List<Ceremonie> cerems) {

	boolean ok = true;
	List<Ceremonie> findAll = ceremRepo.findAll();
	if (findAll == null || findAll.size() == 0) {
	    ceremRepo.saveAll(cerems);
	} else {
	    ok = false;
	}

	return ok;
    }

    public void save(Ceremonie ceremonie) {
	ceremRepo.save(ceremonie);
    }


    public List<Ceremonie> getAllActivesCeremoniesByDisplay(Display display) {
	Set<Display> displays = new HashSet<>();
	displays.add(Display.BOTH);
	displays.add(display);

	List<Ceremonie> findByDisplay = ceremRepo.findByDisplayAndActif(displays);
	return findByDisplay;
    }

    public List<Ceremonie> getAllCeremoniesByDisplay(Display display) {
	Set<Display> displays = new HashSet<>();
	displays.add(Display.BOTH);
	displays.add(display);

	List<Ceremonie> findByDisplay = ceremRepo.findByDisplay(displays);
	return findByDisplay;
    }

    public Ceremonie getByName(String name) {
	return ceremRepo.findByNom(name);
    }

    public Ceremonie getCeremonie(long id) {
	return ceremRepo.findById(id).orElse(null);
    }

}
