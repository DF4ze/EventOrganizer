package fr.ses10doigts.webApp2.service.tool;

import java.util.Comparator;

import fr.ses10doigts.webApp2.model.Participation;

public class ParticipationComparator implements Comparator<Participation> {

    @Override
    public int compare(Participation o1, Participation o2) {

	int i = 0;
	if (o1.getCeremonie().getOrdre() < o2.getCeremonie().getOrdre()) {
	    i = -1;
	} else if (o1.getCeremonie().getOrdre() > o2.getCeremonie().getOrdre()) {
	    i = 0;
	}

	return i;
    }

}
