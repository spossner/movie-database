package JavaKlausur;

import JavaKlausur.model.Film;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
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
            Repository repository = Repository.fillRepository("./movieproject.db");
            List<Film> result = repository.suchen(options.get("f"), options.get("g"), options.get("a"), options.get("d"), options.containsKey("l") ? Integer.parseInt(options.get("l")) : 200);
            System.out.println(result);
            System.exit(0);
        } else {

            System.out.println("interactive move");

            Scanner scan = new Scanner(System.in);
            String buffer;
            //@TODO println vs print.. und \n
            System.out.println("Suche nach deinem Film\n");
            buffer = scan.nextLine();
            System.out.println(buffer + "\n");

            // currentTimeMillis
            long start = System.currentTimeMillis();
            //@TODO STATIC vs NON-STATIC -> dann rüber in Repository
            Repository repository = Repository.fillRepository("./movieproject.db");
            long end = System.currentTimeMillis();
            System.out.println("took " + (end - start) + "ms");
            List<Film> filme = repository.suchenMitTitel(buffer);
            //@TODO Object.toString -> siehe auch Film
            System.out.println("found " + filme);
            scan.close();
        }
    }

    private static Map<String, String> parseOptions(String[] args) {
        Map<String,String> options = new HashMap<>();
        try {
            for (String s : args) {
                String[] kv = s.split("=", 2);
                String key = kv[0];
                if (key.startsWith("--")) {
                    key = key.substring(2);
                } else if (key.startsWith("-")) {
                    key = key.substring(1);
                } else {
                    throw new IllegalArgumentException("key must start with - or --: " + s);
                }
                String value = null;
                if (kv.length == 2) {
                    value = kv[1];
                    if (value.startsWith("'")) {
                        if (value.endsWith("'")) {
                            value = value.substring(1, value.length() - 1);
                        } else {
                            throw new IllegalArgumentException("quoted parameter not closed: " + s);
                        }
                    }
                }
                String[] match = null;
                for (String[] option : OPTIONS) {
                    if (option[0].equals(key) || option[1].equals(key)) {
                        match = option;
                        break;
                    }
                }

                if (match == null) {
                    throw new IllegalArgumentException("unknown parameter " + key + " in " + s);
                }

                if (match[2] != null && value == null) {
                    throw new IllegalArgumentException("missing value of parameter " + key + " in " + s);
                }
                options.put(match[0], value);
            }
        } catch(IllegalArgumentException e) {
            System.err.println(e.getMessage());
            printHelp();
            System.exit(1);
        }
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