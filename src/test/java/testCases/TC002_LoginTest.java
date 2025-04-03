package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups = {"Master", "Sanity"})
		public void verify_login()
		{
			logger.info("****** Startign TC_002_LoginTest *****");
			
			try
			{
			//HomePage
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
		
			//Login page
			LoginPage lp=new LoginPage(driver);
			lp.setEmail(p.getProperty("email")); //p is properties class object.
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();
			
			//MyAccount page
			MyAccountPage macc=new MyAccountPage(driver);
			boolean targetPage=macc.isMyAccountPageExists();
			
			Assert.assertTrue(targetPage);//Assert.assertEquals(targetPage, true,"Login failed");
			}
			catch(Exception e)
			{
				Assert.fail();
			}
			logger.info("****** Finished TC_002_LoginTest *****");
		}

}
