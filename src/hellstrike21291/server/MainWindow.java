package hellstrike21291.server;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow {
	
	private JFrame frame;
	private JLabel portLabel;
	private JTextField portField;
	private JTextArea logArea;
	private JButton exitButton;
	private JButton startButton;
	private JButton stopButton;
	private JButton saveButton;
	private JLabel kickLabel;
	private JTextField kickField;
	private JButton kickButton;
	
	private boolean isStart;
	private int port;
	
	private Date currentDate;
	private SimpleDateFormat formatForDate;
	private Font logFont;
	
	public static void main(String[] args) {
		new MainWindow();
	}
	
	public MainWindow() {
		frame = new JFrame("������. ���-818 ������� ������� ������� ��������");
		portLabel = new JLabel("����:");
		portField = new JTextField();
		logArea = new JTextArea();
		exitButton = new JButton("�����");
		startButton = new JButton("���������");
		stopButton = new JButton("����������");
		saveButton = new JButton("��������� ���");
		kickLabel = new JLabel("��������� �� ID:");
		kickField = new JTextField();
		kickButton = new JButton("���������");
		
		isStart = false;
		port = 0;
		
		formatForDate = new SimpleDateFormat("[HH:mm:ss] ");
		logFont = new Font("Monospaced", 0, 16);
		
		startButton.setBounds(600, 70, 170, 20);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isStart == true) {
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "������ ��� �������\n");
				}
				else {
					/*
					 * ����������� ������ �������
					 */
					try {
						port = Integer.parseInt(portField.getText());
						if(port > 65535 || port < 0)
							throw new NumberFormatException();
					}
					catch(NumberFormatException ex) {
						currentDate = new Date();
						logArea.append(formatForDate.format(currentDate) + "������� ������ ����\n");
						port = 0;
						return;
					}
					
					isStart = true;
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "������ �������\n");
				}
			}
		});
		frame.add(startButton);
		
		stopButton.setBounds(600, 90, 170, 20);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isStart == false) {
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "������ ��� ����������\n");
				}
				else {
					/*
					 * ����������� ��������� �������
					 */
					isStart = false;
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "������ ����������\n");
				}
			}
		});
		frame.add(stopButton);
		
		saveButton.setBounds(600, 110, 170, 20);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try(FileOutputStream logFile = new FileOutputStream("./server.log")) {
					logFile.write(logArea.getText().getBytes());
				} catch (FileNotFoundException e) {
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "������ ��� �������� ���� �������\n");
				} catch (IOException e) {
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "������ ��� ������ ���� �������\n");
				}
			}
		});
		frame.add(saveButton);
		
		kickButton.setBounds(600, 190, 170, 20);
		kickButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isStart == false) {
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "������ ������� ������: ������ ��������\n");
					return;
				}
				int kickID = 0;
				try {
					kickID = Integer.parseInt(kickField.getText());
					if(kickID < 0)
						throw new NumberFormatException();
				}
				catch(NumberFormatException ex) {
					currentDate = new Date();
					logArea.append(formatForDate.format(currentDate) + "������� ������ ID �������\n");
					return;
				}
				
			}
		});
		frame.add(kickButton);	
		
		exitButton.setBounds(600, 520, 170, 20);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isStart) {
					stopButton.doClick();
					saveButton.doClick();
				}
				System.exit(0);
			}
		});
		frame.add(exitButton);
		
		
		portLabel.setBounds(600, 30, 200, 20);
		frame.add(portLabel);
		
		kickLabel.setBounds(600, 150, 170, 20);
		frame.add(kickLabel);
		
		portField.setBounds(600, 50, 170, 20);
		frame.add(portField);
		
		kickField.setBounds(600, 170, 170, 20);
		frame.add(kickField);
		
		logArea.setBounds(30, 30, 540, 510);
		logArea.setEditable(false);
		logArea.setFont(logFont);
		frame.add(logArea);
		
		frame.setBounds(550, 150, 800, 600);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
	}
}
