package JavaKlausur.model;

import java.util.ArrayList;
import java.util.List;

public class Benutzer {
    private String username; // kannst das auch name nennen
    // @TODO new ArrayList<Bewertung>() vs new ArrayList<>() -> umbauen auf <>
    private List<Bewertung> bewertungen = new ArrayList<Bewertung>();

    public Benutzer(String username) {
        this.username = username;
    }

    public List<Bewertung> getBewertungen() {
        return bewertungen;
    }

    // @TODO bewertung als Map mit username als key?
    public void addBewertung(Bewertung bewertung) {
        this.bewertungen.add(bewertung);

		/*
		// @TODO einmal durch führen.. was hast Du Dir gedacht?
		boolean exist = false;
		for(int i = 0 ; i < bewertungen.size(); i++) {
			if((bewertungen.get(i).getBenutzer().getName()).equals(username)) {
				if((bewertungen.get(i).getFilm().getId()) == bewertung.getFilm().getId()) {
					exist = true;
					bewertungen.get(i).changeRating(bewertung.getRating());
					// hier könntest Du die Schleife abbrechen..
				}
			}
		}
		if(!exist) {
		bewertungen.add(bewertung);
		}*/
    }

    public String getName() {
        return username;
    }
}
