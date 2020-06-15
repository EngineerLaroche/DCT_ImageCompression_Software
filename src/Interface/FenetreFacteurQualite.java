package Interface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*******************************************************************
 * @Titre_Classe: FENETRE FACTEUR QUALITE
 * 
 * @Resumer:
 * Affiche une fenêtre qui permet au User de choisir le facteur 
 * de qualité de l'image à compresser.
 * 
 *******************************************************************/
public class FenetreFacteurQualite implements ActionListener{

	/***************************
	 * Constantes
	 ***************************/
	private static final String 
	MSG_FENETRE = "Fenêtre Facteur de Qualité",
	MSG_LABEL = "Facteur de Qualité chosi :   ",
	MSG_SELECTION = "\n\n\n\n\nVeuillez sélectionner un Facteur de Qualité entre 1 et 100 . ";

	/***************************
	 * Objet Swing
	 ***************************/
	private JLabel lblResult = null;

	/***************************
	 * Variable
	 ***************************/
	private double facteurQualite;


	/******************************************************
	 * CONSTRUCTEUR
	 * 
	 * @Resumer:
	 * Affiche une fenêtre qui permet la sélection du facteur 
	 * de Qualité avec un JSlider. 
	 * Si le User cancel, il retourne au menu principal.  
	 * 
	 ******************************************************/
	public FenetreFacteurQualite(){

		JFrame parent = new JFrame();		
		JOptionPane optionPane = new JOptionPane();	

		//Recupere le JSlider et ses actions
		JSlider slider = getSlider(optionPane);

		//Affiche le nom du fichier a traiter et le facteur de qualite choisi
		lblResult = new JLabel(MSG_LABEL + 50);

		optionPane.setMessage(new Object[] {MSG_SELECTION, "", lblResult, slider});	
		optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);	

		//Permet l'affichage de la fenetre principale 
		JDialog dialog = optionPane.createDialog(parent, MSG_FENETRE);
		dialog.setVisible(true);
	
		// Si le User confirme le facteur de qualité
		if(optionPane.getValue() == (Object) JOptionPane.OK_OPTION){
			setFacteurQualite(slider.getValue());
		}
	}

	/******************************************************
	 * GET SLIDER
	 * 
	 * @Resumer:
	 * C'est ici qu'on ajoute les options visuels au
	 * JSlider et qu'on verifie son état. 
	 * 
	 * Le listener est aussi utilisé pour mettre à jour
	 * le JLabel qui affiche le facteur de qualité choisi
	 * en temps réel.
	 * 
	 * @Param: JOptionPane optionPane
	 * @Return: JSlider slider
	 * @Complexity: O(1)
	 * 
	 ******************************************************/
	private JSlider getSlider(JOptionPane optionPane) {

		//Slider avec minimum 0 et maximum de 100. Plus loin, si la valeur est zero, on fait + 1.
		JSlider sliderAction = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);

		//Ajout des détails visuels du JSlider
		sliderAction.setPreferredSize(new Dimension(450,100));
		sliderAction.setMajorTickSpacing(25);
		sliderAction.setMinorTickSpacing(5);
		sliderAction.setPaintTicks(true);
		sliderAction.setPaintLabels(true);

		//Écoute les changements du JSlider apporté par l'utilisateur
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {

				//Permet la mise à jour de la valeur sélectionnée avec le JSlider
				JSlider theSlider = (JSlider) changeEvent.getSource();
				int sliderValor;

				// Si on prend le facteur de qualité minimum (0), on ajuste pour obtenir 1.
				if(sliderAction.getValue() == 0){
					sliderValor = sliderAction.getValue() + 1;
				}else{
					sliderValor = sliderAction.getValue();
				}
				
				//Permet la mise à jour en temps réel du JLabel
				lblResult.setText(MSG_LABEL + String.valueOf(sliderValor));

				if (!theSlider.getValueIsAdjusting()) {
					optionPane.setInputValue(new Integer(theSlider.getValue()));
				}
			}
		};
		//Ajout de l'écouteur au JSLider
		sliderAction.addChangeListener(changeListener);
		return sliderAction;
	}

	/******************************************************
	 * GET FACTEUR DE QUALITE
	 * 
	 * @Return: double facteurQualite
	 * 
	 ******************************************************/
	public double getFacteurQualite(){
		return facteurQualite;
	}

	/******************************************************
	 * SET FACTEUR DE QUALITE
	 * 
	 * @Param: double facteurQualite
	 * 
	 ******************************************************/
	public void setFacteurQualite(double _facteurQualite){
		this.facteurQualite = _facteurQualite;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
