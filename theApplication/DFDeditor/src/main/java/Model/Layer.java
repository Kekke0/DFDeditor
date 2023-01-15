package Model;

import Model.VOs.VisualObject;
import javafx.beans.DefaultProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

@DefaultProperty("children")
public class Layer extends Pane{

    private Layer parentLayer_;
    private int Level;
    private Rectangle cornersGui_[];
    private final List<VisualObject> VOs=new ArrayList<>();
    private final List<Flow> Flows=new ArrayList<>();
    private VisualObject seleccted_;

    public Layer() {
        super();
        initailzeGui();
    }

    public Layer(Node... nodes) {
        super(nodes);
        initailzeGui();
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

    public VisualObject getSeleccted() {
        return seleccted_;
    }

    public void setSeleccted(VisualObject seleccted) {
        if(seleccted==null){
            for (int i = 0; i < 4; i++) {
                this.getChildren().remove(cornersGui_[i]);
            }
            this.seleccted_=seleccted;
            return;
        }

        if (seleccted_==null) {
            this.getChildren().add(cornersGui_[0]);
            this.getChildren().add(cornersGui_[1]);
            this.getChildren().add(cornersGui_[2]);
            this.getChildren().add(cornersGui_[3]);
        }
        this.seleccted_=seleccted;
        AlignGuiToCorners();
    }

    public int IsinCornerGui(Double x,Double y){
        for (int i = 0; i < 4; i++) {
            if(cornersGui_[i].getX()<x && cornersGui_[i].getY()<y &&
                    cornersGui_[i].getX()+6 >x && cornersGui_[i].getY()+6>y) {
                return i;
            }
        }
        return -1;
    }

    private void AlignGuiToCorners() {
        cornersGui_[0].setX(seleccted_.getCorners()[0].getX()-5);
        cornersGui_[0].setY(seleccted_.getCorners()[0].getY()-5);
        cornersGui_[1].setX(seleccted_.getCorners()[1].getX()+5);
        cornersGui_[1].setY(seleccted_.getCorners()[1].getY()-5);
        cornersGui_[2].setX(seleccted_.getCorners()[2].getX()-5);
        cornersGui_[2].setY(seleccted_.getCorners()[2].getY()+5);
        cornersGui_[3].setX(seleccted_.getCorners()[3].getX()+5);
        cornersGui_[3].setY(seleccted_.getCorners()[3].getY()+5);

        cornersGui_[0].setFill(Color.BLUE);
    }

    public void addVO(VisualObject vo){
        VOs.add(vo);
        this.getChildren().add(vo.getImageView());
    }

    public void removeVO(VisualObject rvo){
        this.getChildren().remove(rvo.getImageView());
        VOs.remove(rvo);
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

    public void Select(double x, double y) {
        if(seleccted_!=null &&seleccted_.isInside(x,y)) return;
        for (VisualObject VO: VOs) {
            if (VO.isInside(x,y)){
                setSeleccted(VO);
                return;
            }
        }
        setSeleccted(null);
    }

    private void initailzeGui() {
        cornersGui_=new Rectangle[4];
        for (int i = 0; i < 4; i++) {
            cornersGui_[i]= new Rectangle();
            cornersGui_[i].setHeight(6);
            cornersGui_[i].setWidth(6);
            cornersGui_[i].setFill(Color.WHITE);
            cornersGui_[i].setStroke(Color.BLACK);
            cornersGui_[i].setStrokeWidth(1);
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

    public void ChangeSelectedPosition(Coordinate moveto){
        this.getSeleccted().ChangePosition(moveto);
        AlignGuiToCorners();
    }

    public void ResizeSelected(int corner, Double x, Double y){
        getSeleccted().ResizeCorners(corner,x, y);
        AlignGuiToCorners();
    }

    public void DeleteSelected() {
        if (seleccted_!=null){
            removeVO(seleccted_);
            removeVO(seleccted_);
            this.getChildren().remove(seleccted_.getImageView());
            this.setSeleccted(null);
        }
    }
}
