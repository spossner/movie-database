package JavaKlausur.model;

import java.util.ArrayList;
import java.util.List;

public class Film {
	
	private int id;
	private String name;
	private String beschreibung;
	private String release;
	private int votes;
	private double rating;
	private List<Schauspieler> schauspieler = new ArrayList<Schauspieler>();
	private List<Regisseur> regisseure = new ArrayList<Regisseur>();
	private List<Genre> genre = new ArrayList<Genre>();
	private List<Bewertung> bewertungen = new ArrayList<>();
	
	
	public Film(int id, String name, String beschreibung, String release, int votes, double rating ) {
		this.id = id;
		this.name = name;
		this.beschreibung = beschreibung;
		this.release = release;
		this.votes = votes;
		this.rating = rating;
	}
	
	public Integer getId(){
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Schauspieler> getSchauspieler(){
		return schauspieler;
	}
	
	public List<Regisseur> getRegisseure(){
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

	@Override
	public String toString() {
		return "Film{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}


