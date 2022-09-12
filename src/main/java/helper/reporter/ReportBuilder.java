package helper.reporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class ReportBuilder {
	
	
	private ExtentReports  extent;
	private ExtentTest logger;
 
	private static ThreadLocal<ExtentTest> extentLogger = new ThreadLocal<ExtentTest>();
    
	public ReportBuilder() {
		/**
		 * 
		 */
 
	}
    
    public ExtentReports getExtent() {
		return extent;
	}


	public void setExtent(ExtentReports extent) {
		this.extent = extent;
	}


	public ExtentTest getLogger() {
		return logger;
	}


	public void setLogger(ExtentTest logger) {
		this.logger = logger;
	}



	/**
	 * Start Reporting for a single test case
	 * 
	 * @param reportname {@link String}
	 * @param extent    {@link ExtentReports}
	 * @param logger    {@link ExtentTest}
	 */
	public void startReport(String reportname,String browser) {
        //ExtentReports(String filePath,Boolean replaceExisting
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        extent = new ExtentReports(System.getProperty("user.dir")+"\\Reports\\"+reportname+"_"+timeStamp+".html", false);    
        extent.addSystemInfo("Browser",browser).addSystemInfo("Browser version", "xxx").addSystemInfo("User Name", System.getProperty("user.name"));
        //loading the external xml file (i.e., extent-config.xml) which was placed under the base directory
        //You could find the xml file below. Create xml file in your project and copy past the code mentioned below
        extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
       
    }
	
	

	public void startTestcase(String testcasename) {
		logger = extent.startTest(testcasename);
	}
	
	//end the reporter and write to file
	public void endTestcase() {
		extent.endTest(logger);
	}
	
	
	//end the reporter and write to file
	public void endReport() {
		extent.flush();
	}


}
