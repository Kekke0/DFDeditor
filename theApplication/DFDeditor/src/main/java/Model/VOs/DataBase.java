package Model.VOs;

import Model.Coordinate;

public class DataBase extends MultipliableObjects{

    public DataBase(String ID, Coordinate[] corners) {
        super(ID, corners);
    }

    public DataBase(String ID, Coordinate[] corners, boolean multiplied) {
        super(ID, corners, multiplied);
    }
}
