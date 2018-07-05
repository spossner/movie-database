package JavaKlausur.model;

import java.util.ArrayList;
import java.util.List;

public class Regisseur {

    private int id;
    private String name;
    private List<Film> filme = new ArrayList<>();


    public Regisseur(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Film> getFilme() {
        return filme;
    }

    public void addFilm(Film film) {
        filme.add(film);
    }
}
