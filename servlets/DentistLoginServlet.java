
package servlets;

import business.Appointment;
import business.Dentist;
import business.Patient;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "DentistLoginServlet", urlPatterns = {"/DentistLoginServlet"})
public class DentistLoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String dentID = request.getParameter("dentId");
            String dentPw = request.getParameter("dentPass");
            System.out.println("Dentist pw is " +dentPw);
            
            Dentist d = new Dentist();
            d.selectDB(dentID);
            
            String dbID = d.getDentId();
            System.out.println("db id is "+dbID);
            String dbPw = d.getDentPass();
            
            if(dentPw.equals(dbPw)) {
                HttpSession sessionDent = request.getSession();
                sessionDent.setAttribute("sessionD", d);
                
                
                ArrayList<String> listOfAppts = new ArrayList<>();
                List<Appointment> list = d.getApptList().getAllAppointments();
                if (list.size() > 0) {
                    list.forEach((app) -> {
                        try {
                            listOfAppts.add(Appointment.buildAppointmentsHtml(app));
                        } catch (SQLException ex) {
                            Logger.getLogger(DentistLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(DentistLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    System.out.println("List in servlet size is "+listOfAppts.size());
                }
                HttpSession sessionApp = request.getSession();
                sessionApp.setAttribute("sessionApptList", listOfAppts);

                RequestDispatcher dp = request.getRequestDispatcher("/DisplayDentist.jsp");
                dp.forward(request, response);
                
            }else{
                RequestDispatcher dp = request.getRequestDispatcher("/LoginError.jsp");
                dp.forward(request,response);
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
            Logger.getLogger(DentistLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DentistLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
