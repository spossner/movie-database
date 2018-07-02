package JavaKlausur;

import JavaKlausur.model.Film;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		String buffer;
		// println vs print.. und \n
		System.out.println("Suche nach deinem Film\n");
		buffer = scan.nextLine();
		System.out.println(buffer + "\n");

        long start = System.currentTimeMillis();
        Repository repository = Repository.fillRepository("./movieproject.db");
        long end = System.currentTimeMillis();
        System.out.println("took "+(end-start)+"ms");
        List<Film> filme = repository.suchen(buffer);
		System.out.println("found "+filme);
		scan.close();
		
	}

}