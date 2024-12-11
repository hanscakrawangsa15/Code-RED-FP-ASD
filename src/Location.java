import java.util.*;
public class Location {
    private String name;
    private Witness witness;
    private String evidence;

    public Location(String name, Witness witness, String evidence) {
        this.name = name;
        this.witness = witness;
        this.evidence = evidence;
    }

    public String getName() {
        return name;
    }

    public Witness getWitness() {
        return witness;
    }

    public String getEvidence() {
        return evidence;
    }
}