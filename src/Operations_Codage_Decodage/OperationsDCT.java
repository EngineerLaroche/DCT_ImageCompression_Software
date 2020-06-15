package Operations_Codage_Decodage;

import Application.Application;

/******************************************************
 * @Classe: OPERATIONS DCT
 * 
 * @Resumer:
 * Cette classe contient les méthodes pour traiter 
 * la transformation discrète du Cosinus et la 
 * transformation inverse du Cosinus.

 * @Source:
 * Comprehension: https://www.math.cuhk.edu.hk/~lmlui/dct.pdf
 * Inspiration DCT: https://imagej.nih.gov/ij/plugins/download/DCT_.java
 * 
 ******************************************************/
public class OperationsDCT {

	/***************************
	 * Constante
	 ***************************/
	private static final int 
	BLOCK_SIZE = Application.BLOCK_SIZE;
	
	private static final double
	RACINE_COEFF = (1.0 / Math.sqrt(2.0)),
	RACINE_BLOCK = Math.sqrt(2.0 * BLOCK_SIZE);
	
	/***************************
	 * Constante Matrice Cos
	 ***************************/
	//On pourrait simplifier les calculs, mais on le laisse ainsi pour aider à la compréhension
	private static final double[][] DCT = {
			
			{Math.cos(0.0*Math.PI*(0.125 * 0.0 + 0.0625)),Math.cos(0.0*Math.PI*(0.125 * 1.0 + 0.0625)),Math.cos(0.0*Math.PI*(0.125 * 2.0 + 0.0625)),Math.cos(0.0*Math.PI*(0.125 * 3.0 + 0.0625)),Math.cos(0.0*Math.PI*(0.125 * 4.0 + 0.0625)),Math.cos(0.0*Math.PI*(0.125 * 5.0 + 0.0625)),Math.cos(0.0*Math.PI*(0.125 * 6.0 + 0.0625)),Math.cos(0.0*Math.PI*(0.125 * 7.0 + 0.0625))},
			{Math.cos(1.0*Math.PI*(0.125 * 0.0 + 0.0625)),Math.cos(1.0*Math.PI*(0.125 * 1.0 + 0.0625)),Math.cos(1.0*Math.PI*(0.125 * 2.0 + 0.0625)),Math.cos(1.0*Math.PI*(0.125 * 3.0 + 0.0625)),Math.cos(1.0*Math.PI*(0.125 * 4.0 + 0.0625)),Math.cos(1.0*Math.PI*(0.125 * 5.0 + 0.0625)),Math.cos(1.0*Math.PI*(0.125 * 6.0 + 0.0625)),Math.cos(1.0*Math.PI*(0.125 * 7.0 + 0.0625))},
			{Math.cos(2.0*Math.PI*(0.125 * 0.0 + 0.0625)),Math.cos(2.0*Math.PI*(0.125 * 1.0 + 0.0625)),Math.cos(2.0*Math.PI*(0.125 * 2.0 + 0.0625)),Math.cos(2.0*Math.PI*(0.125 * 3.0 + 0.0625)),Math.cos(2.0*Math.PI*(0.125 * 4.0 + 0.0625)),Math.cos(2.0*Math.PI*(0.125 * 5.0 + 0.0625)),Math.cos(2.0*Math.PI*(0.125 * 6.0 + 0.0625)),Math.cos(2.0*Math.PI*(0.125 * 7.0 + 0.0625))},
			{Math.cos(3.0*Math.PI*(0.125 * 0.0 + 0.0625)),Math.cos(3.0*Math.PI*(0.125 * 1.0 + 0.0625)),Math.cos(3.0*Math.PI*(0.125 * 2.0 + 0.0625)),Math.cos(3.0*Math.PI*(0.125 * 3.0 + 0.0625)),Math.cos(3.0*Math.PI*(0.125 * 4.0 + 0.0625)),Math.cos(3.0*Math.PI*(0.125 * 5.0 + 0.0625)),Math.cos(3.0*Math.PI*(0.125 * 6.0 + 0.0625)),Math.cos(3.0*Math.PI*(0.125 * 7.0 + 0.0625))},
			{Math.cos(4.0*Math.PI*(0.125 * 0.0 + 0.0625)),Math.cos(4.0*Math.PI*(0.125 * 1.0 + 0.0625)),Math.cos(4.0*Math.PI*(0.125 * 2.0 + 0.0625)),Math.cos(4.0*Math.PI*(0.125 * 3.0 + 0.0625)),Math.cos(4.0*Math.PI*(0.125 * 4.0 + 0.0625)),Math.cos(4.0*Math.PI*(0.125 * 5.0 + 0.0625)),Math.cos(4.0*Math.PI*(0.125 * 6.0 + 0.0625)),Math.cos(4.0*Math.PI*(0.125 * 7.0 + 0.0625))},
			{Math.cos(5.0*Math.PI*(0.125 * 0.0 + 0.0625)),Math.cos(5.0*Math.PI*(0.125 * 1.0 + 0.0625)),Math.cos(5.0*Math.PI*(0.125 * 2.0 + 0.0625)),Math.cos(5.0*Math.PI*(0.125 * 3.0 + 0.0625)),Math.cos(5.0*Math.PI*(0.125 * 4.0 + 0.0625)),Math.cos(5.0*Math.PI*(0.125 * 5.0 + 0.0625)),Math.cos(5.0*Math.PI*(0.125 * 6.0 + 0.0625)),Math.cos(5.0*Math.PI*(0.125 * 7.0 + 0.0625))},
			{Math.cos(6.0*Math.PI*(0.125 * 0.0 + 0.0625)),Math.cos(6.0*Math.PI*(0.125 * 1.0 + 0.0625)),Math.cos(6.0*Math.PI*(0.125 * 2.0 + 0.0625)),Math.cos(6.0*Math.PI*(0.125 * 3.0 + 0.0625)),Math.cos(6.0*Math.PI*(0.125 * 4.0 + 0.0625)),Math.cos(6.0*Math.PI*(0.125 * 5.0 + 0.0625)),Math.cos(6.0*Math.PI*(0.125 * 6.0 + 0.0625)),Math.cos(6.0*Math.PI*(0.125 * 7.0 + 0.0625))},
			{Math.cos(7.0*Math.PI*(0.125 * 0.0 + 0.0625)),Math.cos(7.0*Math.PI*(0.125 * 1.0 + 0.0625)),Math.cos(7.0*Math.PI*(0.125 * 2.0 + 0.0625)),Math.cos(7.0*Math.PI*(0.125 * 3.0 + 0.0625)),Math.cos(7.0*Math.PI*(0.125 * 4.0 + 0.0625)),Math.cos(7.0*Math.PI*(0.125 * 5.0 + 0.0625)),Math.cos(7.0*Math.PI*(0.125 * 6.0 + 0.0625)),Math.cos(7.0*Math.PI*(0.125 * 7.0 + 0.0625))}};


	/******************************************************
	 * CONSTRUCTEUR
	 ******************************************************/
	public OperationsDCT(){}
	
	/******************************************************
	 * GET MATRICE DCT
	 * 
	 * @Resumer:
	 * Traitement du DCT avec la matrice YUV reçu en paramètre.
	 * Les quatre boucles permettent de traiter les coefficients
	 * U, V, I et J de la formule mathématique du DCT. 
	 * 
	 * On utilise un tableau 2D qui possède des valeurs
	 * pré-calculées pour améliorer l'efficacité du DCT.
	 * Dans le cas présent, nous savons que les coefficients
	 * auront une valeur en 0 et 7.
	 * 
	 * @Param: double[][] matriceYUV
	 * @Return: double[][] matriceDCT
	 * @Complexity: O(n^4)
	 * 
	 ******************************************************/
	public double[][] getMatriceDCT(double[][] matriceYUV){		
		double[][] matriceDCT = new double[BLOCK_SIZE][BLOCK_SIZE];
	
		for (int u = 0; u < BLOCK_SIZE; u++) {
			for (int v = 0; v < BLOCK_SIZE; v++) {
				double somme = 0.0;		
				for (int i = 0; i < BLOCK_SIZE; i++) {
					for (int j = 0; j < BLOCK_SIZE; j++) {	
						
						//Somme des multiplications des cosinus avec les coefficients de la matrice
						somme += (DCT[u][i] * DCT[v][j] * matriceYUV[i][j]);
					}
				}
				matriceDCT[u][v] = (getCoeffUV(u, v) * somme);
			}
		}
		return matriceDCT;
	}

	/******************************************************
	 * GET MATRICE I-DCT
	 * 
	 * @Resumer:
	 * Traitement du DCT inverse avec la matrice YUV reçu en 
	 * paramètre. Les quatre boucles permettent de traiter
	 * les coefficients U, V, I et J de la formule mathématique 
	 * du DCT. 
	 * 
	 * On utilise un tableau 2D qui possède des valeurs
	 * pré-calculées pour améliorer l'efficacité du IDCT.
	 * Dans le cas présent, nous savons que les coefficients
	 * auront une valeur en 0 et 7.
	 * 
	 * @Param: double[][] matriceYUV
	 * @Return: double[][] matriceIDCT
	 * @Complexity: O(n^4)
	 * 
	 ******************************************************/
	public double[][] getMatriceIDCT(double[][] matriceYUV){		
		double[][] matriceIDCT = new double[BLOCK_SIZE][BLOCK_SIZE];
	
		for (int i = 0; i < BLOCK_SIZE; i++) {
			for (int j = 0; j < BLOCK_SIZE; j++) {
				double somme = 0.0;		
				for (int u = 0; u < BLOCK_SIZE; u++) {
					for (int v = 0; v < BLOCK_SIZE; v++) {
						
						//Somme des multiplications des cosinus avec les coefficients de la matrice
						somme += (getCoeffUV(u, v) * DCT[u][i] * DCT[v][j] * matriceYUV[u][v]);
					}
				}
				matriceIDCT[i][j] = somme;
			}
		}
		return matriceIDCT;
	}

	/******************************************************
	 * GET COEFFICIENTS U et V
	 * 
	 * @Resumer:
	 * On applique des transformation mathématique pour les 
	 * coefficients U et V.
	 * 
	 * - Racine_Block: sqrt(2 * BLOCK_SIZE)
	 * - retourn: (u * v) / sqrt(2 * BLOCK_SIZE)
	 * 
	 * @Param: double u, double v
	 * @Return: double
	 * @Complexity: O(1)
	 * 
	 ******************************************************/
	private double getCoeffUV(double u, double v){
		
		u = u == 0.0 ? RACINE_COEFF : 1.0 ;
		v = v == 0.0 ? RACINE_COEFF : 1.0 ;
		return ((u * v) / RACINE_BLOCK);
	}
}
