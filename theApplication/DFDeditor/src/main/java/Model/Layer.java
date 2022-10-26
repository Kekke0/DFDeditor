package Model;

import Model.VOs.VisualObject;
import javafx.application.Preloader;
import javafx.beans.property.SimpleObjectProperty;

import java.net.Proxy;
import java.security.SignedObject;
import java.util.ArrayList;
import java.util.List;

public class Layer {
    private int Level;
    private final List<VisualObject> VOs=new ArrayList<>();
    private final List<Flow> Flows=new ArrayList<>();

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

    public void consolShow(){
        for (VisualObject VO: VOs) {
            for (int i = 0; i < Level; i++) {
                System.out.print("  ");
            }
            System.out.print(VO.getID()+"->"+ VO.getName());
            VO.showConsol();
            System.out.print("\n");
        }
        for (Flow fl: Flows) {
            for (int i = 0; i < Level; i++) {
                System.out.print("  ");
            }
            System.out.print(fl.getID()+" "+fl.getConnected()[1]+"-->"+fl.getConnected()[2]);
            System.out.print("\n");
        }
    }

}
