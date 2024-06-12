package map;

import java.awt.*;
import java.util.List;

/**
 * Cette classe représente une liaison entre deux équipements.
 */
public class Liaison {
    private Equipement equipment1;
    private String connector1;
    private Equipement equipment2;
    private String connector2;
    private List<Point> breakPoints;

    public Liaison(Equipement equipment1, String connector1, Equipement equipment2, String connector2, List<Point> breakPoints) {
        this.equipment1 = equipment1;
        this.connector1 = connector1;
        this.equipment2 = equipment2;
        this.connector2 = connector2;
        this.breakPoints = breakPoints;
    }

    public Equipement getEquipment1() {
        return equipment1;
    }

    public String getConnector1() {
        return connector1;
    }

    public Equipement getEquipment2() {
        return equipment2;
    }

    public String getConnector2() {
        return connector2;
    }

    public List<Point> getBreakPoints() {
        return breakPoints;
    }
}
