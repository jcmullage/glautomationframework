package testcases;

import java.util.Properties;

import org.testng.annotations.Test;

import helper.cofig.Configurartion;
import pageObjects.Buggy_LogIn;
import pageObjects.Buggy_Profile;
import testbase.TestBase;

public class TC_profile extends TestBase{

Buggy_LogIn login = new Buggy_LogIn();
Buggy_Profile profile = new Buggy_Profile();
Properties prop = null;
Configurartion config = new Configurartion();
	
	@Test
    public void tc_editProfile() throws InterruptedException
    {
		prop = config.readProperties();
		
        OpenBrowser(login.appURL);
        //Fill(login.tf_uname,prop.getProperty("login.uname"));
        Fill(login.tf_uname,TC_buggyRegister.loginName);
        Fill(login.tf_password,prop.getProperty("login.password"));
         Click(login.brn_submit);
        CheckElementPresent(login.lbl_firstName);
        Wait(3000);
        
        Click(profile.lbl_profile);
        CheckElementPresent(profile.lbl_checkprofile);
        
        Clear(profile.tf_gender);
        Fill(profile.tf_gender,prop.getProperty("profile.gender"));
        Clear(profile.tf_age);
        Fill(profile.tf_age,prop.getProperty("profile.age"));
        Clear(profile.tf_address);
        Fill(profile.tf_address,prop.getProperty("profile.address"));
        Clear(profile.tf_phoneno);
        Fill(profile.tf_phoneno,prop.getProperty("profile.phoneNo"));
        SelectOptionByVisibleText(profile.dd_hobby, prop.getProperty("profile.hobby"));
        
        Click(profile.btn_save);
        
        CheckElementPresent(profile.lbl_checksave);
        
        Wait(3000);
        
        Click(login.btn_logout);
        CheckElementPresent(login.btn_login);
        Wait(3000);
        
        Quit();
       
    }
}
