import java.util.*;
public class GameState {
    private List<Sorting.ClueData> collectedClues;
    private Set<String> visitedLocations;
    private String currentLocation;
    private boolean gameOver;

    public GameState() {
        this.collectedClues = new ArrayList<>();
        this.visitedLocations = new HashSet<>();
        this.currentLocation = "Sistem Informasi";
        this.gameOver = false;
    }

    public void addClue(String clueText, int order) {
        collectedClues.add(new Sorting.ClueData(clueText, order));
    }

    public List<Sorting.ClueData> getCollectedClues() {
        return collectedClues;
    }

    public void visitLocation(String location) {
        visitedLocations.add(location);
    }

    public boolean hasVisitedLocation(String location) {
        return visitedLocations.contains(location);
    }


    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String location) {
        this.currentLocation = location;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}