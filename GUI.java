package vectorQuantization;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField widthtext;
	private JTextField heighttext;
	private JTextField vectornumbertext;

	/**
	 * Launch the application.
	 * 
	 * 
	 */
	 int vectorwidth;
	 int vectorheight;
	 int vectornumber;
	 String inputpath;
	 String outputpath;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnUploadImg = new JButton("upload img");
		btnUploadImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfilechooser = new JFileChooser("C:\\Users\\amr\\eclipse-workspace\\vectorQuantization");
				jfilechooser.setDialogTitle("Select file");
				int result = jfilechooser.showOpenDialog(null);
				if(result == jfilechooser.APPROVE_OPTION) {
			     File file = jfilechooser.getSelectedFile();
			     inputpath = file.getAbsolutePath();
				}
				
			}	
		});
		btnUploadImg.setBounds(12, 13, 97, 25);
		contentPane.add(btnUploadImg);
		
		JButton btnCompressimg = new JButton("compressimg ");
		btnCompressimg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfilechooser = new JFileChooser("C:\\Users\\amr\\eclipse-workspace\\vectorQuantization");
				jfilechooser.setDialogTitle("Save file");
				int result = jfilechooser.showSaveDialog(null);
				if(result == jfilechooser.APPROVE_OPTION) {
					File file = jfilechooser.getSelectedFile();
					outputpath = file.getAbsolutePath();
				}
				vectorwidth = Integer.parseInt(widthtext.getText());
				vectorheight = Integer.parseInt(heighttext.getText());
				vectornumber = Integer.parseInt(vectornumbertext.getText());
				Encode e = new Encode(inputpath,vectorwidth,vectorheight,vectornumber);
				e.quantize(outputpath);				
			}
		});
		btnCompressimg.setBounds(253, 215, 138, 25);
		contentPane.add(btnCompressimg);
		
		JLabel lblNewLabel = new JLabel("vector size");
		lblNewLabel.setBounds(39, 67, 83, 16);
		contentPane.add(lblNewLabel);
		
		widthtext = new JTextField();
		widthtext.setBounds(132, 64, 48, 22);
		contentPane.add(widthtext);
		widthtext.setColumns(10);
		
		heighttext = new JTextField();
		heighttext.setColumns(10);
		heighttext.setBounds(192, 64, 48, 22);
		contentPane.add(heighttext);
		
		vectornumbertext = new JTextField();
		vectornumbertext.setBounds(132, 99, 97, 22);
		contentPane.add(vectornumbertext);
		vectornumbertext.setColumns(10);
		
		JLabel lblVectorNumber = new JLabel("vector number");
		lblVectorNumber.setBounds(39, 102, 97, 16);
		contentPane.add(lblVectorNumber);
	}
}
