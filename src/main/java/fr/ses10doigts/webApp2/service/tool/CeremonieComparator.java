package fr.ses10doigts.webApp2.service.tool;

import java.util.Comparator;

import fr.ses10doigts.webApp2.model.Ceremonie;

public class CeremonieComparator implements Comparator<Ceremonie> {

    @Override
    public int compare(Ceremonie o1, Ceremonie o2) {
	int i = 0;
	if (o1.getOrdre() < o2.getOrdre()) {
	    i = -1;
	} else if (o1.getOrdre() > o2.getOrdre()) {
	    i = 0;
	}

	return i;
    }

}
