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
 * Affiche une fen�tre qui permet au User de choisir le facteur 
 * de qualit� de l'image � compresser.
 * 
 *******************************************************************/
public class FenetreFacteurQualite implements ActionListener{

	/***************************
	 * Constantes
	 ***************************/
	private static final String 
	MSG_FENETRE = "Fen�tre Facteur de Qualit�",
	MSG_LABEL = "Facteur de Qualit� chosi :   ",
	MSG_SELECTION = "\n\n\n\n\nVeuillez s�lectionner un Facteur de Qualit� entre 1 et 100 . ";

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
	 * Affiche une fen�tre qui permet la s�lection du facteur 
	 * de Qualit� avec un JSlider. 
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
	
		// Si le User confirme le facteur de qualit�
		if(optionPane.getValue() == (Object) JOptionPane.OK_OPTION){
			setFacteurQualite(slider.getValue());
		}
	}

	/******************************************************
	 * GET SLIDER
	 * 
	 * @Resumer:
	 * C'est ici qu'on ajoute les options visuels au
	 * JSlider et qu'on verifie son �tat. 
	 * 
	 * Le listener est aussi utilis� pour mettre � jour
	 * le JLabel qui affiche le facteur de qualit� choisi
	 * en temps r�el.
	 * 
	 * @Param: JOptionPane optionPane
	 * @Return: JSlider slider
	 * @Complexity: O(1)
	 * 
	 ******************************************************/
	private JSlider getSlider(JOptionPane optionPane) {

		//Slider avec minimum 0 et maximum de 100. Plus loin, si la valeur est zero, on fait + 1.
		JSlider sliderAction = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);

		//Ajout des d�tails visuels du JSlider
		sliderAction.setPreferredSize(new Dimension(450,100));
		sliderAction.setMajorTickSpacing(25);
		sliderAction.setMinorTickSpacing(5);
		sliderAction.setPaintTicks(true);
		sliderAction.setPaintLabels(true);

		//�coute les changements du JSlider apport� par l'utilisateur
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {

				//Permet la mise � jour de la valeur s�lectionn�e avec le JSlider
				JSlider theSlider = (JSlider) changeEvent.getSource();
				int sliderValor;

				// Si on prend le facteur de qualit� minimum (0), on ajuste pour obtenir 1.
				if(sliderAction.getValue() == 0){
					sliderValor = sliderAction.getValue() + 1;
				}else{
					sliderValor = sliderAction.getValue();
				}
				
				//Permet la mise � jour en temps r�el du JLabel
				lblResult.setText(MSG_LABEL + String.valueOf(sliderValor));

				if (!theSlider.getValueIsAdjusting()) {
					optionPane.setInputValue(new Integer(theSlider.getValue()));
				}
			}
		};
		//Ajout de l'�couteur au JSLider
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
