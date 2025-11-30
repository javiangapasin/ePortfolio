package jgapasin_A3;

/**
 * Class Name: Stock
 * 
 * Description: Represents a stock investment in the portfolio. It is a subclass of the Investment class 
 * and contains attributes and behaviors specific to stocks, such as commission fees. This class manages 
 * stock-specific operations, including buying, selling, and updating stock prices.
 */
public class Stock extends Investment{

	private static final double COMMISSION = 9.99;
	
	/**
	 * 
	 * @param type: defines what type of investment it is. Either a stock or mutualfund
	 * @param symbol, which is the user entered symbol for the investment
	 * @param name, which is the user entered name for the investment
	 * @param quantity, which is the user entered amount of shares that we are buying
	 * @param price, which is the user entered price for each share
	 * @throws InvalidInputException 
	 */
	public Stock(String type, String symbol, String name, int quantity, double price) throws InvalidInputException {
		super(type, symbol, name, quantity, price);
		this.bookValue = quantity * price + COMMISSION;
	}
	/**
	 * 
	 * @param addQuantity, user entered quantity for amount of shares that are being bought
	 * @param price, user entered price for the price of each share
	 */
	
	public void buy (int addQuantity, double price) {
		this.price = price;
	    if (this.quantity == 0) { // If it's the first purchase
	        this.bookValue = price * addQuantity + COMMISSION;
	        this.quantity = addQuantity;
	    } 
	    else { // If it's an additional purchase
	        this.bookValue += price * addQuantity + COMMISSION;
	        this.quantity += addQuantity;
	    }
	}
	
	/**
	 * 
	 * @param symbol, user entered symbol for the investment
	 * @param quantity, user entered quantity for the amount of shares to sell
	 * @param price, user entered price for how much we are selling each share
	 * @throws InvalidInputException 
	 */
	public void sell (String symbol, int quantity, double price) throws InvalidInputException {
		if (quantity <= 0 || quantity > this.quantity) {
            throw new InvalidInputException("Quantity must be a positive number and must not be more than your total shares.\n");
        }
        if (price <= 0) {
            throw new InvalidInputException("Price must be a positive number.\n");
        }
		int remainingQuantity = this.quantity - quantity;
		double remainingBookValue = this.bookValue * ((double)remainingQuantity / this.quantity);
		double bookValueSold = this.bookValue - remainingBookValue;
		double payment = price * quantity - COMMISSION;
		double gain = payment - bookValueSold;
		this.quantity = remainingQuantity; //Update the quantity to whatever is remaining
		this.bookValue = remainingBookValue; //Update the book value to whatever is remaining
		System.out.println("Your payment total was $" + String.format("%.2f", payment));
		System.out.println("Your gain on the sale was $" + String.format("%.2f", gain));
	}
	
	/**
	 * 
	 * @param quantity, given number for quantity to calculate for a payment
	 * @param price, given number for price to calcualte for a payment
	 * @return the theoretical payment
	 */
	public double getPayment (int quantity, double price) {
		int remainingQuantity = this.quantity - quantity;
		double remainingBookValue = this.bookValue * ((double)remainingQuantity / this.quantity);
		double bookValueSold = this.bookValue - remainingBookValue;
		double payment = price * quantity - COMMISSION;
		double gain = payment - bookValueSold;
		return payment;
	}
	
	/**
	 * 
	 * @param symbol, user entered symbol for the investment
	 * @param quantity, the current amount of shares for an investment
	 * @param price, the current price for an investment
	 * @return returns the gain or profit on the investment
	 */
	public double getGain(String symbol, int quantity, double price) {
		int remainingQuantity = this.quantity - quantity;
		double remainingBookValue = this.bookValue * ((double)remainingQuantity / this.quantity);
		double bookValueSold = this.bookValue - remainingBookValue;
		double payment = price * quantity - COMMISSION;
		double gain = payment - bookValueSold;
		return gain;
	}
}
