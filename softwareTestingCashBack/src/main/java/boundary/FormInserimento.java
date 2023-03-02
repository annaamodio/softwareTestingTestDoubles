package boundary;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import exceptions.*;
import javax.swing.ImageIcon;

public class FormInserimento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField_IDProgramma;
	private JTextField textField_Cittadino;
	private JPasswordField textField_Password;

	/**
	 * Create the dialog.
	 */
	public FormInserimento() {
		this.setVisible(true);
		this.setTitle("Cashback");
		this.setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(30, 144, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lbl1 = new JLabel("Inserire i dati: ");
			lbl1.setForeground(new Color(0, 0, 0));
			lbl1.setFont(new Font("Arial Hebrew Scholar", Font.BOLD, 20));
			lbl1.setBounds(30, 6, 165, 33);
			contentPanel.add(lbl1);
		}
		
		JLabel lblProgramma = new JLabel("ID Programma");
		lblProgramma.setFont(new Font("Arial Hebrew Scholar", Font.PLAIN, 15));
		lblProgramma.setBackground(new Color(30, 144, 255));
		lblProgramma.setBounds(30, 51, 103, 48);
		contentPanel.add(lblProgramma);
		
		textField_IDProgramma = new JTextField();
		textField_IDProgramma.setBackground(new Color(135, 206, 250));
		textField_IDProgramma.setBounds(165, 60, 160, 26);
		contentPanel.add(textField_IDProgramma);
		textField_IDProgramma.setColumns(10);
		
		JLabel lblCodiceFiscale = new JLabel("ID Cittadino");
		lblCodiceFiscale.setFont(new Font("Arial Hebrew Scholar", Font.PLAIN, 15));
		lblCodiceFiscale.setBackground(new Color(30, 144, 255));
		lblCodiceFiscale.setBounds(30, 92, 103, 48);
		contentPanel.add(lblCodiceFiscale);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Arial Hebrew Scholar", Font.PLAIN, 15));
		lblPassword.setBackground(new Color(30, 144, 255));
		lblPassword.setBounds(30, 129, 103, 48);
		contentPanel.add(lblPassword);
		
		textField_Cittadino = new JTextField();
		textField_Cittadino.setColumns(10);
		textField_Cittadino.setBackground(new Color(135, 206, 250));
		textField_Cittadino.setBounds(165, 98, 160, 26);
		contentPanel.add(textField_Cittadino);
		
		textField_Password = new JPasswordField();
		textField_Password.setColumns(10);
		textField_Password.setBackground(new Color(135, 206, 250));
		textField_Password.setBounds(165, 138, 160, 26);
		textField_Password.setEchoChar('*');
		contentPanel.add(textField_Password);
		
		JButton btnNewButton = new JButton("richiedi rimborso");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(!textField_IDProgramma.getText().isEmpty() 
						&& !textField_Cittadino.getText().isEmpty() && !textField_Password.getText().isEmpty()) {
				BCittadino cittadino = new BCittadino();
				float rimborsoRicevuto=0;
				try {
					rimborsoRicevuto = cittadino.richiediRimborso(
							Integer.parseInt(textField_IDProgramma.getText()), textField_Cittadino.getText(),textField_Password.getText());
						//stampa messaggio di conferma al cittadino
					String conferma = "Alla registrazione con ID:"+textField_Cittadino.getText()+
							" è stato associato un rimborso di "+rimborsoRicevuto+" euro.";
					
						JOptionPane.showConfirmDialog(contentPanel, conferma, "Conferma", JOptionPane.OK_CANCEL_OPTION);
						
					}catch(ProgrammaNonTrovato | MinAcquistiNonRaggiunto | PasswordErrata | 
							IscrizioneNonTrovata| ProgrammaNonTerminato | IllegalArgumentException  e1){
						JOptionPane.showMessageDialog(contentPanel, e1.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
					}
				
				}
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	
		
		btnNewButton.setForeground(new Color(30, 144, 255));
		btnNewButton.setFont(new Font("Arial Hebrew Scholar", Font.PLAIN, 14));
		btnNewButton.setBackground(new Color(30, 144, 255));
		btnNewButton.setBounds(282, 202, 149, 33);
		contentPanel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(FormInserimento.class.getResource("/icons/cashback_piccolo.png")));
		lblNewLabel.setBounds(367, 6, 77, 76);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(30, 144, 255));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			
		}
	}
}
