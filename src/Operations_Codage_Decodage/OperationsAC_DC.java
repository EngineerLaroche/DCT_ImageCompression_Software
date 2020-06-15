package Operations_Codage_Decodage;

import java.util.ArrayList;
import java.util.List;
import Application.Application;
import Outils_Reader_Writer.Entropy;

/*******************************************************
 * @Titre: OPERATIONS AC ET DC
 * 
 * @Resumer:
 * Cette classe Permet de supporter les opérations
 * DC et AC pour le codage et le décodage.
 *  
 ******************************************************/
public class OperationsAC_DC {

	/***************************
	 * Constantes
	 ***************************/
	private static final int 
	BLOCK_SIZE = Application.BLOCK_SIZE,
	COLOR_SIZE = Application.COLOR_SPACE_SIZE;

	private static final String
	ERROR_RUNLENGTH = "\n***** ERREUR *****\n\nLa valeur runLength ne peut pas être négative.";

	private List<double[][]> listeDynamiqueInverse = new ArrayList<double[][]>();


	/******************************************************
	 * CONSTRUCTEUR
	 ******************************************************/
	public OperationsAC_DC(){}


	/******************************************************
	 * ECRIRE AC ET DC 
	 * 
	 * @Resumer:
	 * On commence avec les opérations qui concerne le DC 
	 * et ensuite on procède aux opérations du AC. 
	 * 
	 * @Param: List<double[][]> listeDynamique, int espace
	 * @Complexity: O(n^2)
	 * 
	 ******************************************************/
	public void ecrireAC_DC(List<double[][]> listeDynamique, int espace){

		/* Portion de l'ecriture DC */

		for (int dc = 0; dc < listeDynamique.size(); dc++) {
			Entropy.writeDC((int) listeDynamique.get(dc)[espace][0]);
		}
		
		/* Portion de l'ecriture AC */

		for (int ac = 0; ac < listeDynamique.size(); ac++) {

			//initialisation les coefficients AC
			int runLength = 0, value = 0;

			//Parcourt les coefficients du block 8 x 8
			for (int i = 1; i < (BLOCK_SIZE * BLOCK_SIZE); i++) {
				if(listeDynamique.get(ac)[espace][i] == 0 && i == ((BLOCK_SIZE * BLOCK_SIZE) - 1)){

					//On s'assure que runLength n'est pas négatif
					if(runLength >= 0){
						Entropy.writeAC(runLength, value);
					}
					else{//Message indiquanr un runLength qui est négatif
						throw new IllegalArgumentException(ERROR_RUNLENGTH); 
					}
				}
				else if(listeDynamique.get(ac)[espace][i] == 0){
					runLength ++;
				}
				else{//On s'assure que runLength n'est pas négatif
					if(runLength >= 0){
						Entropy.writeAC(runLength, (int) listeDynamique.get(ac)[espace][i]);
						runLength = 0; value = 0;	
					}
					else{//Message indiquanr un runLength qui est négatif
						throw new IllegalArgumentException(ERROR_RUNLENGTH); 
					}
				}
			}
		}
	}

	/******************************************************
	 * LIRE AC ET DC (INVERSE)
	 * 
	 * @Resumer:
	 * On commence avec les opérations qui concerne le DC 
	 * et ensuite on procède aux opérations du AC. 
	 * 
	 * @Param: int nbrBlock, int espace
	 * @Return: List<double[][]>
	 * @Complexity: O(n^2)
	 * 
	 ******************************************************/
	public List<double[][]> lireAC_DC(int nbrBlock, int espace){

		/* Portion de lecture DC */

		if(espace == Application.Y){

			//Boucle qui parcourt la totalite des blocks (8 x 8) de l'image
			for (int dc = 0; dc < (nbrBlock * nbrBlock); dc++) {

				double[][] matrice_DC = new double[COLOR_SIZE][BLOCK_SIZE * BLOCK_SIZE];

				//Garde en mémoire dans une matrice l'Amplitude et la taille de la table Huffman
				matrice_DC[espace][0] = Entropy.readDC();		
				listeDynamiqueInverse.add(matrice_DC);
			}
		}
		else{
			//Boucle qui parcourt la totalite des blocks (8 x 8) de l'image
			for (int dc = 0; dc < (nbrBlock * nbrBlock); dc++) {		
				listeDynamiqueInverse.get(dc)[espace][0] = Entropy.readDC();	
			}
		}

		/* Portion de lecture AC */

		for (int ac = 0; ac < listeDynamiqueInverse.size(); ac++) {

			//Parcourt les coefficients du block 8 x 8
			for(int valeur = 1; valeur < (BLOCK_SIZE * BLOCK_SIZE);){

				//Récupère la paire des coefficients AC runLength et value
				int[] array_AC = Entropy.readAC();

				//Si la premiere position n'est pas 0
				if(array_AC[0] != 0){
					for (int i = 0; i < array_AC[0]; i++) {
						listeDynamiqueInverse.get(ac)[espace][valeur++] = 0.0;
					}
					listeDynamiqueInverse.get(ac)[espace][valeur++] = (double) array_AC[1];
				}
				//Si la premiere position est 0
				else if(array_AC[0] == 0){
					listeDynamiqueInverse.get(ac)[espace][valeur++] = (double) array_AC[1];
				}
			}
		}
		return listeDynamiqueInverse;
	}
}
