package JavaKlausur;

import JavaKlausur.model.Film;

import java.io.IOException;
import java.util.*;


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

    static String[][] COMMANDS = {
            {"user [<name>]", "Wechselt den Benutzer"},
            {"list [<film>]", "Zeigt Filme an"},
            {"film <id>", "Zeigt die Details zu dem Film mit der ID an"},
            {"ratings", "Zeigt die Bewertungen des gesetzten Benutzers"},
            {"recos", "Filmempfehlungen"},
            {"options", "Zeigt die konfigurierbaren Optionen an"},
            {"set <option>=<value>", "setzt eine Option"},
            {"clear <option>", "löscht die Option"},
            {"quit", "Programm beenden"},
            {"help", "Diese Liste"}

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
            System.out.println("MOVIE DATABASE\n");
            System.out.println("loading data...");

            Repository repository = Repository.fillRepository("./movieproject.db");
            System.out.println("loaded " + repository.getFilme().size()+" movies");

            printCommands();

            Scanner scan = new Scanner(System.in);
            Map<String, String> options = new TreeMap<>();  // stores session options sorted
            options.put("user", null);
            for (String[] option : OPTIONS) {
                if (option[2] != null) {
                    options.put(option[1],null);
                }
            }
            String buffer = null;
            try {
                do {
                    System.out.printf("%s$ ", options.get("user") != null ? options.get("user") : "");
                    buffer = scan.nextLine();

                    try {
                        if (buffer.equalsIgnoreCase("quit")) {
                            System.exit(0);
                        } else if (buffer.equalsIgnoreCase("help")) {
                            printCommands();
                        } else if (buffer.toLowerCase().startsWith("list")) {
                            String[] kv = Utils.splitAndTrimQuotes(buffer, " ", 2);
                            if (kv.length == 2) {
                                options.put("film", kv[1]);
                            }
                            List<Film> filmList = repository.suchen(options.get("film"), options.get("genre"), options.get("actor"), options.get("director"), options.get("limit") != null ? Integer.parseInt(options.get("limit")) : 200);
                            System.out.printf(filmList.size()+" Filme (%s, %s, %s, %s, %s) gefunden:\n\n",options.get("film"), options.get("genre"), options.get("actor"), options.get("director"), options.get("limit") != null ? Integer.parseInt(options.get("limit")) : 200);
                            Utils.dump(filmList);
                        } else if (buffer.toLowerCase().startsWith("film")) {
                            String[] kv = Utils.splitAndTrimQuotes(buffer, " ", 2);
                            if (kv.length == 2) {
                                System.out.println(repository.getFilm(Integer.parseInt(kv[1])));
                            } else {
                                throw new IllegalArgumentException("Film id fehlt in "+buffer);
                            }
                        } else if (buffer.equalsIgnoreCase("options")) {
                            printOptions(options);
                        } else if (buffer.toLowerCase().startsWith("set ")) {
                            String[] kv = Utils.splitAndTrimQuotes(buffer.substring(4), "=", 2);
                            if (kv.length == 2) {
                                if (!options.containsKey(kv[0])) {
                                    throw new IllegalArgumentException("Unbekannte Option "+kv[0]);
                                }
                                options.put(kv[0], kv[1]);
                                printOptions(options);
                            } else {
                                throw new IllegalArgumentException("Ungültige Eingabe " + buffer);
                            }
                        } else if (buffer.toLowerCase().startsWith("clear ")) {
                            String option = buffer.substring(6).trim();
                            if (!options.containsKey(option)) {
                                throw new IllegalArgumentException("Unbekannte Option "+option);
                            }
                            options.put(option, null);
                            printOptions(options);
                        } else {
                            throw new IllegalArgumentException("Unbekannter Befehl "+buffer);
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        printCommands();
                    }

                } while (buffer != null);
            } finally {
                scan.close();
            }
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
        printOptions(options);
        return options;
    }

    private static void printHelp() {
        System.out.println("Usage:\t  java -jar programm.jar [OPTIONS]\n");
        for (String[] option : OPTIONS) {
            System.out.println(String.format(" %-26s  %s"," -"+option[0]+",--"+option[1]+(option[2] != null ? " <"+option[2]+">" : ""),option[3]));
        }
        System.out.println("\nKeine Options startet den interaktiven Modus.");
    }

    private static void printCommands() {
        for (String[] command : COMMANDS) {
            System.out.printf("%-20s  %s\n", command[0], command[1]);
        }
        System.out.println();
    }

    private static void printOptions(Map<String, String> options) {
        System.out.println("OPTION     WERT");
        for (Map.Entry<String, String> e : options.entrySet()) {
            System.out.printf("%-10s %s\n", e.getKey(), e.getValue() != null ? e.getValue() : "-");
        }
        System.out.println();
    }
}