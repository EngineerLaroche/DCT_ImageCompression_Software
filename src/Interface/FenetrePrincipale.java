package Interface;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


/*******************************************************
 * @Titre: FENETRE PRINCIPALE
 * 
 * @Resumer:
 * Affiche une fenetre avec le menu permettant 
 * la compression ou decompression selon 
 * l<option choisi par le User.
 *  
 ******************************************************/
public class FenetrePrincipale extends JFrame{


	/********************
	 * Serial UID 
	 ********************/
	private static final long serialVersionUID = 1L;

	/******************************************************
	 * CONSTRUCTEUR
	 * 
	 * @Resumer:
	 * Insertion du menu à un panneau. 
	 * Le panneau est ajouté au JFrame.
	 * 
	 ******************************************************/
	public FenetrePrincipale(){ 

		// Nouveau panneau qui contient le menu
		JPanel panneau = new JPanel(new FlowLayout(FlowLayout.LEADING));	
		MenuFenetre menuOptions = new MenuFenetre();
		menuOptions.setBackground(Color.LIGHT_GRAY);
		panneau.add(menuOptions);

		add(panneau);
		setSize(500,300);
		setVisible(true);
		setTitle("Fenetre Principale");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
