public class TourJoueur2 {

  public static void J2Turn(char[][] damier, boolean joueur) {
    String coordonnees;
    char pieceSelected;
    boolean[][] deplacementsPossibles = new boolean[8][8];
    do {
      do {
        coordonnees = Test.selectionPiece();
        pieceSelected =
          damier[(coordonnees.charAt(1) - '0') - 1][7 -
            (coordonnees.charAt(0) - 'A')];
      } while ((!Character.isLowerCase(pieceSelected)));

      switch (pieceSelected) {
        case 'p':
          deplacementsPossibles =
            DeplacementJ2.getDeplacementsPossiblesPionJ2(damier, coordonnees);
          break;
        case 'n':
          deplacementsPossibles =
            DeplacementJ2.getDeplacementsPossiblesKnightJ2(damier, coordonnees);
          break;
        case 'b':
          deplacementsPossibles =
            DeplacementJ2.getDeplacementsPossiblesBishopJ2(damier, coordonnees);
          break;
        case 'r':
          deplacementsPossibles =
            DeplacementJ2.getDeplacementsPossiblesRookJ2(damier, coordonnees);
          break;
        case 'q':
          deplacementsPossibles =
            DeplacementJ2.getDeplacementsPossiblesQueenJ2(damier, coordonnees);
          break;
        case 'k':
          deplacementsPossibles =
            DeplacementJ2.getDeplacementsPossiblesKingJ2(damier, coordonnees);
          break;
      }
    } while (!Test.pieceImmobile(deplacementsPossibles));

    Affichage.afficherDamierDeplacement(damier, deplacementsPossibles, joueur);

    String coordonnesDeplacement = Test.selectionDeplacementJ2(
      deplacementsPossibles
    );
    damier[(coordonnees.charAt(1) - '0') - 1][7 -
      (coordonnees.charAt(0) - 'A')] =
      ' ';
    damier[(coordonnesDeplacement.charAt(1) - '0') - 1][7 -
      (coordonnesDeplacement.charAt(0) - 'A')] =
      pieceSelected;
  }
}
