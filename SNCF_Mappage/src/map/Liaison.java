package map;

import java.awt.*;

public class Liaison {
    private DrawingArea.Equipment equipment1;
    private String connector1;
    private DrawingArea.Equipment equipment2;
    private String connector2;

    public Liaison(DrawingArea.Equipment equipment1, String connector1, DrawingArea.Equipment equipment2, String connector2) {
        this.equipment1 = equipment1;
        this.connector1 = connector1;
        this.equipment2 = equipment2;
        this.connector2 = connector2;
    }

    public DrawingArea.Equipment getEquipment1() {
        return equipment1;
    }

    public String getConnector1() {
        return connector1;
    }

    public DrawingArea.Equipment getEquipment2() {
        return equipment2;
    }

    public String getConnector2() {
        return connector2;
    }
}
