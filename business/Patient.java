package business;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class has SelectDB, UpdateDB, InsertDB, DeleteDB that accesses and
 * retrieves data from the database.
 * It also has getter and setters for all properties.
 * @author Emmanuel Nyaletey
 */
public class Patient {
    private String patId;
    private String patPass;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String insCo;
    
    public Patient(){
        
    }
    public Patient(String patientId, String patPassword, String fName, String lName, String addr, String eMail, String insuranceCo){
        patientId = patId;
        patPassword = patPass;
        fName = firstName;
        lName = lastName;
        addr = address;
        eMail = email;
        insuranceCo = insCo;   
    }
    public Patient selectDB(String patId) throws IOException{
        
            String databaseURL = "jdbc:ucanaccess://C:/Users/Wolasi/"
                    + "Documents/NetBeansProjects/DentistOffice/DentistOfficeMDB.mdb";
            String query = "select * from Patients where patId = '" + patId + "'";
        try {    
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(databaseURL);
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultset = statement.executeQuery();
            
            while (resultset.next()) {
                setPatId(resultset.getString("patId"));
                setPatPass(resultset.getString("passwd"));
                setFirstName(resultset.getString("firstName"));
                setLastName(resultset.getString("lastName"));
                setAddress(resultset.getString("addr"));
                setEmail(resultset.getString("email"));
                setInsCo(resultset.getString("insCo"));
            }
            
        } catch (SQLException | ClassNotFoundException  ex) {
            System.out.println(ex);
        }
        return null;
    }
    public Patient addNewDB(String patID, String patPw, String fName, String lName, String address, String email, String insCo) throws SQLException{
        String databaseURL = "jdbc:ucanaccess://C:/Users/Wolasi/"
                    + "Documents/NetBeansProjects/DentistOffice/DentistOfficeMDB.mdb";
            String query = "INSERT into Patients (patId, passwd, firstName, lastName, addr, email, insCo) VALUES(?, ?, ?, ?, ?, ?, ?)";
            
                    try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(databaseURL);
            PreparedStatement statement = conn.prepareStatement(query);
            
            statement.setString(1, patID);
            statement.setString(2, patPw);
            statement.setString(3, fName);
            statement.setString(4, lName);
            statement.setString(5, address);
            statement.setString(6, email);
            statement.setString(7, insCo);
            
            statement.executeUpdate();
            conn.close();
            
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return null;
        
    }
    public Patient updateDB(String patId, String firstName, String lastName, String address) throws IOException, SQLException, ClassNotFoundException{
        String databaseURL = "jdbc:ucanaccess://C:/Users/Wolasi/"
                    + "Documents/NetBeansProjects/DentistOffice/DentistOfficeMDB.mdb";
        String query = "UPDATE Patients SET firstName= ?, lastName= ?, addr= ?  WHERE patId = '" + patId + "'";
        
        try{    
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(databaseURL);
            PreparedStatement statement = conn.prepareStatement(query);
            
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, address);
            
            statement.executeUpdate();
            
            setFirstName(firstName);
            setLastName(lastName);
            setAddress(address);
            
            
            conn.close();

        }
        catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return null;
        
    }
    public boolean deleteDB(String patId) throws SQLException {
        String databaseURL = "jdbc:ucanaccess://C:/Users/Wolasi/"
                    + "Documents/NetBeansProjects/DentistOffice/DentistOfficeMDB.mdb";
            String query = "DELETE from Patients WHERE patId = '"+patId+"'";
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

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getPatPass() {
        return patPass;
    }

    public void setPatPass(String patPass) {
        this.patPass = patPass;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInsCo() {
        return insCo;
    }

    public void setInsCo(String insCo) {
        this.insCo = insCo;
    }
    public static void main(String [] arg) throws IOException, SQLException, ClassNotFoundException{
        Patient p = new Patient();
        p.selectDB("A900");
        p.updateDB("A900", "James", "Mark", "Atlanta");
        System.out.println("update patient = "+p.getFirstName());
    }
  
}
