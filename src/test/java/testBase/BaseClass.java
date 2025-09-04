package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;  //Log4j
import org.apache.logging.log4j.Logger;  //Log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException
	{
		
		//loading config.properties file
		
		FileReader file = new FileReader("./src//test//resources//config.properties"); // can use file input stream class also
		p=new  Properties();
		p.load(file);
		logger=LogManager.getLogger(this.getClass()); //log4j2 snippet
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities=new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
				
			}
			else if (os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC); 
			}
			else
			{
				System.out.println("No matching os");
				return;
			}
			
			
			switch(br.toLowerCase())
			{
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			case "firefox": capabilities.setBrowserName("firefox"); break;
			default: System.out.println("No matching browser"); return;
			}
			
			driver=new RemoteWebDriver(new URL("http://192.168.1.7:4444/wd/hub"),capabilities);
		}
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{	
		
		switch (br.toLowerCase())  //which browser to launch
		{
		case "chrome": driver=new ChromeDriver(); break;
		case "edge": driver=new EdgeDriver(); break;
		case "firefox": driver=new FirefoxDriver(); break;
		
		default:System.out.println("Invaid browser name"); return;
			
		}
		
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//driver.get("https://demo.opencart.com/");
		//driver.get("https://tutorialsninja.com/demo/");
		driver.get(p.getProperty("appURL2")); //to read url from config .properties file
		driver.manage().window().maximize();
		
		
	}
	
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown()
	{
		driver.quit();
	}

	
	public String randomeString()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
		
	}
	
	public String randomeNumber()
	{
		String generatednumber=RandomStringUtils.randomNumeric(5);
		return generatednumber;
		
	}
	
	public String randomeAlphaNumberic()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(3);
		String generatednumber=RandomStringUtils.randomNumeric(3);
		return(generatedstring+"@*"+generatednumber);//has alphabets,no's,spl chars
		
	}
	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
			
		return targetFilePath;
	
}
}
