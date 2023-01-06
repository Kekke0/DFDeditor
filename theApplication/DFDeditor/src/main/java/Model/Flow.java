package Model;

public class Flow {

    private String ID;
    private String Name;
    private String[] Connected= new String[2];
    private Coordinate Start;
    private Coordinate End;
    private Boolean onesieded= false;

    public Flow(String ID, Coordinate start, Coordinate end) {
        this.ID = ID;
        Start = start;
        End = end;
    }

    public Flow(String ID, String[] connected, Coordinate start, Coordinate end) {
        this.ID = ID;
        Connected = connected;
        Start = start;
        End = end;
    }

    public String getID() {
        return ID;
    }

    public Boolean isOnesieded() {
        return onesieded;
    }

    public void setOnesieded(Boolean onesieded) {
        this.onesieded = onesieded;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String[] getConnected() {
        return Connected;
    }

    public void setConnected(String[] connected) {
        Connected[0] = connected[0];
        Connected[1] = connected[1];
    }

    public Coordinate getStart() {
        return Start;
    }

    public void setStart(Coordinate start) {
        Start = start;
    }

    public Coordinate getEnd() {
        return End;
    }

    public void setEnd(Coordinate end) {
        End = end;
    }

    public void addConnection(String connection, boolean side){
        if (side){
            Connected[0]=connection;
            return;
        }
        Connected[1]=connection;
    }
}
