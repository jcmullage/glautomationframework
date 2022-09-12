package testcases;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.testng.annotations.Test;

import helper.cofig.Configurartion;
import pageObjects.Buggy_LogIn;
import pageObjects.Buggy_PopularMake;
import testbase.TestBase;

public class TC_popularMake extends TestBase{
	
	Buggy_LogIn login = new Buggy_LogIn();
	Buggy_PopularMake popularMake = new Buggy_PopularMake();
	Properties prop = null;
	Configurartion config = new Configurartion();
	
	@Test
	public void tc_voteforpopular() throws InterruptedException
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
       
        
        Click(popularMake.img_popularMake);
        CheckElementPresent(popularMake.lbl_checkModel);
        CheckElementPresent(popularMake.lbl_brand);
        Click(popularMake.lbl_brand);
        
        CheckElementPresent(popularMake.lbl_checkBrand);
        
        int beforeVote =Integer.parseInt(GetText(popularMake.lbl_voteCount));
        		
        Fill(popularMake.ta_comment, prop.getProperty("comment"));
        Click(popularMake.btn_vote);
        CheckElementPresent("//td[normalize-space()='"+prop.getProperty("comment")+ "']");
        Thread.sleep(7000);
        
        int afterVote =Integer.parseInt(GetText(popularMake.lbl_voteCount));
        
        if(afterVote==(beforeVote+1)) {
        	Pass("Voting is Successefull");
        } else {
        	Fail("Voting is UnSuccessefull");
        }
        
        Click(login.btn_logout);
        CheckElementPresent(login.btn_login);
        Wait(3000);
        
        Quit();
       
    }
}
