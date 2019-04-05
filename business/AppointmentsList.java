
package business;

import java.util.ArrayList;

/**
 * This class has an arrayList that contains appointment objects.
 * @author Emmanuel Nyaletey
 */
public class AppointmentsList {
    private final ArrayList<Appointment> apptList = new ArrayList();
    
    public void addAppointment(Appointment ap){
        apptList.add(ap);
    }
    public ArrayList<Appointment> getAllAppointments() {
        return this.apptList;
    }
    public void display(){
        for (Appointment a : this.apptList) {
           a.display();
             
        }
         
    }
    public int count() {
    	return apptList.size();
    }
    
}
