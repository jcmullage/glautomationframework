package helper.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDBQuery {
	
	public String getApplicantName(int applicantID) throws SQLException {
		String name = null;
		String dbQuery = "SELECT first_name FROM db_vis.applicant WHERE applicant_id=" + applicantID;
		ResultSet result = DataBaseHelper.getResultSet(dbQuery);
		while(result.next()) {
			name = result.getString("first_name");
		}
		return name;
	}
	
	public List<Applicant> getApplicants() throws SQLException {
		List<Applicant> data = new ArrayList<Applicant>();
		String dbQuery = "SELECT * FROM db_vis.applicant";
		ResultSet result = DataBaseHelper.getResultSet(dbQuery);
		while(result.next()) {
			Applicant applicant = new Applicant();
			applicant.setApplicantId(Integer.parseInt(result.getString("applicant_id")));
			applicant.setEmail(result.getString("email"));
			applicant.setFirstName(result.getString("first_name"));
			applicant.setLastName(result.getString("last_name"));
			data.add(applicant);
		}
		return data;
	}
	
	public static void main(String[] args) throws SQLException {
		ApplicationDBQuery applicationdbquery = new ApplicationDBQuery();
		String name = applicationdbquery.getApplicantName(100);
		System.out.println(name);
		List<Applicant> listOfData = applicationdbquery.getApplicants();
		for (Applicant applicant : listOfData) {
			System.out.println("Applicant id : " + applicant.getApplicantId() + " Email : " + applicant.getEmail() + " First Name : " + applicant.getFirstName() + " Last Name : " + applicant.getLastName());
		}
	}

}
