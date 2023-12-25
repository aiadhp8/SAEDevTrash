public class Menu {
	public static boolean afficherMenu() {
		Interface.clearTerminal();

		System.out.println("Menu :");
		System.out.println("1. 2 joueurs");
		System.out.println("2. 1 joueurs contre IA");
		System.out.println("3. Manuel");
		System.out.println("4. Quitter");

		int sel;
		do {
			System.out.print("Sélection : ");
			sel = Interface.scanner.nextInt();
		}
		while (sel <= 0 || sel > 4);

		Interface.scanner.nextLine(); // flush le scanner

		switch (sel) {
			case 1:
				Interface.clearTerminal();
				Echec.joueurVSJoueur();
				break;

			case 2:
				Interface.clearTerminal();
				Echec.joueurVSIA();
				break;

			case 3:
				Interface.clearTerminal();
				afficherManuel();
				break;

			default:
				System.out.println("Au revoir !");
				return false;
		}

		return true;
	}

	public static void afficherManuel() {
		System.out.println("Manuel :");
		System.out.println("Notre jeu d'échecs se base sur la notation algébrique, il faut donc entrer les coordonnées au format A1 ou a1 pour la case en bas à gauche du plateau par exemple.");
		System.out.println("Il existe des raccourcies lorsque l'on sait quel coup jouer, il suffit d'entrer E2-E4 par exemple pour une ouverte française.");
		System.out.println("Lorsqu'un nul est possible, vous pouvez tapper \"nul\" ce qui a pour effet, s'il est accordé, de stopper la partie sur une égalité.");
		System.out.println();
	}
}
