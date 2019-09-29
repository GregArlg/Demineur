/**
 * 
 */
package emse.ismin.demineur;

import java.util.*;

/**
 * @author Greg
 *
 */
public class Champ {
	
	/**
	 * Variables et constantes
	 */
	private static final int DIM_EASY = 3;
    private static final int DIM_MEDIUM = 20;
    private static final int DIM_HARD = 30;
    
    private static final int NBMINES_EASY = 5;
    private static final int NBMINES_MEDIUM = 12;
    private static final int NBMINES_HARD = 18;
    
    private Level level;
    
	private int dimx = 0;
	private int dimy = 0;
	private int nbMines = 0;
	
	private Random alea = new Random();
	
	/**
	 * Constructeurs
	 */
	
	public Champ(Level level) {
		this.level = level;
		
		newPartie(level);
		
		
	}
	
	/**
	 * Constructeur du champ pour une difficulté custom
	 * @param level
	 * @param dx
	 * @param dy
	 * @param nbM
	 */
	public Champ(Level level, int dx, int dy, int nbM) {
		this.level = level;
		if(level == Level.CUSTOM) {
			initChamp(dx, dy, nbM);
		}
	}
	
	public Champ() {
		this(Level.EASY);
	}
	
	private void initChamp(int dx, int dy, int nbM) {
		dimx = dx;
		dimy = dy;
		nbMines = nbM;
		placeMines();
    }
	
	
	public void newPartie(Level level) {
		switch(level) {
				
				case EASY:
					initChamp(DIM_EASY, DIM_EASY, NBMINES_EASY);
					break;
				case MEDIUM:
					initChamp(DIM_MEDIUM, DIM_MEDIUM, NBMINES_MEDIUM);
					break;
				case HARD:
					initChamp(DIM_HARD, DIM_HARD, NBMINES_HARD);
					break;
				default:
					break;
		}
	}
	
	
	//Init tableau en fonction du niveau choisi
	private boolean [][] tabMines;
	
	
	
	/**
	 * Méthodes
	 */
	
	//get dimension x
	public int getDimX() {
		return dimx;
	}
	
	//get dimension y
	public int getDimY() {
		return dimy;
	}
	
	//Placement des mines
	public void placeMines() {
		tabMines = new boolean [dimx][dimy];
		
		//Remplit le tableau de false
		for(int i=0 ; i < tabMines.length ; i++) {
			for(int j=0 ; j < tabMines[0].length ; j++) {
				tabMines[i][j] = false;
			}
		}
		
		
		//Pose aléatoirement nbMines mines
		for (int i=0 ; i < nbMines ;) {
			int rx = alea.nextInt(tabMines.length) ; // tirage au sort nb E [0,DIM-1]
			int ry = alea.nextInt(tabMines[0].length) ; // tirage au sort nb E [0,DIM-1]
			
			if(!tabMines[rx][ry]) {
				tabMines[rx][ry] = true;
				i++;
			}
		}
	}
	
	/**
	 * @param x abscisse
	 * @param y ordonnée
	 * @return le nb de mines autour d'une case de coord x,y
	 */
	public int cptMinesAutour(int x, int y) {
		int nbM = 0;
		int borneMinI = x == 0 ? 0 : x-1;
		int borneMinJ = y == 0 ? 0 : y-1;
		int borneMaxI = x == tabMines.length-1 ? tabMines.length : x+2;
		int borneMaxJ = y == tabMines[0].length-1 ? tabMines[0].length : y+2;
		
		for(int i = borneMinI ; i<borneMaxI ; i++) {
			for(int j = borneMinJ ; j<borneMaxJ ; j++) {
				if(!(i==x && j==y) && tabMines[i][j]) {
					nbM++;
				}
			}
		}
		
		return nbM;
	}
	
	//Affichage du champ de mines dans la console
	public void affText() {
		for(int i=0 ; i < tabMines.length ; i++) {
			for(int j=0 ; j < tabMines[0].length ; j++) {
				if(tabMines[i][j])
					System.out.print('X');
				else
					System.out.print(cptMinesAutour(i,j));
			}
			System.out.println();
		}
	}
	
	/**
	 * retourne s'il y a la présence d'une mine
	 * @param x abs
	 * @param y ord
	 * @return mine ou pas de mine
	 */
	public boolean isMine(int x, int y) {
		return tabMines[x][y];
	}
	
	/**
	 * Renvoie le nombre de mines
	 */
	public int getNbCasesVides() {
		return dimx*dimy-nbMines;
	}
	
	
}
