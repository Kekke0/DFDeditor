package Model;

import Model.VOs.Flow;
import Model.VOs.VisualObject;
import javafx.beans.DefaultProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

@DefaultProperty("children")
public class Layer extends Pane{

    private Layer parentLayer_;
    private int Level;
    private Rectangle cornersGui_[];
    private final List<VisualObject> VOs=new ArrayList<>();
    private final List<Flow> Flows=new ArrayList<>();
    private VisualObject selected_;

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

    public VisualObject getSelected() {
        return selected_;
    }

    public void setSelected(VisualObject seleccted) {
        if(seleccted==null){
            for (int i = 0; i < 4; i++) {
                this.getChildren().remove(cornersGui_[i]);
            }
            this.selected_ =seleccted;
            return;
        }

        if (selected_ ==null) {
            this.getChildren().add(cornersGui_[0]);
            this.getChildren().add(cornersGui_[1]);
            this.getChildren().add(cornersGui_[2]);
            this.getChildren().add(cornersGui_[3]);
        }
        this.selected_ =seleccted;
        AlignGuiToCorners();
    }

    public void setSelected(Flow seleccted) {
        if(seleccted==null){
            for (int i = 0; i < 4; i++) {
                this.getChildren().remove(cornersGui_[i]);
            }
            this.selected_ =seleccted;
            return;
        }

        if (selected_ ==null) {
            this.getChildren().add(cornersGui_[0]);
            this.getChildren().add(cornersGui_[1]);
            this.getChildren().add(cornersGui_[2]);
            this.getChildren().add(cornersGui_[3]);
        }
        this.selected_ =seleccted;
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
        if (selected_.getTypeString().equals("FL")){
            cornersGui_[0].setX(selected_.getCorners()[0].getX()-5);
            cornersGui_[0].setY(selected_.getCorners()[0].getY()-5);
            cornersGui_[1].setX(selected_.getCorners()[1].getX()+5);
            cornersGui_[1].setY(selected_.getCorners()[1].getY()-5);
            cornersGui_[2].setVisible(false);
            cornersGui_[3].setVisible(false);
            return;
        }
        cornersGui_[2].setVisible(true);
        cornersGui_[3].setVisible(true);
        cornersGui_[0].setX(selected_.getCorners()[0].getX()-5);
        cornersGui_[0].setY(selected_.getCorners()[0].getY()-5);
        cornersGui_[1].setX(selected_.getCorners()[1].getX()+5);
        cornersGui_[1].setY(selected_.getCorners()[1].getY()-5);
        cornersGui_[2].setX(selected_.getCorners()[2].getX()-5);
        cornersGui_[2].setY(selected_.getCorners()[2].getY()+5);
        cornersGui_[3].setX(selected_.getCorners()[3].getX()+5);
        cornersGui_[3].setY(selected_.getCorners()[3].getY()+5);
    }

    private void addTexts(VisualObject vo){
        for (Text votext : vo.getTexts()) {
            this.getChildren().add(votext);
        }
    }

    private void removeTexts(VisualObject vo){
        for (Text votext : vo.getTexts()) {
            this.getChildren().remove(votext);
        }
    }

    public void addVO(VisualObject vo){
        VOs.add(vo);
        this.getChildren().add(vo.getImageView());
        this.addTexts(vo);
    }

    public void removeVO(VisualObject rvo){
        this.getChildren().remove(rvo.getImageView());
        this.removeTexts(rvo);
        VOs.remove(rvo);
    }

    public void addFlow(Flow flow){
        Flows.add(flow);
        for (Line line: flow.getimage()) {
            this.getChildren().add(line);
        }
        this.addTexts(flow);
    }

    public void removeFlow(Flow rf){
        for (Line line: rf.getimage()) {
            this.getChildren().remove(line);
        }
        this.removeTexts(rf);
        Flows.remove(rf);
    }

    public void Select(double x, double y) {
        if(selected_ !=null && selected_.isInside(x,y)) return;
        for (Flow FL: Flows) {
            if (FL.isInside(x,y)){
                setSelected(FL);
                return;
            }
        }
        for (VisualObject VO: VOs) {
            if (VO.isInside(x,y)){
                setSelected(VO);
                return;
            }
        }
        setSelected(null);
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
        this.getSelected().ChangePosition(moveto);
        AlignGuiToCorners();
    }

    public void ResizeSelected(int corner, Double x, Double y){
        getSelected().ResizeCorners(corner,x, y);
        AlignGuiToCorners();
    }

    public void DeleteSelected() {
        if (selected_ !=null){
            if(selected_.getTypeString().equals("FL")){
                removeFlow((Flow) selected_);
            } else {
                removeVO(selected_);
            }
            this.setSelected(null);
        }
    }
}
