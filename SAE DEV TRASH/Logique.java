import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

public class Logique {
	public static Map<String, Integer> coups = new Hashtable<String, Integer>();

	public static boolean isEchecEtMat(char[][] echiquier, boolean isJ1) {
		int[] posRoi = Deplacement.findRoi(echiquier, !isJ1);
		
		if (Deplacement.getCasesEchecs(echiquier, !isJ1)[posRoi[0]][posRoi[1]]) {
			boolean[][] deplacement = new boolean[8][8];
	
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (isJ1 ^ Character.isUpperCase(echiquier[y][x]) && echiquier[y][x] != ' ') {
						deplacement = combinerOU(deplacement, Deplacement.getDeplacements(echiquier, new int[] {y, x}));
					}
				}
			}
	
			return immobile(deplacement);
		}
	
		return false;
	}

	public static boolean isPat(char[][] echiquier, boolean isJ1) {
		int[] posRoi = Deplacement.findRoi(echiquier, !isJ1);
		
		if (!Deplacement.getCasesEchecs(echiquier, !isJ1)[posRoi[0]][posRoi[1]]) {
			boolean[][] deplacement = new boolean[8][8];
			
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (isJ1 ^ Character.isUpperCase(echiquier[y][x]) && echiquier[y][x] != ' ') {
						deplacement = combinerOU(deplacement, Deplacement.getDeplacements(echiquier, new int[] {y, x}));
					}
				}
			}
			
			return immobile(deplacement);
		}
		
		return false;
	}

	public static void ajouterCoups(char[][] echiquier, boolean isJ1) {
		String key = echiqiuerToString(echiquier, isJ1);

		if (coups.containsKey(key))
			coups.replace(key, coups.get(key) + 1);
		else
			coups.put(key, 1);
	}

	public static boolean repetition() {
		for (int rep : coups.values()) {
			if (rep >= 3) return true;
		}

		return false;
	}

	/*
	* Crée et retourne une copie de l'échiquier passé en paramètre.
	*/
	public static char[][] copierEchiquier(char[][] echiquier) {
		char[][] copieEchequier = new char[8][8];
		for (int i = 0; i < echiquier.length; i++) {
			copieEchequier[i] = Arrays.copyOf(echiquier[i], echiquier[i].length);
		}
		return copieEchequier;
	}

	/*
	* Réinitialise toutes les valeurs d'un tableau bidimensionnel de booléens à 'false'.
	*/
	public static void flushBoolean(boolean[][] tab) {
		for (int i = 0; i < tab.length; i++) {
			Arrays.fill(tab[i], false);
		}
	}

	/*
	* Combine deux tableaux bidimensionnels de booléens en utilisant l'opération logique OU sur chacune des case de 2 tableau.
	* Ne marche que lorsque t1 et t2 ont les même dimensions
	*/
    public static boolean[][] combinerOU(boolean[][] t1, boolean[][] t2) {
        boolean[][] combinaison = new boolean[8][8];

        for (int i = 0; i < t1.length; i++) {
            for (int j = 0; j < t1[0].length; j++) {
                combinaison[i][j] = t1[i][j] || t2[i][j];
            }
        }

        return combinaison;
    }

	/*
	* Combine deux tableaux bidimensionnels de booléens en utilisant une opération de différence:
	* Chaque élément du tableau résultant est 'true' si l'élément correspondant dans t1 est 'true'
	* et l'élément correspondant dans t2 est 'false'.
	* Ne marche que lorsque t1 et t2 ont les même dimensions
	*/
    public static boolean[][] combinerDIFF(boolean[][] t1, boolean[][] t2) {
        boolean[][] combinaison = new boolean[8][8];

        for (int i = 0; i < t1.length; i++) {
            for (int j = 0; j < t1[0].length; j++) {
                combinaison[i][j] = t1[i][j] && !t2[i][j];
            }
        }

        return combinaison;
    }

	/*
	* Vérifie si une pièce est immobile grace a sont tableau des positions possibles.
	* Renvoie 'true' si aucune position n'est possible, sinon false.
	*/
	public static boolean immobile(boolean[][] tab) {
		for (boolean[] ligne : tab)
			for (boolean b : ligne)
				if (b) return false;

		return true;
	}

	/*
	* Convertit une coordonnée de l'échiquier au format texte en position dans un tableau au format {y, x}.
	*/
	public static int[] getPosition(String coordonne) {
		if (coordonne.length() != 2 ||
			coordonne.charAt(1) > '8' ||
			coordonne.charAt(1) < '1' ||
			Character.toUpperCase(coordonne.charAt(0)) < 'A' ||
			Character.toUpperCase(coordonne.charAt(0)) > 'H') return null;

		return new int[]{
			8 - (coordonne.charAt(1) - '0'),
			Character.toUpperCase(coordonne.charAt(0)) - 'A'
		};
	}

	/*
	* Convertit une position dans un tableau au format {y, x} en une coordonnée de l'échiquier au format texte.
	*/
	public static String getCoordonne(int[] position) {
		StringBuilder coo = new StringBuilder(2);
		coo.append((char)('i' - position[1]));
		coo.append((char)('1' + position[0]));
		return coo.toString();
	}

	public static String echiqiuerToString(char[][] echiquier, boolean isJ1) {
		StringBuilder notation = new StringBuilder(30);
		
		for (int y = 0; y < echiquier.length; y++) {
			int espace = 0;
			for (int x = 0; x < echiquier[y].length; x++) {
				if (echiquier[y][x] == ' ') espace++;
				else {
					if (espace != 0) notation.append(espace);
					notation.append(echiquier[y][x]);
				}
			}
			if (espace != 0) notation.append(espace);
			if (y != 7) notation.append('/');
		}

		notation.append(' ');
		notation.append((isJ1 ? 'w' : 'b'));

		return notation.toString();
	}

	/*
	* Convertit l'état actuel d'un échiquier en notation Forsyth-Edwards (FEN) qui représentant
	* en une chaine de caractère: la position des pièces sur l'échiquier, le joueur au trait,
	* les droits au roque,  la possibilité de prise en passant, le nombre de demi-coups et le nombre de coups.
	*/
	public static String echiqiuerToFEN(char[][] echiquier, boolean isJ1, int demiCoups, int coups) {
		StringBuilder FEN = new StringBuilder(40);

		FEN.append(echiqiuerToString(echiquier, isJ1));
		FEN.append(' ');

		StringBuilder roque = new StringBuilder(4);
		if (!Deplacement.deplacementPieceRoque[0][0] && (!Deplacement.deplacementPieceRoque[0][1] || !Deplacement.deplacementPieceRoque[0][2])) {
			if (!Deplacement.deplacementPieceRoque[0][2]) {
				roque.append('K');
			}
			if (!Deplacement.deplacementPieceRoque[0][1]) {
				roque.append('Q');
			}
		}

		if (!Deplacement.deplacementPieceRoque[1][0] && (!Deplacement.deplacementPieceRoque[1][1] || !Deplacement.deplacementPieceRoque[1][2])) {
			if (!Deplacement.deplacementPieceRoque[1][2]) {
				roque.append('k');
			}
			if (!Deplacement.deplacementPieceRoque[1][1]) {
				roque.append('q');
			}
		}

		if (roque.length() == 0) FEN.append('-');
		else FEN.append(roque);
		FEN.append(' ');

		StringBuilder enPassant = new StringBuilder(4);
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 8; i++) {
				if (Deplacement.deplacementDeuxCases[j][i]) enPassant.append(getCoordonne(new int[] {(j == 0 ? 5 : 2), i}));
			}
		}

		if (enPassant.length() == 0) FEN.append('-');
		else FEN.append(enPassant);
		FEN.append(' ');

		FEN.append(demiCoups);
		FEN.append(' ');
		FEN.append(coups);

		return FEN.toString();
	}
}
