import java.util.*;
public class Witness {
    private String name;
    private String characterDesc;
    private String evidence;
    private String statement;
    private int sort;

    public Witness(String name, String characterDesc, String evidence, int sort, String statement) {
        this.name = name;
        this.characterDesc = characterDesc;
        this.evidence = evidence;
        this.statement = statement;
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public String getCharacterDesc() {
        return characterDesc;
    }

    public String getEvidence() {
        return evidence;
    }

    public String getStatement() {
        return statement;
    }

    public int getSort() {
        return sort;
    }
}