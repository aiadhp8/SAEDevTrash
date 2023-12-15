import java.util.Scanner;

public class Echec {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		char[][] damier = {
				{'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'},
				{'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
				{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
				{'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
				{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'},
		};
		boolean isJ1 = true;
		int test = 0;
		while (test < 10) {
			Interface.afficherDamier(damier, isJ1);
			System.out.print("Sélection : ");
			String cooString = scanner.next();
			int[] depart = Interface.getPosition(cooString);

			boolean[][] deplacement =  Deplacement.getDeplacements(damier, depart);

			System.out.print("\033[H\033[2J"); 
			System.out.flush();

			Interface.afficherDamierDeplacement(damier, deplacement, isJ1);
			
			System.out.print("Destination : ");
			cooString = scanner.next();
			int[] arrivee = Interface.getPosition(cooString);

			if (deplacement[arrivee[0]][arrivee[1]]) {
				damier[arrivee[0]][arrivee[1]] = damier[depart[0]][depart[1]];
				damier[depart[0]][depart[1]] = ' ';
			}

			System.out.print("\033[H\033[2J"); 
			System.out.flush();

			isJ1 = !isJ1;
			test++;
		}

		scanner.close();
	}
}