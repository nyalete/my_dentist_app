package business;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class contains selectDb, updateDB, InstertDB, and DeleteDB.
 * It also has a list that returns a list of appointments.
 * Class has getters and setters for all properties.
 * @author Emmanuel Nyaletey
 */
public class Appointment {
    private String apptDateTime;
    private String patId;
    private String dentId;
    private String procCode; 
    
    public Appointment(){
        
    }
    public Appointment(String dateTime, String patientId, String dentistId, String proceCode){
        dateTime = apptDateTime;
        patientId = patId;
        dentistId = dentId;
        proceCode = procCode;
    }
    
    public void display(){
        
        System.out.println("Appt Day time:"+apptDateTime);
        System.out.println("Appt patid:"+patId);
        System.out.println("dentid:"+dentId);
        System.out.println("Proc:"+procCode);
    }
    public Appointment selectDB(String patId) throws SQLException{
        
            String databaseURL = "jdbc:ucanaccess://C:/Users/Wolasi/"
                    + "Documents/NetBeansProjects/DentistOffice/DentistOfficeMDB.mdb";
            String query = "SELECT * from Appointments where patId = '"+patId+"'";
            
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(databaseURL);
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultset = statement.executeQuery();
            
            while(resultset.next()){
                setApptDateTime(resultset.getString("apptDateTime"));
                setPatId(resultset.getString("patId"));
                setDentId(resultset.getString("dentId"));
                setProcCode(resultset.getString("procCode"));
               
                conn.close();
            }
            
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return null;
    }
    public AppointmentsList selectDDB(String dentId) throws IOException{
        String databaseURL = "jdbc:ucanaccess://C:/Users/Wolasi/"
                + "Documents/NetBeansProjects/DentistOffice/DentistOfficeMDB.mdb";
        String query = "select * from Appointments where dentId = '"+dentId+"'";
        AppointmentsList  aList = new AppointmentsList();
    try {
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        Connection conn = DriverManager.getConnection(databaseURL);
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet result = statement.executeQuery();
            
        while (result.next()) {
            String pid =result.getString("patId");
            
            Appointment a1=new Appointment();
            a1.selectDB(pid);
            aList.addAppointment(a1);
            a1.display();
            
 
        }
        System.out.println("Appt  ");
        aList.display();
        return aList;
        
 
    }catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return null;
    }
    public Appointment updateDB(String apptDateTime, String patId) throws SQLException {
        String databaseURL = "jdbc:ucanaccess://C:/Users/Wolasi/"
                    + "Documents/NetBeansProjects/DentistOffice/DentistOfficeMDB.mdb";
            String query = "UPDATE Appointments SET apptDateTime = '"+apptDateTime+"' WHERE patId = '"+patId+"'";
            
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(databaseURL);
            PreparedStatement statement = conn.prepareStatement(query);
            
            statement.executeUpdate();
            setApptDateTime(apptDateTime);
                        
            conn.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }          
        return null;
        
    } public Appointment createDB(String apptDateTime, String patId, String dentId, String procCode) throws SQLException{
        String databaseURL = "jdbc:ucanaccess://C:/Users/Wolasi/"
                    + "Documents/NetBeansProjects/DentistOffice/DentistOfficeMDB.mdb";
            String query = "INSERT into Appointments (apptDateTime, patId, dentId, procCode) VALUES(?, ?, ?, ?)";
            
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(databaseURL);
            PreparedStatement statement = conn.prepareStatement(query);
            
            statement.setString(1, apptDateTime);
            statement.setString(2, patId);
            statement.setString(3, dentId);
            statement.setString(4, procCode);
            
            statement.executeUpdate();
            conn.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }          
        return null;
        
    }
    public boolean deleteDB(String patId) throws SQLException{
        String databaseURL = "jdbc:ucanaccess://C:/Users/Wolasi/"
                    + "Documents/NetBeansProjects/DentistOffice/DentistOfficeMDB.mdb";
            String query = "DELETE from Appointments WHERE patId = '"+patId+"'";
            
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(databaseURL);
            PreparedStatement statement = conn.prepareStatement(query);
            statement.executeUpdate();
            conn.close();
            
            return true;
            
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
            
        return false;
        
    }
    public static String buildAppointmentsHtml(Appointment appt) throws SQLException, IOException  {
      StringBuilder stringBuilder = new StringBuilder();
        Patient pat = new Patient();
        pat.selectDB(appt.getPatId());
        Dentist dent = new Dentist();
        dent.selectDB(appt.getDentId());
        Procedures proc = new Procedures();
        proc.selectDB(appt.getProcCode());
        stringBuilder.append("<tr>");
        stringBuilder.append("<td>" + appt.getApptDateTime() + "</td>");
        stringBuilder.append("<td>" + pat.getFirstName() + " " + pat.getLastName() + "</td>");
        stringBuilder.append("<td>" + dent.getLastName() + "</td>");
        stringBuilder.append("<td>" + proc.getProcDesc() + "</td>");
        stringBuilder.append("</tr>");
          
      return stringBuilder.toString();
  }

    public String getApptDateTime() {
        return apptDateTime;
    }

    public void setApptDateTime(String apptDateTime) {
        this.apptDateTime = apptDateTime;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getDentId() {
        return dentId;
    }

    public void setDentId(String dentId) {
        this.dentId = dentId;
    }

    public String getProcCode() {
        return procCode;
    }

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }

}

