package JavaKlausur.model;

public class Bewertung {
    private Benutzer benutzer;
    private Film film;
    private double rating;

    public Bewertung(Benutzer benutzer, Film film, double rating) {
        this.benutzer = benutzer;
        this.film = film;
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public Film getFilm() {
        return film;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }
}
