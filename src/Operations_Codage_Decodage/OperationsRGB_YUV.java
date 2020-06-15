package Operations_Codage_Decodage;
import Application.Application;


/******************************************************
 * @Classe: OPERATIONS RGB ET YUV
 * 
 * @Resumer: 
 * Cette classe possède les méthodes pour convertir
 * la matrice d'une image RGB vers une matrice YUV et
 * d'une matrice YUV vers une matrice RGB. 
 * 
 * @Source:
 * Inspiration: //https://www.google.ca/url?sa=t&rct=j&q=&esrc=s&source=web&cd=10&cad=rja&uact=8&ved=0ahUKEwijwYv8kdrXAhVlzIMKHXtBCbYQFgh1MAk&url=http%3A%2F%2Fsvn.j3d.org%2Fcode%2Ftrunk%2Fsrc%2Fjava%2Forg%2Fj3d%2Futil%2FColorUtils.java&usg=AOvVaw0_JCreiimbmAfP3Jd5fyOc
 * Alternative: https://commons.apache.org/proper/commons-imaging/jacoco/org.apache.commons.imaging.formats.jpeg.decoder/YCbCrConverter.java.html
 * 
 ******************************************************/
public class OperationsRGB_YUV {

	
	/******************************************************
	 * CONSTRUCTEUR
	 ******************************************************/
	public OperationsRGB_YUV(){}
	
	/******************************************************
	 * GET RGB a YUV
	 * 
	 * @Resumer:
	 * Conversion de la matrice RGB vers une matrice YUV.
	 * 
	 * @Param: int[][][] matriceRGB
	 * @Return: double[][][] matriceYUV
	 * @Complexity: O(n^2)
	 * 
	 ******************************************************/
	public double[][][] getRGBtoYUV(int[][][] matriceRGB){

		//Matrice 3D representant le nombre de couleurs, la hauteur et la largeur 
		double[][][] matriceRGBtoYUV = new double[matriceRGB.length][matriceRGB[0].length][matriceRGB[0][0].length];

		//Deux boucles pour traiter la hauteur et la largeur d'une image
		for (int i = 0; i < matriceRGB[0].length; i++) {
			for (int j = 0; j < matriceRGB[0][0].length; j++) {
				
				//Recupere les coefficiens R, G et B de la matricd 3D
				double R = matriceRGB[Application.R][i][j];
				double G = matriceRGB[Application.G][i][j];
				double B = matriceRGB[Application.B][i][j];

				//Initialisaton des coefficients Y, U et V
				double Y = (0.299 * R + 0.587 * G + 0.114 * B);
				double U = ((B - Y) * 0.492);
				double V = ((R - Y) * 0.877);	
				
				//Insertion des coefficients Y, U et V dans une matrice 3D
				matriceRGBtoYUV[Application.Y][i][j] = Y;
				matriceRGBtoYUV[Application.U][i][j] = U;
				matriceRGBtoYUV[Application.V][i][j] = V;
			}
		}
		return matriceRGBtoYUV;
	}
	
	/******************************************************
	 * GET YUV a RGB
	 * 
	 * @Resumer:
	 * Conversion de la matrice YUV vers une matrice RGB.
	 * 
	 * @Param: double[][][] matriceYUV
	 * @Return: int[][][] matriceRGB
	 * @Complexity: O(n^2)
	 * 
	 ******************************************************/
	public int[][][] getYUVtoRGB(double[][][] matriceYUV){
		
		//Matrice 3D representant le nombre de couleurs, la hauteur et la largeur 
		int[][][] matriceYUVtoRGB = new int[matriceYUV.length][matriceYUV[0].length][matriceYUV[0][0].length];
		
		//Deux boucles pour traiter la hauteur et la largeur d'une image
		for (int i = 0; i < matriceYUV[0].length; i++) {
			for (int j = 0; j < matriceYUV[0][0].length; j++) {
				 
				//Recupere les coefficiens Y, U et V de la matricd 3D
				double Y = matriceYUV[Application.Y][i][j];
				double U = matriceYUV[Application.U][i][j];
				double V = matriceYUV[Application.V][i][j];
				
				//Initialisation des coefficients R, G et B
				int R = (int) (Y + 1.13983 * V);
				int G = (int) (Y - 0.39465 * U - 0.58060 * V);
				int B = (int) (Y + 2.03211 * U);	
				
				//Conversion des coefficients R, G et B
				R = R < 0 ? 0 : R > 255 ? 255 : R;
				G = G < 0 ? 0 : G > 255 ? 255 : G;
				B = B < 0 ? 0 : B > 255 ? 255 : B;
				
				//Insertion des coefficients R, G et B dans une matrice 3D
				matriceYUVtoRGB[Application.R][i][j] = R;
				matriceYUVtoRGB[Application.G][i][j] = G;
				matriceYUVtoRGB[Application.B][i][j] = B;				
			}
		}
		return matriceYUVtoRGB;
	}
}
