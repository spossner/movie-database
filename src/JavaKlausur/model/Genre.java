package JavaKlausur.model;

import java.util.ArrayList;
import java.util.List;

public class Genre {
    private String name;
    private List<Film> filme = new ArrayList<>();

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addFilm(Film film) {
        filme.add(film);
    }

    public List<Film> getFilme() {
        return filme;
    }
}
