import java.util.Scanner;

public class Test {

  public static void main(String[] args) {
    boolean joueur = true;

    char[][] damier = {
      { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
      { 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p' },
      { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
      { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
      { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
      { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
      { 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' },
      { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },
    };

    int test = 0;

    while (test < 10) {
      System.out.println("Au J" + ((joueur) ? "1" : "2") + " de jouer \n");
      Affichage.afficherDamier(damier, joueur);
      System.out.println();
      if (joueur) TourJoueur1.J1Turn(damier, joueur); else TourJoueur2.J2Turn(
        damier,
        joueur
      );
      System.out.println();
      changementDeJoueur(damier, joueur);
      joueur = !joueur;
      test++;
    }
  }

  public static void changementDeJoueur(char[][] echiquier, boolean joueur) {
    for (int i = 0; i < 8 / 2; i++) {
      for (int j = 0; j < 8; j++) {
        char temp = echiquier[i][j];
        echiquier[i][j] =
          echiquier[echiquier.length - 1 - i][echiquier.length - 1 - j];
        echiquier[echiquier.length - 1 - i][echiquier.length - 1 - j] = temp;
      }
    }
  }

  public static boolean pieceImmobile(boolean[][] deplacementsPossibles) {
    for (boolean[] deplacementsPossiblesBis : deplacementsPossibles) {
      for (boolean bs : deplacementsPossiblesBis) {
        if (bs) return true;
      }
    }
    return false;
  }

  public static String selectionPiece() {
    Scanner scanner = new Scanner(System.in);

    String coordonnees;
    do {
      System.out.print("Entrez coordonnées de la pièce à bouger : ");
      coordonnees = scanner.nextLine();
    } while (
      coordonnees.length() != 2 ||
      coordonnees.charAt(0) > 'H' ||
      coordonnees.charAt(0) < 'A' ||
      coordonnees.charAt(1) > '8' ||
      coordonnees.charAt(1) < '1'
    );
    return coordonnees;
  }

  public static String selectionDeplacementJ1(
    boolean[][] deplacementsPossibles
  ) {
    Scanner scanner = new Scanner(System.in);
    String coordonnees;

    do {
      System.out.println();
      System.out.print("Entrez deplacement : ");
      coordonnees = scanner.nextLine();
    } while (
      coordonnees.length() != 2 ||
      coordonnees.charAt(0) > 'H' ||
      coordonnees.charAt(0) < 'A' ||
      coordonnees.charAt(1) > '8' ||
      coordonnees.charAt(1) < '1' ||
      !deplacementsPossibles[(
          8 - 1 - ((coordonnees.charAt(1) - '0')) + 1
        )][coordonnees.charAt(0) - 'A']
    );
    return coordonnees;
  }

  public static String selectionDeplacementJ2(
    boolean[][] deplacementsPossibles
  ) {
    Scanner scanner = new Scanner(System.in);
    String coordonnees;

    do {
      System.out.print("Entrez deplacement : ");
      coordonnees = scanner.nextLine();
    } while (
      coordonnees.length() != 2 ||
      coordonnees.charAt(0) > 'H' ||
      coordonnees.charAt(0) < 'A' ||
      coordonnees.charAt(1) > '8' ||
      coordonnees.charAt(1) < '1' ||
      !deplacementsPossibles[(coordonnees.charAt(1) - '0') - 1][7 -
        (coordonnees.charAt(0) - 'A')]
    );
    return coordonnees;
  }
}
