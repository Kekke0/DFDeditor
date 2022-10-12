package Model;

public class Flow {
    private String Name;
    private String[] Connected= new String[2];
    private Coordinate Start, End;

    public Flow(Coordinate start, Coordinate end) {
        Start = start;
        End = end;
    }

    public Flow(Coordinate start, Coordinate end, String[] connected) {
        setConnected(connected);
        Start = start;
        End = end;
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
