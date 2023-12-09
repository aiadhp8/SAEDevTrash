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

    Affichage.afficherDamierDeplacement(damier, deplacementsPossibles, joueur);
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

    String coo;
    do {
      System.out.println("Entrez coo");
      coo = scanner.nextLine();
    } while (
      coo.length() != 2 ||
      coo.charAt(0) > 'H' ||
      coo.charAt(0) < 'A' ||
      coo.charAt(1) > '8' ||
      coo.charAt(1) < '1'
    );
    return coo;
  }
}
