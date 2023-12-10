public class TourJoueur1 {

  public static void J1Turn(char[][] damier, boolean joueur) {
    String coordonnees;
    char pieceSelected;
    boolean[][] deplacementsPossibles = new boolean[8][8];
    do {
      do {
        coordonnees = Test.selectionPiece();
        pieceSelected =
          damier[(
              8 - 1 - ((coordonnees.charAt(1) - '0')) + 1
            )][coordonnees.charAt(0) - 'A'];
      } while ((!Character.isUpperCase(pieceSelected)));

      switch (pieceSelected) {
        case 'P':
          deplacementsPossibles =
            DeplacementJ1.getDeplacementsPossiblesPionJ1(damier, coordonnees);
          break;
        case 'N':
          deplacementsPossibles =
            DeplacementJ1.getDeplacementsPossiblesKnightJ1(damier, coordonnees);
          break;
        case 'B':
          deplacementsPossibles =
            DeplacementJ1.getDeplacementsPossiblesBishopJ1(damier, coordonnees);
          break;
        case 'R':
          deplacementsPossibles =
            DeplacementJ1.getDeplacementsPossiblesRookJ1(damier, coordonnees);
          break;
        case 'Q':
          deplacementsPossibles =
            DeplacementJ1.getDeplacementsPossiblesQueenJ1(damier, coordonnees);
          break;
        case 'K':
          deplacementsPossibles =
            DeplacementJ1.getDeplacementsPossiblesKingJ1(damier, coordonnees);
          break;
      }
    } while (!Test.pieceImmobile(deplacementsPossibles));

    Affichage.afficherDamierDeplacement(damier, deplacementsPossibles, joueur);

    String coordonnesDeplacement = Test.selectionDeplacementJ1(
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
  }
}
