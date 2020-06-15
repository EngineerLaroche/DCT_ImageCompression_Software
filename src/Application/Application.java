package Application;
import javax.swing.SwingUtilities;

import Interface.FenetrePrincipale;

/*******************************************************
 * @Titre: APPLICATION
 * 
 * @Resumer:
 * The Application class is where the different functions are called to either encode
 * a PPM file to the Squeeze-Light format or to decode a Squeeze-Ligth image
 * into PPM format. It is the implementation of the simplified JPEG block 
 * diagrams.
 *  
 ******************************************************/
public class Application {

	/*
	 * The entire application assumes that the blocks are 8x8 squares.
	 */
	public static final int BLOCK_SIZE = 8;

	/*
	 * The number of dimensions in the color spaces.
	 */
	public static final int COLOR_SPACE_SIZE = 3;

	/***************************
	 * Espace Couleur RGB
	 ***************************/
	public static final int R = 0;
	public static final int G = 1;
	public static final int B = 2;

	/***************************
	 * Espace Couleur YUV
	 ***************************/
	public static final int Y = 0;
	public static final int U = 1;
	public static final int V = 2;

	
	/*******************************************************
	 * APPLICATION
	 * 
	 * @Resumer:
	 * D�marrage d'une nouvelle application dans un processus 
	 * s�par� pour �viter les conflits de gestion d'�v�nements.
	 * 
	 * @Param: String args[]
	 * @Complexity: O(1)
	 *  
	 ******************************************************/
	public static void main(String[] args) {

		//Le systeme d�marre le programme d�s que possible.
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
	
				try {
					//Demarre l'interface graphique de l'Application
					new FenetrePrincipale();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
