import java.util.Scanner;

public class Test {

  public static void main(String[] args) {
    // Initialisation du joueur actuel
    boolean joueur = true;

    // Initialisation du damier avec les pièces
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

    // Affichage du damier initial
    afficherDamier(damier, joueur);

    // Sélection de la pièce
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

    // Calcul des déplacements possibles pour le cavalier
    boolean[][] deplacementsPossibles = getDeplacementsPossiblesKnight(
      damier,
      coordonnees
    );

    // Affichage du damier avec les déplacements possibles
    afficherDamierDeplacement(damier, deplacementsPossibles, joueur);
  }

  // Méthode pour calculer les déplacements possibles pour le cavalier
  public static boolean[][] getDeplacementsPossiblesKnight(
    char[][] damier,
    String coo
  ) {
    int row = 8 - 1 - (coo.charAt(1) - '0') + 1;
    int col = coo.charAt(0) - 'A';

    boolean[][] deplacementsPossibles = new boolean[8][8];

    // Déplacements possibles du cavalier
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

    // Parcours des déplacements possibles
    for (int i = 0; i < deplacementsCavalier.length; i++) {
      int newRow = row + deplacementsCavalier[i][0];
      int newCol = col + deplacementsCavalier[i][1];

      // Vérification si la nouvelle position est à l'intérieur du damier
      if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
        // Vérification si la case est vide ou occupée par une pièce ennemie
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

  // Méthode pour calculer les déplacements possibles pour le pion
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

  // Méthode pour saisir les coordonnées de la pièce
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

  // Méthode pour afficher le damier avec les déplacements possibles
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
              ANSI_RED_BACKGROUND
