package JavaKlausur;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class UtilsTest {
    @Test
    public void testSplit() {
        String line = "--genre='Thriller,Action'";// --actor='Keanu Reeves,Jason Statham' --film='Matrix' --limit=10";
        String[] words = Utils.splitAndTrimQuotes(line, "=");
        assertEquals(2, words.length);
        for (String s : words) {
            System.out.println(s);
        }

        String[] genres = Utils.splitAndTrimQuotes(words[1],",");
        assertEquals(2, genres.length);
        for (String s : genres) {
            System.out.println(s);
        }
    }
}