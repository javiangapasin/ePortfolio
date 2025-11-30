package jgapasin_A3;

import java.util.Objects;

/*
 * Class Name: Investment
 * 
 * Description: A superclass for managing general investment data. This class contains common attributes 
 * and methods shared by different types of investments such as stocks and mutual funds. It includes details 
 * like symbol, name, quantity, price, and book value, and provides functionality to calculate gains and update values.
 */

public abstract class Investment {

	private String type;
	protected String symbol = "";
	protected String name = "";
	protected int quantity = 0;
	protected double price = 0.0;
	protected double bookValue = 0.0;

	/**
	 * 
	 * @param type: defines what type of investment it is. Either a stock or mutualfund
	 * @param symbol, which is the user entered symbol for the investment
	 * @param name, which is the user entered name for the investment
	 * @param quantity, which is the user entered amount of shares that we are buying
	 * @param price, which is the user entered price for each share
	 * @throws InvalidInputException 
	 */
	public Investment (String type, String symbol, String name, int quantity, double price) throws InvalidInputException {
		if (symbol == null || symbol.isEmpty()) {
            throw new InvalidInputException("Symbol cannot be empty.\n");
        }
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("Name cannot be empty.\n");
        }
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be a positive number.\n");
        }
        if (price <= 0) {
            throw new InvalidInputException("Price must be a positive number.\n");
        }
		this.type = type;
		this.symbol = symbol;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.bookValue = price * quantity; //Initial bookValue, initialize in the investment class
	}
	
	public String getType() {
		return type.toString();
	}
	public String getSymbol() {
		return symbol.toString();
	}
	
	public String getName() {
		return name.toString();
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getPrice() {
		return price;
	}
	
	public double getbookValue() {
		return bookValue;
	}

	public double setBookValue(double newBookValue) {
		bookValue = newBookValue;
		return bookValue;
	}

	/**
	 * 
	 * @param price, user entered value for new price
	 */
	public void updatePrice (double price) {
		this.price = price;
	}
	public String toString() {
		return "\nInvestment symbol: " + this.symbol + "\nInvestment Name: " + this.name + "\nNumber of shares: " + this.quantity + "\nPrice per share $" + this.price + "\nBook value: $" + (String.format("%.2f", this.bookValue));
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true; // Same object reference
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false; // Null or different class type
	    }

	    Investment other = (Investment) obj;

	    // Compare each field for equality
	    return Objects.equals(symbol, other.symbol) &&
	           Objects.equals(name, other.name) &&
	           quantity == other.quantity &&
	           Double.compare(price, other.price) == 0 &&
	           Double.compare(bookValue, other.bookValue) == 0;
	}

}
