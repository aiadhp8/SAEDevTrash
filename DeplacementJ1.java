public class DeplacementJ1 {

  public static boolean[][] getDeplacementsPossiblesKingJ1(
    char[][] damier,
    String coo
  ) {
    int row = 8 - 1 - (coo.charAt(1) - '0') + 1;
    int col = coo.charAt(0) - 'A';

    boolean[][] deplacementsPossibles = new boolean[8][8];

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

    for (int i = 0; i < deplacementsRoi.length; i++) {
      int newRow = row + deplacementsRoi[i][0];
      int newCol = col + deplacementsRoi[i][1];

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

  public static boolean[][] getDeplacementsPossiblesQueenJ1(
    char[][] damier,
    String coo
  ) {
    int row = 8 - 1 - (coo.charAt(1) - '0') + 1;
    int col = coo.charAt(0) - 'A';

    boolean[][] deplacementsPossibles = new boolean[8][8];

    int[][] deplacementsTour = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    int[][] deplacementsFou = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };

    for (int i = 0; i < deplacementsTour.length; i++) {
      int newRow = row;
      int newCol = col;

      while (true) {
        newRow += deplacementsTour[i][0];
        newCol += deplacementsTour[i][1];

        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
          if (damier[newRow][newCol] == ' ') {
            deplacementsPossibles[newRow][newCol] = true;
          } else {
            if (
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
            break;
          }
        } else {
          break;
        }
      }
    }

    for (int i = 0; i < deplacementsFou.length; i++) {
      int newRow = row;
      int newCol = col;

      while (true) {
        newRow += deplacementsFou[i][0];
        newCol += deplacementsFou[i][1];

        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
          if (damier[newRow][newCol] == ' ') {
            deplacementsPossibles[newRow][newCol] = true;
          } else {
            if (
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
            break;
          }
        } else {
          break;
        }
      }
    }

    return deplacementsPossibles;
  }

  public static boolean[][] getDeplacementsPossiblesRookJ1(
    char[][] damier,
    String coo
  ) {
    int row = 8 - 1 - (coo.charAt(1) - '0') + 1;
    int col = coo.charAt(0) - 'A';

    boolean[][] deplacementsPossibles = new boolean[8][8];

    int[][] deplacementsTour = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    for (int i = 0; i < deplacementsTour.length; i++) {
      int newRow = row;
      int newCol = col;

      while (true) {
        newRow += deplacementsTour[i][0];
        newCol += deplacementsTour[i][1];

        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
          if (damier[newRow][newCol] == ' ') {
            deplacementsPossibles[newRow][newCol] = true;
          } else {
            if (
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
            break;
          }
        } else {
          break;
        }
      }
    }

    return deplacementsPossibles;
  }

  public static boolean[][] getDeplacementsPossiblesBishopJ1(
    char[][] damier,
    String coo
  ) {
    int row = 8 - 1 - (coo.charAt(1) - '0') + 1;
    int col = coo.charAt(0) - 'A';

    boolean[][] deplacementsPossibles = new boolean[8][8];

    int[][] deplacementsFou = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };

    for (int i = 0; i < deplacementsFou.length; i++) {
      int newRow = row;
      int newCol = col;

      while (true) {
        newRow += deplacementsFou[i][0];
        newCol += deplacementsFou[i][1];

        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
          if (damier[newRow][newCol] == ' ') {
            deplacementsPossibles[newRow][newCol] = true;
          } else {
            if (
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
            break;
          }
        } else {
          break;
        }
      }
    }
    return deplacementsPossibles;
  }

  public static boolean[][] getDeplacementsPossiblesKnightJ1(
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

  public static boolean[][] getDeplacementsPossiblesPionJ1(
    char[][] damier,
    String coo
  ) {
    int row = 8 - 1 - (coo.charAt(1) - '0') + 1;
    int col = coo.charAt(0) - 'A';

    boolean[][] deplacementsPossibles = new boolean[8][8];

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
    return deplacementsPossibles;
  }
}
