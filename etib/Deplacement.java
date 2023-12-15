public class Deplacement {
	public static boolean[][] getDeplacements(char[][] damier, int[] position) {
		boolean isJ1 = Character.isUpperCase(damier[position[0]][position[1]]);

		return switch (Character.toLowerCase(damier[position[0]][position[1]])) {
			case 'k' -> getDeplacementsRoi(damier, position, isJ1);
			case 'q' -> getDeplacementsDame(damier, position);
			case 'r' -> getDeplacementsTour(damier, position);
			case 'b' -> getDeplacementsFou(damier, position);
			case 'n' -> getDeplacementsCavalier(damier, position);
			case 'p' -> getDeplacementsPion(damier, position, isJ1);
			default -> new boolean[0][0];
		};
	}

	private static boolean[][] getDeplacementsPossibles(
		char[][] damier, 
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

				if (damier[newRow][newCol] != ' ') {
					if (Character.isUpperCase(damier[position[0]][position[1]]) != Character.isUpperCase(damier[newRow][newCol])) {
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

	private static boolean[][] getDeplacementsRoi(char[][] damier, int[] position, boolean isJ1) {
		int[][] deplacementsRoi = {
			{ -1, -1 }, { -1, 0 }, { -1, 1 },
			{ 0, -1 }, 			   { 0, 1 },
			{ 1, -1 },  { 1, 0 },  { 1, 1 }
		};
		boolean[][] deplacements = getDeplacementsPossibles(damier, position, deplacementsRoi, false);

		boolean[][] caseEchec = new boolean[8][8];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (isJ1 ^ Character.isUpperCase(damier[y][x]) && Character.toLowerCase(damier[y][x]) != 'k' && damier[y][x] != ' ') {
					if (Character.toLowerCase(damier[y][x]) == 'p')
						caseEchec = Logique.combinerOU(caseEchec, getMangerPion(damier, new int[] {y, x}, !isJ1));
					else
						caseEchec = Logique.combinerOU(caseEchec, getDeplacements(damier, new int[] {y, x}));
				}
			}
		}
		deplacements = Logique.combinerDIFF(deplacements, caseEchec);

		return deplacements;
	}

	private static boolean[][] getDeplacementsDame(char[][] damier, int[] position) {
		int[][] deplacementsDame = {
			{ -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 },
			{ -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }
		};
		return getDeplacementsPossibles(damier, position, deplacementsDame, true);
	}

	private static boolean[][] getDeplacementsTour(char[][] damier, int[] position) {
		int[][] deplacementsTour = {
			{ -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }
		};
		return getDeplacementsPossibles(damier, position, deplacementsTour, true);
	}

	private static boolean[][] getDeplacementsFou(char[][] damier, int[] position) {
		int[][] deplacementsFou = {
			{ -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 }
		};
		return getDeplacementsPossibles(damier, position, deplacementsFou, true);
	}

	private static boolean[][] getDeplacementsCavalier(char[][] damier, int[] position) {
		int[][] deplacementsCavalier = {
		  { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 },
		  { 1, -2 }, { 1, 2 }, { 2, -1 }, { 2, 1 }
		};
		return getDeplacementsPossibles(damier, position, deplacementsCavalier, false);
	}

	private static boolean[][] getDeplacementsPion(char[][] damier, int[] position, boolean isJ1) {
		boolean[][] deplacements = new boolean[8][8];
		if (position[0] != (isJ1 ? 7 : 0) && damier[position[0] + (isJ1 ? -1 : 1)][position[1]] == ' ') deplacements[position[0] + (isJ1 ? -1 : 1)][position[1]] = true;

		if (position[0] != (isJ1 ? 0 : 7)) {
			int[][] diagonal = {{position[0] + (isJ1 ? -1 : 1), position[1] - 1}, {position[0] + (isJ1 ? -1 : 1), position[1] + 1}};
			if (position[1] != 0 && damier[diagonal[0][0]][diagonal[0][1]] != ' ' && isJ1 ^ Character.isUpperCase(damier[diagonal[0][0]][diagonal[0][1]]))
				deplacements[diagonal[0][0]][diagonal[0][1]] = true;
			if (position[1] != 7 && damier[diagonal[1][0]][diagonal[1][1]] != ' ' && isJ1 ^ Character.isUpperCase(damier[diagonal[1][0]][diagonal[1][1]]))
				deplacements[diagonal[1][0]][diagonal[1][1]] = true;
		}

		int[] avance = { position[0] + (isJ1 ? -2 : 2), position[1] };
		if (position[0] == (isJ1 ? 6 : 1) && damier[avance[0]][avance[1]] == ' ') {
			deplacements[avance[0]][avance[1]] = true;
		}

		// En passant
	
		return deplacements;
	}

	public static boolean[][] getMangerPion(char[][] damier, int[] position, boolean isJ1) {
		boolean[][] deplacements = new boolean[8][8];

		int[] pos = {position[0] + (isJ1 ? -1 : 1), position[1]};
		if (position[0] != (isJ1 ? 0 : 7)) {
			if (position[1] > 0 && (damier[pos[0]][pos[1] - 1] == ' ' || isJ1 ^ Character.isUpperCase(damier[pos[0]][pos[1] - 1])))
				deplacements[pos[0]][pos[1] - 1] = true;
			if (position[1] < 7 && (damier[pos[0]][pos[1] + 1] == ' ' || isJ1 ^ Character.isUpperCase(damier[pos[0]][pos[1] + 1])))
				deplacements[pos[0]][pos[1] + 1] = true;
		}

		return deplacements;
	}
}
