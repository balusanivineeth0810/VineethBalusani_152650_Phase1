package com.cg.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import com.cg.WalletApplication.Exception.BankException;
import com.cg.WalletApplication.bean.Customer;
import com.cg.WalletApplication.bean.Wallet;
import com.cg.WalletApplication.service.IWalletService;
import com.cg.WalletApplication.service.WalletServiceImpl;

public class WalletServiceImplTest {
	public static IWalletService iWalletService=null;
	Customer customer1,customer2,customer3,customer4,customer5,customer6,customer7;
	
	@Before
	public void initData() {
		iWalletService=new WalletServiceImpl();
		customer1= new Customer("8885599774","Vineeth","Vineeth@123","balusanivineeth8@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
		customer2 = new Customer("9949453482","Varun","Varun@123","pulivarun125@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
		customer3 = new Customer("8949453482","Vinay","Vinay@123","vinay_gr@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
		customer4= new Customer("7885599774","John","John@123","johndoe12@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
		customer5= new Customer("6885599774","Doe","Doe#doe@123","doe_doe.doe@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
		customer6= new Customer("8885599775","Sherlock","Sherlock@123","sherlock8@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
		customer7= new Customer("8885599773","Watson","Watson@123","watson12@gmail.com",new Wallet(),new TreeMap<LocalDateTime, String>());
	}
	
	@Test(expected = BankException.class)
	public void checkMobileNumberError() throws BankException
	{
		iWalletService.checkMobileNumber("8888");
	}
	@Test
	public void checkMobileNumberTrue() throws BankException
	{
		iWalletService.checkMobileNumber("8885599774");
	}
	
	@Test(expected = BankException.class)
	public void checkNameTestError() throws BankException
	{
		iWalletService.checkName("");
	}
	
	@Test
	public void checkNameTest() throws BankException
	{
		iWalletService.checkName("Vineeth");
	}
	
	@Test(expected = BankException.class)
	public void passwordLengthError() throws BankException
	{
		iWalletService.checkPassword("varun");
	}
	
	@Test(expected = BankException.class)
	public void passwordSpecialCharacterError() throws BankException
	{
		iWalletService.checkPassword("varun1234");
	}
	
	@Test(expected = BankException.class)
	public void passwordCapitalLetterError() throws BankException
	{
		iWalletService.checkPassword("varun@123");
	}
	@Test
	public void passwordTest() throws BankException
	{
		iWalletService.checkPassword("Varun@123");
	}
	
	@Test(expected = BankException.class)
	public void emailTestFailDueToSymbol() throws BankException
	{
		iWalletService.checkEmail("johndoe123gmail.com");
	}
	
	@Test(expected = BankException.class)
	public void emailTestFailDueTodotcom() throws BankException
	{
		iWalletService.checkEmail("johndoe123@gmail");
	}
	@Test(expected = BankException.class)
	public void emailTestFailDueToMissingDomain() throws BankException
	{
		iWalletService.checkEmail("johndoe123@.com");
	}
	
	@Test(expected = BankException.class)
	public void emailTestFailDueToMissingId() throws BankException {
		iWalletService.checkEmail("@gmail.com");
	}
	
	@Test(expected = BankException.class)
	public void emailTestFailDueToUnAllowedSpecialCharacters() throws BankException {
		iWalletService.checkEmail("john?doe/%@#gmail.com");
	}
	
	@Test
	public void emailTestForValidEmailId() throws BankException {
		iWalletService.checkEmail("john.doe_12@xyzmail123.com");
	}
	
	
	@Test
	public void addCustomerTestTrue() throws BankException
	{
		assertEquals("8885599774",iWalletService.addCustomer(customer1));
			
	}
	
	@Test
	public void checkForExistingCustomer() throws BankException
	{
		assertNotNull(iWalletService.check("8885599774", "Vineeth@123"));
	}
    
	@Test(expected = BankException.class)
	public void checkForNonExistingCustomer() throws BankException
	{
		assertNull(iWalletService.check("9586234523", "John@1John"));
	}
	

    @Test
  	public void addCustomerTestFalse() throws BankException
  	{
  		assertNotEquals("8885599774",iWalletService.addCustomer(customer2));
  			
  	}
    
    @Test(expected = BankException.class)
    public void addExistingCustomer() throws BankException
    {
    	iWalletService.addCustomer(customer2);
    }
    
    @Test
    public void initialBalanceTest() throws BankException
    {
    	assertEquals(BigDecimal.valueOf(0.0),iWalletService.showBalance("8885599774","Vineeth@123").getWallet().getBalance());
    }
    
    @Test
    public void depositTestTrue()
    {
    	iWalletService.deposit(customer1, BigDecimal.valueOf(5000));
    }
    
    @Test
    public void withdrawTest() throws BankException
    {
    	iWalletService.deposit(customer2, BigDecimal.valueOf(5000));
    	assertEquals(true,iWalletService.withDraw(customer2, BigDecimal.valueOf(4000)));
    }
    
    @Test(expected = BankException.class)
    public void withdrawTestException() throws BankException
    {
    	iWalletService.addCustomer(customer3);
    	iWalletService.withDraw(customer3, BigDecimal.valueOf(5000));
  
    }
	@Test
	public void checkForReceiverExists() throws BankException
	{
		assertTrue(iWalletService.isFound("8885599774"));
	}
	
	@Test(expected = BankException.class)
	public void checkForReceiverNotExists() throws BankException
	{
		assertTrue(iWalletService.isFound("9494232097"));
	}
    @Test
    public void fundTransferTest() throws InterruptedException, BankException
    {
    	iWalletService.addCustomer(customer4);
    	iWalletService.addCustomer(customer5);
    	iWalletService.deposit(customer4, BigDecimal.valueOf(5000));
    	assertEquals(true,iWalletService.transfer("7885599774", "6885599774", BigDecimal.valueOf(5000)));
    }
    
    @Test(expected = BankException.class)
    public void fundTransferFalse() throws BankException, InterruptedException
    {
    	iWalletService.addCustomer(customer6);
    	iWalletService.addCustomer(customer7);
    	assertEquals(false,iWalletService.transfer("8885599775", "8885599773", BigDecimal.valueOf(1000)));
    }
    
    @Test
    public void amountValidationTestForInteger() throws BankException {
    	assertTrue(iWalletService.checkEnteredAmount(BigDecimal.valueOf(5000)));
    }
    
    @Test
    public void amountValidationTestIfDigitsExistsAfterDecimalPoint() throws BankException {
    	assertTrue(iWalletService.checkEnteredAmount(BigDecimal.valueOf(5000.00)));
    }
    
    @Test
    public void amountValidationTestIfDigitsDoesntExistsAfterDecimalPoint() throws BankException {
    	assertTrue(iWalletService.checkEnteredAmount(BigDecimal.valueOf(5000.)));
    }
    
    
}
