/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class has SelectDB, UpdateDB, InsertDB, DeleteDB that accesses and
 * retrieves data from the database.
 * It also has getter and setters for all properties.
 * @author Emmanuel Nyaletey
 */
public class Dentist {
    private String dentId;
    private String dentPass;
    private String firstName;
    private String lastName;
    private String email;
    private String office;
    private AppointmentsList apptList;
    
    public Dentist(){
        
    }
    public Dentist(String dentistId, String password, String fName, String lName, String eMail, String dentOffice){
        dentistId = dentId;
        password = dentPass;
        fName = firstName;
        lName = lastName;
        eMail = email;
        dentOffice = office;
        
    }
    public Dentist selectDB(String dentId) throws SQLException, IOException{
        
        String databaseURL = "jdbc:ucanaccess://C:/Users/Wolasi/"
                + "Documents/NetBeansProjects/DentistOffice/DentistOfficeMDB.mdb";
        String query = "SELECT * from Dentists where id = '" +dentId + "'";
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(databaseURL);
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultset = statement.executeQuery();
            
            while (resultset.next()){
                setDentId(resultset.getString("id"));
                setDentPass(resultset.getString("passwd"));
                setFirstName(resultset.getString("firstName"));
                setLastName(resultset.getString("lastName"));
                setEmail(resultset.getString("email"));
                setOffice(resultset.getString("office"));
                
            }
            this.apptList=new Appointment().selectDDB(dentId);
             System.out.println("dent  ");
             apptList.display();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dentist.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    public Dentist updateDB(String dentId, String fName, String lName, String email) {
        String databaseURL = "jdbc:ucanaccess://C:/Users/Wolasi/"
                    + "Documents/NetBeansProjects/DentistOffice/DentistOfficeMDB.mdb";
            String query = "UPDATE Dentists SET firstName = ?, lastName = ?, email = ? WHERE id = '"+dentId+"'";
        
        try{    
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(databaseURL);
            PreparedStatement statement = conn.prepareStatement(query);
            
            statement.setString(1, fName);
            statement.setString(2, lName);
            statement.setString(3, email);
            
            statement.executeUpdate();
            
            setFirstName(fName);
            setLastName(lName);
            setEmail(email);
                
            conn.close();
        }
        catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }                
        return null;
        
    }

    public String getDentId() {
        return dentId;
    }

    public void setDentId(String dentId) {
        this.dentId = dentId;
    }

    public String getDentPass() {
        return dentPass;
    }

    public void setDentPass(String dentPass) {
        this.dentPass = dentPass;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
    public void setApptList(AppointmentsList apptList){
      this.apptList = apptList;
    }
    public AppointmentsList getApptList(){
      return apptList;
    }
    public static void main (String [] args) throws SQLException, IOException {
        Dentist d = new Dentist();
        d.selectDB("D201");
        System.out.println("dentist is "+ d.getFirstName());
        d.updateDB("D201", "Frederick", "Martin", "fm@aol.com");
        System.out.println("update dent is "+ d.getFirstName());
        System.out.println("dent office is "+d.getOffice());
    }
   
}
