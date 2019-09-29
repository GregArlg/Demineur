/**
 * 
 */
package emse.ismin.demineur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * @author Greg
 *
 */
public class Compteur extends JPanel implements Runnable {

	private float cpt = 0;
	
	public float getCpt() {
		return cpt;
	}


	public void setCpt(int cpt) {
		this.cpt = cpt;
		repaint();
	}

	private Thread th;
	
	private final static int DIMX = 70 ;
	private final static int DIMY = 50 ;
	
	/**
	 * Constructeur de la classe Compteur
	 * Init la taille et la couleur du compteur
	 */
	public Compteur() {
		setPreferredSize(new Dimension(DIMX, DIMY)); // taille de la case
		setBackground(Color.darkGray);
		
	}
	
	
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc); // appel méthode mère (efface le dessin précedent)
		
		gc.setColor(Color.black);
		gc.fillRect(getWidth()/2-DIMX/2, 0, DIMX, DIMY);
		
		gc.setColor(Color.white);
		gc.setFont(new Font("Baskerville Old Face", 0, 25));
		gc.drawString(String.valueOf(cpt), getWidth()/2-DIMX/2+20, 30); // dessin du texte à la position 25, 30
		
	}
	
	/**
	 * Démarrage du compteur
	 */
	public void startCpt() {
		cpt = 0;
		th = new Thread(this);
		th.start();
	}
	
	/**
	 * stop du compteur
	 */
	public void stopCpt() {
		th = null;
	}
	
	@Override
	public void run() {
		while(th != null) {
			try {
				th.sleep(500);
				cpt += 0.5;
				repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
