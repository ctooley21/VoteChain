package com.ultimate.votechain.data;
import javax.swing.*;
import java.awt.*;

public class GUI_Login{
	private JFrame login;
	Dimension screenSize;
	
	private int width;
	private int height;
	
	private JPanel loginPanel;
	private JPanel controlPanel;
	
	private JTextField username;
	private JTextField password;
	private JTextField user;
	private JTextField pass;
	
	public GUI_Login(){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.width;
		height = screenSize.height;
		guiSetup();
		showLogin();
	}
	
	private void guiSetup() {
		login = new JFrame("Login Screen");
		login.setSize(width,height);
	    //login.setLayout(new GridLayout(3, 1));
	    
	    //user = new JTextField("user", 64);
	    //pass = new JTextField("pass", 64);
	    
	    controlPanel = new JPanel();
	    controlPanel.setLayout(new SpringLayout());
	    
	    //login.add(user);
	    login.add(controlPanel);
	   // login.add(pass);
	    login.setVisible(true);  
	}
	
	private void showLogin() {
	     SpringLayout layout = new SpringLayout();
	     JPanel loginPanel = new JPanel();
	    // loginPanel.setLayout(layout);
	     
	     username = new JTextField("username", 64);
	     password = new JTextField("password", 64);
	     username.setSize(width, height);
	     controlPanel.add(username);
	     controlPanel.add(password);
	     
	     layout.putConstraint(SpringLayout.WEST, username,((width/2)), SpringLayout.WEST, controlPanel);
	     layout.putConstraint(SpringLayout.NORTH, username,(height/2), SpringLayout.NORTH, controlPanel);
	     layout.putConstraint(SpringLayout.NORTH, password,5, SpringLayout.SOUTH, username);
	     layout.putConstraint(SpringLayout.WEST, password,((width/2)), SpringLayout.WEST, controlPanel);
	      
	     layout.putConstraint(SpringLayout.EAST, loginPanel, 5, SpringLayout.EAST, password);
	     layout.putConstraint(SpringLayout.SOUTH, loginPanel,5, SpringLayout.SOUTH, password);
	     //controlPanel.add(loginPanel);
	     login.setVisible(true);  
	     
	}
	
}
