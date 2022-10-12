package Model;

import Model.VOs.VisualObject;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private int Level;
    private List<VisualObject> VOs=new ArrayList<>();
    private List<Flow> Flows=new ArrayList<>();

    public Layer(int level) {
        Level = level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getLevel() {
        return Level;
    }


    public void addVO(VisualObject vo){
        VOs.add(vo);
    }

    public void removeVO(VisualObject rvo){
        int i = 0;
        while (i<VOs.size()&& VOs.get(i)!=rvo){
            i++;
        }
        if (i < VOs.size()) VOs.remove(i);
    }

    public void addFlow(Flow flow){
        Flows.add(flow);
    }

    public void removeFlow(Flow rf){
        int i=0;
        while (i<Flows.size()&&Flows.get(i)!=rf){
            i++;
        }
        if (i<Flows.size()) Flows.remove(i);
    }

}
