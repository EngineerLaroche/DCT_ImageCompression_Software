package Operations_Codage_Decodage;
import Application.Application;

/******************************************************
 * @Classe: OPERATIONS QUANTIFICATION
 * 
 * @Resumer:
 * Cette classe supporte les opérations de 
 * quantification simple et inverse. 
 * Le facteur de qualité et le traitement des 
 * coefficients à partir les tables se fait ici.
 * 
 ******************************************************/
public class OperationsQuantification {

	/***************************
	 * Constantes Matrice[][]
	 ***************************/
	public final static int[][] MATRICE_Q_Y = {
			{16, 40, 40, 40, 40, 40, 51, 61},
			{40, 40, 40, 40, 40, 58, 60, 55},
			{40, 40, 40, 40, 40, 57, 69, 56},
			{40, 40, 40, 40, 51, 87, 80, 62},
			{40, 40, 40, 56, 68, 109, 103, 77},
			{40, 40, 55, 64, 81, 104, 113, 92},
			{49, 64, 78, 87, 103, 121, 120, 101},
			{72, 92, 95, 98, 112, 100, 103, 95}};

	public final static int[][] MATRICE_Q_UV = { 
			{17, 40, 40, 95, 95, 95, 95, 95},
			{40, 40, 40, 95, 95, 95, 95, 95},
			{40, 40, 40, 95, 95, 95, 95, 95},
			{40, 40, 95, 95, 95, 95, 95, 95},
			{95, 95, 95, 95, 95, 95, 95, 95},
			{95, 95, 95, 95, 95, 95, 95, 95},
			{95, 95, 95, 95, 95, 95, 95, 95},
			{95, 95, 95, 95, 95, 95, 95, 95}};

	/***************************
	 * Constantes
	 ***************************/
	private static final double 
	MIN_QUALITE = 1.0,
	MI_QUALITE = 50.0,
	MAX_QUALITE = 100.0;

	private static final int
	BLOCK_SIZE = Application.BLOCK_SIZE;

	private static final 
	String ERREUR_FACTEUR_QUALITE = "\n***** ERREUR *****\nLe facteur de qualité ne correspond pas à une valeur entre 1 et 100.\nAllez voire la classe Quantification.";

	/***************************
	 * Variable
	 ***************************/
	private double facteurQualite;


	/******************************************************
	 * CONSTRUCTEUR
	 * 
	 * @Param: double _facteurQualite
	 * 
	 ******************************************************/
	public OperationsQuantification(double _facteurQualite){

		if(_facteurQualite == 0){
			this.facteurQualite = _facteurQualite + 1;
		}else{
			this.facteurQualite = _facteurQualite;
		}
	}


	/******************************************************
	 * GET MATRICE QUANTIFICATION
	 * 
	 * @Resumer:
	 * Traitement des coefficients en fonction du facteur
	 * de qualité. 
	 * 
	 * Si le facteur de qualité = 100, rien de change
	 * Si le facteur de qualité est entre 1 et 99, on 
	 * applique des transformations. (etc.)
	 * 
	 * @Param: double[][] matriceDCT, double[][] matrice_Q_YUV
	 * @Return: double[][] matriceQuantif
	 * @Complexity: O(n^2)
	 * 
	 ******************************************************/
	public double[][] getQuantification(double[][] matriceDCT, int[][] matrice_Q_YUV){

		double[][] matriceQuantif = new double[BLOCK_SIZE][BLOCK_SIZE];

		//On parcourt les coefficients d'un block 8 x 8
		for (int i = 0; i < BLOCK_SIZE; i++) {
			for (int j = 0; j < BLOCK_SIZE; j++) {

				//Si le facteur qualité = 100
				if(facteurQualite == MAX_QUALITE){
					matriceQuantif[i][j] = matriceDCT[i][j];
				}
				//Si le facteur qualité est entre 1 et 99
				else if(MIN_QUALITE <= facteurQualite && facteurQualite < MAX_QUALITE){
					matriceQuantif[i][j] =  Math.round(matriceDCT[i][j] / (getAlpha() * matrice_Q_YUV[i][j]));	
				}
				else{
					//Message erreur indiquant un facteur de qualité non entre 1 et 100
					throw new IllegalArgumentException(ERREUR_FACTEUR_QUALITE); 
				}
			}
		}
		return matriceQuantif;
	}

	/******************************************************
	 * GET AMATRICE DEQUANTIFICATION
	 * 
	 * @Resumer:
	 * Si le facteur de qualité = 100, rien de change
	 * Si le facteur de qualité est entre 1 et 99, on 
	 * applique des transformations. (etc.)
	 * 
	 * @Param: double[][] matriceZigZag, double[][] matrice_Q_YUV
	 * @Return: double[][] matriceDeQuantif
	 * @Complexity: O(n^2)
	 * 
	 ******************************************************/
	public double[][] getDequantification(double[][] matriceZigZag, int[][] matrice_Q_YUV){

		double[][] matriceDeQuantif = new double[BLOCK_SIZE][BLOCK_SIZE];

		//On parcourt les coefficients d'un block 8 x 8
		for (int i = 0; i < BLOCK_SIZE; i++) {
			for (int j = 0; j < BLOCK_SIZE; j++) {

				//Si le facteur qualité = 100
				if(facteurQualite == MAX_QUALITE){
					matriceDeQuantif[i][j] = matriceZigZag[i][j];
				}
				//Si le facteur qualité est entre 1 et 99
				else if(MIN_QUALITE <= facteurQualite && facteurQualite < MAX_QUALITE){
					matriceDeQuantif[i][j] = Math.round(matriceZigZag[i][j] * (getAlpha() * matrice_Q_YUV[i][j]));
				}
				else{
					//Message erreur indiquant un facteur de qualité non entre 1 et 100
					throw new IllegalArgumentException(ERREUR_FACTEUR_QUALITE); 
				}
			}
		}
		return matriceDeQuantif;
	}

	/******************************************************
	 * GET ALPHA
	 * 
	 * @Resumer:
	 * Transformation de la valeur alpha selon le facteur
	 * de qualité de l'image.
	 * 
	 * Si facteur de qualité <= 50, alors il devient (50 / facteur qualité) 
	 * Si facteur de qualité > 50, alors il devient (2 - (facteur qualité/50)
	 * 
	 * @Return: double alpha
	 * @Complexity: O(1)
	 * 
	 ******************************************************/
	private double getAlpha(){
		return (facteurQualite <= MI_QUALITE ? (MI_QUALITE / facteurQualite) : (2.0 - (facteurQualite / MI_QUALITE)));
	}
}
