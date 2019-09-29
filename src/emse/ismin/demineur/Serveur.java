package emse.ismin.demineur;

import java.net.*;
import java.util.HashSet;
import java.util.Set;
import java.io.*;

import javax.swing.JFrame;

public class Serveur extends JFrame implements Runnable {

	private ServerSocket serverSocket;
	
	private Set<DataInputStream> collInput = new HashSet<>();
	private Set<DataOutputStream> collOutput = new HashSet<>();
			
	private IHM_serveur ihmS;
	/**
	 * 
	 */
	public Serveur(){
		super("BatServeur");
		
		System.out.println("Démarrage du serveur");
		
		ihmS = new IHM_serveur(this);
		
		setContentPane(ihmS) ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack() ;
		setVisible(true) ;
		
		//démarrage serveur
		startServeur();
	}
	
	
	/**
	 * Main du serveur
	 * @param args
	 */
	public static void main(String[] args) {
		new Serveur();
	}
	
	void startServeur() {
		ihmS.addMsg("Attente des clients");
		
		
		try {// gestionnaire de socket, port 10000
			serverSocket = new ServerSocket(Demineur.PORT);
			
			run();
			
			new Thread(this).start();
			
			/*
			// envoi d ’une donnée : 0 par exemple
			sortie.writeInt(0);
			
			
			serverSocket.close() ;
			*/
		} 
			
		catch (IOException e) {
			e.printStackTrace( );}
	}
	
	public void run() {
		try {
		Socket socket = serverSocket.accept() ; //attente
		
		// ouverture des streams
		DataInputStream entree = new DataInputStream(socket.getInputStream());
		DataOutputStream sortie = new DataOutputStream(socket.getOutputStream());
		
		//Ajout des entrées et sorties dans une collection
		collInput.add(entree);
		collOutput.add(sortie);
		
		// lecture d’une donnée string
		String nomJoueur = entree.readUTF() ;
		
		ihmS.addMsg("\n"+nomJoueur+" connected");
		
		
		new Thread(this).start();
		}
		
		catch (IOException e) {
			e.printStackTrace( );}
	}
	
	/**
	 * Envoi le message passé en paramètre à tous les joueurs connectés
	 * @param msg
	 */
	public void envoiMsgTous(String msg) {
		for(DataOutputStream out: collOutput) {
			try {
				out.write(Demineur.MSG);
				out.writeUTF(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
