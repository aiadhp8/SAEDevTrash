import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Interface {

  public static final Scanner scanner = new Scanner(System.in);
  public static boolean finPartie = false;

  /*
   * Affiche l'échiquier d'échecs dans la console, en mettant en évidence les déplacements possibles pour une pièce sélectionnée en rouge.
   */
  public static void afficherDamierDeplacement(
    char[][] damier,
    boolean[][] deplacements,
    boolean joueur
  ) {
    final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    final String ANSI_RED_BACKGROUND = "\u001B[41m";
    final String ANSI_RESET = "\u001B[0m";

    for (int y = 0; y < 8; y++) {
      int calcY = joueur ? y : 7 - y;
      System.out.print((joueur ? 8 - y : y + 1) + " ");

      for (int x = 0; x < 8; x++) {
        int calcX = joueur ? x : 7 - x;

        String cellContent = symbolPiece(damier[calcY][calcX]);
        String fond = deplacements[calcY][calcX]
          ? ANSI_RED_BACKGROUND
          : (x % 2 == y % 2 ? ANSI_WHITE_BACKGROUND : "");

        System.out.print(fond + String.format("%-3s", cellContent) + ANSI_RESET);
      }
      System.out.println();
    }

    System.out.print("  \u2004");
    for (int i = 0; i < 8; i++) {
      char letter = (char) (joueur ? 'A' + i : 'H' - i);
      System.out.print("\u2002" + letter + "\u2002\u2002");
    }
    System.out.println();
  }

  /*
   * Affiche l'échiquier d'échecs dans la console avec une orientation spécifique en fonction du joueur.
   */
  public static void afficherDamier(char[][] damier, boolean joueur) {
    final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    final String ANSI_RESET = "\u001B[0m";

    for (int y = 0; y < 8; y++) {
      System.out.print((joueur ? 8 - y : y + 1) + " ");

      for (int x = 0; x < 8; x++) {
        String cellContent = symbolPiece(damier[joueur ? y : 7 - y][joueur ? x : 7 - x]);
        System.out.print(
          (x % 2 == y % 2)
            ? ANSI_WHITE_BACKGROUND + String.format("%-3s", cellContent) + ANSI_RESET
            : String.format("%-3s", cellContent)
        );
      }
      System.out.println();
    }

    System.out.print("  \u2004");
    for (int i = 0; i < 8; i++) {
      char letter = (char) (joueur ? 'A' + i : 'H' - i);
      System.out.print("\u2002" + letter + "\u2002\u2002");
    }
    System.out.println();
  }

  /*
   * Efface le contenu actuel du terminal.
   */
  public static void clearTerminal() {
    System.out.println();
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  /*
   * Permet à un joueur de sélectionner une pièce sur l'échiquier et de la déplacer. Comme ceci :
   *   - Affiche l'échiquier et invite le joueur à sélectionner une pièce (par ses coordonnées) et à indiquer sa destination.
   *   - Permet la saisie soit d'une sélection unique suivie d'une destination, soit d'un déplacement complet sous forme 'A2-A4'.
   *   - Vérifie la validité du déplacement (la pièce appartient au joueur, le déplacement est légal, etc.).
   *   - Effectue le déplacement de la pièce sur l'échiquier si toutes les conditions sont remplies.
   */
  public static void deplacerPiece(char[][] damier, boolean joueur) {
    String cooString;
    boolean[][] deplacement = new boolean[0][0];

    int[] depart = null;
    int[] arrivee;

    do {
      arrivee = null;

      clearTerminal();
      afficherDamier(damier, joueur);
      System.out.print("Sélection ou déplacement : ");
      cooString = scanner.nextLine();

      if (cooString.equals("nul")) {
        if (Logique.repetition() || Deplacement.demiCoups >= 50) {
          System.out.println("Le nul est accordé, il y a donc égalité !");
          finPartie = true;
          return;
        }
      } else if (cooString.matches("[A-H][1-8]-[A-H][1-8]")) {
        String[] parts = cooString.split("-");
        depart = Logique.getPosition(parts[0]);
        arrivee = Logique.getPosition(parts[1]);
      } else {
        depart = Logique.getPosition(cooString);
      }

      if (depart != null && damier[depart[0]][depart[1]] != ' ') deplacement =
        Deplacement.getDeplacements(damier, depart);
    } while (
      depart == null ||
      damier[depart[0]][depart[1]] == ' ' ||
      (
        arrivee == null &&
        (
          joueur ^
          Character.isUpperCase(damier[depart[0]][depart[1]]) ||
          deplacement.length == 0 ||
          Logique.immobile(deplacement)
        )
      ) ||
      (arrivee != null && !deplacement[arrivee[0]][arrivee[1]])
    );

    if (arrivee == null) {
      do {
        clearTerminal();
        afficherDamierDeplacement(damier, deplacement, joueur);

        System.out.print("Destination : ");
        cooString = scanner.nextLine();
        arrivee = Logique.getPosition(cooString);
      } while (!deplacement[Objects.requireNonNull(arrivee)[0]][arrivee[1]]);
    }

    Deplacement.deplacerPiece(damier, depart, arrivee, joueur);

    if (Deplacement.promotion) {
      char piece;
      do {
        System.out.println(
          "Entrez la pièce en la quel vous voulez promouvoir votre pion : "
        );
        piece = scanner.nextLine().charAt(0);
      } while (
        joueur ^
        Character.isUpperCase(piece) ||
        Character.toLowerCase(piece) != 'q' &&
        Character.toLowerCase(piece) != 'r' &&
        Character.toLowerCase(piece) != 'n' &&
        Character.toLowerCase(piece) != 'b'
      );

      Deplacement.promouvoir(damier, piece, joueur);
    }
  }

  public static String symbolPiece(char piece) {
    Map<Character, String> mapSymbol = new HashMap<>() {
      {
        put('K', " \u2654");
        put('Q', " \u2655");
        put('R', " \u2656");
        put('B', " \u2657");
        put('N', " \u2658");
        put('P', " \u2659");
        put('k', " \u265A");
        put('q', " \u265B");
        put('r', " \u265C");
        put('b', " \u265D");
        put('n', " \u265E");
        put('p', " \u265F");
        put(' ', " \u2003");
      }
    };
    return mapSymbol.get(piece);
  }
}
