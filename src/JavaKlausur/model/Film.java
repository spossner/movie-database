package JavaKlausur.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Film {

    private int id;
    private String name;
    private String beschreibung;
    private String release;
    private int votes;
    private double rating;
    private List<Schauspieler> schauspieler = new ArrayList<>();
    private List<Regisseur> regisseure = new ArrayList<>();
    private List<Genre> genre = new ArrayList<>();
    private List<Bewertung> bewertungen = new ArrayList<>();


    public Film(int id, String name, String beschreibung, String release, int votes, double rating) {
        this.id = id;
        this.name = name;
        this.beschreibung = beschreibung;
        this.release = release;
        this.votes = votes;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Schauspieler> getSchauspieler() {
        return schauspieler;
    }

    public List<Regisseur> getRegisseure() {
        return regisseure;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public String getRelease() {
        return release;
    }

    public int getVotes() {
        return votes;
    }

    public double getRating() {
        return rating;
    }

    public List<Bewertung> getBewertungen() {
        return this.bewertungen;
    }

    public void addSchauspieler(Schauspieler actor) {
        schauspieler.add(actor);
    }

    public void addRegisseur(Regisseur regisseur) {
        regisseure.add(regisseur);
    }

    public void addGenre(Genre genre) {
        this.genre.add(genre);
    }

    public void addBewertung(Bewertung bewertung) {
        this.bewertungen.add(bewertung);
    }

    public boolean hasRegisseur(String[] names) {
        for (Regisseur r : this.regisseure) {
            for (String name : names) {
                if (r.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasGenre(String[] genres) {
        for (Genre g : this.genre) {
            for (String name : genres) {
                if (g.getName().equals(name)) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean hasSchauspieler(String[] names) {
        for (Schauspieler s : this.schauspieler) {
            for (String name : names) {
                if (s.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasInTitle(String[] names) {
        for (String name : names) {
            if (this.name.contains(name)) {
                return true;
            }
        }
        return false;
    }


    public List<Benutzer> getBenutzer(double minRating) {
        return this.bewertungen.stream().filter(b -> b.getRating() >= minRating).map(b -> b.getBenutzer()).collect(Collectors.toList());
    }

    @Override
    public String toString() {

        return String.format("%-6s  *%.1f  %-40s", id, rating, String.format("%s (%.4s)", name, release));

    }

    // Filme sind gleich, wenn gleiche Id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id;
    }

    // id als hashcode für Maps
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


