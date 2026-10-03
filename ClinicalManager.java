import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ClinicalManager {

    static Scanner sc = new Scanner(System.in);
    static Clinic clinic = new Clinic();

    public static void main(String[] args) {

        clinic.loadData();

        while (true) {

            System.out.println("\n=================================");
            System.out.println("        CLINIC MANAGER");
            System.out.println("=================================");
            System.out.println("1. Book Appointment");
            System.out.println("2. Reschedule Appointment");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. Issue Prescription");
            System.out.println("5. Patient History");
            System.out.println("6. Doctor Schedule");
            System.out.println("7. Doctor Utilisation");
            System.out.println("8. Busiest Hours");
            System.out.println("9. Add Doctor");
            System.out.println("10. Add Patient");
            System.out.println("11. Find Doctor by Speciality");
            System.out.println("12. Show All Appointments");
            System.out.println("13. Save Data");
            System.out.println("14. Exit");
            System.out.print("Choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    bookAppointment();
                    break;

                case 2:
                    rescheduleAppointment();
                    break;

                case 3:
                    cancelAppointment();
                    break;

                case 4:
                    issuePrescription();
                    break;

                case 5:
                    patientHistory();
                    break;

                case 6:
                    doctorSchedule();
                    break;

                case 7:
                    clinic.displayUtilisation();
                    break;

                case 8:
                    clinic.displayBusiestHours();
                    break;

                case 9:
                    addDoctor();
                    break;

                case 10:
                    addPatient();
                    break;

                case 11:
                    findDoctorBySpeciality();
                    break;

                case 12:
                    clinic.displayAllAppointments();
                    break;

                case 13:
                    clinic.saveData();
                    break;

                case 14:
                    clinic.saveData();
                    System.out.println("Thank you for using Clinic Manager.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
