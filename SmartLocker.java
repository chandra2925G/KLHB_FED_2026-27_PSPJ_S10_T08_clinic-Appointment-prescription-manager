public class SmartLocker {
    static int findFreeSlot (int[] occupied) {
        for (int i = 0; i < occupied.length; i++) {
            if (occupied[i] == 0) {
                return i;
            }
        }
        return -1;
     }
     public static void main(String[] args) {
        int [] occupied = {1, 1, 0, 1, 1,};
        int freeSlot = findFreeSlot(occupied);
        System.out.println("Free Slot:" + freeSlot);
     }
}
