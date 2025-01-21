package Model;

import Model.VOs.Flow;
import Model.VOs.SolidObject;

public class Connection {
    Flow Connecting;
    SolidObject Connected;
    boolean ConnectingSide;
    int[] ConnectedSide = new int[2];
    ConnectionPoint connectionPoint;

    public Connection(Flow connecting, SolidObject connected, boolean connectingSide, int[] connectedSide, ConnectionPoint connectionPoint) {
        Connecting = connecting;
        Connected = connected;
        ConnectingSide = connectingSide;
        ConnectedSide = connectedSide;
        this.connectionPoint = connectionPoint;
        Connecting.addConnection(this,connectingSide);
        Connected.AddConnection(this);
    }

    public void RemoveFromBoth(){
        Connected.RemoveFromConnection(this);
        Connecting.RemoveFromConnection(ConnectingSide?0:1);
    }

    public Flow getConnecting() {
        return Connecting;
    }

    public SolidObject getConnected() {return Connected;}

    public void AlignToConnected(){

        Coordinate midleCorner = Coordinate.getMiddleOf(Connected.getCorners()[ConnectedSide[0]],Connected.getCorners()[ConnectedSide[1]]);
        Coordinate connection = null;
        switch (connectionPoint){
            case A ->{
                connection = new Coordinate(Connected.getCorners()[ConnectedSide[0]]);
            }
            case B -> {
                connection = new Coordinate(Coordinate.getMiddleOf(Connected.getCorners()[ConnectedSide[0]],midleCorner));
            }
            case C -> {
                connection = new Coordinate(midleCorner);
            }
        }

        if (ConnectingSide){
            Connecting.setStart(connection);
        } else {
            Connecting.setEnd(connection);
        }

        Connecting.resizeImageView();
    }

    public static Connection createConnectionTo(Flow fl,int side, SolidObject so, Coordinate coordinate){
        Coordinate[] corners =so.getCorners();
        double minDistance = Double.MAX_VALUE;
        int[] chosenSide = new int[2];
        ConnectionPoint chosenPoint = ConnectionPoint.B;


        for (int i = 0; i < corners.length; i++) {
            double distance = Coordinate.distanceBetween(coordinate,corners[i]);

            if (distance>=minDistance){
                continue;
            }

            minDistance = distance;
            chosenSide[0] = i;
            double precentdistance =1;
            for (int j = 0; j < corners.length; j++) {
                if (i==j){
                    continue;
                }
                double actualPrecent = Coordinate.distanceBetween(corners[j],coordinate)/Coordinate.distanceBetween(corners[j],corners[i]);
                if (actualPrecent<precentdistance){
                    precentdistance = actualPrecent;
                    chosenSide[1]=j;
                }
            }
        }

        Coordinate midlePoint = Coordinate.getMiddleOf(corners[chosenSide[0]],corners[chosenSide[1]]);
        Coordinate quarterPoint = Coordinate.getMiddleOf(corners[chosenSide[0]],midlePoint);

        double quarterdis = Coordinate.distanceBetween(coordinate,quarterPoint);
        if (minDistance<quarterdis){
            chosenPoint = ConnectionPoint.A;
        }

        if (Coordinate.distanceBetween(coordinate,midlePoint)<quarterdis){
            chosenPoint = ConnectionPoint.C;
        }

        return new Connection(fl,so,side==0,chosenSide,chosenPoint);
    }
}
