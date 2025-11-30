package jgapasin_A3;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.HashMap;

public class Portfolio {
	
	static ArrayList <Investment> investments = new ArrayList<>();
	static HashMap<String, ArrayList <Integer>> wordIndex = new HashMap <String, ArrayList<Integer>>();
	
	public static void main(String[] args) throws InvalidInputException {
		Scanner keyboard = new Scanner(System.in);
		boolean repeat = true;
		
		loadPortfolio("portfolio.txt"); //Load the portfolio
		createHashEntry(); //Create the hashmap entries with any investments
	
		//Declare our variables to be used in switch cases later.
		int userSelect = 0;
		String symbol = "";
		String name = "";
		int quantity = 0;
		double price = 0;
		double bookValue = 0;
		String userChoice = "";
		Investment existingInvestment;
		double newPrice = 0;

		
		while (repeat == true) {
			printMenu();
			userSelect = keyboard.nextInt();
			while (userSelect < 1 || userSelect > 6) {
				System.out.println("Invalid choice. Please try again: ");
				printMenu();
				userSelect = keyboard.nextInt();
			}
			keyboard.nextLine(); // Consumes the newline character
			switch (userSelect) {
			
			//1. BUY COMMAND
			case (1):
	
			System.out.print("Would you like to buy a stock or mutual fund? Enter Stock or Fund: ");
			userChoice = keyboard.nextLine();
			while (!(userChoice.toLowerCase().equals("stock")) && !(userChoice.toLowerCase().equals("fund"))) {
				System.out.print("Invalid. Please try again: ");
				userChoice = keyboard.nextLine();
			}
			
			existingInvestment = null;
			boolean dupeInvestment = false;
			System.out.print("What is the symbol of the investment? ");
			symbol = keyboard.nextLine();
			//Check if the symbol already exists
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
				System.out.println("An investment with this symbol already exists. An investment cannot be both a stock and mutual fund. ");
				break;
			}
			
			if (existingInvestment != null) { //A stock was found, we only need price and quantity
				System.out.print("How many shares are you buying? ");
				quantity = keyboard.nextInt();
				while (quantity < 0) {
					System.out.print("Invalid quantity value. Enter the quantity: ");
					quantity = keyboard.nextInt();
				}
				System.out.print("Enter the price: ");
				price = keyboard.nextDouble();
				while (price < 0) {
					System.out.print("Invalid price value. Enter the price: ");
					price = keyboard.nextDouble();
				}
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
				System.out.println("Company name: " + existingInvestment.getName() + "(" + existingInvestment.getSymbol() +")"); //Print company name and symbol
				System.out.println("Value of stocks (bookValue): $" + String.format("%.2f", existingInvestment.getbookValue())); //Print bookValue
				System.out.println("Price per share: $ " + String.format("%.2f", existingInvestment.getPrice())); //Print price per share
				System.out.println("Number of shares owned: " + existingInvestment.getQuantity()); //Print quantity
				existingInvestment.updatePrice(price); //Update the price after buying
			}
			
			
			else { // It is a new investment, we also need to get input for the name
				/* Side Note, there's no need to update information if the stock is new, since it's done in the constructor */
				System.out.print("What is the name of the investment? ");
				name = keyboard.nextLine();
				System.out.print("How many shares are you buying? ");
				quantity = keyboard.nextInt();
				while (quantity < 0) {
					System.out.print("Invalid quantity value. Enter the quantity: ");
					quantity = keyboard.nextInt();
				}
				System.out.print("Enter the price: ");
				price = keyboard.nextDouble();
				while (price < 0) {
					System.out.print("Invalid price value. Enter the price: ");
					price = keyboard.nextDouble();
				}
			
				if (userChoice.toLowerCase().equals("stock")) {
					Stock newStock = new Stock ("Stock",symbol,name,quantity,price);
					//No need to buy the investment if it's new
					investments.add(newStock);
					//Print the details of the new stock
					System.out.println("Company name: " + newStock.getName() + "(" + newStock.getSymbol() +")"); //Print company name and symbol
					System.out.println("Value of stocks (bookValue): $" + String.format("%.2f", newStock.getbookValue())); //Print bookValue
					System.out.println("Price per share: $ " + String.format("%.2f", newStock.getPrice())); //Print price per share
					System.out.println("Number of shares owned: " + newStock.getQuantity()); //Print quantity
				}
				else if (userChoice.toLowerCase().equals("fund")) {
					MutualFund newFund = new MutualFund ("mutualfund",symbol,name,quantity,price);
					investments.add(newFund);
					//Print the details of the new fund
					System.out.println("Company name: " + newFund.getName() + "(" + newFund.getSymbol() +")"); //Print company name and symbol
					System.out.println("Value of stocks (bookValue): $" + String.format("%.2f", newFund.getbookValue())); //Print bookValue
					System.out.println("Price per share: $ " + String.format("%.2f", newFund.getPrice())); //Print price per share
					System.out.println("Number of shares owned: " + newFund.getQuantity()); //Print quantity
				}
	
			}
			createHashEntry();
			break;
			
			
			//2. SELL COMMAND
			case (2):
			System.out.print("Would you like to sell a stock or mutual fund? Enter Stock or Fund: ");
			userChoice = keyboard.nextLine();
			while (!(userChoice.toLowerCase().equals("stock")) && !(userChoice.toLowerCase().equals("fund"))) {
				System.out.print("Invalid. Please try again: ");
				userChoice = keyboard.nextLine();
			}
			
			existingInvestment = null;
			System.out.print("What is the symbol of the investment? ");
			symbol = keyboard.nextLine();
			//Check if the symbol already exists
			for (Investment investment : investments) {
				if (investment.getSymbol().toLowerCase().equals(symbol.toLowerCase())) {
					existingInvestment = investment;
					break;
				}
			}
			if (existingInvestment != null) { //A stock was found, we only need price and quantity
				System.out.print("How many shares are you selling? ");
				quantity = keyboard.nextInt();
				while (quantity < 0) {
					System.out.print("Invalid quantity value. Enter the quantity: ");
					quantity = keyboard.nextInt();
				}
				//Check to see if the user has enough quantity
				if(existingInvestment.getQuantity() < quantity) {
					System.out.println("You don't have enough shares to sell. Current amount of shares: " + existingInvestment.getQuantity());
					System.out.println("Please try again.");
					break;
				}
				System.out.print("Enter the price: ");
				price = keyboard.nextDouble();
				while (price < 0) {
					System.out.print("Invalid price value. Enter the price: ");
					price = keyboard.nextDouble();
				}
				//Existing investment is a stock
				if (userChoice.toLowerCase().equals("stock")) {
					if (existingInvestment instanceof Stock) {
						Stock foundStock = (Stock) existingInvestment;
						foundStock.sell(symbol, quantity, price);
					}
				}
				//Existing investment is a fund
				else if (userChoice.toLowerCase().equals("fund")) {
					if (existingInvestment instanceof MutualFund) {
						MutualFund foundFund = (MutualFund) existingInvestment;
						foundFund.sell(symbol,quantity,price);
					}
				}
				
				//Print the details of the purchase
				System.out.println("Number of shares sold: " + quantity); //Print quantity
				System.out.println("Company name: " + existingInvestment.getName() + " (" + existingInvestment.getSymbol() +")"); //Print company name and symbol
				System.out.println("Remaining bookValue: $" + String.format("%.2f", existingInvestment.getbookValue())); //Print bookValue
				System.out.println("Remaining shares: " + existingInvestment.getQuantity()); //Print price per share
				existingInvestment.updatePrice(price);
			
			
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
					System.out.println("You sold all your shares of " + existingInvestment.getName());
					}
				}
			
			else { // The stock doesn't exist
				System.out.println("Investment not found in your portfolio.");
			
			}
			break;
				
			//3. Update
			case (3):
				for (Investment investment : investments) {
					System.out.print("Enter the new price for " + investment.getName() + " (" + investment.getSymbol() +") ");
					newPrice = keyboard.nextDouble();
					while (newPrice < 0) {
						System.out.print("Invalid price value. Enter the price: ");
						newPrice = keyboard.nextDouble();
					}
					investment.updatePrice(newPrice);
					System.out.println("New price for " + investment.getName() + " (" + investment.getSymbol() +"): " + String.format("%.2f", investment.getPrice()));
				
			
				}
				break;
				
			//4. getGain COMMAND
			case (4):
				double totalGain = 0;
				double currentGain = 0;
				for (Investment investment : investments) {
					if (investment instanceof Stock) {
						Stock thisStock = (Stock) investment;
						currentGain = thisStock.getGain(thisStock.getSymbol(), thisStock.getQuantity(), thisStock.getPrice());
					}
					
					else if (investment instanceof MutualFund) {
						MutualFund thisFund = (MutualFund) investment;
						currentGain = thisFund.getGain(thisFund.getSymbol(), thisFund.getQuantity(), thisFund.getPrice());
						
					}
					
					totalGain += currentGain;
				}
				
				System.out.println("Potential total gain across all investments: $" + String.format("%.2f", totalGain));
				break;
				
				
			//5. Search 
			case (5):
				
				//Declare our variables that we will search for.
				String minPriceStr, maxPriceStr;
				String userKeywords;
				String userSymbol;
				boolean investmentFound = false;
	
				
				System.out.print("Enter a symbol (or enter to skip): ");
				userSymbol = keyboard.nextLine();
				System.out.print("Enter a set of keywords to search for (or enter to skip): ");
				userKeywords = keyboard.nextLine();
				System.out.print("Enter a minimum price value (or enter to skip): ");
				minPriceStr = keyboard.nextLine();
				System.out.print("Enter a maximum price value (or enter to skip): ");
				maxPriceStr = keyboard.nextLine();
				String [] keywords = userKeywords.toLowerCase().split("\\W"); //Split when encountering any non word characters
				
				//Extreme case where all filters are empty, print all 
				if (minPriceStr.isEmpty() && maxPriceStr.isEmpty() && userKeywords.isEmpty() && userSymbol.isEmpty()) { 
					if(investments.isEmpty() == true) {
						System.out.println("There are currently no investments that match your criteria.");
					}
					else {
						for (Investment investment : investments) {
							System.out.println(investment.toString());
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
				            System.out.println(investment.toString());  // Print investment if it matches criteria
				            investmentFound = true;
				        }
					}
					if (investmentFound == false) {
				        System.out.println("There are currently no investments that match your criteria.");
					}
				}				
			
				break;
				
			//6. Exit the program
			case (6):
				System.out.print("Exiting program...");
				savePortfolio("portfolio.txt");
				repeat = false;
			
			}
		}

	}
	
	/* HELPER METHODS */
	
	public static void printMenu() {
		System.out.println("1. Buy");
		System.out.println("2. Sell");
		System.out.println("3. Update");
		System.out.println("4. getGain");
		System.out.println("5. Search");
		System.out.println("6. End program");
		System.out.print("Enter an option: ");
		
	}
	/**
	 * 
	 * @param filename: name of the file to be saved
	 */
	private static void savePortfolio (String filename) {
		//Connect a stream to the file
			PrintWriter outputStream = null;
			try {
				 outputStream = new PrintWriter (new FileOutputStream(filename));
			}
			catch (FileNotFoundException e) {
				System.out.println("Error opening the file.");
				System.exit(0);
			}
			if (investments.isEmpty()) {
				System.out.println("There are no investments to save.");
			}
			for (Investment investment : investments) {
				outputStream.println("type = \"" + investment.getType().toLowerCase() + "\"");
				outputStream.println("symbol = \"" + investment.getSymbol().toLowerCase() + "\"");
				outputStream.println("name = \"" + investment.getName().toLowerCase() + "\"");
				outputStream.println("quantity = \"" + investment.getQuantity() + "\"");
				outputStream.println("price = \"" + investment.getPrice()+ "\"");
				outputStream.println("bookvalue = \"" + String.format("%.2f", investment.getbookValue()) + "\"\n");
			}
			outputStream.close();
	}
	
	/**
	 * 
	 * @param filename: name of the file that is being loaded
	 * file is of the format:
	 * type = "stock"
	 * symbol = "appl"
	 * name = "apple inc"
	 * quantity = "100"
	 * price = "132.02"
	 * bookvalue = "13211.990000000002"
	 * @throws InvalidInputException 
	 */
	private static void loadPortfolio (String filename) throws InvalidInputException {
			Scanner inputStream = null;
		
			try {
				inputStream = new Scanner (new FileInputStream(filename));
			}
			catch (FileNotFoundException e) {
				System.out.println("Error opening the file.");
				System.exit(0);
			}		
			if (!inputStream.hasNextLine()) {
		        System.out.println("The file is empty. No investments loaded.");
		        inputStream.close();
		        return; // Exit the method since there's nothing to load
		    }
			
			while (inputStream.hasNextLine()) {
				//Read the type
				String readType = inputStream.nextLine().trim(); //Trim the next line to get rid of any leading spaces removed
				String [] strType = readType.split("="); // Should split it based on the equals
				String [] quoteType = strType[1].trim().split("\""); //The stock name should be at an index of 1, so get rid of the quotes around it
				//E.g the array noQuotes should look like 0. 1."stock"
				String type = quoteType[1];

				//Read symbol
				String readSymbol = inputStream.nextLine().trim();
				//System.out.println(readSymbol);
				String [] strSymbol = readSymbol.split("="); // Should split it based on the equals
				String [] quoteSymbol = strSymbol[1].trim().split("\""); //The investment symbol should be at an index of 1, so get rid of the quotes around it
				String symbol = quoteSymbol[1];

				
				//Read name
				String readName = inputStream.nextLine().trim();
				//System.out.println(readName);
				String [] strName = readName.split("="); // Should split it based on the equals
				String [] quoteName = strName[1].trim().split("\""); //The investment symbol should be at an index of 1, so get rid of the quotes around it
				String name = quoteName[1];
				
				//Read quantity
				String readQuant = inputStream.nextLine().trim();
				//System.out.println(readQuant);
				String [] strQuant = readQuant.split("="); // Should split it based on the equals
				String [] quoteQuant = strQuant[1].trim().split("\""); //The investment symbol should be at an index of 1, so get rid of the quotes around it
	            int quantity = Integer.parseInt(quoteQuant[1]); // Read quantity and convert to int

				
				//Read price
				String readPrice = inputStream.nextLine().trim();
				//System.out.println(readPrice);
				String [] strPrice = readPrice.split("="); // Should split it based on the equals
				String [] quotePrice = strPrice[1].trim().split("\""); //The investment symbol should be at an index of 1, so get rid of the quotes around it
	            double price = Double.parseDouble(quotePrice[1]); // Read price and convert to double
				
				//Read bookvalue
				String readBookValue = inputStream.nextLine().trim();
				//System.out.println(readBookValue);
				String [] strBookValue = readBookValue.split("="); // Should split it based on the equals
				String [] quoteBookValue = strBookValue[1].trim().split("\""); //The investment symbol should be at an index of 1, so get rid of the quotes around it
	            double bookValue = Double.parseDouble(quoteBookValue[1]); // Read postID and convert to int

				Investment existingInvestment = null;
	
				//Check if the symbol already exists
				for (Investment investment : investments) {
					//Make sure that the symbol is not both a stock and a fund, the investment type must match what the user entered
					if (investment.getSymbol().toLowerCase().equalsIgnoreCase(symbol)) {
						existingInvestment = investment;
						break;
					}
					
				}
				//If the investment already exists in the list of investments, don't bother adding it, move onto the next investment
				if (!(existingInvestment == null)) {
					if (inputStream.hasNextLine()) {
						String skipLine = inputStream.nextLine(); //Skip over the line between each investment
					}
					else {
						break;
					}
				}

				else {
					//Type was a stock, create a new stock
					if (type.equalsIgnoreCase("stock")) {
						Stock newStock = new Stock ("stock", symbol, name, quantity, price);
						newStock.setBookValue(bookValue);
						investments.add(newStock);
					}
					//Type was a fund, create a new fund
					else if (type.equalsIgnoreCase("mutualfund")) {
						MutualFund newFund = new MutualFund ("fund", symbol, name, quantity, price);
						newFund.setBookValue(bookValue);
						investments.add(newFund);
					}
				}
				if (inputStream.hasNextLine()) {
					String skipLine = inputStream.nextLine(); //Skip over the line between each investment
				}
				else {
					break;
				}
			}
			System.out.println("File loaded successfully!");
			inputStream.close();
	}
	
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
