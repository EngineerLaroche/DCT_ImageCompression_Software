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
 * @Titre: SEQUENCE DECODAGE
 * 
 * @Resumer:
 * Cette classe s'occupe d'appeler toutes les classes
 * nécessaire afin de décompresser une image de format .szl
 * vers un format .ppm.
 *  
 ******************************************************/
public class SequenceDecodage {

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
	BLOCK_SIZE = Application.BLOCK_SIZE;

	private static final String 
	ERROR_IMAGE_SIZE = "\n***** ERREUR *****\n\nImage importée n'est pas de HAUTEUR égale à sa LARGEUR.\n",
	ERROR_BLOCK_SIZE = "\n***** ERREUR *****\n\nTaille du bloc n'est pas 8 x 8.\n";


	/******************************************************
	 * CONSTRUCTEUR
	 * 
	 * @Resumer:
	 * On initialise les classes nécessaires à la séquence
	 * qui permet la décompression d'une image.
	 * On appel la méthode qui démarre la séquence.
	 * 
	 * @Param:String inputFile, String outputDecompressed
	 * 
	 ******************************************************/
	public SequenceDecodage(String inputFile, String outputFile) throws IOException{

		operationsDCT = new OperationsDCT();
		operationsAC_DC = new OperationsAC_DC();
		operationsZigZag = new OperationsZigZag();
		operationsRGB_YUV = new OperationsRGB_YUV();

		demarrerSequencesDecodage(inputFile, outputFile);
	}

	/******************************************************
	 * DEMARRER SEQUENCES DECODAGE
	 * 
	 * @Resumer:
	 * On appelle toutes les classes nécessaire pour traiter 
	 * et transformer les données d'une image choisie afin 
	 * de la decompresser.
	 * 
	 * On traite les actions suivantes: 
	 * 
	 * - Lecture .szl
	 * - AC et DC
	 * - Obtention blocs 8 x 8
	 * - ZigZag
	 * - Quantification
	 * - IDCT
	 * - RGB vers YUV
	 * - Ecriture image
	 * 
	 * @Param: String inputFile, String outputDecompressed
	 * @Complexity: O(n^4)
	 * 
	 ******************************************************/
	private void demarrerSequencesDecodage(String inputFile, String outputFile) throws IOException{

		//Liste Image compresse
		int[] array_Compressed = SZLReaderWriter.readSZLFile(inputFile);	

		//On verifie si l'image est carré (hauteur = largeur).
		if(array_Compressed[0] == array_Compressed[1]){

			//Obtenir la quantite de bloc (8 x 8) de l'image
			int nbrBlock = (array_Compressed[0] / BLOCK_SIZE);

			//Initialisation de la classe Quantification avec le facteur de qualité en parametre
			operationsQuantif = new OperationsQuantification((double) array_Compressed[3]);

			//Traitement de la portion inverse AC et inverse DC pour Y, U et V
			List<double[][]> listeDynamique = new ArrayList<double[][]>();
			listeDynamique = operationsAC_DC.lireAC_DC(nbrBlock, Application.Y);
			listeDynamique = operationsAC_DC.lireAC_DC(nbrBlock, Application.U);
			listeDynamique = operationsAC_DC.lireAC_DC(nbrBlock, Application.V);	

			//Matrice Image compressé: lecture fichier (couleurs, hauteur et largeur)
			double[][][] matrice_YUV = new double[array_Compressed[2]][array_Compressed[0]][array_Compressed[1]];

			//Utilisé pour récupérer les matrice dans la liste dynamique AC et DC
			int index = 0;

			//Deux boucles pour passer à travers la hauteur et la largeur de l'image
			for (int i = 0; i < array_Compressed[0]; i++) {
				for (int j = 0; j < array_Compressed[1]; j++) {

					//On s'assure d'obtenir pour chaque bloc un format 8 x 8
					if((i % BLOCK_SIZE) == 0 && (j % BLOCK_SIZE) == 0){

						//Matrice Zigzag inverse pour Y, U et V
						double[][] matriceZigZag_Y = operationsZigZag.getMatriceZigZag(listeDynamique.get(index), Application.Y);
						double[][] matriceZigZag_U = operationsZigZag.getMatriceZigZag(listeDynamique.get(index), Application.U);
						double[][] matriceZigZag_V = operationsZigZag.getMatriceZigZag(listeDynamique.get(index), Application.V);

						//Matrice de Quantification inverse pour Y, U et V
						double[][] matriceQuantif_Y = operationsQuantif.getDequantification(matriceZigZag_Y, operationsQuantif.MATRICE_Q_Y);
						double[][] matriceQuantif_U = operationsQuantif.getDequantification(matriceZigZag_U, operationsQuantif.MATRICE_Q_UV);
						double[][] matriceQuantif_V = operationsQuantif.getDequantification(matriceZigZag_V, operationsQuantif.MATRICE_Q_UV);
						
						//On verifie si la taille des block est bien 8 x 8
						if(respectTailleBlock(matriceQuantif_Y, matriceQuantif_U, matriceQuantif_V)){

							//Matrice inverse DCT pour Y, U et V
							double[][] matriceIDCT_Y = operationsDCT.getMatriceIDCT(matriceQuantif_Y);
							double[][] matriceIDCT_U = operationsDCT.getMatriceIDCT(matriceQuantif_U);
							double[][] matriceIDCT_V = operationsDCT.getMatriceIDCT(matriceQuantif_V);

							//On verifie si la taille des block est bien 8 x 8
							if(respectTailleBlock(matriceIDCT_Y, matriceIDCT_U, matriceIDCT_V)){

								//Deux boucles pour insérer tous les blocs 8 x 8 dans des matrices
								for (int m = 0; m < BLOCK_SIZE; m++) {
									for (int n = 0; n <BLOCK_SIZE; n++) {

										//Conversion d'une matrice 2D à une matrice 3D
										matrice_YUV[Application.Y][m + i][n + j] = matriceIDCT_Y[m][n];
										matrice_YUV[Application.U][m + i][n + j] = matriceIDCT_U[m][n];
										matrice_YUV[Application.V][m + i][n + j] = matriceIDCT_V[m][n];
									}
								}
								index++;
							}
						}
					}
				}
			}

			//Conversion de la matrice 3D YUV vers RGB
			int[][][] matrice_Image = operationsRGB_YUV.getYUVtoRGB(matrice_YUV);

			//Affiche l'information de l'image et du temps de compression dans la console
			afficheInfo(inputFile, outputFile, matrice_Image, array_Compressed[3]);

			//Écriture des données de l'image compressé dans un format .ppm
			PPMReaderWriter.writePPMFile(outputFile, matrice_Image);
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
	 * utile concernant l'image et la rapidité de la décompression.
	 * 
	 * @Param: String inputFile, int[][][] matrice_Image, int facteurQualite
	 * 
	 ******************************************************/
	private void afficheInfo(String inputFile, String outputFile, int[][][] matrice_Image, int facteurQualite){

		//Information concernant l'image
		System.out.println("\n************************************\n* 	Décompression Image    	   *\n************************************");
		System.out.println("Dimensions image: 	" + matrice_Image[0].length + " x " + matrice_Image[0][0].length);
		System.out.println("Nombre de Blocks 8x8: 	" + (matrice_Image[0].length / 8));
		System.out.println("Facteur de Qualité:	" + facteurQualite);
		System.out.println("\nImage Input:  	" + inputFile);
		System.out.println("Image Output:  	" + outputFile);
	}
}
