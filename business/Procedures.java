/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

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
public class Procedures {
        private String procCode;
    private String procName;
    private String procDesc;
    private String cost;
    
    public Procedures(){
        
    }
    public Procedures(String proceCode, String proceName, String proceDesc, String procCost ){
        proceCode = procCode;
        proceName = procName;
        proceDesc = procDesc;
        procCost = cost;
    }
    public Procedures selectDB(String procCode) throws SQLException{
        
            String databaseURL = "jdbc:ucanaccess://C:/Users/Wolasi/"
                    + "Documents/NetBeansProjects/DentistOfficeApp/DentistOfficeMDB.mdb";
            String query = "select * from Procedures where procCode = '"+procCode+"'";
        
        try {    
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(databaseURL);
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultset = statement.executeQuery();
            
            while(resultset.next()){
                setProcCode(resultset.getString("procCode"));
                setProcName(resultset.getString("procName"));
                setProcDesc(resultset.getString("procDesc"));
                setCost(resultset.getString("cost"));
                
                System.out.println(resultset.getString("procName"));
                
                conn.close();
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Procedures.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getProcCode() {
        return procCode;
    }

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getProcDesc() {
        return procDesc;
    }

    public void setProcDesc(String procDesc) {
        this.procDesc = procDesc;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
    
}
