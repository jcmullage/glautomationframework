package testcases;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.testng.annotations.Test;

import helper.cofig.Configurartion;
import pageObjects.Buggy_LogIn;
import pageObjects.Buggy_Register;
import testbase.TestBase;

public class TC_buggyRegister extends TestBase {
	
	Buggy_Register register = new Buggy_Register();
	Buggy_LogIn login = new Buggy_LogIn();
	Properties prop = null;
	Configurartion config = new Configurartion();

//    public static String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
//    public static final String loginName = ("jcmullage")+timeStamp;

    @Test
    public void tc_register() 
    {

        prop = config.readProperties();
        // String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());

        OpenBrowser(register.appURL);
        Click(register.btn_register);
        CheckElementPresent(register.lbl_header);
        //Fill(register.tf_login,prop.getProperty("login")+timeStamp);
        Fill(register.tf_login,loginName);
        Fill(register.tf_fname,prop.getProperty("firstName"));
        Wait(10000);
        Fill(register.tf_lname,prop.getProperty("lastName"));
        Fill(register.tf_password,prop.getProperty("password"));
        Fill(register.tf_repassword,prop.getProperty("password"));
        Click(register.btn_submit);
        CheckElementPresent(register.lbl_success);
        Wait(10000);
        
        //Fill(login.tf_uname,prop.getProperty("login")+timeStamp);
        Fill(login.tf_uname,loginName);
        Fill(login.tf_password,prop.getProperty("password"));
        Click(login.brn_submit);
        CheckElementPresent(login.lbl_firstName);
        Wait(3000);
        
        Click(login.btn_logout);
        CheckElementPresent(login.btn_login);
        Wait(3000);
        
        Quit();
       
    }
}
