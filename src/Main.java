import java.util.*;

public class Main {
    private static GraphMap graphMap;
    private static Map<String, Location> locations;
    private static GameState gameState;
    private static Scanner scanner;

    public static void main(String[] args) {
        initializeGame();
        playGame();
    }

    private static void initializeGame() {
        graphMap = new GraphMap();
        locations = new HashMap<>();
        gameState = new GameState();
        scanner = new Scanner(System.in);
        initializeGraph();
        initializeLocations();
        displayIntroduction();
    }
    private static void initializeGraph() {
        // Adding edges based on the relationships
        graphMap.addEdge("Sistem Informasi", "Dr. Angka");
        graphMap.addEdge("Sistem Informasi", "Kedokteran");
        graphMap.addEdge("Sistem Informasi", "Research Center");
        graphMap.addEdge("Sistem Informasi", "Perpustakaan");
        graphMap.addEdge("Kedokteran", "Teknik Kelautan");
        graphMap.addEdge("Kedokteran", "Teknik Geomatika");
        graphMap.addEdge("Teknik Geomatika", "Teknik Informatika");
        graphMap.addEdge("Teknik Kelautan", "Teknik Informatika");
        graphMap.addEdge("Dr. Angka", "Perpustakaan");
        graphMap.addEdge("Perpustakaan", "Research Center");
        graphMap.addEdge("Research Center", "Teknik Informatika");
    }

    private static void displayIntroduction() {
        System.out.println("=== CODE RED: Murder Mystery ===");
        System.out.println("A tragic incident has occurred at the Information Systems Department.");
        System.out.println("Joko, a brilliant student, has been found dead in the department's storage room.");
        System.out.println("As a detective, your task is to solve this mystery.");
        System.out.println("Investigate locations, interrogate witnesses, and find the killer.");
        System.out.println("===============================\n");
    }

    private static void playGame() {
        while (!gameState.isGameOver()) {
            displayCurrentLocation();
            displayMenu();
            processChoice();
        }
        scanner.close();
    }

    private static void displayCurrentLocation() {
        System.out.println("\nCurrent Location: " + gameState.getCurrentLocation());
        if (!gameState.hasVisitedLocation(gameState.getCurrentLocation())) {
            System.out.println("This is your first time visiting this location.");
        }
    }

    private static void displayMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Interrogate witness");
        System.out.println("2. Examine evidence");
        System.out.println("3. Review collected clues");
        System.out.println("4. Move to another location");
        System.out.println("5. Make an accusation");
        System.out.println("6. Exit game");
    }

    private static void processChoice() {
        int choice = getValidChoice(1, 6);
        switch (choice) {
            case 1:
                interrogateWitness();
                break;
            case 2:
                examineEvidence();
                break;
            case 3:
                reviewClues();
                break;
            case 4:
                moveLocation();
                break;
            case 5:
                makeAccusation();
                break;
            case 6:
                exitGame();
                break;
        }
    }

    private static int getValidChoice(int min, int max) {
        int choice;
        while (true) {
            System.out.print("Enter your choice (" + min + "-" + max + "): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    private static void interrogateWitness() {
        Location currentLocation = locations.get(gameState.getCurrentLocation());
        if (currentLocation.getWitness() != null) {
            Witness witness = currentLocation.getWitness();
            System.out.println("\n=== Interrogating " + witness.getName() + " ===");
            System.out.println("Character Description: " + witness.getCharacterDesc());
            System.out.println("Statement: " + witness.getStatement());
            gameState.addClue(witness.getStatement(), witness.getSort());  // Changed from getOrder() to getSort()
            gameState.visitLocation(gameState.getCurrentLocation());
        } else {
            System.out.println("There is no witness at this location.");
        }
    }

    private static void examineEvidence() {
        Location currentLocation = locations.get(gameState.getCurrentLocation());
        System.out.println("\n=== Examining Evidence ===");
        System.out.println("Found: " + currentLocation.getEvidence());
    }

    private static void reviewClues() {
        List<Sorting.ClueData> clues = gameState.getCollectedClues();
        if (clues.isEmpty()) {
            System.out.println("\nNo clues collected yet.");
            return;
        }
    
        // Sort and print the clues directly since they're already in ClueData format
        Sorting.printSortedClues(clues);
    }

    private static void moveLocation() {
        List<String> availableLocations = graphMap.getNeighbors(gameState.getCurrentLocation());
        System.out.println("\n=== Available Locations ===");
        for (int i = 0; i < availableLocations.size(); i++) {
            System.out.println((i + 1) + ". " + availableLocations.get(i));
        }

        System.out.print("\nChoose a location (1-" + availableLocations.size() + "): ");
        int choice = getValidChoice(1, availableLocations.size());
        gameState.setCurrentLocation(availableLocations.get(choice - 1));
    }

    private static void makeAccusation() {
        System.out.println("\n=== Make an Accusation ===");
        System.out.println("WARNING: Making an incorrect accusation will end the game!");
        System.out.println("\nSuspects:");
        
        Map<Integer, String> suspects = new LinkedHashMap<>();
        int index = 1;
        for (Location location : locations.values()) {
            Witness witness = location.getWitness();
            if (witness != null && !witness.getName().equals("Bu Ita") && !witness.getName().equals("Pak Budi")) {
                System.out.println(index + ". " + witness.getName() + " - " + witness.getCharacterDesc());
                suspects.put(index++, witness.getName());
            }
        }
    
        System.out.print("\nEnter the number of your suspect (or 0 to cancel): ");
        int choice = getValidChoice(0, suspects.size());
        
        if (choice == 0) {
            System.out.println("Accusation cancelled.");
            return;
        }
    
        String accusedPerson = suspects.get(choice);
        System.out.println("\nYou are accusing " + accusedPerson + ".");
        System.out.print("Are you sure? This cannot be undone (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y")) {
            if (accusedPerson.equals("Pak Santoso")) {
                System.out.println("\n=== Case Solved! ===");
                System.out.println("Congratulations! You've identified the killer!");
                System.out.println("\nCase Summary:");
                System.out.println("Pak Santoso killed Joko to protect the secret of the forbidden research.");
                System.out.println("\nThe evidence trail:");
                System.out.println("1. Joko discovered something dangerous in the research");
                System.out.println("2. The missing documents contained incriminating evidence");
                System.out.println("3. The argument caught on CCTV was between Joko and Pak Santoso");
                System.out.println("4. Pak Santoso's refusal to comment shows consciousness of guilt");
                System.out.println("5. The note 'Only I am the winner' refers to maintaining control over the research");
                gameState.setGameOver(true);
            } else {
                System.out.println("\nYour accusation is incorrect!");
                System.out.println("The investigation continues, but be careful - the real killer is still out there.");
            }
        }
    }

    private static void exitGame() {
        System.out.println("\nThank you for playing Code Red!");
        gameState.setGameOver(true);
    }
    private static void initializeLocations() {
        locations = new HashMap<>();
        
        // Initialize all locations with their witnesses and evidence
        locations.put("Kedokteran", new Location("Kedokteran", 
            new Witness("Beta","Teman penelitian Joko", "Sarung tangan Latex",1,  "Semalam dia bilang ingin mengambil sesuatu di departemennya"),
            "Sarung tangan Latex yang mencurigakan"));
    
        locations.put("Sistem Informasi", new Location("Sistem Informasi",
            new Witness("Bu Ita","Petugas Kebersihan Kampus",  "Tali dan bercak darah", 2, "Malam itu saya melihat perempuan dari arah yang sama"),
            "Tali dan bercak darah, CCTV yang pecah"));
    
        locations.put("Dr. Angka", new Location("Dr. Angka",
            new Witness("Pak Budi","Satpam kampus yang mengawasi cctv", "Rekaman CCTV", 3, "Melalui CCTV, saya melihat ada dua orang sedang bertengkar"),
            "Rekaman CCTV yang menunjukkan pertengkaran"));
        locations.put("Teknik Kelautan", new Location("Teknik Kelautan",
                new Witness("Leo", "Kakak tingkat yang sangat membenci Joko","Pipa air", 4, "Aku tidak menyangka dia pergi secepat itu, padahal aku ingin mengungkapkannya"),
                "Pipa air"));
        locations.put("Teknik Geomatika", new Location("Teknik Geomatika",
                new Witness("Ari", "Kakak tingkat sekaligus teman penelitian Joko","Saluran Air Bocor", 5, "Dia sepertinya khawatir karena ada dokumen yang hilang"),
                "Saluran Air Bocor"));
        locations.put("Teknik Informatika", new Location("Saka",
                new Witness("Saka","Teman Joko yang pintar", "Ketapel", 6, "Dia orang baik dan kami sering belajar bersama"),
                "Ketapel"));
        locations.put("Perpustakaan", new Location("Fia",
                new Witness("Fia", "Sahabat baik Joko yang sudah berteman selama 10 tahun","Sobekan buku", 7, "Dia dalam bahaya dan seharusnya aku tidak meninggalkannya"),
                "Sobekan buku"));
        locations.put("Research Center", new Location("Pak Santoso",
                new Witness("Pak Santoso", "Dosen pengampu penelitian","Dokumen Penelitian Terlarang", 8, "Saya tidak ingin berkomentar apapun"),
                "Dokumen Penelitian Terlarang"));

    }
    private static void sortClues(List<String> clues) {
        int n = clues.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Assuming clues are strings with time information
                if (clues.get(j).compareTo(clues.get(j + 1)) > 0) {
                    // Swap
                    String temp = clues.get(j);
                    clues.set(j, clues.get(j + 1));
                    clues.set(j + 1, temp);
                }
            }
        }
    }
    private static void showClues(List<String> clues) {
        System.out.println("\nClues obtained:");
        for (String clue : clues) {
            System.out.println(clue);
        }
    }

}