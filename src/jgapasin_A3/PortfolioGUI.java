package jgapasin_A3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.*;

/*
 * * Class Name: PortfolioGUI
 * 
 * Description: Manages the graphical user interface (GUI) for the portfolio application. This class 
 * provides an interface for users to interact with their investments, including options for registering users, 
 * creating posts, and searching for posts. It uses Java Swing components for the GUI layout and functionality.
 * 
 * Additional Information: This class includes three panels for different functions: user registration, 
 * creating posts, and searching posts. The panels are accessible through an 'Options' menu.
 * 
 * The main method is also contained in this class
 */
public abstract class PortfolioGUI extends JFrame implements ActionListener {
	
	static ArrayList <Investment> investments = new ArrayList<>();
	static HashMap<String, ArrayList <Integer>> wordIndex = new HashMap <String, ArrayList<Integer>>();
	
	//We'll use this when keeping track of our investments later
	private static int currentIndex = 0; // Initialize the current investment
	
	
	//Main method, will handle the functionality and creation of the GUI
	public static void main (String [] args) throws InvalidInputException {
				//Create main frame
				JFrame frame = new JFrame ("ePortfolio");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setSize(800, 600);
		        frame.setLayout(new BorderLayout());
		        
		        //Main Panel 
		        JPanel mainPanel = new JPanel(new BorderLayout());
		      
		        //Content Panel, will display either the register, create, or search panels
		        JPanel contentPanel = new JPanel(new BorderLayout());
		        
		        //Create default welcome panel
		        JPanel welcomePanel = new JPanel();
		        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS)); // Stack components vertically

			     // Welcome Label
			     JLabel welcomeLabel = new JLabel("Welcome to ePortfolio.");
			     welcomeLabel.setFont(new Font("Calibri", Font.PLAIN, 32));
			     welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Left-align the label
	
			     // Welcome Message Text
			     JTextPane welcomeMessage = new JTextPane();
			     welcomeMessage.setBackground(null); // Transparent background
			     welcomeMessage.setFont(new Font("Calibri", Font.PLAIN, 20));
			     welcomeMessage.setText("Choose a command from the “Commands” menu to buy or sell\n"
			             + "an investment, update prices for all investments, get gain for the\n"
			             + "portfolio, search for relevant investments, or quit the program.");
			     welcomeMessage.setEditable(false); // Prevent editing
			     welcomeMessage.setAlignmentX(Component.LEFT_ALIGNMENT); // Left-align the text pane
	
			     // Add Components to Welcome Panel
			     welcomePanel.add(welcomeLabel);
			     welcomePanel.add(Box.createVerticalStrut(50)); // Add spacing between main title and text
			     welcomePanel.add(welcomeMessage);
	
			     // Add Welcome Panel to Main Frame
			     contentPanel.add(welcomePanel, BorderLayout.NORTH); // Place it at the top
			     
		        		
			     
		        //BUY INVESTMENTS PANEL
			    JPanel mainBuyPanel = new JPanel(new BorderLayout());
			    
			    //The main buy panel will be seperated into 3 smaller panels using nested layouts. 
			    //It will use a border layout for each smaller panel
			    
			    //Create the form panel which will hold the fields for inputting information for the stock
			    JPanel formPanel = new JPanel(new GridLayout(6,2)); //Use a grid layout for the form, with 5 rows for each entry and 2 columns
			    // Labels and Fields for the form
			    
			    //Buying label
			    JLabel buyLabel = new JLabel("Buying an investment");
			    buyLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
			    buyLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Left-align the label
			    formPanel.add(buyLabel);
			    JLabel nullLabel = new JLabel();
			    formPanel.add(nullLabel);
			    
			    //Actual labels for the investment
		        JLabel typeLabel = new JLabel("Type");
		        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"stock", "mutual fund"});
		        typeCombo.setPreferredSize(new Dimension (200,50));
		        JLabel symbolLabel = new JLabel("Symbol");
		        JTextField symbolField = new JTextField();
		        symbolField.setPreferredSize(new Dimension(200,50));
		        JLabel nameLabel = new JLabel("Name");
		        JTextField nameField = new JTextField();
		        nameField.setPreferredSize(new Dimension(200,50));
		        JLabel quantityLabel = new JLabel("Quantity");
		        JTextField quantityField = new JTextField();
		        quantityField.setPreferredSize(new Dimension(200,50));
		        JLabel priceLabel = new JLabel("Price");
		        JTextField priceField = new JTextField();
		        priceField.setPreferredSize(new Dimension (200,50));

		        // Add the components to the form panel
		        formPanel.add(typeLabel);
		        formPanel.add(typeCombo);
		        formPanel.add(symbolLabel);
		        formPanel.add(symbolField);
		        formPanel.add(nameLabel);
		        formPanel.add(nameField);
		        formPanel.add(quantityLabel);
		        formPanel.add(quantityField);
		        formPanel.add(priceLabel);
		        formPanel.add(priceField);

		        // Create the button panel (buttons on the right)
		        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // 2 rows, 1 column
		        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20)); // Add padding around buttons
		        JButton resetButton = new JButton("Reset");
		        JButton buyButton = new JButton("Buy");

		        // Set preferred size to make buttons larger
		        resetButton.setPreferredSize(new Dimension(300, 10));
		        buyButton.setPreferredSize(new Dimension(300, 10));

		        // Add buttons to the panel
		        buttonPanel.add(resetButton);
		        buttonPanel.add(buyButton);

		        // Add the form and button panels to the main buy panel
		        mainBuyPanel.add(formPanel, BorderLayout.LINE_START); // Form on top
		        mainBuyPanel.add(buttonPanel, BorderLayout.LINE_END); // Buttons on bottom

		        // Create the message box (with scrollable text area)
		        JTextArea messageArea = new JTextArea(5, 20);
		        messageArea.setEditable(false);
		        messageArea.setBorder(BorderFactory.createTitledBorder("Messages"));
		        messageArea.setLineWrap(false); // Disable line wrapping for horizontal scrolling
		        messageArea.setWrapStyleWord(false);

		        // Wrap the text area in a scroll pane
		        JScrollPane scrollPane = new JScrollPane(messageArea);
		        scrollPane.setPreferredSize(new Dimension(800, 150)); // Set preferred size for the scroll pane
		        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		        // Add the scroll pane to the bottom of the main buy panel
		        mainBuyPanel.add(scrollPane, BorderLayout.PAGE_END);

		        // Add the buy panel to the content panel
		        contentPanel.add(mainBuyPanel, BorderLayout.CENTER);
		        
		        
		        
		        //SELL INVESTMENTS PANEL
		        //Create a main sell panel, similar to the buy panel
		        JPanel mainSellPanel = new JPanel(new BorderLayout()); //Main post panel
		        
		        //Create a form panel to go on the left hand side
		        JPanel sellFormPanel = new JPanel (new GridLayout(4,2));
		        JLabel sellLabel = new JLabel("Selling an investment");
			    sellLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
			    sellLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Left-align the label
			    sellFormPanel.add(sellLabel);
			    JLabel sellNull = new JLabel();
			    sellFormPanel.add(sellNull);
		        
		        //Labels and fields for the form
		        JLabel sellSymbolLabel = new JLabel ("Symbol");
		        JTextField sellSymbolField = new JTextField();
		        sellSymbolField.setPreferredSize(new Dimension(200,50));
		        JLabel sellQuantityLabel = new JLabel("Quantity");
		        JTextField sellQuantityField = new JTextField();
		        sellQuantityField.setPreferredSize(new Dimension(200,50));
		        JLabel sellPriceLabel = new JLabel("Price");
		        JTextField sellPriceField = new JTextField();
		        sellPriceField.setPreferredSize(new Dimension (200,50));
		        
		        // Add the components to the sell form panel
		        sellFormPanel.add(sellSymbolLabel);
		        sellFormPanel.add(sellSymbolField);
		        sellFormPanel.add(sellQuantityLabel);
		        sellFormPanel.add(sellQuantityField);
		        sellFormPanel.add(sellPriceLabel);
		        sellFormPanel.add(sellPriceField);
		        
		        
		        // Create the sell panel (buttons on the right)
		        JPanel sellButtonPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // 2 rows, 1 column
		        sellButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20)); // Add padding around buttons
		        JButton sellResetButton = new JButton("Reset");
		        JButton sellButton = new JButton("Sell");

		        // Set preferred size to make buttons larger
		        sellResetButton.setPreferredSize(new Dimension(300, 10));
		        sellButton.setPreferredSize(new Dimension(300, 10));

		        // Add buttons to the panel
		        sellButtonPanel.add(sellResetButton);
		        sellButtonPanel.add(sellButton);

		        // Add the form and button panels to the main buy panel
		        mainSellPanel.add(sellFormPanel, BorderLayout.LINE_START); // Form on top
		        mainSellPanel.add(sellButtonPanel, BorderLayout.LINE_END); // Buttons on bottom

		        
		        // Create the message box (with scrollable text area)
		        JTextArea sellMessageArea = new JTextArea(5, 20);
		        sellMessageArea.setEditable(false);
		        sellMessageArea.setBorder(BorderFactory.createTitledBorder("Messages"));
		        sellMessageArea.setLineWrap(false); // Disable line wrapping for horizontal scrolling
		        sellMessageArea.setWrapStyleWord(false);

		        // Wrap the text area in a scroll pane
		        JScrollPane sellScrollPane = new JScrollPane(sellMessageArea);
		        sellScrollPane.setPreferredSize(new Dimension(800, 150)); // Set preferred size for the scroll pane
		        sellScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		        sellScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		        // Add the scroll pane to the bottom of the main buy panel
		        mainSellPanel.add(sellScrollPane, BorderLayout.PAGE_END);

		        // Add the buy panel to the content panel
		        contentPanel.add(mainSellPanel, BorderLayout.CENTER);

		        
		        
		        
		        //UPDATE PRICE PANEL
		        //Create a main update panel, similar to the buy and sell panels
		        JPanel mainUpdatePanel = new JPanel(new BorderLayout()); //Main post panel
		        
		        //Create a form panel to go on the left hand side
		        JPanel updateFormPanel = new JPanel (new GridLayout(4,2));
		        JLabel updateLabel = new JLabel("Updating investments");
			    updateLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
			    updateLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Left-align the label
			    updateFormPanel.add(updateLabel);
			    JLabel updateNull = new JLabel();
			    updateFormPanel.add(updateNull);
		        
		        //Labels and fields for the form
		        JLabel updateSymbolLabel = new JLabel ("Symbol");
		        JTextField updateSymbolField = new JTextField();
		        updateSymbolField.setPreferredSize(new Dimension(200,50));
		        updateSymbolField.setEditable(false); //User shouldn't be able to change symbol
		        JLabel updateNameLabel = new JLabel("Name");
		        JTextField updateNameField = new JTextField();
		        updateNameField.setPreferredSize(new Dimension(200,50));
		        updateNameField.setEditable(false); //User shouldn't be able to change name
		        JLabel updatePriceLabel = new JLabel("Price");
		        JTextField updatePriceField = new JTextField();
		        updatePriceField.setPreferredSize(new Dimension (200,50));
		        
		        // Add the components to the sell form panel
		        updateFormPanel.add(updateSymbolLabel);
		        updateFormPanel.add(updateSymbolField);
		        updateFormPanel.add(updateNameLabel);
		        updateFormPanel.add(updateNameField);
		        updateFormPanel.add(updatePriceLabel);
		        updateFormPanel.add(updatePriceField);
		        
		        
		        // Create the update button panel (buttons on the right)
		        JPanel updateButtonPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // 3 rows, 1 column
		        sellButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20)); // Add padding around buttons
		        JButton prevButton = new JButton("Prev");
		        JButton nextButton = new JButton("Next");
		        JButton saveButton = new JButton("Save");

		        // Set preferred size to make buttons larger
		        prevButton.setPreferredSize(new Dimension(300, 10));
		        nextButton.setPreferredSize(new Dimension(300, 10));
		        saveButton.setPreferredSize(new Dimension(300, 10));

		        // Add buttons to the panel
		        updateButtonPanel.add(prevButton);
		        updateButtonPanel.add(nextButton);
		        updateButtonPanel.add(saveButton);
		        

		        // Add the form and button panels to the main buy panel
		        mainUpdatePanel.add(updateFormPanel, BorderLayout.LINE_START); // Form on top
		        mainUpdatePanel.add(updateButtonPanel, BorderLayout.LINE_END); // Buttons on bottom

		        
		        // Create the message box (with scrollable text area)
		        JTextArea updateMessageArea = new JTextArea(5, 20);
		        updateMessageArea.setEditable(false);
		        updateMessageArea.setBorder(BorderFactory.createTitledBorder("Messages"));
		        updateMessageArea.setLineWrap(false); // Disable line wrapping for horizontal scrolling
		        updateMessageArea.setWrapStyleWord(false);

		        // Wrap the text area in a scroll pane
		        JScrollPane updateScrollPane = new JScrollPane(updateMessageArea);
		        updateScrollPane.setPreferredSize(new Dimension(800, 150)); // Set preferred size for the scroll pane
		        updateScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		        updateScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		        // Add the scroll pane to the bottom of the main buy panel
		        mainUpdatePanel.add(updateScrollPane, BorderLayout.PAGE_END);

		        // Add the buy panel to the content panel
		        contentPanel.add(mainUpdatePanel, BorderLayout.CENTER);
		        
		        
		        
		        //GetGain Panel
		        //Create a main gain panel, similar to the buy and sell panels
		        JPanel mainGainPanel = new JPanel();
		        mainGainPanel.setLayout(new BoxLayout(mainGainPanel,  BoxLayout.Y_AXIS)); //Main post panel
		        
			    
			    //Create a form panel to go on the left hand side
		        JPanel gainPanel = new JPanel (new GridLayout(2,2));
		        JLabel gainLabel = new JLabel("Getting total gain");
			    gainLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
			    gainLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Left-align the label
			    gainPanel.add(gainLabel);
			    JLabel gainNull = new JLabel();
			    gainPanel.add(gainNull);
		        
		        //Labels and fields for the form
		        JLabel totalGainLabel = new JLabel ("Total gain");
		        totalGainLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		        JTextField totalGainField = new JTextField();
		        totalGainField.setPreferredSize(new Dimension(100,50));
		        totalGainField.setEditable(false);
		        totalGainField.setAlignmentX(Component.LEFT_ALIGNMENT);
		        
			    
		        gainPanel.add(totalGainLabel);
		        gainPanel.add(totalGainField);
		        mainGainPanel.add(gainPanel);
		        
		        // Create the message box (with scrollable text area)
		        JTextArea gainMessageArea = new JTextArea(5, 20);
		        gainMessageArea.setEditable(false);
		        gainMessageArea.setBorder(BorderFactory.createTitledBorder("Individual gains"));
		        gainMessageArea.setLineWrap(false); // Disable line wrapping for horizontal scrolling
		        gainMessageArea.setWrapStyleWord(false);

		        // Wrap the text area in a scroll pane
		        JScrollPane gainScrollPane = new JScrollPane(gainMessageArea);
		        gainScrollPane.setPreferredSize(new Dimension(800, 500)); // Set preferred size for the scroll pane
		        gainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		        gainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		        

		        // Add the scroll pane to the bottom of the main buy panel
		        mainGainPanel.add(gainScrollPane);

		        // Add the buy panel to the content panel
		        contentPanel.add(mainGainPanel, BorderLayout.CENTER);
		        
		        
		        
		        
		        //SEARCH FOR INVESTMENT PANEL
		        JPanel mainSearchPanel = new JPanel(new BorderLayout());
		        //Create the form panel which will hold the fields for inputting information for the stock
			    JPanel searchFormPanel = new JPanel(new GridLayout(5,2)); //Use a grid layout for the form, with 5 rows for each entry and 2 columns
			    // Labels and Fields for the form
			    
			    //Buying label
			    JLabel searchLabel = new JLabel("Searching investments");
			    searchLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
			    searchLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Left-align the label
			    searchFormPanel.add(searchLabel);
			    JLabel searchNull = new JLabel();
			    searchFormPanel.add(searchNull);
			    
			    //Actual labels for the investment
		        JLabel searchSymbolLabel = new JLabel("Symbol");
		        JTextField searchSymbolField = new JTextField();
		        searchSymbolField.setPreferredSize(new Dimension(200,50));
		        JLabel keywordsLabel = new JLabel("Name keywords");
		        JTextField keywordsField = new JTextField();
		        keywordsField.setPreferredSize(new Dimension(200,50));
		        JLabel minPriceLabel = new JLabel("Low price");
		        JTextField minPriceField = new JTextField();
		        minPriceField.setPreferredSize(new Dimension(200,50));
		        JLabel maxPriceLabel = new JLabel("High price");
		        JTextField maxPriceField = new JTextField();
		        maxPriceField.setPreferredSize(new Dimension (200,50));

		        // Add the components to the form panel
		        searchFormPanel.add(searchSymbolLabel);
		        searchFormPanel.add(searchSymbolField);
		        searchFormPanel.add(keywordsLabel);
		        searchFormPanel.add(keywordsField);
		        searchFormPanel.add(minPriceLabel);
		        searchFormPanel.add(minPriceField);
		        searchFormPanel.add(maxPriceLabel);
		        searchFormPanel.add(maxPriceField);

		        // Create the button panel (buttons on the right)
		        JPanel searchButtonPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // 2 rows, 1 column
		        searchButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20)); // Add padding around buttons
		        JButton searchResetButton = new JButton("Reset");
		        JButton searchButton = new JButton("Search");

		        // Set preferred size to make buttons larger
		        searchResetButton.setPreferredSize(new Dimension(300, 10));
		        searchButton.setPreferredSize(new Dimension(300, 10));

		        // Add buttons to the panel
		        searchButtonPanel.add(searchResetButton);
		        searchButtonPanel.add(searchButton);

		        // Add the form and button panels to the main buy panel
		        mainSearchPanel.add(searchFormPanel, BorderLayout.LINE_START); // Form on top
		        mainSearchPanel.add(searchButtonPanel, BorderLayout.LINE_END); // Buttons on bottom

		        // Create the message box (with scrollable text area)
		        JTextArea results = new JTextArea(5, 20);
		        results.setEditable(false);
		        results.setBorder(BorderFactory.createTitledBorder("Search results"));
		        results.setLineWrap(false); // Disable line wrapping for horizontal scrolling
		        messageArea.setWrapStyleWord(false);

		        // Wrap the text area in a scroll pane
		        JScrollPane searchScrollPane = new JScrollPane(results);
		        searchScrollPane.setPreferredSize(new Dimension(800, 150)); // Set preferred size for the scroll pane
		        searchScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		        searchScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		        // Add the scroll pane to the bottom of the main buy panel
		        mainSearchPanel.add(searchScrollPane, BorderLayout.PAGE_END);

		        // Add the buy panel to the content panel
		        contentPanel.add(mainSearchPanel, BorderLayout.CENTER);
		        
		        
		        
		        /*Button Actions for Each Panel*/
		        
		        //BUY PANEL BUTTONS
		        //Register Button Action
		        resetButton.addActionListener(new ActionListener() {
		            @Override
		         // Clear all relevant text fields and reset combo boxes
		            public void actionPerformed(ActionEvent e) {
			            symbolField.setText(""); 
			            nameField.setText(""); 
			            quantityField.setText(""); 
			            priceField.setText(""); 
			            
			            //Reset combo box to default value
			            typeCombo.setSelectedIndex(0);
	
			            //Clear messages in message areas
			            messageArea.setText("");
		        	}
		        
		        });
		        
		        //Buy Button Action
		        buyButton.addActionListener(new ActionListener() {
		        	@Override
		        	public void actionPerformed(ActionEvent e) {
		        		Investment existingInvestment = null;
		    			boolean dupeInvestment = false;
		    			String symbol = "";
		    			String userChoice = "";
		    			String name = "";
		    			int quantity = 0;
		    			double price = 0;
		    			messageArea.setText("");
		    			
		    			try {
			    			symbol = symbolField.getText().trim();
			    			userChoice = typeCombo.getSelectedItem().toString();
			    			name = nameField.getText();
			    			quantity = Integer.valueOf(quantityField.getText());
		    				price = Double.valueOf(priceField.getText());
		    			}
	    				catch (NumberFormatException ex) {
	    		            messageArea.setText("Invalid input: Please enter numbers for quantity and price.");
	    		            return;
	    		        }
		    			
		    			
		    				
	    				
		    			for (Investment investment : investments) {
		    				//Make sure that the symbol is not both a stock and a fund, the investment type must match what the user entered
		    				if (investment.getSymbol().toLowerCase().equalsIgnoreCase(symbol) && investment.getType().equalsIgnoreCase(userChoice)){ 
		    					existingInvestment = investment;
		    					break;
		    				}
		    				//The type of investment isnt the same as what the user entered, it's a duplicate, and isn't allowed to be inserted
		    				else if (investment.getSymbol().toLowerCase().equals(symbol.toLowerCase()) && !(investment.getType().equalsIgnoreCase(userChoice))){
		    					dupeInvestment = true;
		    					break;
		    				}
		    				
		    			}
		    			//If a duplicate investment was found, print a message and break out of the switch
		    			if (dupeInvestment == true) {
		    				messageArea.setText("An investment with this symbol already exists.");
		    				return;
		    			}
		    			if (existingInvestment != null) { //A stock was found, we only need price and quantity
		    				
		    				//Existing investment is a stock
		    				if (userChoice.toLowerCase().equals("stock")) {
		    					if (existingInvestment instanceof Stock) {
		    						Stock foundStock = (Stock) existingInvestment;
		    						foundStock.buy(quantity,price);
		    					}
		    				}
		    				//Existing investment is a fund
		    				else if (userChoice.toLowerCase().equals("fund")) {
		    					if (existingInvestment instanceof MutualFund) {
		    						MutualFund foundFund = (MutualFund) existingInvestment;
		    						foundFund.buy(quantity,price);
		    					}
		    				}
		    				//Print the details of the purchase
		    				messageArea.append("Company name: " + existingInvestment.getName() + "(" + existingInvestment.getSymbol() +")"+ "\n"); //Print company name and symbol
		    				messageArea.append("Value of stocks (bookValue): $" + String.format("%.2f", existingInvestment.getbookValue())+ "\n"); //Print bookValue
		    				messageArea.append("Price per share: $ " + String.format("%.2f", existingInvestment.getPrice())+ "\n"); //Print price per share
		    				messageArea.append("Number of shares owned: " + existingInvestment.getQuantity()+ "\n"); //Print quantity
		    				existingInvestment.updatePrice(price); //Update the price after buying
		    			
		    			}
		    			else { // It is a new investment, we also need to get input for the name
		    				/* Side Note, there's no need to update information if the stock is new, since it's done in the constructor */
		    				Stock newStock = null;
		    				MutualFund newFund = null;
		    				
		    				if (userChoice.toLowerCase().equals("stock")) {
								try {
									newStock = new Stock ("Stock",symbol,name,quantity,price);
								} catch (InvalidInputException e1) {
									// TODO Auto-generated catch block
									e1.getMessage();
									messageArea.setText("Exception occured: "+ e1);
									return;
								}
		    					//No need to buy the investment if it's new
		    					investments.add(newStock);
		    					//Print the details of the new stock
		    					messageArea.append("Company name: " + newStock.getName() + "(" + newStock.getSymbol() +")"+ "\n"); //Print company name and symbol
		    					messageArea.append("Value of stocks (bookValue): $" + String.format("%.2f", newStock.getbookValue())+ "\n"); //Print bookValue
		    					messageArea.append("Price per share: $ " + String.format("%.2f", newStock.getPrice())+ "\n"); //Print price per share
		    					messageArea.append("Number of shares owned: " + newStock.getQuantity()+ "\n"); //Print quantity
		    				}
		    				else if (userChoice.toLowerCase().equals("mutual fund")) {
		    					
								try {
									newFund = new MutualFund ("mutualfund",symbol,name,quantity,price);
								} catch (InvalidInputException e1) {
									// TODO Auto-generated catch block
									e1.getMessage();
									messageArea.setText("Exception occured: "+ e1);
									return;
								}
		    					investments.add(newFund);
		    					//Print the details of the new fund
		    					messageArea.append("Company name: " + newFund.getName() + "(" + newFund.getSymbol() +")"+ "\n"); //Print company name and symbol
		    					messageArea.append("Value of stocks (bookValue): $" + String.format("%.2f", newFund.getbookValue())+ "\n"); //Print bookValue
		    					messageArea.append("Price per share: $ " + String.format("%.2f", newFund.getPrice())+ "\n"); //Print price per share
		    					messageArea.append("Number of shares owned: " + newFund.getQuantity()+ "\n"); //Print quantity
		    				}
		    			
		    			}
		    			messageArea.append("\n"); //Add spacing
		                createHashEntry();
		    			}
		        	
		        });
		        
		        
		        //SELL PANEL BUTTONS
		        sellResetButton.addActionListener(new ActionListener() {
		        	 @Override
			         // Clear all relevant text fields and reset combo boxes
			            public void actionPerformed(ActionEvent e) {
				            sellSymbolField.setText(""); 
				            sellQuantityField.setText(""); 
				            sellPriceField.setText(""); 
				          
				            // Clear messages in message areas
				            sellMessageArea.setText("");
			        	}
		        }); 
		        
		        sellButton.addActionListener(new ActionListener() {
		        	@Override
		        	public void actionPerformed (ActionEvent e) {
		        		Investment existingInvestment = null;
		    			String symbol = "";
		    			int quantity = 0;
	    				double price = 0;
		    			double payment = 0;
		    			double gain = 0;
		    			sellMessageArea.setText("");
		    			
		    			try {
			    			symbol = sellSymbolField.getText().trim();
			    			quantity = Integer.valueOf(sellQuantityField.getText());
		    				price = Double.valueOf(sellPriceField.getText());
		    			}
	    				catch (NumberFormatException ex) {
	    		            sellMessageArea.setText("Invalid input: Please enter numbers for quantity and price.");
	    		            return;
	    		        }
		    			
		    			//Check if the symbol already exists
		    			for (Investment investment : investments) {
		    				if (investment.getSymbol().toLowerCase().equals(symbol.toLowerCase())) {
		    					existingInvestment = investment;
		    					break;
		    				}
		    			}
		    			
		    			System.out.println(existingInvestment);
		    			if (existingInvestment == null){ // The stock doesn't exist
		    				sellMessageArea.setText("Investment not found in your portfolio."+ "\n");
		    				return;
		    			
		    			}
		    			else if (existingInvestment != null) { //A stock was found, we only need price and quantity
	    					if (existingInvestment instanceof Stock) {
	    						Stock foundStock = (Stock) existingInvestment;
	    						
	    						// Calculate payment and gain BEFORE the state changes
	    					    payment = foundStock.getPayment(quantity, price);
	    					    gain = foundStock.getGain(symbol, quantity, price);
	    					    
	    						try {
									foundStock.sell(symbol, quantity, price);
								} 
	    						catch (InvalidInputException e1) {
									// TODO Auto-generated catch block
									e1.getMessage();
									sellMessageArea.setText("Exception occured: "+ e1);
									return;
								}
	    					}
    				
	    					if (existingInvestment instanceof MutualFund) {
	    						MutualFund foundFund = (MutualFund) existingInvestment;
	    						
	    						// Calculate payment and gain BEFORE the state changes
	    					    payment = foundFund.getPayment(quantity, price);
	    					    gain = foundFund.getGain(symbol, quantity, price);
	    					    
	    						try {
									foundFund.sell(symbol,quantity,price);
								} 
	    						catch (InvalidInputException e1) {
									// TODO Auto-generated catch block
									e1.getMessage();
									sellMessageArea.setText("Exception occured: "+ e1);
									return;
								}
	    					}
	    				}
		    				sellMessageArea.setText(""); //Clear the messages
		    				
		    				//Print the details of the sale
		    				sellMessageArea.append("Number of shares sold: " + quantity + "\n"); //Print quantity
		    				sellMessageArea.append("Company name: " + existingInvestment.getName() + " (" + existingInvestment.getSymbol() +")"+ "\n"); //Print company name and symbol
		    				sellMessageArea.append("Remaining bookValue: $" + String.format("%.2f", existingInvestment.getbookValue())+ "\n"); //Print bookValue
		    				sellMessageArea.append("Remaining shares: " + existingInvestment.getQuantity()+ "\n"); //Print price per share
		    				sellMessageArea.append("Payment on the sale: $" + String.format("%.2f", payment)+ "\n"); //Print price per share
		    				sellMessageArea.append("Gain on the sale: $" + String.format("%.2f", gain)+ "\n"); //Print price per share
		    				existingInvestment.updatePrice(price); //The price that we use for other calculations should be the new price the user entered
		    			
		    			
		    				//If the investment has no more quanity, we have to delete it and update the hashmap accordingly
		    				if (existingInvestment.getQuantity() == 0) {
		    					int investmentPosition = 0;
		    					//Iterate through investment list to find the current investment
		    					for (int currentIndex = 0; currentIndex < investments.size(); currentIndex++) { 
		    						Investment currentInvestment = investments.get(currentIndex); //Get the current investment
		    						if (currentInvestment.getSymbol().equalsIgnoreCase(existingInvestment.getSymbol())) { //If the symbols match, investment found
		    							investmentPosition = currentIndex; //The current index in our loop is the index of the investment
		    						}
		    					}
		    					//Delete and remove the investments
		    					deleteHashEntry(existingInvestment.getName(),investmentPosition); //Pass through the name of the investment and it's position as an argument
		    					investments.remove(existingInvestment);
		    					sellMessageArea.append("You sold all your shares of " + existingInvestment.getName()+ "\n");
	    					}
	    		
		    			sellMessageArea.append("\n"); //Just add some spacing
		        	}
		
		        });
		        
		        
		        //UPDATING PRICE BUTTONS
		        prevButton.addActionListener(new ActionListener(){
		        	@Override
		        	public void actionPerformed (ActionEvent e) {
		        		currentIndex--;
		        		nextButton.setEnabled(true); //Set next to true, there should always be a next if there's not more previous
		        		if (currentIndex < 0) {
		        			prevButton.setEnabled(false);
		        			updateMessageArea.append("There are no previous investments."+ "\n");
		        			currentIndex++;
		        			return;
		        		}
		        		else {
		        			Investment currentInvestment = investments.get(currentIndex);
		        			updateSymbolField.setText(currentInvestment.getSymbol());
		        			updateNameField.setText(currentInvestment.getName());
		        		}
		        	}
		     
		        });
		        nextButton.addActionListener(new ActionListener(){
		        	@Override
		        	public void actionPerformed (ActionEvent e) {
		        		currentIndex++;
		        		prevButton.setEnabled(true); //Set to true, there should always be a previous if next gets pushed
		        		if (currentIndex > investments.size()-1) {
		        			nextButton.setEnabled(false);
		        			updateMessageArea.append("There are no further investments."+ "\n");
		        			currentIndex--; //Decrement the current index again 
		        			return;
		        		}
		        		else {
		        			Investment currentInvestment = investments.get(currentIndex);
		        			updateSymbolField.setText(currentInvestment.getSymbol());
		        			updateNameField.setText(currentInvestment.getName());
		        		}
		        	}
		     
		        });
		        saveButton.addActionListener(new ActionListener(){
		        	@Override
		        	public void actionPerformed (ActionEvent e) {
	        			Investment currentInvestment = investments.get(currentIndex);
		        		double newPrice = Double.valueOf(updatePriceField.getText());
		        		currentInvestment.updatePrice(newPrice);
						updateMessageArea.append("New price for " + currentInvestment.getName() + " (" + currentInvestment.getSymbol() +"): " + String.format("%.2f", currentInvestment.getPrice())+ "\n");
		        	}
		     
		        });
		        
		        //SEARCH PANEL BUTTONS
		        searchResetButton.addActionListener(new ActionListener() {
		        	@Override
		        	public void actionPerformed (ActionEvent e) {
		        		searchSymbolField.setText(""); 
			            keywordsField.setText(""); 
			            minPriceField.setText(""); 
			            maxPriceField.setText(""); 
			          
			            // Clear messages in message areas
			            results.setText("");
		        	
		        	}
		        });
		        searchButton.addActionListener(new ActionListener() {
		        	@Override
		        	public void actionPerformed (ActionEvent e) {
		        		//Declare our variables that we will search for.
						String minPriceStr, maxPriceStr;
						String userKeywords;
						String userSymbol;
						boolean investmentFound = false;
						results.setText("");
						
						//Get values for all the fields
						userSymbol = searchSymbolField.getText();
						minPriceStr = minPriceField.getText();
						maxPriceStr = maxPriceField.getText();
						userKeywords = keywordsField.getText();
						String [] keywords = userKeywords.toLowerCase().split("\\W"); //Split when encountering any non word characters
						
						//Extreme case where all filters are empty, print all 
						if (minPriceStr.isEmpty() && maxPriceStr.isEmpty() && userKeywords.isEmpty() && userSymbol.isEmpty()) { 
							if(investments.isEmpty() == true) {
								results.append("There are currently no investments that match your criteria."+"\n");
							}
							else {
								for (Investment investment : investments) {
									results.append(investment.toString()+"\n");
								}
							}
						}
						
						else {
							for (Investment investment : investments) {
								boolean investmentKWFound = true;
								boolean matchPrice = priceCheck(investment.getPrice(), minPriceStr, maxPriceStr);
								/* Now we'll actually want to search for words */
						        for (String keyword : keywords) {
						        	//If the keyword isn't in the hashmap, or the current index doesn't exist in the keyword's list of indices, then it's not a match
						        	if (!(wordIndex.containsKey(keyword)) || !(wordIndex.get(keyword).contains(investments.indexOf(investment)))) {
						        		investmentKWFound = false;
						        		break; //We don't need to keep searching if our keyword doesn't exist
						        	}
						        }
						        // If keywords are found and the price matches, check for symbol match
						        if (investmentKWFound == true && matchPrice == true && (userSymbol.isEmpty() || userSymbol.toLowerCase().equalsIgnoreCase(investment.getSymbol()))) {
						   
						            results.append(investment.toString()+"\n");  // Print investment if it matches criteria
						            investmentFound = true;
						        }
						        if (userKeywords.isEmpty() && matchPrice == true && (userSymbol.isEmpty() || userSymbol.toLowerCase().equalsIgnoreCase(investment.getSymbol()))) {
						        	results.append(investment.toString()+"\n");  // Print investment if it matches criteria
						            investmentFound = true;
						        }
							}
							if (investmentFound == false) {
						        results.append("There are currently no investments that match your criteria."+"\n");
							}
						}
						
		        	
		        	}
		        });
		        
		        
		        //Create Menu Bar
		        JMenuBar menuBar = new JMenuBar();
		        JMenu optionsMenu = new JMenu("Commands");
		        JMenuItem buyMenuItem = new JMenuItem("Buy");
		        JMenuItem sellMenuItem = new JMenuItem("Sell");
		        JMenuItem updateMenuItem = new JMenuItem("Update Price");
		        JMenuItem getGainMenuItem = new JMenuItem("Get Gain");
		        JMenuItem searchMenuItem = new JMenuItem("Search for an Investment");
		        JMenuItem quitMenuItem = new JMenuItem("Quit");
		        
		        
		        // Add items to menu
		        optionsMenu.add(buyMenuItem);
		        optionsMenu.add(sellMenuItem);
		        optionsMenu.add(updateMenuItem);
		        optionsMenu.add(getGainMenuItem);
		        optionsMenu.add(searchMenuItem );
		        optionsMenu.add(quitMenuItem);
		        
		        menuBar.add(optionsMenu);
		        frame.setJMenuBar(menuBar);

		        // Menu Item Actions to Switch Panels
		        buyMenuItem.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                contentPanel.removeAll();
		                contentPanel.add(mainBuyPanel, BorderLayout.CENTER);
		                contentPanel.revalidate();
		                contentPanel.repaint();
		            }
		        });

		        sellMenuItem.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                contentPanel.removeAll();
		                contentPanel.add(mainSellPanel, BorderLayout.CENTER);
		                contentPanel.revalidate();
		                contentPanel.repaint();
		            }
		        });

		        updateMenuItem.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		            	if (investments.isEmpty()) { 
		            		updateMessageArea.setText("There are no prices to update");
		            		contentPanel.removeAll();
			                contentPanel.add(mainUpdatePanel, BorderLayout.CENTER);
			                contentPanel.revalidate();
			                contentPanel.repaint();
			                prevButton.setEnabled(false); //Disable all the buttons if there aren't any investments
			                nextButton.setEnabled(false);
			                saveButton.setEnabled(false);
		            	}
		            	else {
		            		prevButton.setEnabled(true);
			                nextButton.setEnabled(true);
			                saveButton.setEnabled(true);
			            	currentIndex = 0; //Set the current index to the first investment whenver we get to update Menu
			            	Investment currentInvestment = investments.get(currentIndex);
			            	updateSymbolField.setText(currentInvestment.getSymbol()); //Set the symbol of the investment
			            	updateNameField.setText(currentInvestment.getName()); //Set the name of the investment
			                contentPanel.removeAll();
			                contentPanel.add(mainUpdatePanel, BorderLayout.CENTER);
			                contentPanel.revalidate();
			                contentPanel.repaint();
		            	}
		            }
		        });
		        getGainMenuItem.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		            	gainMessageArea.setText(""); //Always have a blank space when entering the gain command
		            	double totalGain = 0;
						double currentGain = 0;
						for (Investment investment : investments) {
							if (investment instanceof Stock) {
								Stock thisStock = (Stock) investment;
								currentGain = thisStock.getGain(thisStock.getSymbol(), thisStock.getQuantity(), thisStock.getPrice());
								gainMessageArea.append("Gain for "+ investment.getName() + "(" + investment.getSymbol() + ": $"+String.format("%.2f", currentGain)+"\n");
							}
							
							else if (investment instanceof MutualFund) {
								MutualFund thisFund = (MutualFund) investment;
								currentGain = thisFund.getGain(thisFund.getSymbol(), thisFund.getQuantity(), thisFund.getPrice());
								gainMessageArea.append("Gain for "+ investment.getName() + "(" + investment.getSymbol()+ "): $"+String.format("%.2f", currentGain)+"\n");
							}
							
							totalGain += currentGain;
						}
						totalGainField.setText("$" + String.format("%.2f", totalGain));
		                contentPanel.removeAll();
		                contentPanel.add(mainGainPanel, BorderLayout.CENTER);
		                contentPanel.revalidate();
		                contentPanel.repaint();
		            }
		        });
		        searchMenuItem.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                contentPanel.removeAll();
		                contentPanel.add(mainSearchPanel, BorderLayout.CENTER);
		                contentPanel.revalidate();
		                contentPanel.repaint();
		            }
		        });
		        quitMenuItem.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                contentPanel.removeAll();
		                contentPanel.add(welcomePanel, BorderLayout.CENTER);
		                contentPanel.revalidate();
		                contentPanel.repaint();
		                System.exit(1);
		            }
		        });

		        // Add Components to Main Panel
		        mainPanel.add(contentPanel, BorderLayout.CENTER); // Dynamic content in the center

		        // Set initial content (default to Welcome Panel)
		        contentPanel.add(welcomePanel, BorderLayout.CENTER);

		        // Add Main Panel to Frame and Display
		        frame.add(mainPanel);
		        frame.setVisible(true);
		        
		        
		    }
	
	
	
	/* Helper Methods*/

	/**
	 * Method to create mappings for each investment within our hashmap
	 */
	public static void createHashEntry() {
		//Go through each investment, to create our hashmap index
		for (int currentIndex = 0; currentIndex < investments.size(); currentIndex++) { 
			/*First we'll want to put map words in our index*/
			Investment currentInvestment = investments.get(currentIndex); //Get the current investment
			// Split the name into keywords and process each one
	        for (String keyName : currentInvestment.getName().toLowerCase().split("\\W")) {
	        	//Keyword doesn't exist yet, create a mapping for it with a new arraylist as it's value
	             if (!(wordIndex.containsKey(keyName))) {
	            	 wordIndex.put(keyName, new ArrayList<Integer>());
	             }
	             //Add the current index to that already existing arraylist
	             for (int i = 0; i <= wordIndex.get(keyName).size(); i++){ //Iterate through each keyword's array list
	            	 if (wordIndex.get(keyName).contains(currentIndex)) { //If the index already exists in the arraylist, we don't want to add it
	            		 break;
	            	 }
	            	 else {
	            		 wordIndex.get(keyName).add(currentIndex); //If it doesn't exist yet then add it
	            	 }
	             }
	        }
		}
	}
	/**
	 * 
	 * @param name: the name of the stock to be deleted from the hashmap
	 * @param position: position / index in the investment array list of the keyword to be deleted
	 * 
	 */
	public static void deleteHashEntry(String name, int position) {
		//Split the investment name that was passed into the function into seperate words, go through each word
		String [] keynames = name.toLowerCase().split("\\W");
		 // Iterate through each word in the investment name
	    for (String keyName : keynames) {
	        if (wordIndex.containsKey(keyName)) {
	            // Remove the exact numerical position in the list for the deleted investment
	            wordIndex.get(keyName).remove(Integer.valueOf(position));

	            // Decrement remaining positions greater than 'position' in the list
	            for (int i = 0; i < wordIndex.get(keyName).size(); i++) {
	                int currentPosition = wordIndex.get(keyName).get(i); //Get the current position that's in the arraylist
	                if (currentPosition > position) { //Check if the current position is greater than the position given to the function
	                    wordIndex.get(keyName).set(i, currentPosition - 1); //Decrement the position in the arraylist by 1
	                }
	            }

	            // Remove the keyword if it's arraylist is empty
	            if (wordIndex.get(keyName).isEmpty()) {
	                wordIndex.remove(keyName);
	            }
	        }
	    }
	}
	/**
	 * 	//Check the price range of the user's input, and if specific fields were left null
	 * @param price, which is the price of the stock
	 * @param minPriceStr, which is the lower bound in the price range
	 * @param maxPriceStr, which is the upper bound in the price range
	 * @return true if both fields are empty, or will evaluate if the price is within rage
	 */
	public static boolean priceCheck (double price, String minPriceStr, String maxPriceStr) { 
		if (minPriceStr.isEmpty() && maxPriceStr.isEmpty()) {
			return true; // Both fields were empty, so print everything
		}
		else if (minPriceStr.isEmpty() && !(maxPriceStr.isEmpty())) { //Only the minimum field was left empty
			double maxPrice = Double.valueOf(maxPriceStr);
			return price <= maxPrice; //Return true or false, based on if the stock price is less than or equal to the max price
		}
		else if (maxPriceStr.isEmpty() && !(minPriceStr.isEmpty())) { //Only the maximum field was left empty
			double minPrice = Double.valueOf(minPriceStr);
			return price >= minPrice; //Return true or false, based on if the stock price is greater than the minimum price
		}
		else {
			double minPrice = Double.valueOf(minPriceStr);
			double maxPrice = Double.valueOf(maxPriceStr);
			return price <= maxPrice && price >= minPrice; //Return true or false, based on if price is between the min and max range
		}

	}
	
}
