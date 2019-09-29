package emse.ismin.demineur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * Classe Interface homme machine
 * @author Greg
 *
 */
public class IHM extends JPanel implements ActionListener {

	private JButton butConnexion = new JButton("Connexion");
	
	private JMenuItem mQuitter = new JMenuItem("Quitter", KeyEvent.VK_Q);
	private JMenuItem mRestart = new JMenuItem("Recommencer cette partie", KeyEvent.VK_N);
	private JMenuItem mRegles = new JMenuItem("Règles du jeu", KeyEvent.VK_A);
	
	private JRadioButtonMenuItem easyR = new JRadioButtonMenuItem("Easy");
    private JRadioButtonMenuItem mediumR = new JRadioButtonMenuItem("Medium");
    private JRadioButtonMenuItem hardR = new JRadioButtonMenuItem("Hard");
    
    private JPanel gridPanel;
    
	private Demineur demin;
	
	private Case [][] tabCases;
	
	private Compteur cpt;
	
	public Compteur getCpt() {
		return cpt;
	}
	
	private JTextField nomServeur = new JTextField(Demineur.HOSTNAME, 20);
	private JTextField numPort = new JTextField(String.valueOf(Demineur.PORT), 7);
	private JTextField pseudo = new JTextField("NomJoueur", 10);
	
	private JTextArea msgAreas = new JTextArea(5, 5);
	
	public IHM(Demineur demin){
		
		this.demin = demin;
		
		//Modifie la couleur du background
		setBackground(Color.darkGray);
		
		
		//Crée un borderlayout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.darkGray);
		
		//Crée un borderlayout dans le panel north pour mettre titre et compteur
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new BorderLayout());
		panelNorth.setBackground(Color.darkGray);
		
		//titre
		JLabel title = new JLabel("<html> Le Joker vient de placer des mines dans Gotham <br> Trouve-les toutes avant que la ville n'explose </html>");
		title.setForeground(Color.white);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Baskerville Old Face", 0, 30));
		panelNorth.add(title, BorderLayout.NORTH);
		
		
		//Compteur
		cpt = new Compteur();
		setLayout(new GridBagLayout());
		
		panelNorth.add(cpt, BorderLayout.SOUTH);
		
		
		mainPanel.add(panelNorth, BorderLayout.NORTH);
		
		
		//Grille centrale pour le démineur
		gridPanel = new JPanel();
		gridPanel.setBackground(Color.darkGray);
		fillGridPanel(gridPanel);
		
		mainPanel.add(gridPanel, BorderLayout.CENTER);
		
		
		
		
		
		
		//Création panel pour le sud
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout(new BorderLayout());
		panelSouth.setBackground(Color.darkGray);
		
		//Création panel pour le nord du sud
		JPanel panelSouthNorth = new JPanel();
		panelSouthNorth.setBackground(Color.darkGray);
		
		//Rentrer nom du joueur
		panelSouthNorth.add(nomServeur);
		
		//rentrer numéro de port
		panelSouthNorth.add(numPort);
		
		//rentrer pseudo
		panelSouthNorth.add(pseudo);
		
		//Bouton connexion
		butConnexion.setFont(new Font("Baskerville Old Face", 0, 15));
		butConnexion.setForeground(Color.darkGray);
		butConnexion.setBackground(Color.white);
		
		butConnexion.addActionListener(this);
		panelSouthNorth.add(butConnexion);
		
		panelSouth.add(panelSouthNorth, BorderLayout.NORTH);
		
		//Zone de texte
		msgAreas.setEditable(false);
		panelSouth.add(msgAreas, BorderLayout.SOUTH);
		
		
		mainPanel.add(panelSouth, BorderLayout.SOUTH);
		
		
		
		
		
		
		//Création d'un menu
		JMenuBar menuBar = new JMenuBar();
		
		//Onglet Partie
		JMenu menuPartie = new JMenu("Partie");
		menuPartie.setToolTipText("Menu Partie");
		menuBar.add(menuPartie);
		
		//Choix nouvelle partie en fonction de la difficulté
		JMenu mNewP = new JMenu("Nouvelle Partie");
		mNewP.setToolTipText("Commencer une nouvelle partie");
		menuPartie.add(mNewP);
		
		//Sous-choix difficulté
		ButtonGroup bg = new ButtonGroup(); //Crée un crew de boutons
		easyR.setToolTipText("Nouvelle partie facile");
        bg.add(easyR);
        mNewP.add(easyR);
        mediumR.setToolTipText("Nouvelle partie moyenne");
        bg.add(mediumR);
        mNewP.add(mediumR);
        hardR.setToolTipText("Nouvelle partie difficile");
        bg.add(hardR);
        mNewP.add(hardR);
        easyR.addActionListener(this);
        mediumR.addActionListener(this);
        hardR.addActionListener(this);
		
		//Choix recommencer partie
		mRestart.setToolTipText("Recommencer la partie actuelle");
		mRestart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuPartie.add(mRestart);
		mRestart.addActionListener(this);
	
		//Choix Quitter
		mQuitter.setToolTipText("Quitter partie");
		mQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		menuPartie.add(mQuitter);
		mQuitter.addActionListener(this);
		
		
		//Glue bar pour mettre menu aide à droite
		menuBar.add(Box.createGlue());
		
		//Onglet Aide
		JMenu menuAide = new JMenu("Aide");
		menuAide.setToolTipText("Affiche l'aide");
		menuBar.add(menuAide);
		//Choix Règles
		menuAide.add(mRegles);
		mRegles.addActionListener(this);
		
		
		demin.setJMenuBar(menuBar);
		
		add(mainPanel);
	}
	
	
	/**
	 * Initialise le panneau central aux dimensions correspondant à la difficulté
	 * @param gridPanel
	 */
	private void fillGridPanel(JPanel gridPanel) {
		int dimX = demin.getChamp().getDimX();
		int dimY = demin.getChamp().getDimY();
		gridPanel.setLayout(new GridLayout(dimX, dimY));
		tabCases = new Case[dimX][dimY];
		
		for(int i=0 ; i < dimX ; i++) {
			for(int j=0 ; j < dimY ; j++) {
				
				tabCases[i][j] = new Case(demin, i, j);
				
				gridPanel.add(tabCases[i][j]);
				
				demin.pack();
			}
		}
	}
	

	/**
	 * Crée une nouvelle partie et réinitialise toutes les cases
	 */
	private void newPartie() {
		gridPanel.removeAll();
		fillGridPanel(gridPanel);
		demin.pack();
		cpt.stopCpt();
		demin.setStarted(false);
		demin.setLost(false);
		cpt.setCpt(0);
		demin.setCptCasesVides(0);
		for(int i=0 ; i < demin.getChamp().getDimX() ; i++) {
			for(int j=0 ; j < demin.getChamp().getDimY() ; j++) {
				tabCases[i][j].newPartie();
			}
		}
	}
	
	
	/**
	 * Lance une nouvelle partie avec la difficulté level
	 * @param level nouvelle difficulté
	 */
	private void newPartie(Level level) {
		demin.getChamp().newPartie(level);
		gridPanel.removeAll();
		fillGridPanel(gridPanel);
		demin.pack();
		cpt.stopCpt();
		demin.setStarted(false);
		demin.setLost(false);
		cpt.setCpt(0);
		demin.setCptCasesVides(0);
	}
	
	
	public void addMsg(String str) {
		msgAreas.append(str);
	}
	
	/**
	 * Actions au clic de la souris pour le menu et le bouton quit
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int rep;
		
		if(e.getSource() == mQuitter) {
			rep = JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir quitter ?", "Dis non stp", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(rep == JOptionPane.YES_OPTION)
				System.exit(0);
		}
		else if(e.getSource() == mRestart) {
			rep = JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir recommencer ?", "C'est pas grave t'en fais pas", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(rep == JOptionPane.YES_OPTION) {
				demin.getChamp().placeMines();
				newPartie();
			}
		}
		else if(e.getSource() == mRegles) {
			JOptionPane.showMessageDialog(null, "Va voir sur Google");
		}
		
		else if(e.getSource() == easyR) {
			newPartie(Level.EASY);
		}
		else if(e.getSource() == mediumR) {
			newPartie(Level.MEDIUM);
		}
		else if(e.getSource() == hardR) {
			newPartie(Level.HARD);
		}
		else if(e.getSource() == butConnexion) {//Si on appuie sur le bouton connexion
			demin.connect2Server(nomServeur.getText(), Integer.parseInt(numPort.getText()), pseudo.getText());
		}
	}
}
