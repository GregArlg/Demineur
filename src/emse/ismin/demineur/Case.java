package emse.ismin.demineur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Case extends JPanel implements MouseListener{
	
	private String txt = "";
	private String minesAutour;
	private Color couleurCase = Color.darkGray;
	private boolean mineIci;
	private boolean clicked = false;
	private boolean rClick = false;
	private final static int DIM = 25 ;
	private Demineur demin;
	private boolean isClicked;
	
	
	
	Toolkit toolkit = getToolkit();
	
	/**
	 * Constructeur de la classe
	 * @param demin démineur actif
	 * @param x absc de la case
	 * @param y ord de la case
	 */
	public Case (Demineur demin, int x, int y) {
		this.demin = demin;
		this.isClicked = clicked;
		setPreferredSize(new Dimension(DIM, DIM)); // taille de la case
		addMouseListener(this); // ajout listener souris
		
		minesAutour = String.valueOf(demin.getChamp().cptMinesAutour(x, y));
		
		mineIci = demin.getChamp().isMine(x, y);
	}
	
	
	/** le dessin de la case */
	@Override
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc); // appel méthode mère (efface le dessin précedent)
		
		gc.setColor(couleurCase);
		gc.fillRect(2, 2, getWidth(), getHeight());
		
		gc.setColor(Color.white);
		gc.setFont(new Font("Baskerville Old Face", 0, 25));
		gc.drawString(txt, this.getWidth()/2, this.getHeight()/2+7); // dessin du texte à la position 30, 30
		
		//Affichage de l'image en cas de clic gauche sur une mine
		if(rClick) {
			BufferedImage image;
			try {
				image = ImageIO.read(new File("img/batman.png"));//Choix de l'image
				gc.drawImage(image, 2, 2, getWidth()-2, getHeight()-2, this);//Ajoute et paramètre la taille de l'image
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(clicked) {
			BufferedImage image;
			try {
				image = ImageIO.read(new File("img/joker.png"));//Choix de l'image
				gc.drawImage(image, 10, 4, getWidth()-15, getHeight()-5, this);//Ajoute et paramètre la taille de l'image
				
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Réinitialise une case
	 */
	public void newPartie() {
		txt = "";
		couleurCase = Color.darkGray;
		clicked = false;
		rClick = false;
		repaint();
	}
	
	
	/** la gestion de la souris */
	@Override
	public void mousePressed (MouseEvent e) {
		
		boolean rightClick = SwingUtilities.isRightMouseButton (e);
		
		if(!demin.isLost() && !isClicked) {
			
			isClicked = true;
			
			//Permet de gérer le clic droit
			if(rightClick) {
				rClick = true;
			}
			//Affichage du nombre de mines autour de la case
			else if(!mineIci) {
				txt = minesAutour; // chgt du texte à redessiner
				couleurCase = Color.lightGray;
				
				demin.setCptCasesVides(demin.getCptCasesVides() + 1);
				
				
				if(demin.getCptCasesVides() == demin.getChamp().getNbCasesVides()) {
					demin.getIhmDemin().getCpt().stopCpt();
					repaint();
					JOptionPane.showMessageDialog(null, "Gagné ! Tu as sauvé Gotham !\n"
							+ "Score : " + demin.getIhmDemin().getCpt().getCpt());
				}
			}
			//Affichage de l'image
			else if(mineIci) {
				clicked = true;
				repaint();
				demin.getIhmDemin().getCpt().stopCpt();
				JOptionPane.showMessageDialog(null, "Perdu ! Le Joker a gagné !");
				demin.setLost(true);
			}
			
			repaint() ; // comme on veut redessiner, on force l’appel de paintComponent()
		}
	}
	

	


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!demin.isStarted()) {
			demin.getIhmDemin().getCpt().startCpt();
			demin.setStarted(true);
			demin.setLost(false);
		}
		repaint();
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
