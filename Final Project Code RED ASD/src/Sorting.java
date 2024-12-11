import java.util.*;

public class Sorting {
    // Class to hold clue data with its order
    public static class ClueData {
        private String clueText;
        private int order;

        public ClueData(String clueText, int order) {
            this.clueText = clueText;
            this.order = order;
        }

        public String getClueText() {
            return clueText;
        }

        public int getOrder() {
            return order;
        }
    }

    // Improved bubble sort implementation
    public static List<ClueData> bubbleSort(List<ClueData> clues) {
        if (clues == null || clues.size() <= 1) {
            return clues;
        }

        int n = clues.size();
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (clues.get(j) != null && clues.get(j + 1) != null && 
                    clues.get(j).getOrder() > clues.get(j + 1).getOrder()) {
                    // Swap elements
                    ClueData temp = clues.get(j);
                    clues.set(j, clues.get(j + 1));
                    clues.set(j + 1, temp);
                    swapped = true;
                }
            }
            // If no swapping occurred, array is already sorted
            if (!swapped) {
                break;
            }
        }
        return clues;
    }

    // Method to print sorted clues
    public static void printSortedClues(List<ClueData> clues) {
        if (clues == null || clues.isEmpty()) {
            System.out.println("No clues to display.");
            return;
        }

        List<ClueData> sortedClues = bubbleSort(new ArrayList<>(clues));
        System.out.println("\n=== Chronological Order of Events ===");
        for (int i = 0; i < sortedClues.size(); i++) {
            ClueData clue = sortedClues.get(i);
            if (clue != null) {
                System.out.println((i + 1) + ". " + clue.getClueText());
            }
        }
    }

    // Helper method to create ClueData from Witness
    public static ClueData createClueData(Witness witness) {
        if (witness == null) {
            return null;
        }
        try {
            return new ClueData(witness.getStatement(), witness.getSort());  // Using getSort() instead of getOrder()
        } catch (Exception e) {
            System.out.println("Error creating clue data: " + e.getMessage());
            return null;
        }
    }

    // Helper method to validate clue order
    public static boolean isValidOrder(int order) {
        return order >= 1 && order <= 8; // We have 8 witnesses in the game
    }
}