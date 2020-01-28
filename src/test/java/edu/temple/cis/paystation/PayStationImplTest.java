/**
 * Testcases for the Pay Station system.
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

import org.junit.Test;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.Before;

public class PayStationImplTest {

    PayStation ps;

    @Before
    public void setup() {
        ps = new PayStationImpl();
    }

    
    /**
     * Entering 50 cents should make empty return 50.
     */
    @Test
    public void emptyShouldReturnTotalAmountEntered() throws IllegalCoinException {
    	ps.addPayment(25);
    	ps.addPayment(25);
    	assertEquals("Empty should return 50.", 50, ps.empty());
    }
    
    //Call to cancel clears the map.
    @Test
    public void cancelShouldClearMap() throws IllegalCoinException{
    	ps.addPayment(10);
    	ps.cancel();
    	Map<Integer, Integer> temp = ps.cancel();
    	assertEquals("25 should return 0", (Integer)0, temp.get(25));
    	assertEquals("10 should return 0", (Integer)0, temp.get(10));
    	assertEquals("5 should return 0", (Integer)0, temp.get(5));

    }
    
    //Call to buy clears the map.
    @Test
    public void buyShouldClearMap() throws IllegalCoinException{
    	ps.addPayment(10);
    	ps.addPayment(10);
    	ps.buy();
    	Map<Integer, Integer> temp = ps.cancel();
    	assertEquals("25 should return 0", (Integer)0, temp.get(25));
    	assertEquals("10 should return 0", (Integer)0, temp.get(10));
    	assertEquals("5 should return 0", (Integer)0, temp.get(5));
    	
    }
    
    
    
    
    
    
    /**
     * Calls to empty should reset the total.
     */
    
    @Test
    public void emptyShouldResetTotalToZero() throws IllegalCoinException {
    	ps.addPayment(25);
    	ps.empty();
    	/**
    	 * coins returned should be 0 if cancelled after emptied! 
    	 * less hacky way of changing visibility of insertedSoFar or getting it via a getter
    	 */
    	//Map<Integer, Integer> temp = ps.cancel();
    	//assertEquals("Nickels should be zero.", (Integer)0, temp.get(5));
    	//assertEquals("Dimes should be zero.", (Integer)0, temp.get(10));
    	//assertEquals("Quarters should be zero.", (Integer)0, temp.get(25));
    	assertEquals("Total should be 0.", 0, ps.getTotal());
    }
    
    /**
     * Canceled entry does not add to the amount returned by empty.
     */
    @Test
    public void canceledEntryDoesNotAddToEmpty() throws IllegalCoinException {
    	ps.addPayment(25);
    	ps.addPayment(25);
    	assertEquals("Empty should return 50.", 50, ps.empty());
    	ps.addPayment(25);
    	assertEquals("Empty should return 25.", 25, ps.empty());
    }
    
    /**
     * Entering 5 cents should make the display report 2 minutes parking time.
     */
    @Test
    public void shouldDisplay2MinFor5Cents()
            throws IllegalCoinException {
        ps.addPayment(5);
        assertEquals("Should display 2 min for 5 cents",
                2, ps.readDisplay());
    }

    /**
     * Entering 25 cents should make the display report 10 minutes parking time.
     */
    @Test
    public void shouldDisplay10MinFor25Cents() throws IllegalCoinException {
        ps.addPayment(25);
        assertEquals("Should display 10 min for 25 cents",
                10, ps.readDisplay());
    }

    /**
     * Verify that illegal coin values are rejected.
     */
    @Test(expected = IllegalCoinException.class)
    public void shouldRejectIllegalCoin() throws IllegalCoinException {
        ps.addPayment(17);
    }

    /**
     * Entering 10 and 25 cents should be valid and return 14 minutes parking
     */
    @Test
    public void shouldDisplay14MinFor10And25Cents()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Should display 14 min for 10+25 cents",
                14, ps.readDisplay());
    }

    /**
     * Buy should return a valid receipt of the proper amount of parking time
     */
    @Test
    public void shouldReturnCorrectReceiptWhenBuy()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Receipt receipt;
        receipt = ps.buy();
        assertNotNull("Receipt reference cannot be null",
                receipt);
        assertEquals("Receipt value must be 16 min.",
                16, receipt.value());
    }
    
    /**
     * Calls Empty and should return the total amount collected before last empty
     **/
    @Test
    public void shouldReturnTotalAmountCollected() throws IllegalCoinException {
    	ps.addPayment(10);
    	ps.addPayment(5);
    	ps.addPayment(25);
    	ps.addPayment(25);
    	assertEquals("The value returned should be 65",
                65, ps.empty());
    	

    }
    

    /**
     * Call to cancel returns a map containing a mixture of coins entered.
     * @throws IllegalCoinException 
     */
    @Test
    public void callToCancelReturnsMixture() throws IllegalCoinException {
    	ps.addPayment(25);
    	ps.addPayment(5);
    	ps.addPayment(10);
    	ps.addPayment(5);
    	Map<Integer, Integer> temp = ps.cancel();
    	assertEquals("Quarters in returnMixture should be 1.", (Integer)1, temp.get(25));
    	assertEquals("Dimes in returnMixture should be 1.", (Integer)1, temp.get(10));
    	assertEquals("Nickels in returnMixture should be 2.", (Integer)2, temp.get(5));
    }
    
    /**
     * Call to cancel returns a map that does not contain a key for a coin not entered.
     * 
     */
    @Test
    public void callToCancelDoesntReturnNonExistantCoin() throws IllegalCoinException {
    	ps.addPayment(5);
    	ps.addPayment(25);
    	Map<Integer, Integer> temp = ps.cancel();
    	//assertEquals("Dimes should be null.")
    	// TODO
    	
    }
    
    
    /**
     * Buy for 100 cents and verify the receipt
     */
    @Test
    public void shouldReturnReceiptWhenBuy100c()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);

        Receipt receipt;
        receipt = ps.buy();
        assertEquals("Receipt should be 40.",40, receipt.value());
    }
    
    //Call to cancel returns a map containing one coin entered.
    @Test 
    public void shouldReturnMapContainingOneCoin() throws IllegalCoinException {
    	//ps.addPayment(25);
    	ps.addPayment(25);
    	Map<Integer, Integer> temp = ps.cancel();
    	assertEquals("25=1,10=0,5=0", (Integer)1, temp.get(25));
    }
    
    

    /**
     * Verify that the pay station is cleared after a buy scenario
     */
    @Test
    public void shouldClearAfterBuy()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.buy(); // I do not care about the result
        // verify that the display reads 0
        assertEquals("Display should have been cleared",
                0, ps.readDisplay());
        // verify that a following buy scenario behaves properly
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Next add payment should display correct time",
                14, ps.readDisplay());
        Receipt r = ps.buy();
        assertEquals("Next buy should return valid receipt",
                14, r.value());
        assertEquals("Again, display should be cleared",
                0, ps.readDisplay());
    }

    /**
     * Verify that cancel clears the pay station
     */
    @Test
    public void shouldClearAfterCancel()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.cancel();
        assertEquals("Cancel should clear display",
                0, ps.readDisplay());
        ps.addPayment(25);
        assertEquals("Insert after cancel should work",
                10, ps.readDisplay());
    }
}
