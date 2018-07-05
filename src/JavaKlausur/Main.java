package JavaKlausur;

import JavaKlausur.model.Film;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    static Repository repository = null;

    static String[][] OPTIONS = {
        {"h", "help", null, "Diese Hilfe."},
        {"g", "genre", "Genres", "Es werden bevorzugt Filme der gegebenen Genres vorgeschlagen."},
        {"a", "actor", "String", "Es werden bevorzugt Filme der gegebenen Schauspieler vorgeschlagen."},
        {"d", "director", "String", "Es werden bevorzugt Filme der gegebenen Regisseure vorgeschlagen."},
        {"f", "film", "String", "Es werden Filme vorgeschlagen, welche von Benutzern ebenfalls gut bewertet wurden."},
        {"l", "limit", "Number", "Maximale Anzahl Ergebnisse. Default ist 200."},
        {"t", "test", null, "Führt einen Test aus und schreibt das Ergebnis in result.txt."}
    };

    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            Map<String, String> options = parseOptions(args);
            if (options.containsKey("h")) {
                printHelp();
                System.exit(0);
            }
            List<Film> result = getRepository().suchen(options.get("f"), options.get("g"), options.get("a"), options.get("d"), options.containsKey("l") ? Integer.parseInt(options.get("l")) : 200);
            Utils.dump(result);
            System.exit(0);
        } else {

            System.out.println("interactive move");

            Scanner scan = new Scanner(System.in);
            String buffer;
            //@TODO println vs print.. und \n
            System.out.println("Suche nach deinem Film\n");
            buffer = scan.nextLine();
            System.out.println(buffer + "\n");

            List<Film> filme = getRepository().suchenMitTitel(buffer);
            //@TODO Object.toString -> siehe auch Film
            System.out.println("found " + filme);
            scan.close();
        }
    }

    private static Repository getRepository() {
        if (repository == null) {
            try {
                repository = Repository.fillRepository("./movieproject.db");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return repository;
    }

    private static Map<String, String> parseOptions(String[] args) {
        Map<String,String> options = new HashMap<>();
        try {
            for (String s : args) {
                String[] kv = Utils.splitAndTrimQuotes(s, "=", 2);
                String[] match = null;
                for (String[] option : OPTIONS) {
                    if (("-"+option[0]).equals(kv[0]) || ("--"+option[1]).equals(kv[0])) {
                        match = option;
                        break;
                    }
                }
                if (match == null) {
                    throw new IllegalArgumentException("unknown parameter " + kv[0] + " in " + s);
                }
                if (match[2] != null && kv[1] == null) {
                    throw new IllegalArgumentException("missing value of parameter " + kv[0] + " in " + s);
                }
                options.put(match[0], kv[1]);
            }
        } catch(IllegalArgumentException e) {
            System.err.println(e.getMessage());
            printHelp();
            System.exit(1);
        }
        System.out.println(options);
        return options;
    }

    private static void printHelp() {
        System.out.println("Usage:\t  java -jar programm.jar [OPTIONS]\n");
        for (String[] option : OPTIONS) {
            System.out.println(String.format(" %-26s  %s"," -"+option[0]+",--"+option[1]+(option[2] != null ? " <"+option[2]+">" : ""),option[3]));
        }
        System.out.println("\nKeine Options startet den interaktiven Modus.");
    }


}