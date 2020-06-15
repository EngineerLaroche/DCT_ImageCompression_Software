package Application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Operations_Codage_Decodage.OperationsAC_DC;
import Operations_Codage_Decodage.OperationsDCT;
import Operations_Codage_Decodage.OperationsQuantification;
import Operations_Codage_Decodage.OperationsRGB_YUV;
import Operations_Codage_Decodage.OperationsZigZag;
import Outils_Reader_Writer.PPMReaderWriter;
import Outils_Reader_Writer.SZLReaderWriter;

/*******************************************************
 * @Titre: SEQUENCE CODAGE
 * 
 * @Resumer:
 * Cette classe s'occupe d'appeler toutes les classes
 * nécessaire afin de compresser une image de format .ppm
 * vers un format .szl.
 *  
 ******************************************************/
public class SequenceCodage {

	/***************************
	 * Classes instanciees
	 ***************************/
	private OperationsDCT operationsDCT = null;
	private OperationsAC_DC operationsAC_DC = null;
	private OperationsZigZag operationsZigZag = null;
	private OperationsRGB_YUV operationsRGB_YUV = null;
	private OperationsQuantification operationsQuantif = null;

	/***************************
	 * Constantes
	 ***************************/
	private static final int 
	BLOCK_SIZE = Application.BLOCK_SIZE,
	COLOR_SIZE = Application.COLOR_SPACE_SIZE;

	private static final String 
	ERROR_IMAGE_SIZE = "\n***** ERREUR *****\n\nImage importée n'est pas de HAUTEUR égale à sa LARGEUR ou est Erronné.\n",
	ERROR_BLOCK_SIZE = "\n***** ERREUR *****\n\nTaille du bloc n'est pas 8 x 8.\n";

	/***************************
	 * Variables
	 ***************************/
	private double facteurQualite;


	/******************************************************
	 * CONSTRUCTEUR
	 * @throws IOException 
	 * 
	 * @Resumer:
	 * On initialise les classes nécessaires à la séquence
	 * qui permet la compression d'une image.
	 * On appel la méthode qui démarre la séquence.
	 * 
	 * @Param:String inputFile, String outputFile, double _facteurQualite
	 * 
	 ******************************************************/
	public SequenceCodage(String inputFile, String outputFile, double _facteurQualite) throws IOException{

		this.facteurQualite = _facteurQualite;

		operationsDCT = new OperationsDCT();
		operationsAC_DC = new OperationsAC_DC();
		operationsZigZag = new OperationsZigZag();
		operationsRGB_YUV = new OperationsRGB_YUV();
		operationsQuantif = new OperationsQuantification(facteurQualite);

		demarrerSequenceCodage(inputFile, outputFile);
	}

	/******************************************************
	 * DEMARRER SEQUENCES CODAGE
	 * 
	 * @Resumer:
	 * On appelle toutes les classes nécessaire pour traiter 
	 * et transformer les données d'une image choisie afin 
	 * de la compresser.
	 * 
	 * On traite les actions suivantes: 
	 * 
	 * - Lecture image
	 * - RGB vers YUV
	 * - Obtention blocs 8 x 8
	 * - DCT
	 * - Quantification
	 * - ZigZag
	 * - AC et DC
	 * - Ecriture image
	 * 
	 * @Param: String inputFile, String outputFile
	 * @Complexity: O(n^4)
	 * 
	 * @Source:
	 * Facteur de 8: https://stackoverflow.com/questions/17738872/check-number-is-multiple-of-another
	 * 
	 ******************************************************/
	private void demarrerSequenceCodage(String inputFile, String outputFile) throws IOException{

		//Matrice Image: lecture fichier (couleurs, hauteur et largeur)
		int[][][] matrice_Image = PPMReaderWriter.readPPMFile(inputFile);
		
		//On verifie si l'image est carré (hauteur = largeur).
		if(matrice_Image[0].length == matrice_Image[0][0].length){

			//Conversion d'une matrice RGB à une matrice YUV
			double[][][] matrice_YUV = operationsRGB_YUV.getRGBtoYUV(matrice_Image);

			List<double[][]> listeDynamique = new ArrayList<double[][]>();
			double[][] matrice_Y = new double[BLOCK_SIZE][BLOCK_SIZE];
			double[][] matrice_U = new double[BLOCK_SIZE][BLOCK_SIZE];
			double[][] matrice_V = new double[BLOCK_SIZE][BLOCK_SIZE];

			//Deux boucles pour passer à travers la hauteur et la largeur de l'image
			for (int i = 0; i < matrice_YUV[0].length; i++) {
				for (int j = 0; j < matrice_YUV[0][0].length; j++) {

					//On s'assure d'obtenir pour chaque des blocs de format 8 x 8
					if((i % BLOCK_SIZE) == 0 && (j % BLOCK_SIZE) == 0){

						//Garde en memoire la position des couleurs (3) et les 64 coefficients d<un block 8 x 8
						double[][] matrice_ZigZag = new double[COLOR_SIZE][BLOCK_SIZE * BLOCK_SIZE];	

						//Deux boucles pour insérer tous les blocs 8 x 8 dans des matrices
						for (int m = 0; m < BLOCK_SIZE; m++) {
							for (int n = 0; n < BLOCK_SIZE; n++) {

								//Conversion d'une matrice 3D à une matrice 2D
								matrice_Y[m][n] = matrice_YUV[Application.Y][i + m][j + n];
								matrice_U[m][n] = matrice_YUV[Application.U][i + m][j + n];
								matrice_V[m][n] = matrice_YUV[Application.V][i + m][j + n];
							}
						}

						//On verifie si la taille des block est bien 8 x 8
						if(respectTailleBlock(matrice_Y, matrice_U, matrice_V)){

							//Matrice DCT pour Y, U et V
							double[][] matriceDCT_Y = operationsDCT.getMatriceDCT(matrice_Y);
							double[][] matriceDCT_U = operationsDCT.getMatriceDCT(matrice_U);
							double[][] matriceDCT_V = operationsDCT.getMatriceDCT(matrice_V);

							//On verifie si la taille des block est bien 8 x 8
							if(respectTailleBlock(matriceDCT_Y, matriceDCT_U, matriceDCT_V)){

								//Matrice de Quantification pour Y, U et V
								double[][] matriceQuantif_Y = operationsQuantif.getQuantification(matriceDCT_Y, operationsQuantif.MATRICE_Q_Y);
								double[][] matriceQuantif_U = operationsQuantif.getQuantification(matriceDCT_U, operationsQuantif.MATRICE_Q_UV);
								double[][] matriceQuantif_V = operationsQuantif.getQuantification(matriceDCT_V, operationsQuantif.MATRICE_Q_UV);

								//On verifie si la taille des block est bien 8 x 8
								if(respectTailleBlock(matriceQuantif_Y, matriceQuantif_U, matriceQuantif_V)){

									//Matrice Zigzag pour Y, U et V
									matrice_ZigZag[Application.Y] = operationsZigZag.getArrayZigZag(matriceQuantif_Y);
									matrice_ZigZag[Application.U] = operationsZigZag.getArrayZigZag(matriceQuantif_U);
									matrice_ZigZag[Application.V] = operationsZigZag.getArrayZigZag(matriceQuantif_V);

									listeDynamique.add(matrice_ZigZag);
								}
							}
						}
					}
				}
			}

			//Traitement de la portion AC et DC pour Y, U et V
			operationsAC_DC.ecrireAC_DC(listeDynamique, Application.Y);
			operationsAC_DC.ecrireAC_DC(listeDynamique, Application.U);
			operationsAC_DC.ecrireAC_DC(listeDynamique, Application.V);

			//Affiche l'information de l'image et du temps de compression dans la console
			afficheInfo(inputFile, outputFile, matrice_Image);

			//Écriture des données de l'image dans un format .szl
			SZLReaderWriter.writeSZLFile(outputFile , matrice_YUV[0].length, matrice_YUV[0][0].length, (int) facteurQualite);
		}
		else{
			//Erreur indiquant que l'image n'est pas de HAUTEUR égale à sa LARGEUR
			throw new IllegalArgumentException(ERROR_IMAGE_SIZE); 
		}	
	}

	/******************************************************
	 * VERIFICATION DE LA TAILLE DES BLOCKS
	 * 
	 * @Resumer:
	 * C'est ici qu'on vérifie la taille des blocks
	 * afin de s'assurer que les matrices ont des blocks
	 * de dimension 8 x 8.
	 * 
	 * @Param: double[][] matrice_1, double[][] matrice_2, double[][] matrice_3
	 * @Complexity: O(1)
	 * 
	 ******************************************************/
	private boolean respectTailleBlock(double[][] matrice_1, double[][] matrice_2, double[][] matrice_3){
		boolean block8x8 = false;
		if(matrice_1.length == matrice_1[0].length 
				&& matrice_2.length == matrice_2[0].length 
					&& matrice_3.length == matrice_3[0].length){
			block8x8 = true;
		}else{
			throw new IllegalArgumentException(ERROR_BLOCK_SIZE); 
		}
		return block8x8;
	}

	/******************************************************
	 * AFFICHER INFORMATION
	 * 
	 * @Resumer:
	 * C'est ici qu'on affiche dans la console de l'information
	 * utile concernant l'image.
	 * 
	 * @Param: String inputFile, int[][][] matrice_Image
	 * 
	 ******************************************************/
	private void afficheInfo(String inputFile, String outputFile, int[][][] matrice_Image){

		//Information concernant l'image
		System.out.println("\n************************************\n* 	  Compression Image	   *\n************************************");
		System.out.println("Dimensions image: 	" + matrice_Image[0].length + " x " + matrice_Image[0][0].length);
		System.out.println("Nombre de Blocks 8x8: 	" + (matrice_Image[0].length / 8));
		System.out.println("Facteur de Qualité:	" + (int) facteurQualite);
		System.out.println("\nImage Input:  	" + inputFile);
		System.out.println("Image Output:  	" + outputFile);
	}
}
