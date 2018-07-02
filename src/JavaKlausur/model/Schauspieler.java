package JavaKlausur.model;

import java.util.ArrayList;
import java.util.List;


public class Schauspieler {
    private int id;
    private String name;
    private List<Film> filme = new ArrayList<Film>();


    public Schauspieler(int id, String name) {
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

    public void addFilm(Film Film) {
        filme.add(Film);
    }
}
