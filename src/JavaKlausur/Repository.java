package JavaKlausur;

import JavaKlausur.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Repository {
    private static final int MAX = 200;

    private Map<Integer, Schauspieler> schauspieler = new HashMap<>();
    private Map<Integer, Film> filme = new HashMap<>();
    private Map<Integer, Regisseur> regisseure = new HashMap<>();
    private Map<String, Benutzer> benutzer = new HashMap<>();
    private List<Bewertung> bewertungen = new ArrayList<>();
    private Map<String, Genre> genre = new HashMap<>();

    public void addSchauspieler(Schauspieler schauspieler) {
        this.schauspieler.put(schauspieler.getId(), schauspieler);
    }

    public void addFilm(Film film) {
        filme.put(film.getId(), film);
    }

    public void addRegisseur(Regisseur regisseur) {
        regisseure.put(regisseur.getId(), regisseur);
    }

    public void addBewertung(Bewertung bewertung) {
        this.bewertungen.add(bewertung);
    }

    public Benutzer ensureBenutzer(String name) {
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

    public Genre ensureGenre(String name) {
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

    public Film getFilm(int id) {
        return this.filme.get(id);
    }

    public Schauspieler getSchauspieler(int id) {
        return this.schauspieler.get(id);
    }


    public List<Film> suchenMitTitel(String name) {
        return suchenMitTitel(name, MAX);
    }

    public List<Film> suchenMitTitel(String name, int limit) {
        List<Film> result = new ArrayList<>();
        String[] names = name.split(",");
        for (Film f : this.filme.values()) {
            if (f.hasInTitle(names)) {
                result.add(f);
                if (result.size() >= limit) {
                    return result;
                }
            }
        }
        return result;
    }

    public List<Film> suchenMitGenre(String name) {
        return suchenMitGenre(name, MAX);
    }

    public List<Film> suchenMitGenre(String name, int limit) {
        return suchen(null, name, null, null, limit);
    }

    public List<Film> suchenMitSchauspieler(String name) {
        return suchenMitSchauspieler(name, MAX);
    }

    public List<Film> suchenMitSchauspieler(String name, int limit) {
        return suchen(null, null, name, null, limit);
    }
    
    public List<Film> suchenMitRegisseur(String name) {
        return suchenMitRegisseur(name, MAX);
    }

    public List<Film> suchenMitRegisseur(String name, int limit) {
        return suchen(null, null, null, name, limit);
    }

    public List<Film> suchen(String fromMovies, String genreFilters, String actorFilters, String directorFilters) {
        return suchen(fromMovies, genreFilters, actorFilters, directorFilters, MAX);
    }

    public List<Film> suchen(String fromMovies, String genreFilters, String actorFilters, String directorFilters, int limit) {
        return suchen(fromMovies,null,genreFilters,actorFilters,directorFilters,limit);
    }

    public List<Film> suchen(String fromMovies, Benutzer fromUser, String genreFilters, String actorFilters, String directorFilters, int limit) {
    	List<Film> result = new ArrayList<>();

        Collection<Film> filmList = null;

        if (fromMovies != null) {
            filmList = new TreeSet<>(Comparator.comparingDouble(Film::getRating).reversed()); // sort by Film.rating desc

            // für alle Filmen mit dem (Teil)Titel
            List<Film> filmsByTitle = suchenMitTitel(fromMovies);
            // ..Benutzer finden, die die auch gut finden und deren guten Filme raus suchen
            for (Film f : filmsByTitle) {
                List<Benutzer> benutzer = f.getBenutzer(4.0);
                for (Benutzer b : benutzer) {
                    filmList.addAll(b.getFilme(4.0));
                }
            }
        } else if (fromUser != null) {
            filmList = new TreeSet<>(Comparator.comparingDouble(Film::getRating).reversed()); // sort by Film.rating desc

            // für alle guten Filmen des fromUser
            List<Film> filmsByTitle = fromUser.getFilme(4.0);
            // ..Benutzer finden, die die auch gut finden und deren guten Filme raus suchen
            for (Film f : filmsByTitle) {
                List<Benutzer> benutzer = f.getBenutzer(4.0);
                for (Benutzer b : benutzer) {
                    filmList.addAll(b.getFilme(4.0));
                }
            }
        } else {
            filmList = filme.values();
        }

        // FILTER MOVIE LIST

        // split all parameters with comma as separator; ignore null values
    	String[] genres = (genreFilters != null ? genreFilters.split(",") : null);
    	String[] actors = (actorFilters != null ? actorFilters.split(",") : null);
    	String[] directors = (directorFilters != null ? directorFilters.split(",") : null);

        for (Film f : filmList) {
            if (    (genres == null || f.hasGenre(genres)) &&
                    (actors == null || f.hasSchauspieler(actors)) &&
                    (directors == null || f.hasRegisseur(directors))) {
                result.add(f);
                if (result.size() >= limit) {
                	return result;
                }
            }
        }
        
        return result;
    }

    public static Repository fillRepository(String fileName) throws IOException {
        Repository repository = new Repository();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        int entity = 0;
        String buffer = null;

        long start = System.currentTimeMillis();
        
        try {
            while ((buffer = reader.readLine()) != null) {
                if (buffer.startsWith("New_Entity:")) {
                    entity = entity + 1;
                } else if (entity > 0) {
                    switch (entity) {
                        case 1: // Schauspieler einlesen
                            List<String> zeile = Utils.zeileZerlegen(buffer);
                            repository.addSchauspieler(new Schauspieler(Integer.parseInt(zeile.get(0)), zeile.get(1)));

                            break;

                        case 2: // Filme einlesen
                            zeile = Utils.zeileZerlegen(buffer);
                            int id = Integer.parseInt(zeile.get(0));

                            Film f = repository.getFilm(id);
                            if (f == null) {
                                f = new Film(id, zeile.get(1), zeile.get(2), zeile.get(4), Utils.toInt(zeile.get(5)), Utils.toDouble(zeile.get(6)));
                                repository.addFilm(f);
                            }

                            Genre g = repository.ensureGenre(zeile.get(3));
                            f.addGenre(g);

                            // @TODO brauchen wir das wirklich? Und auch Genre als Klasse?
                            g.addFilm(f);

                            break;

                        case 3: // Regisseure einlesen
                            zeile = Utils.zeileZerlegen(buffer);
                            repository.addRegisseur(new Regisseur(Integer.parseInt(zeile.get(0)), zeile.get(1)));

                            break;

                        case 4: // Schauspieler des Films
                            zeile = Utils.zeileZerlegen(buffer);
                            Schauspieler s = repository.getSchauspieler(Integer.parseInt(zeile.get(0)));
                            f = repository.getFilm(Integer.parseInt(zeile.get(1)));
                            f.addSchauspieler(s);
                            s.addFilm(f);

                            break;

                        case 5: // Regisseur des Films
                            zeile = Utils.zeileZerlegen(buffer);
                            Regisseur r = repository.getRegisseur(Integer.parseInt(zeile.get(0)));
                            f = repository.getFilm(Integer.parseInt(zeile.get(1)));
                            f.addRegisseur(r);
                            r.addFilm(f);

                            break;

                        case 6: // bewertungen
                            zeile = Utils.zeileZerlegen(buffer);
                            Benutzer user = repository.ensureBenutzer(zeile.get(0));
                            f = repository.getFilm(Integer.parseInt(zeile.get(2)));

                            repository.addBewertung(user, f, Double.parseDouble(zeile.get(1)));

                            break;
                    }
                }
            }
        } finally {
            reader.close();
        }
        
        long end = System.currentTimeMillis();
        System.out.println("loading repository took "+(end-start)+"ms");
        
        return repository;
    }

    public Bewertung addBewertung(Benutzer user, Film f, double rating) {
        Bewertung b = new Bewertung(user, f, rating);
        this.addBewertung(b);
        user.addBewertung(b);
        f.addBewertung(b);
        return b;
    }

    public List<Bewertung> addCustomRatings(String fileName) throws IOException {
        List<Bewertung> bewertungen = new ArrayList<>();

        File r = new File(fileName);
        if (r.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(r));
            String buffer = null;
            try {
                while ((buffer = reader.readLine()) != null) {
                    List<String> zeile = Utils.zeileZerlegen(buffer);
                    Benutzer user = ensureBenutzer(zeile.get(0));
                    Film f = getFilm(Integer.parseInt(zeile.get(2)));
                    bewertungen.add(addBewertung(user, f, Double.parseDouble(zeile.get(1))));
                }
            } finally {
                reader.close();
            }
        }
        return bewertungen;
    }
}
	