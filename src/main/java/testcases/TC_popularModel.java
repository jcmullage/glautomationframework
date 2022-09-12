package testcases;

import java.util.Properties;

import org.testng.annotations.Test;

import helper.cofig.Configurartion;
import pageObjects.Buggy_LogIn;
import pageObjects.Buggy_PopularModel;
import testbase.TestBase;

public class TC_popularModel extends TestBase{

	Buggy_LogIn login = new Buggy_LogIn();
	Buggy_PopularModel popularModel = new Buggy_PopularModel();
	Properties prop = null;
	Configurartion config = new Configurartion();
	
	@Test
    public void tc_checkPopularModel() throws InterruptedException
    {
		prop = config.readProperties();
		
        OpenBrowser(login.appURL);
        //Fill(login.tf_uname,prop.getProperty("login.uname"));
        Fill(login.tf_uname,TC_buggyRegister.loginName);
        Fill(login.tf_password,prop.getProperty("login.password"));
        Click(login.brn_submit);
        CheckElementPresent(login.lbl_firstName);
        Wait(3000);
        
        
        
        Click(popularModel.img_popularmake);
        String makePopularModel = GetText(popularModel.td_mostpopularmodelinmake);
        String makePopularVote = GetText(popularModel.td_mostpopularmodelVotecountinmake);
        
        Navigate("back");
        
        Click(popularModel.img_popularmodel);
        
        String model = popularModel.lbl_modelMostpopular;
        String vote = popularModel.lbl_mostpopularmodelVotecount;
        String popularModel = GetText(model);
        String popularVote = GetText(vote);
        
        if(makePopularModel.equalsIgnoreCase(popularModel)&&makePopularVote.equalsIgnoreCase(popularVote)) {
        	Pass("Popular Model");
        }else {
        	Fail("Not the correct popular model");
        }
        
        Click(login.btn_logout);
        CheckElementPresent(login.btn_login);
        Wait(3000);
        
        Quit();
       
    }
}
