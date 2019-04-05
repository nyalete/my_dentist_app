/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import business.Appointment;
import business.Dentist;
import business.Patient;
import business.Procedures;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Emmanuel Nyaletey
 */
@WebServlet(name = "PatientLoginServlet", urlPatterns = {"/PatientLoginServlet"})
public class PatientLoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String patID = request.getParameter("patId");
            String patPw = request.getParameter("patPass");

            //create new patient object
            Patient p = new Patient();
            p.selectDB(patID);
  
            //check pass and id in db
            String dbID = p.getPatId();
            String dbPw = p.getPatPass();

            //make a decision
            if(patPw.equals(dbPw)){
                //put object in session
                HttpSession sessionPat = request.getSession();
                sessionPat.setAttribute("sessionP", p);
                
                //create new appointment object
                Appointment a = new Appointment();
                a.selectDB(patID);
                
                //put appointment in session
                HttpSession sessionApp = request.getSession();
                sessionApp.setAttribute("sessionA", a);
                
                //create new dentist
                Dentist d = new Dentist();
                d.selectDB(a.getDentId());
                
                //put dentist in session
                HttpSession sessionD = request.getSession();
                sessionD.setAttribute("sessionD", d);
                
                //create new procedure
                Procedures proc = new Procedures();
                proc.selectDB(a.getProcCode());
                
                HttpSession sessionProc = request.getSession();
                sessionProc.setAttribute("sessionProc", proc);
                //forward control
                RequestDispatcher dp = request.getRequestDispatcher("/DisplayPatient.jsp");
                dp.forward(request, response);
                
            }else{
                RequestDispatcher dp = request.getRequestDispatcher("/LoginError.jsp");
                dp.forward(request, response);
                
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PatientLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PatientLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
