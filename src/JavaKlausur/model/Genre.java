package JavaKlausur.model;

import java.util.ArrayList;
import java.util.List;
public class Genre {
	private String name;
	private List<Film> filme = new ArrayList<Film>();
	
	public Genre(String name) {
		this.name = name;
	}
	
	public void addFilm(Film film) {
		filme.add(film);
	}
	
	public List<Film> getFilme(){
		return filme;
	}
	
	public String getName() {
		return name;
	}
}
