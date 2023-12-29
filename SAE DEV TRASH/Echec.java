public class Echec {

  public static void main(String[] args) {
    while(true){
        if (!Menu.afficherMenu()) break;
    }
  }

  public static void joueurVSJoueur() {
    char[][] echiquier = {
            { 'r', ' ', ' ', ' ', 'k', ' ', ' ', 'r' },
            { 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' },
            { 'R', ' ', ' ', ' ', 'K', ' ', ' ', 'R' },
    };

            /*{
      { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' },
      { 'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p' },
      { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
      { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
      { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
      { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
      { 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P' },
      { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' },
    };*/

    boolean isJ1 = true;
    Interface.finPartie = false;

    while (!Interface.finPartie) {

      Interface.deplacerPiece(echiquier, isJ1);

      if (Logique.isEchecEtMat(echiquier, isJ1)) {
        Interface.clearTerminal();
        System.out.println("Echec et mat, victoire des " + (isJ1 ? "blancs !" : "noirs !"));
        Interface.afficherDamier(echiquier, isJ1);
        System.out.println("Appuyez sur Entrée pour revenir au Menu");
        Interface.finPartie = true;
        if (Interface.scanner.nextLine().isEmpty()) break;

      } else if (Logique.isPat(echiquier, isJ1)) {
        Interface.clearTerminal();
        System.out.println("Pat, match nulle !");
        Interface.afficherDamier(echiquier, isJ1);
        System.out.println("Appuyez sur Entrée pour revenir au Menu");
        Interface.finPartie = true;
        if (Interface.scanner.nextLine().isEmpty()) break;
      }

      isJ1 = !isJ1;
    }
  }

  public static void joueurVSIA() {}
}
