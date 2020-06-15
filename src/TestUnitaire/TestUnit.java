package TestUnitaire;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import Operations_Codage_Decodage.OperationsAC_DC;
import Outils_Reader_Writer.SZLReaderWriter;

public class TestUnit {
	
//	public static final String 
//	INPUT_FILE = "C:/Users/Tiahiti/workspace/GTI310_TP04/mandrill.ppm",
//	COMPRESSED_FILE = "C:/Users/Tiahiti/workspace/GTI310_TP04/mandrill.szl",
//	DECOMPRESSED_FILE = "C:/Users/Tiahiti/workspace/GTI310_TP04/mandrill.ppm",
//	
//	COMPRESSED_NAME = "Compressed_",
//	DECOMPRESSED_NAME = "Decompressed_",
//	
//	COMPRESSED_REPERTOIRE = "C:/Users/Tiahiti/workspace/GTI310_TP04/",
//	DECOMPRESSED_REPERTOIRE = "C:/";
//	
//	private RGBYUVTestUnit convertRGBtoYUVTestUnit = new RGBYUVTestUnit();
//	private DCTTestUnit dctTestUnit = new DCTTestUnit();
//	private QuantificationTestUnit quantificationTestUnit = new QuantificationTestUnit();
//	private ZigZagTestUnit zigZagTestUnit = new ZigZagTestUnit();
//	private OperationsAC_DC operationsACDC = new OperationsAC_DC();
//	private FormatYUV_DCTTest formatYUV_DCT = new FormatYUV_DCTTest();
//
//	@Test
//	public void rgbYuvTest() throws IOException {
//		convertRGBtoYUVTestUnit.before();
//		double[][][] tab3D = convertRGBtoYUVTestUnit.rbgToYUV();	
//		convertRGBtoYUVTestUnit.yuvToRBG1(tab3D);
//	}
//	
//	@Test
//	public void dctTest() {
//		convertRGBtoYUVTestUnit.before();
//		double[][][] tab3D = convertRGBtoYUVTestUnit.rbgToYUV();
//		
//		formatYUV_DCT.before();
//		double[] tabYF = formatYUV_DCT.format3DTo1DTest(tab3D, 0);
//		double[] tabUF = formatYUV_DCT.format3DTo1DTest(tab3D, 1);
//		double[] tabVF = formatYUV_DCT.format3DTo1DTest(tab3D, 2);
//		
//		int position = 0;
//		
//		ArrayList<Double> listY = new ArrayList<Double>();
//		ArrayList<Double> listU = new ArrayList<Double>();
//		ArrayList<Double> listV = new ArrayList<Double>();
//		
//		while(position <= tabYF.length - 64){
//			dctTestUnit.before();	
//			double[][] tempo = dctTestUnit.YUVToDCTTest(formatYUV_DCT.format8par8(tabYF, position));
//			position = position + 64;	
//			
//			listY = formatYUV_DCT.tab2DToList(listY, tempo);
//		}
//		
//		position = 0;
//		
//		while(position <= tabUF.length - 64){
//			dctTestUnit.before();
//			double[][] tempo = dctTestUnit.YUVToDCTTest(formatYUV_DCT.format8par8(tabUF, position));	
//			position = position + 64;
//
//			listU = formatYUV_DCT.tab2DToList(listU, tempo);
//		}
//		
//		position = 0;
//		
//		while(position <= tabVF.length - 64){
//			dctTestUnit.before();
//			double[][] tempo = dctTestUnit.YUVToDCTTest(formatYUV_DCT.format8par8(tabVF, position));			
//			position = position + 64;
//			
//			listV = formatYUV_DCT.tab2DToList(listV, tempo);
//		}
//		
//		double[][] tabY = new double[8][8];
//		double[][] tabU = new double[8][8];
//		double[][] tabV = new double[8][8];
//		
//		double[] tabY1D = new double[tabYF.length];
//		double[] tabU1D = new double[tabYF.length];
//		double[] tabV1D = new double[tabYF.length];
//		
//		position = 0;
//		
//		while (position < listY.size()) {
//			tabY = formatYUV_DCT.listToTab2D(listY, position);
//			double[][] tempo = dctTestUnit.DCTToYUVTest(tabY);
//			tabY1D = formatYUV_DCT.tab2DToTab1D(tabY1D, tempo, position);
//			position = position + 64;
//		}
//		
//		position = 0;
//		
//		while (position < listU.size()) {
//			tabU = formatYUV_DCT.listToTab2D(listU, position);
//			double[][] tempo = dctTestUnit.DCTToYUVTest(tabU);
//			tabU1D = formatYUV_DCT.tab2DToTab1D(tabU1D, tempo, position);
//			position = position + 64;
//		}
//		
//		position = 0;
//		
//		while (position < listV.size()) {
//			tabV = formatYUV_DCT.listToTab2D(listV, position);
//			double[][] tempo = dctTestUnit.DCTToYUVTest(tabV);
//			tabV1D = formatYUV_DCT.tab2DToTab1D(tabV1D, tempo, position);
//			position = position + 64;
//		}
//		
//		double[][] finalTabY2D = new double[tab3D[0].length][tab3D[0][0].length];
//		double[][] finalTabU2D = new double[tab3D[0].length][tab3D[0][0].length];
//		double[][] finalTabV2D = new double[tab3D[0].length][tab3D[0][0].length];
//		
//		finalTabY2D = formatYUV_DCT.tab1DToFinalTab(finalTabY2D, tabY1D);
//		finalTabU2D = formatYUV_DCT.tab1DToFinalTab(finalTabU2D, tabU1D);
//		finalTabV2D = formatYUV_DCT.tab1DToFinalTab(finalTabV2D, tabV1D);
//		
//		double[][][] tab3DD = new double[tab3D.length][tab3D[0].length][tab3D[0][0].length];
//		
//		for (int i = 0; i < finalTabY2D.length; i++) {
//			for (int j = 0; j < finalTabY2D[0].length; j++) {
//				tab3DD[0][i][j] = finalTabY2D[i][j];
//				tab3DD[1][i][j] = finalTabU2D[i][j];
//				tab3DD[2][i][j] = finalTabV2D[i][j];
//			}
//		}
//		
//		convertRGBtoYUVTestUnit.yuvToRBG2(tab3DD);
//	}
//
//	@Test
//	public void quantificationTest() {
//		convertRGBtoYUVTestUnit.before();
//		double[][][] tab3D = convertRGBtoYUVTestUnit.rbgToYUV();
//		
//		formatYUV_DCT.before();
//		double[] tabYF = formatYUV_DCT.format3DTo1DTest(tab3D, 0);
//		double[] tabUF = formatYUV_DCT.format3DTo1DTest(tab3D, 1);
//		double[] tabVF = formatYUV_DCT.format3DTo1DTest(tab3D, 2);
//		
//		int position = 0;
//		
//		ArrayList<Double> listY = new ArrayList<Double>();
//		ArrayList<Double> listU = new ArrayList<Double>();
//		ArrayList<Double> listV = new ArrayList<Double>();
//		
//		quantificationTestUnit.before();
//		while(position <= tabYF.length - 64){
//			dctTestUnit.before();	
//			double[][] tempo = dctTestUnit.YUVToDCTTest(formatYUV_DCT.format8par8(tabYF, position));
//			position = position + 64;
//			tempo = quantificationTestUnit.quantifiation(tempo, quantificationTestUnit.Qy);		
//			listY = formatYUV_DCT.tab2DToList(listY, tempo);
//		}
//		
//		position = 0;
//		
//		while(position <= tabUF.length - 64){
//			dctTestUnit.before();
//			double[][] tempo = dctTestUnit.YUVToDCTTest(formatYUV_DCT.format8par8(tabUF, position));	
//			position = position + 64;
//			tempo = quantificationTestUnit.quantifiation(tempo, quantificationTestUnit.Quv);
//			listU = formatYUV_DCT.tab2DToList(listU, tempo);
//		}
//		
//		position = 0;
//		
//		while(position <= tabVF.length - 64){
//			dctTestUnit.before();
//			double[][] tempo = dctTestUnit.YUVToDCTTest(formatYUV_DCT.format8par8(tabVF, position));			
//			position = position + 64;
//			tempo = quantificationTestUnit.quantifiation(tempo, quantificationTestUnit.Quv);
//			listV = formatYUV_DCT.tab2DToList(listV, tempo);
//		}
//		
//		double[][] tabY = new double[8][8];
//		double[][] tabU = new double[8][8];
//		double[][] tabV = new double[8][8];
//		
//		double[] tabY1D = new double[tabYF.length];
//		double[] tabU1D = new double[tabYF.length];
//		double[] tabV1D = new double[tabYF.length];
//		
//		position = 0;
//		
//		while (position < listY.size()) {
//			tabY = formatYUV_DCT.listToTab2D(listY, position);
//			tabY = quantificationTestUnit.dequantifiation(tabY, quantificationTestUnit.Qy);
//			double[][] tempo = dctTestUnit.DCTToYUVTest(tabY);
//			tabY1D = formatYUV_DCT.tab2DToTab1D(tabY1D, tempo, position);
//			position = position + 64;
//		}
//		
//		position = 0;
//		
//		while (position < listU.size()) {
//			tabU = formatYUV_DCT.listToTab2D(listU, position);
//			tabU = quantificationTestUnit.dequantifiation(tabU, quantificationTestUnit.Quv);
//			double[][] tempo = dctTestUnit.DCTToYUVTest(tabU);
//			tabU1D = formatYUV_DCT.tab2DToTab1D(tabU1D, tempo, position);
//			position = position + 64;
//		}
//		
//		position = 0;
//		
//		while (position < listV.size()) {
//			tabV = formatYUV_DCT.listToTab2D(listV, position);
//			tabV = quantificationTestUnit.dequantifiation(tabV, quantificationTestUnit.Quv);
//			double[][] tempo = dctTestUnit.DCTToYUVTest(tabV);
//			tabV1D = formatYUV_DCT.tab2DToTab1D(tabV1D, tempo, position);
//			position = position + 64;
//		}
//		
//		double[][] finalTabY2D = new double[tab3D[0].length][tab3D[0][0].length];
//		double[][] finalTabU2D = new double[tab3D[0].length][tab3D[0][0].length];
//		double[][] finalTabV2D = new double[tab3D[0].length][tab3D[0][0].length];
//		
//		finalTabY2D = formatYUV_DCT.tab1DToFinalTab(finalTabY2D, tabY1D);
//		finalTabU2D = formatYUV_DCT.tab1DToFinalTab(finalTabU2D, tabU1D);
//		finalTabV2D = formatYUV_DCT.tab1DToFinalTab(finalTabV2D, tabV1D);
//		
//		double[][][] tab3DD = new double[tab3D.length][tab3D[0].length][tab3D[0][0].length];
//		
//		for (int i = 0; i < finalTabY2D.length; i++) {
//			for (int j = 0; j < finalTabY2D[0].length; j++) {
//				tab3DD[0][i][j] = finalTabY2D[i][j];
//				tab3DD[1][i][j] = finalTabU2D[i][j];
//				tab3DD[2][i][j] = finalTabV2D[i][j];
//			}
//		}
//		
//		convertRGBtoYUVTestUnit.yuvToRBG3(tab3DD);
//	}
//	
//	@Test
//	public void zigZagTest() {
//		convertRGBtoYUVTestUnit.before();
//		double[][][] tab3D = convertRGBtoYUVTestUnit.rbgToYUV();
//		
//		formatYUV_DCT.before();
//		double[] tabYF = formatYUV_DCT.format3DTo1DTest(tab3D, 0);
//		double[] tabUF = formatYUV_DCT.format3DTo1DTest(tab3D, 1);
//		double[] tabVF = formatYUV_DCT.format3DTo1DTest(tab3D, 2);
//		
//		int position = 0;
//		
//		ArrayList<Double> listY = new ArrayList<Double>();
//		ArrayList<Double> listU = new ArrayList<Double>();
//		ArrayList<Double> listV = new ArrayList<Double>();
//		
//		quantificationTestUnit.before();
//		zigZagTestUnit.before();
//		while(position <= tabYF.length - 64){
//			dctTestUnit.before();	
//			double[][] tabDCT = dctTestUnit.YUVToDCTTest(formatYUV_DCT.format8par8(tabYF, position));
//			position = position + 64;
//			double[][] tabQuant = quantificationTestUnit.quantifiation(tabDCT, quantificationTestUnit.Qy);
//			double[] tabZigZag = zigZagTestUnit.quantToZigZag(tabQuant);
//			listY = formatYUV_DCT.tab1DToList(listY, tabZigZag);
//		}
//		
//		position = 0;
//		
//		while(position <= tabUF.length - 64){
//			dctTestUnit.before();
//			double[][] tabDCT = dctTestUnit.YUVToDCTTest(formatYUV_DCT.format8par8(tabUF, position));	
//			position = position + 64;
//			double[][] tabQuant = quantificationTestUnit.quantifiation(tabDCT, quantificationTestUnit.Quv);
//			double[] tabZigZag = zigZagTestUnit.quantToZigZag(tabQuant);
//			listU = formatYUV_DCT.tab1DToList(listU, tabZigZag);
//		}
//		
//		position = 0;
//		
//		while(position <= tabVF.length - 64){
//			dctTestUnit.before();
//			double[][] tabDCT = dctTestUnit.YUVToDCTTest(formatYUV_DCT.format8par8(tabVF, position));			
//			position = position + 64;
//			double[][] tabQuant = quantificationTestUnit.quantifiation(tabDCT, quantificationTestUnit.Quv);
//			double[] tabZigZag = zigZagTestUnit.quantToZigZag(tabQuant);
//			listV = formatYUV_DCT.tab1DToList(listV, tabZigZag);
//		}
//		
//		List<double[][]> listDoubleY = formatYUV_DCT.listToList2D(listY, 0);
//		List<double[][]> listDoubleU = formatYUV_DCT.listToList2D(listU, 1);
//		List<double[][]> listDoubleV = formatYUV_DCT.listToList2D(listV, 2);
//			
//		System.out.println("test");
//		operationsACDC.ecrireAC_DC(listDoubleY, 0);
//		operationsACDC.ecrireAC_DC(listDoubleU, 1);
//		operationsACDC.ecrireAC_DC(listDoubleV, 2);
//		
//		SZLReaderWriter.writeSZLFile(COMPRESSED_FILE , tab3D[0].length, tab3D[0][0].length, 90);
//		
//		int[] array_Compressed = SZLReaderWriter.readSZLFile(COMPRESSED_FILE);	
//		int nbrBlock = (array_Compressed[0] / 8);
//		
//		List<double[][]> listeDynamique = new ArrayList<double[][]>();
//		listeDynamique = operationsACDC.lireAC_DC(tab3D[0].length/8, 0);
//		listeDynamique = operationsACDC.lireAC_DC(tab3D[0].length/8, 1);
//		listeDynamique = operationsACDC.lireAC_DC(tab3D[0].length/8, 2);
//		
//		listY = formatYUV_DCT.list2DTolist(listeDynamique, 0);
//		listU = formatYUV_DCT.list2DTolist(listeDynamique, 1);
//		listV = formatYUV_DCT.list2DTolist(listeDynamique, 2);
//		
//		double[][] tabY = new double[8][8];
//		double[][] tabU = new double[8][8];
//		double[][] tabV = new double[8][8];
//		
//		double[] tabY1D = new double[tabYF.length];
//		double[] tabU1D = new double[tabYF.length];
//		double[] tabV1D = new double[tabYF.length];
//		
//		position = 0;
//		
////		while (position < listY.size()) {
////			tabY = zigZagTestUnit.zigZagToQuant(formatYUV_DCT.listToTab1D(listY, position));
////			tabY = quantificationTestUnit.dequantifiation(tabY, quantificationTestUnit.Qy);
////			double[][] tempo = dctTestUnit.DCTToYUVTest(tabY);
////			tabY1D = formatYUV_DCT.tab2DToTab1D(tabY1D, tempo, position);
////			position = position + 64;
////		}
////		
////		position = 0;
////		
////		while (position < listU.size()) {
////			tabU = zigZagTestUnit.zigZagToQuant(formatYUV_DCT.listToTab1D(listU, position));
////			tabU = quantificationTestUnit.dequantifiation(tabU, quantificationTestUnit.Quv);
////			double[][] tempo = dctTestUnit.DCTToYUVTest(tabU);
////			tabU1D = formatYUV_DCT.tab2DToTab1D(tabU1D, tempo, position);
////			position = position + 64;
////		}
////		
////		position = 0;
////		
////		while (position < listV.size()) {
////			tabV = zigZagTestUnit.zigZagToQuant(formatYUV_DCT.listToTab1D(listV, position));
////			tabV = quantificationTestUnit.dequantifiation(tabV, quantificationTestUnit.Quv);
////			double[][] tempo = dctTestUnit.DCTToYUVTest(tabV);
////			tabV1D = formatYUV_DCT.tab2DToTab1D(tabV1D, tempo, position);
////			position = position + 64;
////		}
//		
//		double[][] finalTabY2D = new double[tab3D[0].length][tab3D[0][0].length];
//		double[][] finalTabU2D = new double[tab3D[0].length][tab3D[0][0].length];
//		double[][] finalTabV2D = new double[tab3D[0].length][tab3D[0][0].length];
//		
//		finalTabY2D = formatYUV_DCT.tab1DToFinalTab(finalTabY2D, tabY1D);
//		finalTabU2D = formatYUV_DCT.tab1DToFinalTab(finalTabU2D, tabU1D);
//		finalTabV2D = formatYUV_DCT.tab1DToFinalTab(finalTabV2D, tabV1D);
//		
//		double[][][] tab3DD = new double[tab3D.length][tab3D[0].length][tab3D[0][0].length];
//		
//		for (int i = 0; i < finalTabY2D.length; i++) {
//			for (int j = 0; j < finalTabY2D[0].length; j++) {
//				tab3DD[0][i][j] = finalTabY2D[i][j];
//				tab3DD[1][i][j] = finalTabU2D[i][j];
//				tab3DD[2][i][j] = finalTabV2D[i][j];
//			}
//		}
//		
//		convertRGBtoYUVTestUnit.yuvToRBG4(tab3DD);
//	}
}
