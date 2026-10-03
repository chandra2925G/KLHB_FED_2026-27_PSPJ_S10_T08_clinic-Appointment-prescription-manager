class SmartLocker1 {
    public static void main(String[] args) {

        double totalCapacity = 50.0;
        double usedCapacity = 35.0;
        double remainingCapacity;

        int correctPin = 1234;
        int enteredPin = 1234;

        String lockerStatus = "Available";
        int accessCount = 2;

        // 1. Arithmetic operator
        remainingCapacity = totalCapacity - usedCapacity;

        // 2. Relational operator
        boolean hasSpace = remainingCapacity > 0;

        // 3. Logical operator
        boolean accessAllowed = lockerStatus.equals("Available")
                                && enteredPin == correctPin;

        // 4. Increment operator
        accessCount++;

        // 5. Assignment operator
        if (accessAllowed) {
            lockerStatus = "Occupied";
        }

        System.out.println("Remaining Capacity: " + remainingCapacity);
        System.out.println("Has Space: " + hasSpace);
        System.out.println("Access Allowed: " + accessAllowed);
        System.out.println("Access Count: " + accessCount);
        System.out.println("Locker Status: " + lockerStatus);
    }
}