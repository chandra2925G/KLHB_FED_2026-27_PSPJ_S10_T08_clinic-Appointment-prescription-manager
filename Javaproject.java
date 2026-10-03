import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Javaproject{

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

    // ================= BOOK APPOINTMENT =================

    private static void bookAppointment() {

        System.out.println("\n===== BOOK APPOINTMENT =====");

        clinic.displayAllDoctors();
        clinic.displayAllPatients();

        System.out.print("Doctor ID: ");
        String doctorId = sc.nextLine();

        System.out.print("Patient ID: ");
        String patientId = sc.nextLine();

        System.out.print("Slot (HH:MM): ");
        String time = sc.nextLine();

        try {

            Appointment appointment =
                    clinic.bookAppointment(
                            doctorId,
                            patientId,
                            time
                    );

            if (appointment == null) {

                Doctor doctor = clinic.findDoctor(doctorId);

                if (doctor != null) {

                    Slot next =
                            doctor.findNextFreeSlot(time);

                    if (next != null) {

                        System.out.print(
                                "Book next free slot "
                                        + next.getTime()
                                        + "? (y/n): "
                        );

                        String answer = sc.nextLine();

                        if (answer.equalsIgnoreCase("y")) {

                            clinic.bookAppointment(
                                    doctorId,
                                    patientId,
                                    next.getTime()
                            );
                        }
                    }
                }
            }

        } catch (SlotUnavailableException e) {

            System.out.println(e.getMessage());
        }
    }

    // ================= RESCHEDULE =================

    private static void rescheduleAppointment() {

        System.out.println("\n===== RESCHEDULE =====");

        System.out.print("Appointment ID: ");
        String id = sc.nextLine();

        System.out.print("New Slot (HH:MM): ");
        String newTime = sc.nextLine();

        clinic.rescheduleAppointment(
                id,
                newTime
        );
    }

    // ================= CANCEL =================

    private static void cancelAppointment() {

        System.out.println("\n===== CANCEL APPOINTMENT =====");

        System.out.print("Appointment ID: ");
        String id = sc.nextLine();

        clinic.cancelAppointment(id);
    }

    // ================= PRESCRIPTION =================

    private static void issuePrescription() {

        System.out.println("\n===== ISSUE PRESCRIPTION =====");

        System.out.print("Appointment ID: ");
        String appointmentId = sc.nextLine();

        System.out.print("Number of medicines: ");
        int count = readInt();

        if (count <= 0) {
            System.out.println("Prescription must contain at least one medicine.");
            return;
        }

        List<Medicine> medicines = new ArrayList<>();

        for (int i = 1; i <= count; i++) {

            System.out.println("\nMedicine " + i);

            System.out.print("Medicine name: ");
            String name = sc.nextLine();

            System.out.print("Dosage: ");
            String dosage = sc.nextLine();

            System.out.print("Duration in days: ");
            String durationText = sc.nextLine();

            try {

                int duration =
                        Integer.parseInt(durationText);

                Medicine medicine =
                        new Medicine(
                                name,
                                dosage,
                                duration
                        );

                medicines.add(medicine);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid dosage/duration value. Prescription rejected."
                );

                return;

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Invalid medicine: "
                                + e.getMessage()
                );

                return;
            }
        }

        clinic.issuePrescription(
                appointmentId,
                medicines
        );
    }

    // ================= PATIENT HISTORY =================

    private static void patientHistory() {

        System.out.println("\n===== PATIENT HISTORY =====");

        System.out.print("Patient ID: ");
        String id = sc.nextLine();

        clinic.displayPatientHistory(id);
    }

    // ================= DOCTOR SCHEDULE =================

    private static void doctorSchedule() {

        System.out.println("\n===== DOCTOR SCHEDULE =====");

        System.out.print("Doctor ID: ");
        String id = sc.nextLine();

        clinic.displayDoctorSchedule(id);
    }

    // ================= ADD DOCTOR =================

    private static void addDoctor() {

        System.out.println("\n===== ADD DOCTOR =====");

        System.out.print("Doctor ID: ");
        String id = sc.nextLine();

        System.out.print("Doctor Name: ");
        String name = sc.nextLine();

        System.out.print("Speciality: ");
        String speciality = sc.nextLine();

        clinic.addDoctor(
                id,
                name,
                speciality
        );
    }

    // ================= ADD PATIENT =================

    private static void addPatient() {

        System.out.println("\n===== ADD PATIENT =====");

        System.out.print("Patient ID: ");
        String id = sc.nextLine();

        System.out.print("Patient Name: ");
        String name = sc.nextLine();

        clinic.addPatient(
                id,
                name
        );
    }

    // ================= SPECIALITY SEARCH =================

    private static void findDoctorBySpeciality() {

        System.out.println("\n===== FIND DOCTOR =====");

        System.out.print("Speciality: ");
        String speciality = sc.nextLine();

        System.out.print("From time (HH:MM): ");
        String time = sc.nextLine();

        Doctor doctor =
                clinic.findEarliestDoctor(
                        speciality,
                        time
                );

        if (doctor == null) {

            System.out.println(
                    "No doctor available for this speciality."
            );

        } else {

            Slot slot =
                    doctor.findNextFreeSlot(time);

            System.out.println(
                    "Doctor: "
                            + doctor.getName()
            );

            System.out.println(
                    "Speciality: "
                            + doctor.getSpeciality()
            );

            System.out.println(
                    "Earliest Free Slot: "
                            + slot.getTime()
            );
        }
    }

    // ================= INPUT =================

    private static int readInt() {

        while (true) {

            try {

                return Integer.parseInt(
                        sc.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.print(
                        "Enter a valid number: "
                );
            }
        }
    }
}


// ======================================================
//                       CLINIC
// ======================================================

class Clinic {

    private final List<Doctor> doctors;
    private final List<Patient> patients;
    private final List<Appointment> appointments;
    private final List<Prescription> prescriptions;

    private final InteractionChecker interactionChecker;

    private int appointmentCounter = 1;
    private int prescriptionCounter = 1;

    private static final String DATA_FOLDER = "clinic_data";

    public Clinic() {

        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
        prescriptions = new ArrayList<>();

        interactionChecker =
                new BasicInteractionChecker();

        createDataFolder();
    }

    // ================= DATA FOLDER =================

    private void createDataFolder() {

        File folder =
                new File(DATA_FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    // ================= DOCTOR =================

    public void addDoctor(
            String id,
            String name,
            String speciality) {

        if (id.isBlank() ||
                name.isBlank() ||
                speciality.isBlank()) {

            System.out.println(
                    "Doctor details cannot be empty."
            );

            return;
        }

        if (findDoctor(id) != null) {

            System.out.println(
                    "Doctor already exists."
            );

            return;
        }

        doctors.add(
                new Doctor(
                        id,
                        name,
                        speciality
                )
        );

        System.out.println(
                "Doctor added successfully."
        );
    }

    public Doctor findDoctor(String id) {

        return doctors.stream()
                .filter(d ->
                        d.getId()
                                .equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    // ================= PATIENT =================

    public void addPatient(
            String id,
            String name) {

        if (id.isBlank() ||
                name.isBlank()) {

            System.out.println(
                    "Patient details cannot be empty."
            );

            return;
        }

        if (findPatient(id) != null) {

            System.out.println(
                    "Patient already exists."
            );

            return;
        }

        patients.add(
                new Patient(
                        id,
                        name
                )
        );

        System.out.println(
                "Patient added successfully."
        );
    }

    public Patient findPatient(String id) {

        return patients.stream()
                .filter(p ->
                        p.getId()
                                .equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    // ================= BOOK =================

    public Appointment bookAppointment(
            String doctorId,
            String patientId,
            String requestedTime)
            throws SlotUnavailableException {

        Doctor doctor =
                findDoctor(doctorId);

        Patient patient =
                findPatient(patientId);

        if (doctor == null) {

            System.out.println(
                    "Doctor not found."
            );

            return null;
        }

        if (patient == null) {

            System.out.println(
                    "Patient not found."
            );

            return null;
        }

        Slot slot =
                doctor.findSlot(requestedTime);

        if (slot == null) {

            System.out.println(
                    "Invalid time slot."
            );

            return null;
        }

        if (slot.isBooked()) {

            System.out.println(
                    requestedTime
                            + " taken."
            );

            Slot next =
                    doctor.findNextFreeSlot(
                            requestedTime
                    );

            if (next != null) {

                System.out.println(
                        "Next free slot: "
                                + next.getTime()
                );

            } else {

                System.out.println(
                        "No later free slot available."
                );
            }

            return null;
        }

        slot.book();

        String id =
                "A"
                        + String.format(
                        "%03d",
                        appointmentCounter++
                );

        Appointment appointment =
                new Appointment(
                        id,
                        doctor,
                        patient,
                        slot
                );

        appointments.add(appointment);

        patient.addVisit(appointment);

        System.out.println(
                "Appointment booked successfully."
        );

        System.out.println(appointment);

        return appointment;
    }

    // ================= RESCHEDULE =================

    public boolean rescheduleAppointment(
            String appointmentId,
            String newTime) {

        Appointment appointment =
                findAppointment(
                        appointmentId
                );

        if (appointment == null) {

            System.out.println(
                    "Appointment not found."
            );

            return false;
        }

        if (appointment.getStatus()
                .equals("CANCELLED")) {

            System.out.println(
                    "Cannot reschedule a cancelled appointment."
            );

            return false;
        }

        Doctor doctor =
                appointment.getDoctor();

        Slot newSlot =
                doctor.findSlot(newTime);

        if (newSlot == null) {

            System.out.println(
                    "Invalid new time."
            );

            return false;
        }

        if (newSlot.isBooked()) {

            System.out.println(
                    "New slot is already booked."
            );

            return false;
        }

        try {

            appointment.reschedule(newSlot);

            System.out.println(
                    "Appointment rescheduled successfully."
            );

            System.out.println(
                    appointment
            );

            return true;

        } catch (SlotUnavailableException e) {

            System.out.println(
                    e.getMessage()
            );

            return false;
        }
    }

    // ================= CANCEL =================

    public boolean cancelAppointment(
            String appointmentId) {

        Appointment appointment =
                findAppointment(
                        appointmentId
                );

        if (appointment == null) {

            System.out.println(
                    "Appointment does not exist."
            );

            return false;
        }

        if (appointment.getStatus()
                .equals("CANCELLED")) {

            System.out.println(
                    "Appointment is already cancelled."
            );

            return false;
        }

        appointment.cancel();

        System.out.println(
                "Appointment cancelled successfully."
        );

        return true;
    }

    // ================= FIND APPOINTMENT =================

    public Appointment findAppointment(
            String id) {

        return appointments.stream()
                .filter(a ->
                        a.getId()
                                .equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    // ================= PRESCRIPTION =================

    public Prescription issuePrescription(
            String appointmentId,
            List<Medicine> medicines) {

        Appointment appointment =
                findAppointment(
                        appointmentId
                );

        if (appointment == null) {

            System.out.println(
                    "Appointment not found."
            );

            return null;
        }

        if (appointment.getStatus()
                .equals("CANCELLED")) {

            System.out.println(
                    "Cannot issue prescription for a cancelled appointment."
            );

            return null;
        }

        try {

            interactionChecker.check(
                    medicines
            );

        } catch (InteractionWarning e) {

            System.out.println();
            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "WARNING: "
                            + e.getMessage()
            );

            System.out.println(
                    "Clinician decision required."
            );

            System.out.println(
                    "Prescription will still be saved."
            );

            System.out.println(
                    "======================================"
            );
        }

        String id =
                "RX"
                        + String.format(
                        "%03d",
                        prescriptionCounter++
                );

        Prescription prescription =
                new Prescription(
                        id,
                        appointment.getId(),
                        appointment.getPatient().getId(),
                        medicines
                );

        prescriptions.add(
                prescription
        );

        System.out.println(
                "Prescription saved successfully."
        );

        System.out.println(
                prescription
        );

        return prescription;
    }

    // ================= PATIENT HISTORY =================

    public void displayPatientHistory(
            String patientId) {

        Patient patient =
                findPatient(patientId);

        if (patient == null) {

            System.out.println(
                    "Patient not found."
            );

            return;
        }

        System.out.println(
                "\n================================="
        );

        System.out.println(
                "        PATIENT HISTORY"
        );

        System.out.println(
                "================================="
        );

        System.out.println(
                "Patient ID: "
                        + patient.getId()
        );

        System.out.println(
                "Patient Name: "
                        + patient.getName()
        );

        System.out.println(
                "\nVisits:"
        );

        List<Appointment> history =
                patient.getVisitHistory()
                        .stream()
                        .sorted(
                                Comparator.comparing(
                                        Appointment::getSlot
                                )
                        )
                        .toList();

        if (history.isEmpty()) {

            System.out.println(
                    "No visits found."
            );

        } else {

            history.forEach(
                    System.out::println
            );
        }

        System.out.println(
                "\nPrescriptions:"
        );

        List<Prescription> patientPrescriptions =
                prescriptions.stream()
                        .filter(p ->
                                p.patientId()
                                        .equalsIgnoreCase(
                                                patientId
                                        ))
                        .toList();

        if (patientPrescriptions.isEmpty()) {

            System.out.println(
                    "No prescriptions."
            );

        } else {

            patientPrescriptions.forEach(
                    p -> {

                        System.out.println(
                                "\n" + p.id()
                        );

                        for (Medicine medicine :
                                p.medicines()) {

                            System.out.println(
                                    "  "
                                            + medicine
                            );
                        }
                    }
            );
        }

        System.out.println(
                "\nTotal Visits: "
                        + history.size()
        );

        System.out.println(
                "Total Prescriptions: "
                        + patientPrescriptions.size()
        );
    }

    // ================= DOCTOR SCHEDULE =================

    public void displayDoctorSchedule(
            String doctorId) {

        Doctor doctor =
                findDoctor(doctorId);

        if (doctor == null) {

            System.out.println(
                    "Doctor not found."
            );

            return;
        }

        doctor.displaySchedule();
    }

    // ================= UTILISATION =================

    public void displayUtilisation() {

        System.out.println(
                "\n===== DOCTOR UTILISATION ====="
        );

        doctors.stream()
                .sorted(
                        Comparator.comparingDouble(
                                Doctor::getUtilisation
                        ).reversed()
                )
                .forEach(
                        d ->
                                System.out.printf(
                                        "%s (%s) -> %.2f%%%n",
                                        d.getName(),
                                        d.getSpeciality(),
                                        d.getUtilisation()
                                )
                );
    }

    // ================= BUSIEST HOURS =================

    public void displayBusiestHours() {

        System.out.println(
                "\n===== BUSIEST HOURS ====="
        );

        Map<String, Long> hourCount =
                appointments.stream()
                        .filter(a ->
                                a.getStatus()
                                        .equals("BOOKED"))
                        .collect(
                                Collectors.groupingBy(
                                        Appointment::getSlot,
                                        Collectors.counting()
                                )
                        );

        if (hourCount.isEmpty()) {

            System.out.println(
                    "No booked appointments."
            );

            return;
        }

        long maximum =
                Collections.max(
                        hourCount.values()
                );

        System.out.println(
                "Highest number of appointments: "
                        + maximum
        );

        hourCount.entrySet()
                .stream()
                .filter(e ->
                        e.getValue()
                                == maximum)
                .forEach(
                        e ->
                                System.out.println(
                                        e.getKey()
                                                + " -> "
                                                + e.getValue()
                                                + " appointments"
                                )
                );
    }

    // ================= SPECIALITY + RECURSION =================

    public Doctor findEarliestDoctor(
            String speciality,
            String requestedTime) {

        return findEarliestDoctorRecursive(
                speciality,
                requestedTime,
                0,
                null
        );
    }

    private Doctor findEarliestDoctorRecursive(
            String speciality,
            String requestedTime,
            int index,
            Doctor best) {

        if (index >= doctors.size()) {
            return best;
        }

        Doctor doctor =
                doctors.get(index);

        if (doctor.getSpeciality()
                .equalsIgnoreCase(speciality)) {

            Slot free =
                    doctor.findNextFreeSlot(
                            requestedTime
                    );

            if (free != null) {

                if (best == null) {

                    best = doctor;

                } else {

                    Slot bestSlot =
                            best.findNextFreeSlot(
                                    requestedTime
                            );

                    if (bestSlot != null &&
                            free.getTime()
                                    .compareTo(
                                            bestSlot.getTime()
                                    ) < 0) {

                        best = doctor;
                    }
                }
            }
        }

        return findEarliestDoctorRecursive(
                speciality,
                requestedTime,
                index + 1,
                best
        );
    }

    // ================= DISPLAY DOCTORS =================

    public void displayAllDoctors() {

        System.out.println(
                "\n===== DOCTORS ====="
        );

        if (doctors.isEmpty()) {

            System.out.println(
                    "No doctors available."
            );

            return;
        }

        doctors.forEach(
                d ->
                        System.out.println(
                                d
                        )
        );
    }

    // ================= DISPLAY PATIENTS =================

    public void displayAllPatients() {

        System.out.println(
                "\n===== PATIENTS ====="
        );

        if (patients.isEmpty()) {

            System.out.println(
                    "No patients available."
            );

            return;
        }

        patients.forEach(
                p ->
                        System.out.println(
                                p
                        )
        );
    }

    // ================= DISPLAY APPOINTMENTS =================

    public void displayAllAppointments() {

        System.out.println(
                "\n===== ALL APPOINTMENTS ====="
        );

        if (appointments.isEmpty()) {

            System.out.println(
                    "No appointments found."
            );

            return;
        }

        appointments.stream()
                .sorted(
                        Comparator.comparing(
                                Appointment::getSlot
                        )
                )
                .forEach(
                        System.out::println
                );
    }

    // ======================================================
    //                    FILE HANDLING
    // ======================================================

    public void saveData() {

        createDataFolder();

        saveDoctors();
        savePatients();
        saveAppointments();
        savePrescriptions();

        System.out.println(
                "All clinic data saved successfully."
        );
    }

    // ================= SAVE DOCTORS =================

    private void saveDoctors() {

        try (
                PrintWriter writer =
                        new PrintWriter(
                                new FileWriter(
                                        DATA_FOLDER
                                                + "/doctors.txt"
                                )
                        )
        ) {

            for (Doctor doctor : doctors) {

                writer.println(
                        doctor.getId()
                                + "|"
                                + doctor.getName()
                                + "|"
                                + doctor.getSpeciality()
                );

                for (Slot slot :
                        doctor.getSlots()) {

                    writer.println(
                            "SLOT|"
                                    + doctor.getId()
                                    + "|"
                                    + slot.getTime()
                                    + "|"
                                    + slot.isBooked()
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving doctors: "
                            + e.getMessage()
            );
        }
    }

    // ================= SAVE PATIENTS =================

    private void savePatients() {

        try (
                PrintWriter writer =
                        new PrintWriter(
                                new FileWriter(
                                        DATA_FOLDER
                                                + "/patients.txt"
                                )
                        )
        ) {

            for (Patient patient :
                    patients) {

                writer.println(
                        patient.getId()
                                + "|"
                                + patient.getName()
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving patients: "
                            + e.getMessage()
            );
        }
    }

    // ================= SAVE APPOINTMENTS =================

    private void saveAppointments() {

        try (
                PrintWriter writer =
                        new PrintWriter(
                                new FileWriter(
                                        DATA_FOLDER
                                                + "/appointments.txt"
                                )
                        )
        ) {

            for (Appointment appointment :
                    appointments) {

                writer.println(
                        appointment.getId()
                                + "|"
                                + appointment.getDoctor().getId()
                                + "|"
                                + appointment.getPatient().getId()
                                + "|"
                                + appointment.getSlot()
                                + "|"
                                + appointment.getStatus()
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving appointments: "
                            + e.getMessage()
            );
        }
    }

    // ================= SAVE PRESCRIPTIONS =================

    private void savePrescriptions() {

        try (
                PrintWriter writer =
                        new PrintWriter(
                                new FileWriter(
                                        DATA_FOLDER
                                                + "/prescriptions.txt"
                                )
                        )
        ) {

            for (Prescription prescription :
                    prescriptions) {

                writer.println(
                        prescription.id()
                                + "|"
                                + prescription.appointmentId()
                                + "|"
                                + prescription.patientId()
                );

                for (Medicine medicine :
                        prescription.medicines()) {

                    writer.println(
                            "MED|"
                                    + prescription.id()
                                    + "|"
                                    + medicine.name()
                                    + "|"
                                    + medicine.dosage()
                                    + "|"
                                    + medicine.durationDays()
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error saving prescriptions: "
                            + e.getMessage()
            );
        }
    }

    // ======================================================
    //                    LOAD DATA
    // ======================================================

    public void loadData() {

        File doctorFile =
                new File(
                        DATA_FOLDER
                                + "/doctors.txt"
                );

        if (!doctorFile.exists()) {

            createDefaultData();

            return;
        }

        loadDoctors();
        loadPatients();
        loadAppointments();
        loadPrescriptions();

        System.out.println(
                "Existing clinic data loaded."
        );
    }

    // ================= LOAD DOCTORS =================

    private void loadDoctors() {

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(
                                        DATA_FOLDER
                                                + "/doctors.txt"
                                )
                        )
        ) {

            String line;

            while ((line =
                    reader.readLine()) != null) {

                String[] parts =
                        line.split("\\|");

                if (parts.length == 3) {

                    addDoctor(
                            parts[0],
                            parts[1],
                            parts[2]
                    );
                }

                if (parts.length == 4 &&
                        parts[0].equals("SLOT")) {

                    Doctor doctor =
                            findDoctor(parts[1]);

                    if (doctor != null) {

                        Slot slot =
                                doctor.findSlot(
                                        parts[2]
                                );

                        if (slot != null &&
                                Boolean.parseBoolean(
                                        parts[3]
                                )) {

                            try {

                                slot.book();

                            } catch (
                                    SlotUnavailableException ignored) {
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading doctors."
            );
        }
    }

    // ================= LOAD PATIENTS =================

    private void loadPatients() {

        File file =
                new File(
                        DATA_FOLDER
                                + "/patients.txt"
                );

        if (!file.exists()) {
            return;
        }

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(file)
                        )
        ) {

            String line;

            while ((line =
                    reader.readLine()) != null) {

                String[] parts =
                        line.split("\\|");

                if (parts.length == 2) {

                    addPatient(
                            parts[0],
                            parts[1]
                    );
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading patients."
            );
        }
    }

    // ================= LOAD APPOINTMENTS =================

    private void loadAppointments() {

        File file =
                new File(
                        DATA_FOLDER
                                + "/appointments.txt"
                );

        if (!file.exists()) {
            return;
        }

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(file)
                        )
        ) {

            String line;

            while ((line =
                    reader.readLine()) != null) {

                String[] parts =
                        line.split("\\|");

                if (parts.length != 5) {
                    continue;
                }

                Doctor doctor =
                        findDoctor(parts[1]);

                Patient patient =
                        findPatient(parts[2]);

                if (doctor == null ||
                        patient == null) {

                    continue;
                }

                Slot slot =
                        doctor.findSlot(
                                parts[3]
                        );

                if (slot == null) {
                    continue;
                }

                Appointment appointment =
                        new Appointment(
                                parts[0],
                                doctor,
                                patient,
                                slot
                        );

                if (parts[4]
                        .equals("CANCELLED")) {

                    appointment.cancel();

                } else {

                    if (!slot.isBooked()) {

                        try {

                            slot.book();

                        } catch (
                                SlotUnavailableException ignored) {
                        }
                    }
                }

                appointments.add(
                        appointment
                );

                patient.addVisit(
                        appointment
                );

                updateAppointmentCounter(
                        parts[0]
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading appointments."
            );
        }
    }

    // ================= LOAD PRESCRIPTIONS =================

    private void loadPrescriptions() {

        File file =
                new File(
                        DATA_FOLDER
                                + "/prescriptions.txt"
                );

        if (!file.exists()) {
            return;
        }

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(file)
                        )
        ) {

            String line;

            Map<String, String[]> info =
                    new HashMap<>();

            Map<String, List<Medicine>> medicines =
                    new HashMap<>();

            while ((line =
                    reader.readLine()) != null) {

                String[] parts =
                        line.split("\\|");

                if (parts.length == 3) {

                    info.put(
                            parts[0],
                            new String[]{
                                    parts[1],
                                    parts[2]
                            }
                    );

                    medicines.put(
                            parts[0],
                            new ArrayList<>()
                    );
                }

                if (parts.length == 5 &&
                        parts[0].equals("MED")) {

                    try {

                        Medicine medicine =
                                new Medicine(
                                        parts[2],
                                        parts[3],
                                        Integer.parseInt(
                                                parts[4]
                                        )
                                );

                        if (medicines.containsKey(
                                parts[1]
                        )) {

                            medicines.get(
                                    parts[1]
                            ).add(medicine);
                        }

                    } catch (Exception ignored) {
                    }
                }
            }

            for (String id :
                    info.keySet()) {

                String[] values =
                        info.get(id);

                Prescription prescription =
                        new Prescription(
                                id,
                                values[0],
                                values[1],
                                medicines.get(id)
                        );

                prescriptions.add(
                        prescription
                );

                updatePrescriptionCounter(
                        id
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading prescriptions."
            );
        }
    }

    // ================= COUNTERS =================

    private void updateAppointmentCounter(
            String id) {

        try {

            int number =
                    Integer.parseInt(
                            id.substring(1)
                    );

            if (number >= appointmentCounter) {

                appointmentCounter =
                        number + 1;
            }

        } catch (Exception ignored) {
        }
    }

    private void updatePrescriptionCounter(
            String id) {

        try {

            int number =
                    Integer.parseInt(
                            id.substring(2)
                    );

            if (number >= prescriptionCounter) {

                prescriptionCounter =
                        number + 1;
            }

        } catch (Exception ignored) {
        }
    }

    // ================= DEFAULT DATA =================

    private void createDefaultData() {

        addDoctor(
                "D01",
                "Dr. Rao",
                "Cardiology"
        );

        addDoctor(
                "D02",
                "Dr. Sharma",
                "General"
        );

        addDoctor(
                "D03",
                "Dr. Priya",
                "Cardiology"
        );

        addPatient(
                "P017",
                "Rahul"
        );

        addPatient(
                "P018",
                "Ananya"
        );

        addPatient(
                "P019",
                "Arjun"
        );

        System.out.println(
                "Default doctors and patients created."
        );
    }
}


// ======================================================
//                       DOCTOR
// ======================================================

class Doctor {

    private final String id;
    private final String name;
    private final String speciality;

    private final List<Slot> slots;

    public Doctor(
            String id,
            String name,
            String speciality) {

        this.id = id;
        this.name = name;
        this.speciality = speciality;

        slots = new ArrayList<>();

        createDailySlots();
    }

    private void createDailySlots() {

        // Clinic timings: 09:00 to 16:30

        for (int hour = 9; hour <= 16; hour++) {

            slots.add(
                    new Slot(
                            String.format(
                                    "%02d:00",
                                    hour
                            )
                    )
            );

            slots.add(
                    new Slot(
                            String.format(
                                    "%02d:30",
                                    hour
                            )
                    )
            );
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public Slot findSlot(String time) {

        return slots.stream()
                .filter(s ->
                        s.getTime()
                                .equals(time))
                .findFirst()
                .orElse(null);
    }

    public Slot findNextFreeSlot(
            String requestedTime) {

        return slots.stream()
                .filter(s ->
                        !s.isBooked())
                .filter(s ->
                        s.getTime()
                                .compareTo(
                                        requestedTime
                                ) >= 0)
                .findFirst()
                .orElse(null);
    }

    public double getUtilisation() {

        long booked =
                slots.stream()
                        .filter(
                                Slot::isBooked
                        )
                        .count();

        return
                (booked * 100.0)
                        / slots.size();
    }

    public void displaySchedule() {

        System.out.println(
                "\n================================="
        );

        System.out.println(
                "Doctor: "
                        + name
        );

        System.out.println(
                "Speciality: "
                        + speciality
        );

        System.out.println(
                "================================="
        );

        for (Slot slot :
                slots) {

            System.out.println(
                    slot
            );
        }

        System.out.printf(
                "Utilisation: %.2f%%%n",
                getUtilisation()
        );
    }

    @Override
    public String toString() {

        return
                id
                        + " | "
                        + name
                        + " | "
                        + speciality;
    }
}


// ======================================================
//                       PATIENT
// ======================================================

class Patient {

    private final String id;
    private final String name;

    private final List<Appointment> visitHistory;

    public Patient(
            String id,
            String name) {

        this.id = id;
        this.name = name;

        visitHistory =
                new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Appointment> getVisitHistory() {
        return visitHistory;
    }

    public void addVisit(
            Appointment appointment) {

        visitHistory.add(
                appointment
        );
    }

    @Override
    public String toString() {

        return
                id
                        + " | "
                        + name;
    }
}


// ======================================================
//                       SLOT
// ======================================================

class Slot {

    private final String time;

    private boolean booked;

    public Slot(String time) {

        this.time = time;
        booked = false;
    }

    public String getTime() {
        return time;
    }

    public boolean isBooked() {
        return booked;
    }

    public void book()
            throws SlotUnavailableException {

        if (booked) {

            throw new SlotUnavailableException(
                    "Slot "
                            + time
                            + " is already booked."
            );
        }

        booked = true;
    }

    public void cancel() {

        booked = false;
    }

    @Override
    public String toString() {

        return
                time
                        + " -> "
                        + (booked
                        ? "BOOKED"
                        : "FREE");
    }
}


// ======================================================
//                    APPOINTMENT
// ======================================================

class Appointment {

    private final String id;

    private final Doctor doctor;

    private final Patient patient;

    private Slot slot;

    private String status;

    public Appointment(
            String id,
            Doctor doctor,
            Patient patient,
            Slot slot) {

        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.slot = slot;

        status = "BOOKED";
    }

    public String getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Slot getSlotObject() {
        return slot;
    }

    public String getSlot() {
        return slot.getTime();
    }

    public String getStatus() {
        return status;
    }

    public void reschedule(
            Slot newSlot)
            throws SlotUnavailableException {

        // Free old slot first
        slot.cancel();

        try {

            // Book new slot
            newSlot.book();

            slot = newSlot;

        } catch (SlotUnavailableException e) {

            // Restore old slot if booking fails
            slot.book();

            throw e;
        }
    }

    public void cancel() {

        slot.cancel();

        status = "CANCELLED";
    }

    @Override
    public String toString() {

        return
                "Appointment "
                        + id
                        + " | Doctor: "
                        + doctor.getName()
                        + " | Patient: "
                        + patient.getName()
                        + " | Slot: "
                        + slot.getTime()
                        + " | Status: "
                        + status;
    }
}


// ======================================================
//                    MEDICINE RECORD
// ======================================================

record Medicine(
        String name,
        String dosage,
        int durationDays
) {

    public Medicine {

        if (name == null ||
                name.isBlank()) {

            throw new IllegalArgumentException(
                    "Medicine name cannot be empty."
            );
        }

        if (dosage == null ||
                dosage.isBlank()) {

            throw new IllegalArgumentException(
                    "Dosage cannot be empty."
            );
        }

        if (durationDays <= 0) {

            throw new IllegalArgumentException(
                    "Duration must be greater than zero."
            );
        }
    }

    @Override
    public String toString() {

        return
                name
                        + " - "
                        + dosage
                        + " for "
                        + durationDays
                        + " days";
    }
}


// ======================================================
//                 PRESCRIPTION RECORD
// ======================================================

record Prescription(
        String id,
        String appointmentId,
        String patientId,
        List<Medicine> medicines
) {

    public Prescription {

        if (id == null ||
                id.isBlank()) {

            throw new IllegalArgumentException(
                    "Prescription ID is required."
            );
        }

        if (appointmentId == null ||
                appointmentId.isBlank()) {

            throw new IllegalArgumentException(
                    "Appointment ID is required."
            );
        }

        if (patientId == null ||
                patientId.isBlank()) {

            throw new IllegalArgumentException(
                    "Patient ID is required."
            );
        }

        // Immutable copy
        medicines =
                List.copyOf(medicines);
    }

    @Override
    public String toString() {

        return
                "Prescription "
                        + id
                        + " | Patient: "
                        + patientId
                        + " | Medicines: "
                        + medicines;
    }
}


// ======================================================
//               INTERACTION CHECKER
// ======================================================

interface InteractionChecker {

    void check(
            List<Medicine> medicines)
            throws InteractionWarning;
}


// ======================================================
//             BASIC INTERACTION CHECKER
// ======================================================

class BasicInteractionChecker
        implements InteractionChecker {

    private final Map<Set<String>, String>
            interactionTable;

    public BasicInteractionChecker() {

        interactionTable =
                new HashMap<>();

        addInteraction(
                "Aspirin",
                "Warfarin",
                "bleeding risk"
        );

        addInteraction(
                "Ibuprofen",
                "Warfarin",
                "increased bleeding risk"
        );

        addInteraction(
                "Aspirin",
                "Ibuprofen",
                "increased stomach bleeding risk"
        );

        addInteraction(
                "Simvastatin",
                "Clarithromycin",
                "increased risk of muscle toxicity"
        );
    }

    private void addInteraction(
            String drug1,
            String drug2,
            String reason) {

        Set<String> pair =
                new HashSet<>();

        pair.add(
                drug1.toLowerCase()
        );

        pair.add(
                drug2.toLowerCase()
        );

        interactionTable.put(
                pair,
                reason
        );
    }

    @Override
    public void check(
            List<Medicine> medicines)
            throws InteractionWarning {

        // Check every unordered pair

        for (int i = 0;
             i < medicines.size();
             i++) {

            for (int j = i + 1;
                 j < medicines.size();
                 j++) {

                String drug1 =
                        medicines.get(i)
                                .name()
                                .toLowerCase();

                String drug2 =
                        medicines.get(j)
                                .name()
                                .toLowerCase();

                Set<String> pair =
                        new HashSet<>();

                pair.add(drug1);
                pair.add(drug2);

                if (interactionTable
                        .containsKey(pair)) {

                    String reason =
                            interactionTable
                                    .get(pair);

                    throw new InteractionWarning(
                            medicines.get(i).name()
                                    + " + "
                                    + medicines.get(j).name()
                                    + " interaction ("
                                    + reason
                                    + ")."
                    );
                }
            }
        }
    }
}


// ======================================================
//                SLOT EXCEPTION
// ======================================================

class SlotUnavailableException
        extends Exception {

    public SlotUnavailableException(
            String message) {

        super(message);
    }
}


// ======================================================
//              INTERACTION WARNING
// ======================================================

class InteractionWarning
        extends Exception {

    public InteractionWarning(
            String message) {

        super(message);
    }
} 