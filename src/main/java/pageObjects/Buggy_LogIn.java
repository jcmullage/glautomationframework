package pageObjects;

public class Buggy_LogIn {

	public static String appURL = "https://buggy.justtestit.org/";
	public static String tf_uname = "//input[@name='login']";
	public static String tf_password = "//input[@name='password']";
	public static String brn_submit = "(//button[normalize-space()='Login'])[1]";
	public static String lbl_firstName = "//span[contains(text(),'Hi,')]";
	public static String btn_logout = "//a[normalize-space()='Logout']";
	public static String btn_login = "//button[normalize-space()='Login']";
	
}
