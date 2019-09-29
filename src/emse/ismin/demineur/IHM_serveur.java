package emse.ismin.demineur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class IHM_serveur extends JPanel implements ActionListener {

	private JButton butStart = new JButton("Start Partie");
	private Serveur serv;
	private JTextArea msgAreas = new JTextArea(20, 20);
	
	private Demineur demin;
	
	
	
	public IHM_serveur(Serveur serv) {
		this.serv = serv;
		
		setBackground(Color.darkGray);
		
		
		//Crée un borderlayout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.darkGray);
		
		//Ajout d'un titre
		JLabel titre = new JLabel("Rassemble la Justice League !");
		titre.setFont(new Font("Baskerville Old Face", 0, 30));
		titre.setForeground(Color.white);
		titre.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(titre, BorderLayout.NORTH);
		
		//Ajout bouton start pour le serveur
		mainPanel.add(butStart, BorderLayout.SOUTH);
		
		//Zone de texte
		msgAreas.setEditable(false);
		mainPanel.add(msgAreas, BorderLayout.CENTER);
		
		
		add(mainPanel);
	}
	
	public void addMsg(String str) {
		msgAreas.append(str);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//Début de la partie
		if(e.getSource() == butStart) {
			serv.envoiMsgTous("Début de la partie");
		}
	}
}
