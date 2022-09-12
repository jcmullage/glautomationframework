package testcases;

import java.util.Properties;

import org.testng.annotations.Test;

import helper.cofig.Configurartion;
import pageObjects.Buggy_LogIn;
import testbase.TestBase;

public class TC_logIn extends TestBase{

	Buggy_LogIn login = new Buggy_LogIn();
	Properties prop = null;
	Configurartion config = new Configurartion();
	
	@Test
    public void tc_login() throws InterruptedException
    {
		prop = config.readProperties();
		
        OpenBrowser(login.appURL);
        //Fill(login.tf_uname,prop.getProperty("login.uname"));
        Fill(login.tf_uname,loginName);
        Fill(login.tf_password,prop.getProperty("login.password"));
        Click(login.brn_submit);
        CheckElementPresent(login.lbl_firstName);
        Wait(5000);
        
        Click(login.btn_logout);
        CheckElementPresent(login.btn_login);
        Wait(3000);
       
        Quit();
    }
	
}
