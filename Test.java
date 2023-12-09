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

    afficherDamier(damier, joueur);
    String coordonnees;
    char pieceSelected;
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

    boolean[][] deplacementsPossibles = getDeplacementsPossiblesKnight(
      damier,
      coordonnees
    );

    afficherDamierDeplacement(damier, deplacementsPossibles, joueur);
  }

  public static boolean[][] getDeplacementsPossiblesKnight(
    char[][] damier,
    String coo
  ) {
    int row = 8 - 1 - (coo.charAt(1) - '0') + 1;
    int col = coo.charAt(0) - 'A';

    boolean[][] deplacementsPossibles = new boolean[8][8];

    int[][] deplacementsCavalier = {
      { -2, -1 },
      { -2, 1 },
      { -1, -2 },
      { -1, 2 },
      { 1, -2 },
      { 1, 2 },
      { 2, -1 },
      { 2, 1 },
    };

    for (int i = 0; i < deplacementsCavalier.length; i++) {
      int newRow = row + deplacementsCavalier[i][0];
      int newCol = col + deplacementsCavalier[i][1];

      if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
        if (
          damier[newRow][newCol] == ' ' ||
          (
            Character.isLowerCase(damier[row][col]) &&
            Character.isUpperCase(damier[newRow][newCol])
          ) ||
          (
            Character.isUpperCase(damier[row][col]) &&
            Character.isLowerCase(damier[newRow][newCol])
          )
        ) {
          deplacementsPossibles[newRow][newCol] = true;
        }
      }
    }

    return deplacementsPossibles;
  }

  public static boolean[][] getDeplacementsPossiblesPion(
    char[][] damier,
    String coordonnees
  ) {
    int row = 8 - 1 - (coordonnees.charAt(1) - '0') + 1;
    int col = coordonnees.charAt(0) - 'A';

    boolean[][] deplacementsPossibles = new boolean[8][8];

    if (damier[row][col] == 'P') {
      if (row - 1 >= 0 && damier[row - 1][col] == ' ') {
        deplacementsPossibles[row - 1][col] = true;
        if (row == 6 && damier[row - 2][col] == ' ') {
          deplacementsPossibles[row - 2][col] = true;
        }
      }

      if (
        row - 1 >= 0 &&
        col - 1 >= 0 &&
        Character.isLowerCase(damier[row - 1][col - 1])
      ) {
        deplacementsPossibles[row - 1][col - 1] = true;
      }
      if (
        row - 1 >= 0 &&
        col + 1 < 8 &&
        Character.isLowerCase(damier[row - 1][col + 1])
      ) {
        deplacementsPossibles[row - 1][col + 1] = true;
      }
    }
    return deplacementsPossibles;
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
