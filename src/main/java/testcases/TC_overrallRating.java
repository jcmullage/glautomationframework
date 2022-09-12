package testcases;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.testng.annotations.Test;

import helper.cofig.Configurartion;
import pageObjects.Buggy_LogIn;
import pageObjects.Buggy_OverallRating;
import testbase.TestBase;

public class TC_overrallRating extends TestBase {

Buggy_LogIn login = new Buggy_LogIn();
Buggy_OverallRating overallRating = new Buggy_OverallRating();
Properties prop = null;
Configurartion config = new Configurartion();
	
	@Test
	public void tc_overrallRating() throws InterruptedException
    {

		prop = config.readProperties();
        String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
		
		OpenBrowser(login.appURL);
		//Fill(login.tf_uname,prop.getProperty("login.uname")+timeStamp);
        Fill(login.tf_uname,TC_buggyRegister.loginName);
        Fill(login.tf_password,prop.getProperty("login.password"));
        Click(login.brn_submit);
        CheckElementPresent(login.lbl_firstName);
        Wait(3000);
        
        Click(overallRating.img_overall);
        
        Click(overallRating.tr_2select);
        CheckElementPresent(overallRating.lbl_brandName);
        
        CheckElementPresent(overallRating.lbl_model);
        
        Click(overallRating.lbl_model);
        
        CheckElementPresent(overallRating.lbl_modelCheck);
        
        Fill(overallRating.ta_comment, prop.getProperty("comment"));
        Click(overallRating.btn_vote);
        CheckElementPresent("//td[normalize-space()='"+prop.getProperty("comment")+ "']");
        Wait(5000);
        
        Click(login.btn_logout);
        CheckElementPresent(login.btn_login);
        Wait(3000);
        
        Quit();
       
    }
}
