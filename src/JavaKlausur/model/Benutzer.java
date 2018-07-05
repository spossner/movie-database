package JavaKlausur.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Benutzer {
    private String name;
    private List<Bewertung> bewertungen = new ArrayList<>();

    public Benutzer(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public List<Bewertung> getBewertungen() {
        return bewertungen;
    }

    public void addBewertung(Bewertung bewertung) {
        this.bewertungen.add(bewertung);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Benutzer benutzer = (Benutzer) o;
        return Objects.equals(name, benutzer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
