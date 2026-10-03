import java.util.Scanner;
public class ParcelWeight {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter parcel weight:");
            double weight = Double.parseDouble(scanner.nextLine());
            System.out.println("Weight accepted:" + weight + " kg");
        } catch (NumberFormatException e) {
            System.out.println("Invalid weight." + "Please enter a valid number.");
        } finally {
            System.out.println("Weight checking completed.");
        }
        try {
            if (weight < 0) {
                throw new IllegalArgumentException("Weight cannot be negative.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Finalizing weight checking.");
        }
        try { 
            if (weight > 50) {
                throw new IllegalArgumentException("Weight exceeds the maximum limit of 50 kg.");
            }
        } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                System.out.println("Finalizing weight checking.");
            
        }
          scanner.close();
    } 
} 
        
