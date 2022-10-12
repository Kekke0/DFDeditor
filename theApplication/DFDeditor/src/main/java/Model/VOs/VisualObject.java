package Model.VOs;

import Model.Coordinate;

public class VisualObject {
    private String ID, Name;
    private Coordinate[] Corners= new Coordinate[4];

    public VisualObject(Coordinate[] corners) {
        Corners = corners;
    }

    public VisualObject(String ID, String name, Coordinate[] corners) {
        this.ID = ID;
        Name = name;
        Corners = corners;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public Coordinate[] getCorners() {
        return Corners;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCorners(Coordinate[] corners) {
        Corners = corners;
    }

    public Coordinate Distancing (Coordinate first, Coordinate second){
        return new Coordinate(first.getX()-second.getX(),
                first.getY()-second.getY());

    }


    public void ChangePosition(Coordinate maincorner) {
        Coordinate distance= Distancing(Corners[0],maincorner);
        for (int i = 0; i < 4; i++) {
            Corners[i].add(distance);
        }
    }


}
