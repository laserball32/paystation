/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
package edu.temple.cis.paystation;

import java.util.HashMap;
import java.util.Map;

public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought;
    private Map<Integer, Integer> coins = new HashMap<Integer, Integer>();  
    
    private void storeChange(int coin) {
    	if (coins.containsKey(coin))
    		coins.put(coin, coins.get(coin)+1);
    	else
    	{
    		coins.putIfAbsent(coin, 1);
    	}
    }
    
    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: 
            	storeChange(5);
            	
            	break;
            case 10: 
            	storeChange(10);
            	break;
            
            case 25:
            	storeChange(25);
            	break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
    	
        Receipt r = new ReceiptImpl(timeBought);
        reset();
        
        return r;
    }
    public int getTotal() {
    	return insertedSoFar;
    }
    
    public int empty() {
    	int temp = insertedSoFar;
    	insertedSoFar = 0;
    	return temp;
    }
    
    public Map<Integer,Integer> getMap(){
    	return coins;
    }

    @Override
	public Map<Integer, Integer> cancel() {
    	Map<Integer, Integer> returnedCoins = new HashMap<Integer, Integer>();
    	returnedCoins.putAll(coins);
        reset();
		return returnedCoins; 
    }
    
    private void reset() {
    	coins.clear();

        timeBought = insertedSoFar = 0;
    }
}
