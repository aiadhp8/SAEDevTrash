public class Deplacement {

  public static boolean[][] deplacementDeuxCases = new boolean[2][8];
  public static boolean[][] deplacementPieceRoque = new boolean[2][3];

  public static boolean promotion = false;

  public static int coups = 1;
  public static int demiCoups = 0;

  /*
   * Retourne un tableau bidimensionnel de booléens représentant les déplacements possibles pour une pièce donnée sur un échiquier,
   * en s'assurant que ces déplacements ne mettent pas le roi en échec et gère également la prise en passant pour les pions.
   * Chaque élément du tableau est 'true' si le déplacement à cette position est possible sinon 'false'.
   */
  public static boolean[][] getDeplacements(
    char[][] echiquier,
    int[] position
  ) {
    boolean isJ1 = Character.isUpperCase(echiquier[position[0]][position[1]]);
    boolean[][] deplacements = getDeplacementsPossibles(echiquier, position);

    if (echiquier[position[0]][position[1]] != (isJ1 ? 'K' : 'k')) {
      int[] posRoi = findRoi(echiquier, isJ1);

      for (int y = 0; y < 8; y++) {
        for (int x = 0; x < 8; x++) {
          if (deplacements[y][x]) {
            char[][] tempechiquier = Logique.copierEchiquier(echiquier);
            if (
              Character.toLowerCase(echiquier[position[0]][position[1]]) ==
              'p' &&
              x != position[1] &&
              echiquier[y][x] == ' '
            ) {
              tempechiquier[y][x] = tempechiquier[position[0]][position[1]];
              tempechiquier[position[0]][x] = ' ';
              tempechiquier[position[0]][position[1]] = ' ';
            } else {
              tempechiquier[y][x] = tempechiquier[position[0]][position[1]];
              tempechiquier[position[0]][position[1]] = ' ';
            }

            if (
              getCasesEchecs(tempechiquier, isJ1)[posRoi[0]][posRoi[1]]
            ) deplacements[y][x] = false;
          }
        }
      }
    }

    return deplacements;
  }

  /*
   * Déplace une pièce sur l'échiquier, gère les déplacements spéciaux (roque, prise en passant),
   * et met à jour les compteurs de coups. Comme ceci :
   *   - Effectue le déplacement de la pièce de la position de départ à la position d'arrivée.
   *   - Gère la prise en passant pour les pions et le roque pour les rois.
   *   - Met à jour les compteurs de demi-coups et de coups.
   *   - Met à jour le suivi des déplacements pour les pions et le roque.
   */
  public static void deplacerPiece(
    char[][] echiquier,
    int[] depart,
    int[] arrivee,
    boolean isJ1
  ) {
    coups++;
    demiCoups++;

    promotion = false;

    Logique.flushBoolean(deplacementDeuxCases);
    if (
      Character.toLowerCase(echiquier[depart[0]][depart[1]]) == 'p' &&
      (
        (arrivee[0] - depart[0]) < 0
          ? -(arrivee[0] - depart[0])
          : (arrivee[0] - depart[0])
      ) ==
      2
    ) deplacementDeuxCases[(isJ1 ? 1 : 0)][arrivee[1]] = true; else if (
      Character.toLowerCase(echiquier[depart[0]][depart[1]]) == 'p' &&
      arrivee[0] == (isJ1 ? 0 : 7)
    ) promotion = true; else if (
      Character.toLowerCase(echiquier[depart[0]][depart[1]]) == 'r' &&
      !deplacementPieceRoque[(isJ1 ? 1 : 0)][0] &&
      depart[0] == (isJ1 ? 7 : 0) &&
      !deplacementPieceRoque[(isJ1 ? 1 : 0)][(depart[1] == 0 ? 1 : 2)]
    ) deplacementPieceRoque[(isJ1 ? 1 : 0)][(depart[1] == 0 ? 1 : 2)] =
      true; else if (
      Character.toLowerCase(echiquier[depart[0]][depart[1]]) == 'k'
    ) deplacementPieceRoque[(isJ1 ? 1 : 0)][0] = true;

    if (
      Character.toLowerCase(echiquier[depart[0]][depart[1]]) == 'p' &&
      arrivee[1] != depart[1] &&
      echiquier[arrivee[0]][arrivee[1]] == ' '
    ) {
      echiquier[arrivee[0]][arrivee[1]] = echiquier[depart[0]][depart[1]];
      echiquier[depart[0]][arrivee[1]] = ' ';
      echiquier[depart[0]][depart[1]] = ' ';
      demiCoups = 0;
    } else if (
      echiquier[arrivee[0]][arrivee[1]] != ' ' &&
      Character.isUpperCase(echiquier[depart[0]][depart[1]]) ==
      Character.isUpperCase(echiquier[arrivee[0]][arrivee[1]])
    ) {
      boolean petitRoque = arrivee[1] > depart[1];

      int colonneRoiFinal = petitRoque ? depart[1] + 2 : depart[1] - 2;
      int colonneTourFinal = petitRoque
        ? colonneRoiFinal - 1
        : colonneRoiFinal + 1;

      echiquier[depart[0]][colonneRoiFinal] = echiquier[depart[0]][depart[1]];
      echiquier[arrivee[0]][colonneTourFinal] =
        echiquier[arrivee[0]][arrivee[1]];
      echiquier[depart[0]][depart[1]] = ' ';
      echiquier[arrivee[0]][arrivee[1]] = ' ';
    } else {
      if (
        echiquier[arrivee[0]][arrivee[1]] != ' ' ||
        Character.toLowerCase(echiquier[depart[0]][depart[1]]) == 'p'
      ) demiCoups = 0;
      echiquier[arrivee[0]][arrivee[1]] = echiquier[depart[0]][depart[1]];
      echiquier[depart[0]][depart[1]] = ' ';
    }

    Logique.ajouterCoups(echiquier, isJ1);
  }

  public static void promouvoir(char[][] echiquier, char piece, boolean isJ1) {
    for (int i = 0; i < 8; i++) {
      if (Character.toLowerCase(echiquier[(isJ1 ? 0 : 7)][i]) == 'p') {
        echiquier[(isJ1 ? 0 : 7)][i] = piece;
      }
    }
  }

  /*
   * Calcule et retourne les déplacements possibles pour une pièce spécifique sur un échiquier.
   * La fonction détermine le type de la pièce à la position donnée et appelle la fonction
   * appropriée pour calculer ses déplacements possibles.
   * Retourne un tableau de booléens a 2 dimensions représentant les déplacements possibles pour la pièce.
   * Chaque case du tableau est 'true' si le déplacement à cette position est possible sinon 'false'.
   */
  private static boolean[][] getDeplacementsPossibles(
    char[][] echiquier,
    int[] position
  ) {
    boolean isJ1 = Character.isUpperCase(echiquier[position[0]][position[1]]);

    return switch (Character.toLowerCase(echiquier[position[0]][position[1]])) {
      case 'k' -> getDeplacementsRoi(echiquier, position, isJ1);
      case 'q' -> getDeplacementsDame(echiquier, position);
      case 'r' -> getDeplacementsTour(echiquier, position);
      case 'b' -> getDeplacementsFou(echiquier, position);
      case 'n' -> getDeplacementsCavalier(echiquier, position);
      case 'p' -> getDeplacementsPion(echiquier, position, isJ1);
      default -> new boolean[0][0];
    };
  }

  private static boolean[][] getEchec(char[][] echiquier, int[] position) {
    boolean isJ1 = Character.isUpperCase(echiquier[position[0]][position[1]]);

    return switch (Character.toLowerCase(echiquier[position[0]][position[1]])) {
      case 'k' -> getEchecRoi(echiquier, position);
      case 'q' -> getEchecDame(echiquier, position);
      case 'r' -> getEchecTour(echiquier, position);
      case 'b' -> getEchecFou(echiquier, position);
      case 'n' -> getEchecCavalier(echiquier, position);
      case 'p' -> getEchecPion(echiquier, position, isJ1);
      default -> new boolean[0][0];
    };
  }

  /*
   * Calcule et retourne les déplacements possibles pour une pièce d'échecs sur un échiquier, basés sur un ensemble de vecteurs.
   * La fonction vérifie chaque vecteur de déplacement pour les obstacles et les captures possibles puis retourne
   * un tableau de booléens a 2 dimensions représentant les déplacements possibles pour la pièce.
   * Chaque élément du tableau est 'true' si le déplacement à cette position est possible sinon 'false'.
   */
  private static boolean[][] getDeplacementsVector(
    char[][] echiquier,
    int[] position,
    int[][] deplacementsPiece,
    boolean continueMovement
  ) {
    boolean[][] deplacementsPossibles = new boolean[8][8];

    for (int[] ints : deplacementsPiece) {
      int newRow = position[0], newCol = position[1];
      do {
        newRow += ints[0];
        newCol += ints[1];

        if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) break;

        if (echiquier[newRow][newCol] != ' ') {
          if (
            Character.isUpperCase(echiquier[position[0]][position[1]]) !=
            Character.isUpperCase(echiquier[newRow][newCol])
          ) {
            deplacementsPossibles[newRow][newCol] = true;
          }
          break;
        } else {
          deplacementsPossibles[newRow][newCol] = true;
        }
      } while (continueMovement);
    }

    return deplacementsPossibles;
  }

  private static boolean[][] getEchecVector(
    char[][] echiquier,
    int[] position,
    int[][] deplacementsPiece,
    boolean continueMovement
  ) {
    boolean[][] deplacementsPossibles = new boolean[8][8];

    for (int[] ints : deplacementsPiece) {
      int newRow = position[0], newCol = position[1];
      do {
        newRow += ints[0];
        newCol += ints[1];

        if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) break;

        if (echiquier[newRow][newCol] != ' ') {
          deplacementsPossibles[newRow][newCol] = true;
          break;
        } else {
          deplacementsPossibles[newRow][newCol] = true;
        }
      } while (continueMovement);
    }

    return deplacementsPossibles;
  }

  /*
   * Recherche et retourne la position du roi sur un échiquier.
   */
  public static int[] findRoi(char[][] echiquier, boolean isJ1) {
    for (int y = 0; y < 8; y++) for (int x = 0; x < 8; x++) if (
      echiquier[y][x] == (isJ1 ? 'K' : 'k')
    ) return new int[] { y, x };
    return new int[0];
  }

  /*
   * Calcule et retourne un tableau indiquant toutes les cases d'un échiquier qui sont attaquées
   * par les pièces d'un joueur spécifique dans un tableau de booléens a 2 dimensions
   * où chaque élément est 'true' si la case correspondante est attaquée par les pièces du joueur spécifié sinon 'false'.
   */
  public static boolean[][] getCasesEchecs(char[][] echiquier, boolean isJ1) {
    boolean[][] caseEchec = new boolean[8][8];
    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
        if (
          isJ1 ^
          Character.isUpperCase(echiquier[y][x]) &&
          echiquier[y][x] != ' '
        ) caseEchec =
          Logique.combinerOU(
            caseEchec,
            getEchec(echiquier, new int[] { y, x })
          );
      }
    }
    return caseEchec;
  }

  /*
   * Calcule et retourne les déplacements possibles pour un roi sur un échiquier et prend en compte
   * les mouvements standards du roi et les possibilités de roque, tout en s'assurant que le roi ne se déplace pas attaquée.
   * Renvoie un tableau bidimensionnel de booléens représentant les déplacements possibles pour le roi.
   * Chaque élément du tableau est 'true' si le déplacement à cette position est possible sinon 'false'.
   */
  private static boolean[][] getDeplacementsRoi(
    char[][] echiquier,
    int[] position,
    boolean isJ1
  ) {
    int[][] deplacementsRoi = {
      { -1, -1 },
      { -1, 0 },
      { -1, 1 },
      { 0, -1 },
      { 0, 1 },
      { 1, -1 },
      { 1, 0 },
      { 1, 1 },
    };
    boolean[][] deplacements = getDeplacementsVector(
      echiquier,
      position,
      deplacementsRoi,
      false
    );
    boolean[][] echec = getCasesEchecs(echiquier, isJ1);

    if (!deplacementPieceRoque[(isJ1 ? 0 : 1)][0]) {
      if (!deplacementPieceRoque[(isJ1 ? 0 : 1)][1]) {
        boolean roquePossible = true;
        for (int x = position[1] - 1; x > 0; x--) {
          roquePossible &= !echec[position[0]][x];
          roquePossible &= echiquier[position[0]][x] == ' ';
        }
        deplacements[position[0]][0] = roquePossible;
      }
      if (!deplacementPieceRoque[(isJ1 ? 0 : 1)][2]) {
        boolean roquePossible = true;
        for (int x = position[1] + 1; x < 7; x++) {
          roquePossible &= !echec[position[0]][x];
          roquePossible &= echiquier[position[0]][x] == ' ';
        }
        deplacements[position[0]][7] = roquePossible;
      }
    }

    deplacements = Logique.combinerDIFF(deplacements, echec);

    return deplacements;
  }

  private static boolean[][] getEchecRoi(char[][] echiquier, int[] position) {
    int[][] deplacementsRoi = {
      { -1, -1 },
      { -1, 0 },
      { -1, 1 },
      { 0, -1 },
      { 0, 1 },
      { 1, -1 },
      { 1, 0 },
      { 1, 1 },
    };
    return getEchecVector(echiquier, position, deplacementsRoi, false);
  }

  /*
   * Calcule et retourne les déplacements possibles pour une dame (reine) sur un échiquier.
   */
  private static boolean[][] getDeplacementsDame(
    char[][] echiquier,
    int[] position
  ) {
    int[][] deplacementsDame = {
      { -1, -1 },
      { -1, 1 },
      { 1, -1 },
      { 1, 1 },
      { -1, 0 },
      { 1, 0 },
      { 0, -1 },
      { 0, 1 },
    };
    return getDeplacementsVector(echiquier, position, deplacementsDame, true);
  }

  private static boolean[][] getEchecDame(char[][] echiquier, int[] position) {
    int[][] deplacementsDame = {
      { -1, -1 },
      { -1, 1 },
      { 1, -1 },
      { 1, 1 },
      { -1, 0 },
      { 1, 0 },
      { 0, -1 },
      { 0, 1 },
    };
    return getEchecVector(echiquier, position, deplacementsDame, true);
  }

  /*
   * Calcule et retourne les déplacements possibles pour la tour sur un échiquier.
   */
  private static boolean[][] getDeplacementsTour(
    char[][] echiquier,
    int[] position
  ) {
    int[][] deplacementsTour = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    return getDeplacementsVector(echiquier, position, deplacementsTour, true);
  }

  private static boolean[][] getEchecTour(char[][] echiquier, int[] position) {
    int[][] deplacementsTour = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    return getEchecVector(echiquier, position, deplacementsTour, true);
  }

  /*
   * Calcule et retourne les déplacements possibles pour le fou sur un échiquier.
   */
  private static boolean[][] getDeplacementsFou(
    char[][] echiquier,
    int[] position
  ) {
    int[][] deplacementsFou = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };
    return getDeplacementsVector(echiquier, position, deplacementsFou, true);
  }

  private static boolean[][] getEchecFou(char[][] echiquier, int[] position) {
    int[][] deplacementsFou = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };
    return getEchecVector(echiquier, position, deplacementsFou, true);
  }

  /*
   * Calcule et retourne les déplacements possibles pour le cavalier sur un échiquier.
   */
  private static boolean[][] getDeplacementsCavalier(
    char[][] echiquier,
    int[] position
  ) {
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
    return getDeplacementsVector(
      echiquier,
      position,
      deplacementsCavalier,
      false
    );
  }

  private static boolean[][] getEchecCavalier(
    char[][] echiquier,
    int[] position
  ) {
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
    return getEchecVector(echiquier, position, deplacementsCavalier, false);
  }

  /*
   * Calcule et retourne les déplacements possibles pour un pion sur un échiquier en gèrant les déplacements normaux,
   * les captures diagonales, la prise en passant et l'avancement initial de deux cases des pions.
   */
  private static boolean[][] getDeplacementsPion(
    char[][] echiquier,
    int[] position,
    boolean isJ1
  ) {
    boolean[][] deplacements = new boolean[8][8];

    int[] avance = { position[0] + (isJ1 ? -1 : 1), position[1] };
    if (
      position[0] != (isJ1 ? 0 : 7) && echiquier[avance[0]][avance[1]] == ' '
    ) {
      deplacements[avance[0]][avance[1]] = true;
    }

    if (position[0] == (isJ1 ? 6 : 1)) {
      int[] avanceDouble = { position[0] + (isJ1 ? -2 : 2), position[1] };
      if (
        echiquier[avanceDouble[0]][avanceDouble[1]] == ' ' &&
        echiquier[avance[0]][avance[1]] == ' '
      ) {
        deplacements[avanceDouble[0]][avanceDouble[1]] = true;
      }
    }

    if (position[0] != (isJ1 ? 0 : 7)) {
      int[][] diagonals = {
        { position[0] + (isJ1 ? -1 : 1), position[1] - 1 },
        { position[0] + (isJ1 ? -1 : 1), position[1] + 1 },
      };

      for (int[] diagonal : diagonals) {
        if (
          diagonal[0] >= 0 &&
          diagonal[0] < 8 &&
          diagonal[1] >= 0 &&
          diagonal[1] < 8
        ) {
          if (
            echiquier[diagonal[0]][diagonal[1]] != ' ' &&
            isJ1 ^
            Character.isUpperCase(echiquier[diagonal[0]][diagonal[1]])
          ) {
            deplacements[diagonal[0]][diagonal[1]] = true;
          } else if (
            position[0] == (isJ1 ? 4 : 3) &&
            deplacementDeuxCases[(isJ1 ? 0 : 1)][diagonal[1]]
          ) {
            deplacements[diagonal[0]][diagonal[1]] = true;
          }
        }
      }
    }

    return deplacements;
  }

  /*
   * Calcule et retourne les déplacements possibles pour un pion en termes de captures diagonales en gèrant les captures diagonales d'un pion,
   * incluant les cases diagonales devant lui où se trouvent des pièces adverses ou où une prise en passant est possible.
   */
  private static boolean[][] getEchecPion(
    char[][] echiquier,
    int[] position,
    boolean isJ1
  ) {
    boolean[][] deplacements = new boolean[8][8];

    int[] pos = { position[0] + (isJ1 ? -1 : 1), position[1] };
    if (position[0] != (isJ1 ? 0 : 7)) {
      if (
        position[1] > 0 &&
        (
          echiquier[pos[0]][pos[1] - 1] == ' ' ||
          isJ1 ^
          Character.isUpperCase(echiquier[pos[0]][pos[1] - 1])
        )
      ) deplacements[pos[0]][pos[1] - 1] = true;
      if (
        position[1] < 7 &&
        (
          echiquier[pos[0]][pos[1] + 1] == ' ' ||
          isJ1 ^
          Character.isUpperCase(echiquier[pos[0]][pos[1] + 1])
        )
      ) deplacements[pos[0]][pos[1] + 1] = true;
    }

    return deplacements;
  }
}
