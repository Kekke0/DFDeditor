package Model.VOs;

import Model.Coordinate;

public class DataBase extends MultipliableObjects{
    public DataBase(Coordinate[] corners) {
        super(corners);
    }

    public DataBase(Coordinate[] corners, boolean multiplied) {
        super(corners, multiplied);
    }
}
