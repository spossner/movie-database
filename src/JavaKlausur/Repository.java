//@TODO package einmal durch sprechen
package JavaKlausur;

import JavaKlausur.model.*;

import java.io.IOException;
import java.util.*;
import java.io.*;

public class Repository {
    private static final int MAX = 200;

    // @TODO was ist der Unterschied zwischen ArrayList und List bzw Map und Hashmap?
    // @TODO was ist ein Interface?
    private Map<Integer, Schauspieler> schauspieler = new HashMap<>();
    private Map<Integer, Film> filme = new HashMap<>();
    private Map<Integer, Regisseur> regisseure = new HashMap<>();
    private Map<String, Benutzer> benutzer = new HashMap<>();
    private List<Bewertung> bewertungen = new ArrayList<>();
    private Map<String, Genre> genre = new HashMap<>();

    public void addSchauspieler(Schauspieler schauspieler) {
        // was ist hier this.?
        this.schauspieler.put(schauspieler.getId(), schauspieler);
    }

    public void addFilm(Film film) {
        filme.put(film.getId(), film);
    }

    public void addRegisseur(Regisseur regisseur) {
        regisseure.put(regisseur.getId(), regisseur);
    }

    public void addUser(Benutzer benutzer) {
        this.benutzer.put(benutzer.getName(), benutzer);
    }

    public void addBewertung(Bewertung bewertung) {
        this.bewertungen.add(bewertung);
    }

    // @TODO was soll das?
    private Benutzer ensureBenutzer(String name) {
        Benutzer b = getBenutzer(name);
        if (b == null) {
            b = new Benutzer(name);
            addBenutzer(b);
        }
        return b;
    }

    public Collection<Film> getFilme() {
        return this.filme.values();
    }

    public Collection<Schauspieler> getSchauspieler() {
        return schauspieler.values();
    }

    public Collection<Regisseur> getRegisseure() {
        return regisseure.values();
    }

    public Collection<Benutzer> getBenutzer() {
        return benutzer.values();
    }

    public List<Bewertung> getBewertungen() {
        return bewertungen;
    }

    public Collection<Genre> getGenre() {
        return genre.values();
    }

    public void addBenutzer(Benutzer b) {
        this.benutzer.put(b.getName(), b);
    }

    public Benutzer getBenutzer(String name) {
        return this.benutzer.get(name);
    }

    public Regisseur getRegisseur(int id) {
        return this.regisseure.get(id);
    }

    public Genre getGenre(String name) {
        return this.genre.get(name);
    }

    private Genre ensureGenre(String name) {
        Genre g = getGenre(name);
        if (g == null) {
            g = new Genre(name);
            addGenre(g);
        }
        return g;
    }

    public void addGenre(Genre g) {
        this.genre.put(g.getName(), g);
    }


    // @TODO AUTO BOXING BESPRECHEN
    public Film getFilm(int id) {
        return this.filme.get(id);
    }

    public Schauspieler getSchauspieler(int id) {
        return this.schauspieler.get(id);
    }


    // @TODO STATIC vs NON-STATIC: ALLES NICHT STATIC MACHEN UND getSchauspieler(int id) etc benennen
    /*private static Schauspieler suchenSchauspieler(List<Schauspieler> liste, int id) {
        // @TODO hier mal beispielhaft durch gehen.. warum static? warum Liste der Schauspieler mit rein?
        for (int i = 0; i < liste.size(); i++) {
            if ((liste.get(i).getId()) == id) {
                return liste.get(i);
            }
        }
        return null;
    }

    private static Regisseur suchenRegisseur(List<Regisseur> liste, int id) {
        for (int i = 0; i < liste.size(); i++) {
            if ((liste.get(i).getId()) == id) {
                return liste.get(i);
            }
        }
        return null;
    }

    private static Film suchenFilm(List<Film> liste, int id) {
        for (int i = 0; i < liste.size(); i++) {
            if ((liste.get(i).getId()) == id) {
                return liste.get(i);
            }
        }
        return null;
    }

    private static Benutzer suchenBenutzer(List<Benutzer> liste, String name) {
        for (int i = 0; i < liste.size(); i++) {
            if ((liste.get(i).getName()).equals(name)) {
                return liste.get(i);
            }
        }
        return null;
    }

    private static Genre suchenGenre(List<Genre> liste, String name) {
        for (int i = 0; i < liste.size(); i++) {
            if ((liste.get(i).getName()).equals(name)) {
                return liste.get(i);
            }
        }
        return null;
    }

    private static Bewertung suchenBewertung(List<Bewertung> liste, String name, int movieId) {
        for (int i = 0; i < liste.size(); i++) {
            if ((liste.get(i).getBenutzer().getName()).equals(name)) {
                if ((liste.get(i).getFilm().getId()) == movieId) {
                    return liste.get(i);
                }
            }
        }
        return null;
    }*/


    //@TODO überladen von Methoden
    public List<Film> suchen(String film) {
        return suchen(film, MAX);
    }

    public List<Film> suchen(String film, int limit) {
        //@TODO limit berücksichtigen
        List<Film> result = new ArrayList<>();
        // @TODO for (int i=0; i<... vs for (T t : iteratable)..
        for (Film f : this.filme.values()) {
            if (f.getName().contains(film)) {
                result.add(f);
            }
        }
        return result;
    }

    public List<Film> suchenMitGenre(String name) {
        return suchenMitGenre(name, MAX);
    }

    public List<Film> suchenMitGenre(String name, int limit) {
        //@TODO implement mit name enthält kommaseparierte genres
        return null;
    }

    public List<Film> suchenMitSchauspieler(String name) {
        return suchenMitSchauspieler(name, MAX);
    }

    public List<Film> suchenMitSchauspieler(String name, int limit) {
        return null;
    }
    
    public List<Film> suchenMitRegisseur(String name) {
        return suchenMitRegisseur(name, MAX);
    }

    public List<Film> suchenMitRegisseur(String name, int limit) {
        return null;
    }

    public List<Film> suchen(String movieNames, String genreNames, String actorNames, String directorNames, int limit) {
        //@TODO implementieren
        //@TODO refactor suchenMitXYZ, so dass diese Methode genutzt wird
        return null;
    }

    // @TODO STATIC vs NON-STATIC
    /*
     * das hier soll so eine Art Factory sein, die ein Repository aus einer Datei erstellen kann... daher static
     * Das Ergebnis soll dann ein fertig gefülltes Repository sein..
     */
    public static Repository fillRepository(String fileName) throws IOException {
        // @TODO einmal Zeile für Zeile durch gehen..
        Repository repository = new Repository();

        // @TODO was ist das hier komisches? warum fr?
        BufferedReader fr = new BufferedReader(new FileReader(fileName));

        int entity = 0;
        // @TODO initiieren vs nicht initiieren... FINAL vs NON-FINAL
        String buffer;
        List<String> zeile;

        try {
            while ((buffer = fr.readLine()) != null) {
                if (buffer.startsWith("New_Entity:")) {
                    entity = entity + 1;
                } else if (entity > 0) {
                    switch (entity) {
                        case 1: // Schauspieler einlesen
                            zeile = zeileZerlegen(buffer);
                            repository.addSchauspieler(new Schauspieler(Integer.parseInt(zeile.get(0)), zeile.get(1)));
                            break;

                        case 2: // Filme einlesen
                            zeile = zeileZerlegen(buffer);
                            int id = Integer.parseInt(zeile.get(0));

                            Film f = repository.getFilm(id);
                            if (f == null) {
                                f = new Film(id, zeile.get(1), zeile.get(2), zeile.get(4), parseIntWithDefault(zeile.get(5), 0), parseDoubleWithDefault(zeile.get(6), 0.0));
                                repository.addFilm(f);
                            }

                            Genre g = repository.ensureGenre(zeile.get(3));
                            f.addGenre(g);
                            g.addFilm(f);

                            break;

                        case 3: // Regisseure einlesen
                            zeile = zeileZerlegen(buffer);
                            repository.addRegisseur(new Regisseur(Integer.parseInt(zeile.get(0)), zeile.get(1)));
                            break;

                        case 4: // Schauspieler des Films
                            zeile = zeileZerlegen(buffer);
                            Schauspieler s = repository.getSchauspieler(Integer.parseInt(zeile.get(0)));
                            f = repository.getFilm(Integer.parseInt(zeile.get(1)));
                            f.addSchauspieler(s);
                            s.addFilm(f);
                            break;

                        case 5: // Regisseur des Films
                            zeile = zeileZerlegen(buffer);
                            Regisseur r = repository.getRegisseur(Integer.parseInt(zeile.get(0)));
                            f = repository.getFilm(Integer.parseInt(zeile.get(1)));
                            f.addRegisseur(r);
                            r.addFilm(f);

                            break;

                        case 6: // bewertungen
                            zeile = zeileZerlegen(buffer);
                            Benutzer user = repository.ensureBenutzer(zeile.get(0));
                            f = repository.getFilm(Integer.parseInt(zeile.get(2)));

                            Bewertung rating = new Bewertung(user, f, Double.parseDouble(zeile.get(1)));

                            repository.addBewertung(rating);
                            user.addBewertung(rating);
                            f.addBewertung(rating);

                            // auch im Film die Bewertung setzen?
                            break;
                    }
                }
                // hier schließt Du den BufferedReader nach der ersten Zeile... da gibt es eine IOExcreption danach
                // fr.close(); // -> muss ganz am Ende passieren, wenn alles eingeselen ist
            }
        } finally {  // try  catch  finally   -> finally wird immer (egal ob eine Exception geflogen ist) ausgeführt.. perfekter Ort, um aufzuräumen
            fr.close();
        }
        return repository;
    }

    private static int parseIntWithDefault(String s, int i) {
        try {
            if (s != null && !s.isEmpty()) {
                return Integer.parseInt(s);
            }
        } catch (NumberFormatException e) {
        }
        return i;
    }

    private static double parseDoubleWithDefault(String s, double d) {
        try {
            if (s != null && !s.isEmpty()) {
                return Double.parseDouble(s);
            }
        } catch (NumberFormatException e) {
        }
        return d;
    }

    // hier mal durch gehen gemeinsam
    private static List<String> zeileZerlegen(String buffer) {
        boolean anfuehrungszeichen = false;
        String wort = "";
        List<String> zeile = new ArrayList<>();
        for (int i = 0; i < buffer.length(); i++) {
            if (buffer.charAt(i) == '"') {
                if (anfuehrungszeichen) {
                    zeile.add(wort.trim());
                    wort = "";
                    anfuehrungszeichen = false;
                } else {
                    anfuehrungszeichen = true;
                }
            } else {
                if (anfuehrungszeichen) {
                    // StringBuffer anschauen
                    wort = wort + buffer.charAt(i);
                }
            }
        }
        return zeile;
    }
}
	