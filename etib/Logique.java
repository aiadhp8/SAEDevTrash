public class Logique {
    public static boolean[][] combinerOU(boolean[][] t1, boolean[][] t2) {
        boolean[][] combinaison = new boolean[8][8];

        for (int i = 0; i < t1.length; i++) {
            for (int j = 0; j < t1[0].length; j++) {
                combinaison[i][j] = t1[i][j] || t2[i][j];
            }
        }

        return combinaison;
    }

    public static boolean[][] combinerDIFF(boolean[][] t1, boolean[][] t2) {
        boolean[][] combinaison = new boolean[8][8];

        for (int i = 0; i < t1.length; i++) {
            for (int j = 0; j < t1[0].length; j++) {
                combinaison[i][j] = t1[i][j] && !t2[i][j];
            }
        }

        return combinaison;
    }
}
