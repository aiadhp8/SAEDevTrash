public class Affichage {

  public static void afficherDamierDeplacement(
    char[][] damier,
    boolean[][] deplacementsPossibles,
    boolean joueur
  ) {
    final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    final String ANSI_RED_BACKGROUND = "\u001B[41m";
    final String ANSI_RESET = "\u001B[0m";

    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
        if (x == 0 && joueur) System.out.print(8 - y + " "); else if (
          x == 0 && !joueur
        ) System.out.print(y + 1 + " ");

        if (x % 2 == y % 2) {
          if (deplacementsPossibles[y][x]) {
            System.out.print(
              ANSI_RED_BACKGROUND + " " + damier[y][x] + " " + ANSI_RESET
            );
          } else {
            System.out.print(
              ANSI_WHITE_BACKGROUND + " " + damier[y][x] + " " + ANSI_RESET
            );
          }
        } else {
          if (deplacementsPossibles[y][x]) {
            System.out.print(
              ANSI_RED_BACKGROUND + " " + damier[y][x] + " " + ANSI_RESET
            );
          } else {
            System.out.print(" " + damier[y][x] + " ");
          }
        }
      }
      System.out.println();
    }

    if (joueur) {
      for (int i = 0; i <= 8; i++) System.out.print(
        " " + (i == 0 ? "" : (char) ('A' + i - 1)) + " "
      );
      System.out.println();
    } else {
      for (int i = 0; i <= 8; i++) System.out.print(
        " " + (i == 0 ? "" : (char) ('H' - i + 1)) + " "
      );
      System.out.println();
    }
  }

  public static void afficherDamier(char[][] damier, boolean joueur) {
    final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    final String ANSI_RESET = "\u001B[0m";

    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
        if (x == 0 && joueur) System.out.print(8 - y + " "); else if (
          x == 0 && !joueur
        ) System.out.print(y + 1 + " ");

        if (x % 2 == y % 2) System.out.print(
          ANSI_WHITE_BACKGROUND + " " + damier[y][x] + " " + ANSI_RESET
        ); else System.out.print(" " + damier[y][x] + " ");
      }
      System.out.println();
    }

    if (joueur) {
      for (int i = 0; i <= 8; i++) System.out.print(
        " " + (i == 0 ? "" : (char) ('A' + i - 1)) + " "
      );
      System.out.println();
    } else {
      for (int i = 0; i <= 8; i++) System.out.print(
        " " + (i == 0 ? "" : (char) ('H' - i + 1)) + " "
      );
      System.out.println();
    }
  }
}
