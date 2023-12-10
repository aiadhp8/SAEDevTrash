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

    Affichage.afficherDamier(damier, joueur);
    String coordonnees;
    char pieceSelected;
    boolean[][] deplacementsPossibles = new boolean[8][8];
    int test = 0;
    while (test < 10) {
      do {
        do {
          coordonnees = selectionPiece();
          pieceSelected =
            damier[(
                8 - 1 - ((coordonnees.charAt(1) - '0')) + 1
              )][coordonnees.charAt(0) - 'A'];
        } while (
          (joueur && !Character.isUpperCase(pieceSelected)) ||
          (!joueur && !Character.isLowerCase(pieceSelected))
        );

        switch (pieceSelected) {
          case 'P':
            deplacementsPossibles =
              Deplacement.getDeplacementsPossiblesPion(damier, coordonnees);
            break;
          case 'N':
            deplacementsPossibles =
              Deplacement.getDeplacementsPossiblesKnight(damier, coordonnees);
            break;
          case 'B':
            deplacementsPossibles =
              Deplacement.getDeplacementsPossiblesBishop(damier, coordonnees);
            break;
          case 'R':
            deplacementsPossibles =
              Deplacement.getDeplacementsPossiblesRook(damier, coordonnees);
            break;
          case 'Q':
            deplacementsPossibles =
              Deplacement.getDeplacementsPossiblesQueen(damier, coordonnees);
            break;
          case 'K':
            deplacementsPossibles =
              Deplacement.getDeplacementsPossiblesKing(damier, coordonnees);
            break;
        }
      } while (!pieceImmobile(deplacementsPossibles));

      Affichage.afficherDamierDeplacement(
        damier,
        deplacementsPossibles,
        joueur
      );

      String coordonnesDeplacement = selectionDeplacement(
        deplacementsPossibles
      );
      damier[(8 - 1 - ((coordonnees.charAt(1) - '0')) + 1)][coordonnees.charAt(
          0
        ) -
        'A'] =
        ' ';
      damier[(
          8 - 1 - ((coordonnesDeplacement.charAt(1) - '0')) + 1
        )][coordonnesDeplacement.charAt(0) - 'A'] =
        pieceSelected;

      Affichage.afficherDamier(damier, joueur);
      test++;
    }
  }

  public static boolean changementDeJoueur(char[][] echiquier, boolean joueur) {
    joueur = !joueur;
    for (int i = 0; i < 8 / 2; i++) {
      for (int j = 0; j < 8; j++) {
        char temp = echiquier[i][j];
        echiquier[i][j] =
          echiquier[echiquier.length - 1 - i][echiquier.length - 1 - j];
        echiquier[echiquier.length - 1 - i][echiquier.length - 1 - j] = temp;
      }
    }
    return joueur;
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
      System.out.println("Entrez coo");
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

  public static String selectionDeplacement(boolean[][] deplacementsPossibles) {
    Scanner scanner = new Scanner(System.in);
    String coordonnees;

    do {
      System.out.println("Entrez deplacement");
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
}
