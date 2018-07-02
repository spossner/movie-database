package JavaKlausur;

import JavaKlausur.model.Benutzer;
import JavaKlausur.model.Bewertung;
import JavaKlausur.model.Film;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {
    private static Repository repository = null;

    @BeforeAll
    static void setUp() {
        try {
            repository = Repository.fillRepository("./movieproject.db");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBenutzer() {
        Benutzer melanieMcfarland = repository.getBenutzer("Melanie Mcfarland");
        assertEquals(521, melanieMcfarland.getBewertungen().size());

        Bewertung rating = melanieMcfarland.getBewertungen().stream().filter(r -> r.getFilm().getId() == 667).findFirst().orElse(null);
        assertNotNull(rating);
        assertNotNull(rating.getFilm());
        assertNotNull(rating.getBenutzer());

        assertEquals(new Integer(667), rating.getFilm().getId());
        assertEquals("Cable Guy, The", rating.getFilm().getName());

        assertEquals("Melanie Mcfarland", rating.getBenutzer().getName());

        assertEquals(1.0, rating.getRating());
    }

    @Test
    void getFilm() {
        Film film = repository.getFilm(8479);
        assertEquals("Day of the Doctor, The", film.getName());
        // @TODO empty String vs null -> what to do?
        assertTrue(film.getBeschreibung().isEmpty());
    }

    @Test
    void suchen() {
        List<Film> filmList = repository.suchen("Matrix");
        assertEquals(3, filmList.size());
        String[] titles = {"Matrix Revolutions, The", "Matrix Reloaded, The", "Matrix, The"};
        Set<String> expectedTitles = new HashSet<>(Arrays.asList(titles));
        for (Film f : filmList) {
            assertTrue(expectedTitles.contains(f.getName()), f.getName());
        }
    }

    @Test
    void suchenZweiFilme() {
        List<Film> filmList = repository.suchen("Matrix", 2);
        assertEquals(2, filmList.size());
        String[] titles = {"Matrix Revolutions, The", "Matrix Reloaded, The", "Matrix, The"};
        Set<String> expectedTitles = new HashSet<>(Arrays.asList(titles));
        for (Film f : filmList) {
            assertTrue(expectedTitles.contains(f.getName()), f.getName());
        }
    }

    @Test
    void suchenEinFilm() {
        List<Film> filmList = repository.suchen("Matrix", 1);
        assertEquals(1, filmList.size());
        String[] titles = {"Matrix Revolutions, The", "Matrix Reloaded, The", "Matrix, The"};
        Set<String> expectedTitles = new HashSet<>(Arrays.asList(titles));
        for (Film f : filmList) {
            assertTrue(expectedTitles.contains(f.getName()), f.getName());
        }
    }

    @Test
    void testFindMoviesByGenre() {
        List<Film> filme = repository.suchenMitGenre("Thriller,Action", 20000);
        assertEquals(2700, filme.size());

        filme = repository.suchenMitGenre("Thriller,Action", 1500);
        assertEquals(1500, filme.size());

        filme = repository.suchenMitGenre("Thriller,Action");
        assertEquals(200, filme.size());

        filme = repository.suchenMitGenre("Thriller,Action", 1);
        assertEquals(1, filme.size());
    }

    @Test
    void testFindMoviesByActor() {
        List<Film> filme = repository.suchenMitSchauspieler("Keanu Reeves,Hugo Weaving");
        assertEquals(37, filme.size());

        filme = repository.suchenMitSchauspieler("Keanu Reeves,Hugo Weaving", 30);
        assertEquals(30, filme.size());
    }

    @Test
    void testMovieHasDirector() {
        Film film = repository.getFilm(2081);
        String[] directors = { "Laurence Fishburne" };

        assertFalse(film.hatRegisseur(directors));

        String[] directors2 = { "Laurence Fishburne", "Andy Wachowski" };
        assertTrue(film.hatRegisseur(directors2));

        String[] directors3 = { "Andy Wachowski" };
        assertTrue(film.hatRegisseur(directors3));
    }

    @Test
    void testFindMovies() {
        List<Film> filme = repository.suchen(null,"Action", "Jason Statham,Keanu Reeves", null, 50);
        assertEquals(27, filme.size());
    }

    @Test
    void testMovieImport() {
        assertEquals(9125, repository.getFilme().size());
    }

    @Test
    void testActorImport() {
        // import all actors
        assertEquals(18159, repository.getSchauspieler().size());

        // import UTF-8 characters
        assertEquals("François Lallement", repository.getSchauspieler(9816).getName());

        // trim import actor names
        assertEquals("Bill Bambridge", repository.getSchauspieler(10067).getName());
    }

    /*
    @Test
    void testMoviesByRating() {
        Benutzer michaelStone = repository.getBenutzer("Michael Stone");
        List<Film> moviesByRating = michaelStone.findMoviesByRating(4);
        assertEquals(43, moviesByRating.size());
    }

    @Test
    void testUsersByRating() {
        Film matrixMovie = repository.getFilm(2081);
        List<Benutzer> usersByRating = matrixMovie.findUsersByRating(5);
        assertEquals(95, usersByRating.size());
    }

    @Test
    void testMoviesByUsers() {
        Film film = repository.getFilm(285);
        List<Film> movieRecommendations = repository.findMovieRecommendationsByMovies(film);
    }
    */
}