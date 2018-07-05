package JavaKlausur;

import JavaKlausur.model.Film;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {
    	for (String s : args) {
    		System.out.println(s);
    	}
    	
        Scanner scan = new Scanner(System.in);
        String buffer;
        System.out.println("Suche nach deinem Film\n");
        buffer = scan.nextLine();
        System.out.println(buffer + "\n");

        Repository repository = Repository.fillRepository("./movieproject.db");
        List<Film> filme = repository.suchenMitTitel(buffer);
        System.out.println("found " + filme);
        scan.close();

    }

}