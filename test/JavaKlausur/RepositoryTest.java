package JavaKlausur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;

import JavaKlausur.model.Benutzer;
import JavaKlausur.model.Bewertung;
import JavaKlausur.model.Film;

import javax.naming.Name;

public class RepositoryTest {
    private static Repository repository = null;

    @BeforeClass
    public static void setUp() {
        try {
            repository = Repository.fillRepository("./movieproject.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBenutzer() {
        Benutzer melanieMcfarland = repository.getBenutzer("Melanie Mcfarland");
        assertEquals(521, melanieMcfarland.getBewertungen().size());

        Bewertung rating = melanieMcfarland.getBewertungen().stream().filter(r -> r.getFilm().getId() == 667).findFirst().orElse(null);
        assertNotNull(rating);
        assertNotNull(rating.getFilm());
        assertNotNull(rating.getBenutzer());

        assertEquals(new Integer(667), rating.getFilm().getId());
        assertEquals("Cable Guy, The", rating.getFilm().getName());

        assertEquals("Melanie Mcfarland", rating.getBenutzer().getName());

        assertEquals(1.0, rating.getRating(), 0.0001);
    }

    @Test
    public void getFilm() {
        Film film = repository.getFilm(8479);
        assertEquals("Day of the Doctor, The", film.getName());
        // @TODO empty String vs null -> what to do?
        assertTrue(film.getBeschreibung().isEmpty());
    }

    @Test
    public void suchen() {
        List<Film> filmList = repository.suchenMitTitel("Matrix");
        assertEquals(3, filmList.size());
        String[] titles = {"Matrix Revolutions, The", "Matrix Reloaded, The", "Matrix, The"};
        Set<String> expectedTitles = new HashSet<>(Arrays.asList(titles));
        for (Film f : filmList) {
            assertTrue(f.getName(), expectedTitles.contains(f.getName()));
        }
        Utils.dump(filmList);
    }

    @Test
    public void suchenZweiFilme() {
        List<Film> filmList = repository.suchenMitTitel("Matrix", 2);
        assertEquals(2, filmList.size());
        String[] titles = {"Matrix Revolutions, The", "Matrix Reloaded, The", "Matrix, The"};
        Set<String> expectedTitles = new HashSet<>(Arrays.asList(titles));
        for (Film f : filmList) {
            assertTrue(f.getName(), expectedTitles.contains(f.getName()));
        }
    }

    @Test
    public void suchenEinFilm() {
        List<Film> filmList = repository.suchenMitTitel("Matrix", 1);
        assertEquals(1, filmList.size());
        String[] titles = {"Matrix Revolutions, The", "Matrix Reloaded, The", "Matrix, The"};
        Set<String> expectedTitles = new HashSet<>(Arrays.asList(titles));
        for (Film f : filmList) {
            assertTrue(f.getName(), expectedTitles.contains(f.getName()));
        }
    }

    @Test
    public void testFindMoviesByGenre() {
        List<Film> filme = repository.suchenMitGenre("Thriller,Action", 20000);
        assertEquals(2700, filme.size());

        filme = repository.suchenMitGenre("Thriller,Action", 1500);
        assertEquals(1500, filme.size());

        filme = repository.suchenMitGenre("Thriller,Action");
        assertEquals(200, filme.size());
        Utils.dump(filme);


        filme = repository.suchenMitGenre("Thriller,Action", 1);
        assertEquals(1, filme.size());
    }

    @Test
    public void testFindMoviesByActor() {
        List<Film> filme = repository.suchenMitSchauspieler("Keanu Reeves,Hugo Weaving");
        assertEquals(37, filme.size());

        filme = repository.suchenMitSchauspieler("Keanu Reeves,Hugo Weaving", 30);
        assertEquals(30, filme.size());
    }

    @Test
    public void testMovieHasDirector() {
        Film film = repository.getFilm(2081);
        String[] directors = { "Laurence Fishburne" };

        assertFalse(film.hasRegisseur(directors));

        String[] directors2 = { "Laurence Fishburne", "Andy Wachowski" };
        assertTrue(film.hasRegisseur(directors2));

        String[] directors3 = { "Andy Wachowski" };
        assertTrue(film.hasRegisseur(directors3));
    }

    @Test
    public void testFindMovies() {
        List<Film> filme = repository.suchen(null,"Action", "Jason Statham,Keanu Reeves", null, 50);
        assertEquals(27, filme.size());
    }

    @Test
    public void testMovieImport() {
        assertEquals(9125, repository.getFilme().size());
    }

    @Test
    public void testActorImport() {
        // import all actors
        assertEquals(18159, repository.getSchauspieler().size());

        // import UTF-8 characters
        assertEquals("François Lallement", repository.getSchauspieler(9816).getName());

        // trim import actor names
        assertEquals("Bill Bambridge", repository.getSchauspieler(10067).getName());
    }

    @Test
    public void testFindMoviesWithGoodRating() {
        List<Film> filmList = repository.suchen("Matrix", null, null, null, 2000);
        assertEquals(1452, filmList.size());
    }

    @Test
    public void testMoviesByRating() {
        Benutzer michaelStone = repository.getBenutzer("Michael Stone");
        List<Film> moviesByRating = michaelStone.getFilme(4.0);
        assertEquals(43, moviesByRating.size());
    }

    @Test
    public void testUsersByRating() {
        Film matrixMovie = repository.getFilm(2081);
        List<Benutzer> benutzer = matrixMovie.getBenutzer(5.0);
        assertEquals(95, benutzer.size());
    }
/*
    @Test
    public void testMoviesByUsers() {
        Film film = repository.getFilm(285);
        List<Film> movieRecommendations = repository.findMovieRecommendationsByMovies(film);
    }*/
}