package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import Application.SequenceCodage;
import Application.SequenceDecodage;


/*******************************************************************
 * @Titre_Classe: MENU FENETRE
 * 
 * @Resumer:
 * Creer le menu de l'application avec une liste déroulante.
 * On y retrouve l'option: Compresser, Décompresser et Quitter.
 * On démarre le Codage ou Décodage selon l'option Choisi.
 * 
 * @Source: 
 * Utilisation du squelette du Travail Pratique 01 (LOG121)
 * 
 * @Auteur: Alexandre Laroche
 * @Date: 19 juillet 2017 
 *******************************************************************/
public class MenuFenetre extends JMenuBar{

	/********************
	 * Serial UID 
	 ********************/
	private static final long serialVersionUID = 1L;

	/***************************
	 * Classes instanciees
	 ***************************/
	private JFileChooser 
	selectionPPM = new JFileChooser(),
	selectionSZL = new JFileChooser();

	private MenuFenetre menuFenetre;
	private JMenu menu;

	/********************
	 * Constantes
	 ********************/
	private static final String
	PPM = ".ppm",
	SZL = ".szl",

	MENU_FICHIER_TITRE = "Fichier",
	COMPRESSER_IMAGE = "Compresser Image",
	DECOMPRESSER_IMAGE = "Decompresser Image",
	MENU_FICHIER_QUITTER = "Quitter",

	MESSAGE_QUITTER_APPLICATION = "Voullez-vous vraiment quitter l'application ?",
	NOM_FICHIER_COMPRESSED = " Veuiller entrer le nom que portera le fichier Compressé :  ",
	NOM_FICHIER_DECOMPRESSED = " Veuiller entrer le nom que portera le fichier Décompressé :  ",

	TITRE_ERREUR = "Fenetre erreur",
	TITRE_QUITTER = "Fenetre quitter",
	IMPOSSIBLE_NOM = "Le nom entré est invalide",

	ERREUR_PPM_INPUT = "\n***** ERREUR *****\n\nFichier .ppm selectionné est INVALIDE ou NULL ou MANQUANT.\nVeuillez vous assurer que le fichier d'entrée est de format .ppm .\n",
	ERREUR_SZL_ENREGISTRER = "\n***** ERREUR *****\n\nFichier de sauvegarde INVALIDE ou NULL.\nVeuillez vous assurer que le fichier de sauvegarde est de format .szl .\n",
	ERREUR_SZL_INPUT = "\n***** ERREUR *****\n\nFichier .szl selectionné est INVALIDE ou NULL ou MANQUANT.\nVeuillez vous assurer que le fichier d'entrée est de format .szl .\n",
	ERREUR_PPM_ENREGISTRER = "\n***** ERREUR *****\n\nFichier de sauvegarde INVALIDE ou NULL.\nVeuillez vous assurer que le fichier de sauvegarde est de format .ppm .\n",
	ERREUR_FACTEUR_QUALITE = "\n***** ERREUR *****\n\nLe Facteur de Qualité du fichier .ppm est INVALIDE.\nVeuillez vous assurer que le Facteur de Qualité soit entre 1 et 100 .\n";

	/********************
	 * CONSTRUCTEUR
	 ********************/
	public MenuFenetre() {

		menuFenetre = (MenuFenetre) MenuFenetre.this.getParent();
		menu = creerMenu(MENU_FICHIER_TITRE, new String[] {COMPRESSER_IMAGE, DECOMPRESSER_IMAGE, MENU_FICHIER_QUITTER});

		optionCompresser();
		optionDecompresser();
		optionQuitter();

		add(menu);
	}

	/*******************************************************************
	 * @Titre: ONGLET COMPRESSER
	 * 
	 * @Resumer:
	 * Démarre la fenêtre de sélection de fichier .ppm.
	 * Si le User cancel, il retourne au menu principal
	 * 
	 * @Source:
	 * getSelectedFile():	https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html	
	 * File chooser:		https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html
	 * 
	 *******************************************************************/
	protected void optionCompresser() {

		//Onglet "Importer" muni d'un raccourcis clavier
		menu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		menu.getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//Reponse de la fenetre d'ouverture de fichier
				int reponse = getSelectionFicher().showOpenDialog(menuFenetre);
				if(reponse == JFileChooser.APPROVE_OPTION) {
					try {
						demarrerCompression();
					} 
					catch (Exception exception) {
						System.out.println("\nRetour au Menu Principal");
					}
				} 
				else { System.out.println("\nRetour au Menu Principal");
				}
			}
		});
	}

	/*******************************************************************
	 * @Titre: ONGLET DECOMPRESSER
	 * 
	 * @Resumer:
	 * Démarre la fenêtre de sélection de fichier .szl.
	 * Si le User cancel, il retourne au menu principal
	 * 
	 * @Source:
	 * getSelectedFile():	https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html	
	 * File chooser:		https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html
	 * 
	 *******************************************************************/
	protected void optionDecompresser() {

		//Onglet "Importer" muni d'un raccourcis clavier
		menu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		menu.getItem(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//Reponse de la fenetre d'ouverture de fichier
				int reponse = getFichierCompresser().showOpenDialog(menuFenetre);
				if(reponse == JFileChooser.APPROVE_OPTION) {
					try {
						demarrerDecompression();	
					} 
					catch (Exception exception) {
						System.out.println("\nRetour au Menu Principal");
					}
				} 
				else { System.out.println("\nRetour au Menu Principal");
				}
			}
		});
	}

	/*******************************************************************
	 * @Titre: ONGLET QUITTER
	 * 
	 * @Resumer:
	 * Fermeture de l'application si l'option quitter est choisie.
	 * 
	 * @Source:
	 * getSelectedFile():	https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html	
	 * File chooser:		https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html
	 * 
	 *******************************************************************/
	protected void optionQuitter() {

		//Onglet "Quitter" muni d'un raccourcis clavier
		menu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menu.getItem(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//Message de confirmation avant de quitter completement l'application
				int reponse = JOptionPane.showConfirmDialog(null, MESSAGE_QUITTER_APPLICATION, TITRE_QUITTER, JOptionPane.YES_NO_OPTION);
				if(reponse == JOptionPane.YES_OPTION) {
					System.exit(0);
				}else{
					return;
				}	
			}
		});
	}

	/*******************************************************************
	 * @Titre: DEMARRER COMPRESSION
	 * 
	 * @Resumer:
	 * Recupere le répertoire du fichier .ppm a compresser.
	 * Récupere le nouveau nom donné au dichier .szl.
	 * Vérification du nouveau nom et des extensions.
	 * Démarrage de la compression image.
	 * 
	 *******************************************************************/
	private void demarrerCompression() throws Exception{

		// Recupere le repertoire du fichier .ppm 
		String repertoireFichierPPM = selectionPPM.getSelectedFile().getAbsolutePath();
		String nomFichier = selectionPPM.getSelectedFile().getName();
		String nomSimple = new String(nomFichier.replace(PPM, ""));

		// Demande d'inscrire un nouveau nom
		String nouveauNomFichier = JOptionPane.showInputDialog(NOM_FICHIER_COMPRESSED, "Compressed_" + nomSimple);
		String repertoireFichierSZL = new String(repertoireFichierPPM.replace(nomFichier, nouveauNomFichier + SZL));

		// Verifie si un nouveau nom a bien été entré
		if(!nouveauNomFichier.isEmpty()){ // O(1)

			// Selection du facteur de qualite
			FenetreFacteurQualite fenetreQualite = new FenetreFacteurQualite();
			double facteurQualite = fenetreQualite.getFacteurQualite();

			// Vérifie la validité des extensions et démarre la compression
			if(repertoireFichierPPM != null && repertoireFichierPPM.endsWith(PPM)){ // O(1)
				if(repertoireFichierSZL != null && repertoireFichierSZL.endsWith(SZL)){ // O(1)
					if(0 < facteurQualite && facteurQualite <= 100){ // O(1)

						//Demarrage de la compression du fichier .ppm vers un format .szl 
						new SequenceCodage(repertoireFichierPPM, repertoireFichierSZL, facteurQualite);

					} else { throw new IllegalArgumentException(ERREUR_FACTEUR_QUALITE); }
				} else { throw new IllegalArgumentException(ERREUR_SZL_ENREGISTRER); }
			} else { throw new IllegalArgumentException(ERREUR_PPM_INPUT); }
		}
		else {
			// Avise le User que le nom entré est invalide
			JOptionPane.showMessageDialog(menuFenetre, IMPOSSIBLE_NOM, TITRE_ERREUR, JOptionPane.ERROR_MESSAGE);
			demarrerCompression();
		}
	}	

	/*******************************************************************
	 * @Titre: DEMARRER DÉCOMPRESSION
	 * 
	 * @Resumer:
	 * Recupere le répertoire du fichier .szl a décompresser.
	 * Récupere le nouveau nom donné au dichier .ppm.
	 * Vérification du nouveau nom et des extensions.
	 * Démarrage de la décompression image.
	 * 
	 *******************************************************************/
	private void demarrerDecompression() throws Exception{

		// Recupere le repertoire du fichier .szl
		String repertoireFichierSZL = selectionSZL.getSelectedFile().getAbsolutePath();
		String nomFichier = selectionSZL.getSelectedFile().getName();
		String nomSimple = new String(nomFichier.replace(SZL, ""));

		// Demande d'inscrire un nouveau nom
		String nouveauNomFichier = JOptionPane.showInputDialog(NOM_FICHIER_DECOMPRESSED, "Decompressed_" + nomSimple);
		String repertoireFichierPPM = new String(repertoireFichierSZL.replace(nomFichier, nouveauNomFichier + PPM));

		// Vérifie la validité des extensions et du nouveau nom et démarre la décompression
		if(nouveauNomFichier != null && !nouveauNomFichier.isEmpty()){ // O(1)
			if(repertoireFichierSZL != null && repertoireFichierSZL.endsWith(SZL)){ // O(1)
				if(repertoireFichierPPM != null && repertoireFichierPPM.endsWith(PPM)){ // O(1)

					//Demarrage de la decompression du fichier .szl vers un format .ppm
					new SequenceDecodage(repertoireFichierSZL, repertoireFichierPPM);

				} else { throw new IllegalArgumentException(ERREUR_PPM_ENREGISTRER); }
			} else { throw new IllegalArgumentException(ERREUR_SZL_INPUT); }
		} 
		else {
			// Avise le User que le nom entré est invalide
			JOptionPane.showMessageDialog(menuFenetre, IMPOSSIBLE_NOM, TITRE_ERREUR, JOptionPane.ERROR_MESSAGE);
			demarrerDecompression();
		}
	}


	/*******************************************************************
	 * @Titre: CREATION MENU 
	 * 
	 * @Resumer:
	 * Permet l'ajout de l'onglet "Fichier" au menu, suivi 
	 * des sous-options.
	 * 
	 * @param titleKey champs principal
	 * @param itemKeys elements
	 * @return menu
	 *
	 *******************************************************************/
	private static JMenu creerMenu(String titleKey, String[] itemKeys) {

		JMenu menu = new JMenu(titleKey);
		for(int i = 0; i < itemKeys.length; ++i) {
			menu.add(new JMenuItem(itemKeys[i]));
		}
		return menu;
	}

	/*************************************************************************
	 * @Titre: GET FICHIER IMAGE (.PPM)
	 * 
	 * @Resumer: 
	 * On controle le type de format que l'utilisateur peut importer ou
	 * sauvegarder. Le format supporte est celui d'une image.
	 * 
	 * @Source:
	 * setFileFilter(): https://stackoverflow.com/questions/18575655/how-to-restrict-file-choosers-in-java-to-specific-files
	 * 
	 *************************************************************************/
	public JFileChooser getSelectionFicher(){
		selectionPPM.setAcceptAllFileFilterUsed(false);
		selectionPPM.setFileFilter(new FileNameExtensionFilter("Fichiers .ppm", "ppm"));
		return selectionPPM;
	}

	/*************************************************************************
	 * @Titre: GET FICHIER COMPRESSER (.SZL)
	 * 
	 * @Resumer: 
	 * On controle le type de format que l'utilisateur peut importer ou
	 * sauvegarder. Le format supporte est celui d'un fichier .bin .
	 * 
	 * @Source:
	 * setFileFilter(): https://stackoverflow.com/questions/18575655/how-to-restrict-file-choosers-in-java-to-specific-files
	 * 
	 *************************************************************************/
	public JFileChooser getFichierCompresser(){
		selectionSZL.setAcceptAllFileFilterUsed(false);
		selectionSZL.setFileFilter(new FileNameExtensionFilter("Fichiers .szl", "szl"));
		return selectionSZL;
	}
}
