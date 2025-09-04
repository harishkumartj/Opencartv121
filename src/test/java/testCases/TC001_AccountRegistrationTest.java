package testCases;

import static org.testng.Assert.fail;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	
	
	@Test(groups = {"Regression","Master"})
	public void verify_account_registration()
	{
		logger.info("*****Starting TC001 Account registraton*****");
		
		try {
			
		
		HomePage hp=new HomePage(driver);
		
		hp.clickMyAccount();
		logger.info("*****Clicked on my account link*****");
		
		hp.clickRegister();
		logger.info("*****Clicked on register link*****");
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmail.com");
		regpage.setTelephone(randomeNumber());
		
		//String password=randomeAlphaNumberic();
		
		String password=randomeAlphaNumberic(); //in order to pass same password twice in set password and confirm password
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		
		String confmsg = regpage.getConfirmationMsg();
//		if(confmsg.equals("Your Account Has Been Created!"))
//		{
//			Assert.assertTrue(true);
//		}
//		
//		else
//		{
//			logger.error("***Test Failed***");
//			logger.debug("debug logs....");
//			Assert.assertFalse(false);
//		}
		
		
		
		Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		
	
	}
		catch (Exception e) {
			logger.error("***Test Failed***");
			logger.debug("debug logs....");
			Assert.fail();
			
		}
		
		logger.info("*****Test Case completed*****");
		
	}
}
	
	
	

