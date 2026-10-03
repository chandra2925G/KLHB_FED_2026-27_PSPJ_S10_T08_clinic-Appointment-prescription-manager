import java.util.Scanner;
public class Donar1 { 
    public static void main(String [] args)
    {
     Scanner sc = new Scanner(System.in);
     System.out.println("Donar ID");
     int ID = sc.nextInt();
     System.out.println("Donar Name");
     String Name = sc.nextLine();
     System.out.println("Donar Age");
     int Age = sc.nextInt();
     System.out.println("Donar BloodGroup");
     String BloodGroup = sc.nextLine();
     System.out.println("Donar Phone number");
     String Phonenumber = sc.nextLine();
     
     System.out.println("Detials");
     System.out.println("Donar ID:" + ID);
     System.out.println("Donar Name:" + Name);
     System.out.println("Donar Age:" + Age);
     System.out.println("Donar BloodGroup:" + BloodGroup);
     System.out.println("Donar Phonenumber:" + Phonenumber);
     sc.close();
     
    }
}
