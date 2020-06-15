package TestUnitaire;

import java.util.ArrayList;
import java.util.List;

public class FormatYUV_DCT {
	
//	int hauteur;
//	int largeur;
//	
//	//1
//	public double[][] Format3DTo2D(double[][][] tabYUV3D, int couleur) {
//
//		double[][] tabYUV2D = new double[tabYUV3D[0].length][tabYUV3D[0][0].length];
//		
//		for (int i = 0; i < tabYUV2D.length; i++) {
//			for (int j = 0; j < tabYUV2D[0].length; j++) {
//				tabYUV2D[i][j] = tabYUV3D[couleur][i][j];
//			}
//		}
//		return tabYUV2D;
//	}
//	
//	//2
//	public double[] formatFromYUV(double[][] tabRGBYUV) {
//		
//		if (tabRGBYUV.length % 8 != 0)
//			hauteur = tabRGBYUV.length + 8 - (tabRGBYUV.length % 8);
//		else
//			hauteur = tabRGBYUV.length;
//		
//		if (tabRGBYUV[0].length % 8 != 0)
//			largeur = tabRGBYUV[0].length + 8 - (tabRGBYUV[0].length % 8);
//		else
//			largeur = tabRGBYUV[0].length;
//
//		if (tabRGBYUV.length % 8 != 0 || tabRGBYUV[0].length % 8 != 0) {
//			return format8par8(formatMultiple8(hauteur, largeur, tabRGBYUV), hauteur, largeur);
//		} else
//			return format8par8(tabRGBYUV, hauteur, largeur);
//	}
//
//	//2.1
//	public double[][] formatMultiple8(int hauteur, int largeur, double[][] tab_3D) {
//		
//		double[][] tabFormat = new double[hauteur][largeur];
//		
//		for (int i = 0; i < hauteur; i++) {
//			for (int j = 0; j < largeur; j++) {
//				tabFormat[i][j] = tab_3D[i][j];
//			}
//		}	
//
//		return tabFormat;
//	}	
//
//	//2.2
//	public double[] format8par8(double[][] tabMid, int hauteur, int largeur) {
//		
//		int k = 0;
//		int i = 0;
//		int j = 0;
//		int position = 0;
//		
//		double[] tabTempo = new double[hauteur * largeur];
//		
//		do {
//			for (i = 0; i < hauteur; i++) {
//				for (j = position; j < position + 8; j++) {
//					
//					tabTempo[k] = tabMid[i][j];
//
//					k++;
//				}
//			}
//
//			position = position + 8;
//
//		} while (position < largeur);
//		
//		return tabTempo;
//	}
//
//	//3 
//	public double[][] decoupage8par8(double[] tabYUV2D, int position) {
//		double[][] tabYUVF8 = new double[8][8];
//		int j = 0;
//		int k = 0;
//
//		for (int i = position; i < position + 64; i++) {
//
//			tabYUVF8[j][k] = tabYUV2D[i];
//			k++;
//			if (k == 8) {
//				k = 0;
//				j++;
//				if (j > 7)
//					j = 0;
//			}
//		}
//		
//		return tabYUVF8;
//	}
//	
//	//4
//	public ArrayList<Double> tab2DToList(ArrayList<Double> list, double[][] tab) {
//			
//		for (int i = 0; i < tab.length; i++) {
//			for (int j = 0; j < tab[0].length; j++) {
//				list.add(tab[i][j]);
//			}
//		}
//		return list;
//	}
//	
//	//4.1
//	public ArrayList<Double> tab1DToList(ArrayList<Double> list, double[] tab) {
//		
//		for (int i = 0; i < tab.length; i++) {
//			list.add(tab[i]);
//		}
//		return list;
//	}
//	
//	//5
//	public List<double[][]> listToList2D(List<Double> list, int espace) {
//		
//		List<double[][]> listReturn = new ArrayList<double[][]>();
//		int u = 0;
//		int i = 0;
//		
//		double[][] tempo = new double[3][64];
//
//		while(i <= list.size()) {
//
//				for (int k = 0; k < 64; k++) {
//					tempo[espace][k] = list.get(i);
//					i++;
//				}
//				
//			listReturn.add(u, tempo);
//			u++;
//			
//			if(i == list.size()) {
//				break;
//			}
//				
//		}
//		return listReturn;
//	}
//	
//	//5.1
//	public ArrayList<Double> list2DTolist(List<double[][]> list2D, int espace) {
//		ArrayList<Double> listReturn = new ArrayList<Double>();
//	
//		
//		for (int i = 0; i < list2D.size(); i++) {
//			for (int j = 0; j < 64; j++) {
//				listReturn.add(list2D.get(i)[espace][j]);
//			}
//		}
//		
//		return listReturn;
//	}
//	
//	//return 1
//	public double[][] listToTab2D(ArrayList<Double> list, int position) {
//		double[][] tab = new double[8][8];
//		
//		for (int i = 0; i < tab.length; i++) {
//			for (int j = 0; j < tab[0].length; j++) {
//				tab[i][j] = (double) list.get(position);
//				position++;
//			}
//		}
//		return tab;
//	}
//	
//	//return 1.1
//	public double[] listToTab1D(ArrayList<Double> list, int position) {
//			double[] tab = new double[64];
//			
//			for (int i = 0; i < tab.length; i++) {
//					tab[i] = (double) list.get(position);
//					position++;
//			}
//			return tab;
//		}
//	
//	public double[][] tab1DToTab2D(double[] tab1D) {
//		double[][] tab2D = new double[8][8];
//		int k = 0;
//		
//		for (int i = 0; i < tab2D.length; i++) {
//			for (int j = 0; j < tab2D.length; j++) {
//				tab2D[i][j] = tab1D[k];
//				k++;
//			}
//		}
//		return tab2D;
//	}
//	
//	//return 2
//	public double[] tab2DToTab1D(double[] tab1D, double[][] tab2D, int position) {
//		
//		for (int i = 0; i < tab2D.length; i++) {
//			for (int j = 0; j < tab2D.length; j++) {
//				tab1D[position] = tab2D[i][j];
//				position++;
//			}
//		}
//		return tab1D;
//	}
//	
//	//return 3
//	public double[][] tab1DToFinalTab(double[][] finalTab, double[] tab1D) {
//	
//		int h = 0;
//		int l = 0;
//		int positionLMax = 7;
//
//		for (int i = 0; i < tab1D.length; i++) {
//			finalTab[h][l] = tab1D[i];
//			
//			l++;
//			
//			if(l > positionLMax) {
//				l = positionLMax - 7;
//				h++;
//			}
//			if(h == finalTab.length) {
//				h = 0;
//				l = positionLMax + 1;
//				positionLMax = positionLMax + 8;
//				
//			}		
//		}
//		
//		return finalTab;
//	}
	
}
