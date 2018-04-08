package com.ultimate.votechain.data;
import javax.swing.*;
import java.awt.*;

public class GUI_Login{
	private JFrame login;
	private GraphicsDevice gd;
	private int width;
	private int height;
	
	private JPanel loginPanel;
	private JPanel controlPanel;
	
	private JTextField username;
	private JTextField password;
	private JTextField user;
	private JTextField pass;
	
	public GUI_Login(){
		guiSetup();
		showLogin();
	}
	
	private void guiSetup() {
		login = new JFrame("Login Screen");
		login.setSize(400,400);
	    login.setLayout(new GridLayout(3, 1));
	    
	    user = new JTextField("user", 64);
	    pass = new JTextField("pass", 64);
	    
	    controlPanel = new JPanel();
	    controlPanel.setLayout(new FlowLayout());
	    
	    login.add(user);
	    login.add(controlPanel);
	    login.add(pass);
	    login.setVisible(true);  
	}
	
	private void showLogin() {
	     SpringLayout layout = new SpringLayout();
	     JPanel loginPanel = new JPanel();
	     loginPanel.setLayout(layout);
	     
	     username = new JTextField("username", 64);
	     password = new JTextField("password", 64);
	     //username.setSize(width, height);
	     loginPanel.add(username);
	     loginPanel.add(password);
	     
	     layout.putConstraint(SpringLayout.WEST, username,5, SpringLayout.WEST, controlPanel);
	     layout.putConstraint(SpringLayout.NORTH, username,5, SpringLayout.NORTH, controlPanel);
	     layout.putConstraint(SpringLayout.WEST, password,5, SpringLayout.EAST, username);
	     layout.putConstraint(SpringLayout.NORTH, password,5, SpringLayout.NORTH, controlPanel);
	      
	     layout.putConstraint(SpringLayout.EAST, loginPanel,5, SpringLayout.EAST, password);
	     layout.putConstraint(SpringLayout.SOUTH, loginPanel,5, SpringLayout.SOUTH, password);
	     controlPanel.add(loginPanel);
	     login.setVisible(true);  
	     
	}
	
}
