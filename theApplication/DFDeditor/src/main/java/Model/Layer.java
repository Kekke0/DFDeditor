package Model;

import Model.VOs.VisualObject;
import javafx.beans.DefaultProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

@DefaultProperty("children")
public class Layer extends Pane{

    private Layer parentLayer_;
    private int Level;
    private final List<VisualObject> VOs=new ArrayList<>();
    private final List<Flow> Flows=new ArrayList<>();

    public Layer() {
        super();
    }

    public Layer(Node... nodes) {
        super(nodes);
    }

    public Layer getParentLayer_() {
        return parentLayer_;
    }

    public void setParentLayer_(Layer parentLayer_) {
        this.parentLayer_ = parentLayer_;
    }

    public void setLevel(int level) {
        this.Level = level;
    }

    public int getLevel() {
        return Level;
    }


    public void addVO(VisualObject vo){
        VOs.add(vo);
        this.getChildren().add(vo.getImageView());
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

    public void reDraw(){
        this.getChildren().removeAll();
        for (VisualObject VO: VOs) {
            this.getChildren().add(VO.getImageView());
        }
        for (Flow fl: Flows) {
            //TODO
        }
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
