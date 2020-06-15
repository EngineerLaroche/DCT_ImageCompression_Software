package Operations_Codage_Decodage;

import Application.Application;

/******************************************************
 * @Classe: OPERATIONS ZIGZAG
 * 
 * @Resumer:
 * Cette classe supporte les opérations zigzag simple et inverse.
 * Permet de convertir une matrice à une liste zigzag et
 * de convertir une liste zigzag à une matrice.
 * C'est ici qu'on change l'ordre des valeurs des listes 
 * et des matrices.
 * 
 * @Source:
 * Inspiration: https://imagej.nih.gov/ij/plugins/download/DCT_.java
 * 
 ******************************************************/
public class OperationsZigZag {

	/***************************
	 * Constante
	 ***************************/
	private static final int 
	BLOCK_SIZE = Application.BLOCK_SIZE;

	/******************************************************
	 * CONSTRUCTEUR
	 ******************************************************/
	public OperationsZigZag(){}

	/******************************************************
	 * GET ARRAY ZIGZAG
	 * 
	 * @Resumer:
	 * Permet de convertir une matrice en liste zigzag.
	 * On utilise un gabarit universel pour traiter la
	 * matrice avec un processus ZigZag.
	 * 
	 * @Param: double[][] matriceDCT
	 * @Return:double[] arrayZigZag
	 * @Complexity: O(n^2)
	 * 
	 ******************************************************/
	public double[] getArrayZigZag(double[][] matriceDCT) {
		double[] arrayZigZag = new double[BLOCK_SIZE * BLOCK_SIZE];

		//Parcourt les coefficients du block 8 x 8
		for (int i = 0; i < BLOCK_SIZE; i++) {
			for (int j = 0; j < BLOCK_SIZE; j++){ 
				
				//Utilisation du gabarit zigzag pour reorganiser les coefficients
				arrayZigZag[gabaritMatriceZigZag()[i][j]] = matriceDCT[i][j];
			}
		}
		return arrayZigZag;
	}

	/******************************************************
	 * GET MATRICE ZIGZAG (INVERSE)
	 * 
	 * @Resumer:
	 * Permet de convertir un tableau zigzag ordonné en 
	 * matrice zigzag. inverse.
	 * 
	 * On utilise un gabarit universel pour traiter la
	 * matrice avec un processus ZigZag.
	 * 
	 * @Pamar: double[][] arrayACDC, int espace
	 * @Return: double[][] matriceZigZag
	 * @Complexity: O(n^2)
	 * 
	 ******************************************************/
	public double[][] getMatriceZigZag(double[][] arrayACDC, int espace) {
		double[][] matriceZigZag = new double[BLOCK_SIZE][BLOCK_SIZE * BLOCK_SIZE];

		//Parcourt les coefficients du block 8 x 8
		for (int i = 0; i < BLOCK_SIZE; i++) {
			for (int j = 0; j < BLOCK_SIZE; j++) {

				//Utilisation du gabarit zigzag pour reorganiser les coefficients
				matriceZigZag[i][j] = arrayACDC[espace][gabaritMatriceZigZag()[i][j]];
			}
		}
		return matriceZigZag;
	}


	/******************************************************
	 * GABARIT MATRICE ZIGZAG
	 * 
	 * @Resumer:
	 * Utilisé pour supporter les opérations de conversions
	 * zigzag et zigzag inverse.
	 * 
	 * @Return: int[][] matrice
	 * @Complexity: O(n * log(n))
	 * 
	 ******************************************************/
	private int[][] gabaritMatriceZigZag() {
		int[][] matrice = new int[BLOCK_SIZE][BLOCK_SIZE];

		int x, y;
		int zig = 0;
		int zag = BLOCK_SIZE * (BLOCK_SIZE - 1) / 2;

		for (int i = 0; i < BLOCK_SIZE; i++) {
			if (i % 2 == 0) {

				x = i; y = 0; 
				while (x > -1) {
					matrice[x][y] = zig;					
					zig++; x--; y++; 
				}

				x = i; y = BLOCK_SIZE - 1;
				while (x < BLOCK_SIZE) {
					matrice[x][y] = zag;
					zag++; x++; y--; 
				}
			} else {

				y = i; x = 0;
				while ( y > -1) {
					matrice[x][y] = zig;
					zig++; x++; y--;
				}

				y = i; x = BLOCK_SIZE - 1;
				while (y < BLOCK_SIZE) {
					matrice[x][y] = zag;
					zag++; x--; y++; 
				}
			}
		}//retourne la matrice zigzag utilisée comme gabarit
		return matrice;
	}
}
