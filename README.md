# ePortfolio

A Java-based application for managing a portfolio of investments, including stocks and mutual funds.

## Overview

This project provides a comprehensive portfolio management system with a graphical user interface built using Java Swing. Users can manage their investments efficiently with features like buying/selling stocks and mutual funds, updating prices, calculating gains, and searching through their portfolio.

## Features

- **Swing GUI** for intuitive user interaction
- **Investment Management**: Buy and sell stocks or mutual funds
- **Price Updates**: Update investment prices in real-time
- **Gain Calculation**: View total gains or losses across all investments
- **Advanced Search**: Search investments by symbol, keywords, or price range
- **Persistent Storage**: Automatically save and load portfolio data from file
- **Exception Handling**: Robust error handling for invalid inputs
- **Fast Search**: HashMap-based keyword indexing for efficient searches

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Command-line terminal (macOS/Linux) or Command Prompt (Windows)

### Build Instructions

1. **Compile the project:**

   ```bash
   javac jgapasin_A3/*.java
   ```

2. **Create a JAR file:**

   ```bash
   jar cvfe ePortfolio.jar jgapasin_A3.Portfolio jgapasin_A3/*.class
   ```

3. **Run the application:**

   ```bash
   java -jar ePortfolio.jar
   ```

4. **Generate documentation:**
   ```bash
   javadoc -d Javadoc jgapasin_A3
   ```

> **Note:** Execute these commands from the project root directory.

## Usage

### GUI Screens

The application provides six main panels accessible through the "Commands" menu:

1. **Welcome Screen** - Displays introductory message and basic instructions
2. **Buy Investment** - Add stocks or mutual funds to your portfolio
3. **Sell Investment** - Sell stocks or mutual funds from your portfolio
4. **Update Prices** - Update the prices of existing investments
5. **Total Gain** - View total gain or loss for all investments
6. **Search Investments** - Search by symbol, keywords, or price range

## Class Architecture

### Investment

Abstract base class representing a generic investment.

- **Fields**: symbol, name, quantity, price, bookValue
- **Methods**: Abstract methods for `calculateGain()` and `updatePrice()`

### Stock

Extends Investment - represents stock investments.

- Inherits all fields from Investment
- Implements `calculateGain()` for stock-specific calculations
- $9.99 commission fee applied upon sale

### MutualFund

Extends Investment - represents mutual fund investments.

- Inherits all fields from Investment
- Implements `calculateGain()` for mutual fund-specific calculations
- $45.00 redemption fee applied upon sale

### Portfolio

Manages the collection of investments and handles business logic.

- Methods: `addInvestment()`, `removeInvestment()`, `updateInvestment()`, etc.
- Handles file I/O for persistent storage
- Processes all portfolio operations

### PortfolioGUI

Manages the graphical user interface using Java Swing.

## Test Cases

### Buy Command

- Buy a new stock
- Buy a new mutual fund
- Buy an existing investment (updates quantity and price)
- Buy same symbol but different type (error handling)
- Invalid investment type input (error handling)

### Sell Command

- Sell partial quantity of stock/mutual fund
- Sell complete holding (removes from portfolio)
- Attempt to sell non-existent investment (error handling)
- Commission/redemption fees applied correctly

### Update Prices Command

- Update valid stock price
- Reject negative prices (error handling)

### Get Gain Command

- Calculate total gain with no price updates
- Calculate total gain with price updates

### Search Command

- Search by symbol only
- Search by keywords only
- Search by price range only
- Search by multiple criteria
- Search with no filters (returns all investments)
- Handle no matching investments

## Technical Details

### Assumptions

- Users will enter valid numeric data for quantities and prices
- Keyword searches use case-insensitive exact matching
- Stocks incur a $9.99 commission fee upon sale
- Mutual funds incur a $45.00 redemption fee upon sale

### Limitations

- Keyword matching requires exact matches (case-insensitive)
- File storage uses plain text format (portfolio.txt)

## Project Information

- **Author**: Javian Gapasin
- **Student ID**: 1266761
- **Course**: CIS 2430
- **Assignment**: A3

## License

This project is provided for educational purposes.
