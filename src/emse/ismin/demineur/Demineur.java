/**
 * 
 */
package emse.ismin.demineur;


import java.io.*;
import java.net.*;

import javax.swing.JFrame;

/**
 * @author Greg
 *
 */
public class Demineur extends JFrame {
	
	public static final int PORT = 10000;
	public static final String HOSTNAME = "localhost";
	
	public static final int MSG = 0;
	public static final int POS = 1;
	public static final int START = 2;
	public static final int END = 3;
	
	private Thread process;


	private Champ champ = new Champ();
	
	
	private int cptCasesVides = 0;

	public int getCptCasesVides() {
		return cptCasesVides;
	}

	public void setCptCasesVides(int cptCasesVides) {
		this.cptCasesVides = cptCasesVides;
	}
	
	
	
	private boolean lost;
	
	public boolean isLost() {
		return lost;
	}

	public void setLost(boolean lost) {
		this.lost = lost;
	}

	
	private IHM ihmDemin;
	
	public IHM getIhmDemin() {
		return ihmDemin;
	}

	
	private boolean started = false;
	private DataOutputStream out;
	private DataInputStream in;
	
	public boolean isStarted() {return started;}
	
	public void setStarted(boolean started) {
		this.started = started;
	}
	
	
	/**
	 * Construction
	 */
	public Demineur() {
		super("BatDémineur");
		ihmDemin= new IHM(this) ;
		setContentPane(ihmDemin) ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack() ;
		setVisible(true) ;
		
		champ.affText();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Bienvenue");
		new Demineur();

	}
	
	//permet d'accéder aux paramètres du champ
	public Champ getChamp() {
		return champ;
	}

	
	
	/**
	 * Se connecte au serveur avec les données de connexion
	 * @param serveur
	 * @param port
	 * @param pseudo
	 */
	public void connect2Server(String serveur, int port, String pseudo) {
		// TODO Auto-generated method stub
		ihmDemin.addMsg("Try to connect to :"+serveur+" : "+port);

        try {
            Socket sock = new Socket(serveur,port);
            
            
            out = new DataOutputStream(sock.getOutputStream());
            in = new DataInputStream(sock.getInputStream());
            
            ihmDemin.addMsg("\nConnexion OK");
            
            process = new Thread();
            process.start();
            
            out.writeUTF(pseudo);
            
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
            ihmDemin.addMsg("\nConnexion impossible avec :"+serveur+":"+port);
        } catch (IOException e) {
            e.printStackTrace();
            ihmDemin.addMsg("\nConnexion impossible avec :"+serveur+":"+port);
        }
	}
	
	/**
	 * boucle d'attente des evenements du serveur
	 */
	public void run() {
		//boucle infinie
		if(process != null) {
			int cmd = 0;
			
			try {
				cmd = in.readInt();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//en fct de ce que je lis j'affiche les mines/numeros/fin partie etc
			if(cmd == MSG) {
				String msg = null;
				try {
					msg = in.readUTF();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ihmDemin.addMsg(msg);
			}
			else if(cmd == POS) {
				
			}
			else if(cmd == START) {
				
			}
			else if(cmd == END) {
				
			}
		}
		//lecture dans in
		//lecture de la commande
		//lecture du joueur qui a cliqué en x,y
		
	}

}
