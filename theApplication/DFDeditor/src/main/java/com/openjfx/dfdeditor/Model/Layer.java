package com.openjfx.dfdeditor.Model;

import com.openjfx.dfdeditor.Model.VOs.*;
import javafx.beans.DefaultProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

@DefaultProperty("children")
public class Layer extends Pane{

    private Layer parentLayer_;
    private VisualObject selected_;
    private Tool ActiveTool = Tool.MOUSE;
    private final List<SolidObject> VOs=new ArrayList<>();
    private final List<Flow> Flows=new ArrayList<>();
    private final List<Warning> Warnings=new ArrayList<>();

    private int Level;
    private Rectangle[] cornersGui_;
    private Line[] highlighters_ = null;
    private Button LevelDown_;
    private Button BackUp_;

    public Layer() {
        super();
        initailzeGui();
    }

    public Layer(Node... nodes) {
        super(nodes);
        initailzeGui();
    }

    public List<SolidObject> getVOs() {
        return VOs;
    }

    public List<Flow> getFlows() {
        return Flows;
    }

    public List<Warning> getWarnings() {return Warnings;}

    public Layer getParentLayer() {
        return parentLayer_;
    }

    public void setParentLayer(Layer parentLayer_) {
        this.parentLayer_ = parentLayer_;
    }

    public Button getLevelDown() {
        return LevelDown_;
    }

    public void setLevelDown(Button levelDown_) {
        LevelDown_ = levelDown_;
    }

    public VisualObject getSelected() {
        return selected_;
    }

    public void setSelected(VisualObject selected) {
        if(selected == null){
            for (int i = 0; i < 4; i++) {
                this.getChildren().remove(cornersGui_[i]);
            }
            this.selected_ =selected;
            LevelDown_.setVisible(false);
            return;
        }

        if (selected_ == null) {
            this.getChildren().add(cornersGui_[0]);
            this.getChildren().add(cornersGui_[1]);
            this.getChildren().add(cornersGui_[2]);
            this.getChildren().add(cornersGui_[3]);
        }
        this.selected_ = selected;
        LevelDown_.setVisible(selected.isDissociable());
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

    public void AlignGuiToCorners() {
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

        LevelDown_.setLayoutX(selected_.getCorners()[3].getX()-LevelDown_.getPrefWidth()-5);
        LevelDown_.setLayoutY(selected_.getCorners()[3].getY()-LevelDown_.getPrefHeight()-10);
        this.getChildren().removeAll(cornersGui_);
        this.getChildren().addAll(cornersGui_);
    }

    private void addTexts(VisualObject vo){
        addNodesToChildren(vo.getTexts());
    }

    private void removeTexts(VisualObject vo){
        removeNodesFromChildren(vo.getTexts());
    }

    public void addVO(SolidObject vo){
        VOs.add(vo);
        this.resetChildren();
    }

    public void addVO(SolidObject vo, int place){
        VOs.add(place,vo);
        this.resetChildren();
    }

    public void removeAll(){
        for (VisualObject vo: getVOs()) {
            removeNodesFromChildren(vo.getimage());
            this.removeTexts(vo);
        }
        VOs.clear();
        for (Flow fl: getFlows()) {
            removeNodesFromChildren(fl.getimage());
            this.removeTexts(fl);
        }
        Flows.clear();
    }

    public void removeVO(VisualObject rvo){
        removeNodesFromChildren(rvo.getimage());
        this.removeTexts(rvo);
        VOs.remove(rvo);
    }

    public void addFlow(Flow flow){
        Flows.add(flow);
        this.resetChildren();
    }

    public void removeFlow(Flow rf){
        this.removeNodesFromChildren(rf.getimage());
        this.removeTexts(rf);
        Flows.remove(rf);
    }

    public void addWarning(Warning warning){
        Warnings.add(warning);
        this.resetChildren();
    }

    public void removeWarning(Warning warning){
        this.removeNodesFromChildren(warning.getimage());
        Warnings.remove(warning);
    }

    public void resetChildren() {
        for (SolidObject vo: VOs) {
            this.getChildren().removeAll(vo.getimage());
            this.removeTexts(vo);
            this.getChildren().addAll(vo.getimage());
            this.addTexts(vo);
        }
        for (Flow fl: Flows) {
            this.getChildren().removeAll(fl.getimage());
            this.removeTexts(fl);
            this.getChildren().addAll(fl.getimage());
            this.addTexts(fl);
        }
        for (Warning warning: Warnings) {
            this.getChildren().removeAll(warning.getimage());
            this.getChildren().addAll(warning.getimage());
        }
        if (selected_ != null) {
            this.getChildren().removeAll(cornersGui_);
            this.getChildren().addAll(cornersGui_);
        }
    }

    public void addNodesToChildren(Node[] node){
        this.getChildren().addAll(node);
    }

    public void removeNodesFromChildren(Node[] node){
        this.getChildren().removeAll(node);
    }

    public void Select(double x, double y) {
        // if(selected_ !=null && selected_.isInside(x,y)) return;
        for(Warning warning: Warnings){
            if (warning.isInside(x,y)){
                warning.showDialoge();
                return;
            }
        }

        for (Flow FL: Flows) {
            if (FL.isInside(x,y)){
                setSelected(FL);
                return;
            }
        }

        List<SolidObject> reversed = new ArrayList<>();
        for (int i = VOs.size()-1; i >= 0 ; i--) {
            reversed.add(VOs.get(i));
        }
        for (SolidObject VO: reversed) {
            if (VO.isInside(x,y)){
                setSelected(VO);
                VO.ReAdd();
                this.resetChildren();
                return;
            }
        }
        setSelected(null);
    }

    public SolidObject IsInsideOfConnectableObject(double x, double y) {

        for (SolidObject VO: VOs) {
            if (VO.isInside(x,y) &&  !VO.getTypeString().equals("OP")){
                return VO;
            }
        }
        return null;
    }

    public OpenProcess IsInsideOfOpenProcess(Coordinate coordinate) {
        return this.IsInsideOfOpenProcess(coordinate.getX(), coordinate.getY());
    }

    public OpenProcess IsInsideOfOpenProcess(double x, double y) {

        for (SolidObject VO: VOs) {
            if (VO.equals(getSelected())){
                continue;
            }
            if(VO.isInside(x,y) && VO.getTypeString().equals("OP")){
                OpenProcess op =(OpenProcess) VO;
                if(Coordinate.InBound(new Coordinate(x,y),op.getInsideCorners()[0], op.getInsideCorners()[3])){
                    return op;
                }
            }
        }
        return null;
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

    public boolean isEmpty() {
        return getVOs().isEmpty() && getFlows().isEmpty();
    }

    public void setActiveTool(Tool activeTool) {
        ActiveTool = activeTool;
    }

    public int Check() {
        int created = 0;
        for (Warning warning: Warnings) {
            removeNodesFromChildren(warning.getimage());
        }
        this.getWarnings().clear();
        for (VisualObject vo: VOs) {
            created += vo.Check();
        }
        for (VisualObject flow: Flows) {
            created += flow.Check();
        }
        return created;
    }

    public void setWarningVisibility(boolean visible) {
        for (Warning warning: Warnings) {
            warning.getImageView().setVisible(visible);
        }
    }

    public void setBackUp(Button backUp) {
        BackUp_ = backUp;
    }

    public Button getBackUp() {
        return BackUp_;
    }

    public void HighlightInnerLines(OpenProcess owner) {
        if (highlighters_ != null){
            this.getChildren().removeAll(highlighters_);
        }
        if (owner == null){
            highlighters_ = null;
            return;
        }
        highlighters_ = new Line[4];
        Coordinate[] corners = owner.getInsideCorners();

        highlighters_[0] = Coordinate.CreateNewLine(corners[0],corners[1]);
        highlighters_[0].setStyle("-fx-stroke: red;");
        highlighters_[0].setStrokeWidth(2);
        highlighters_[1] = Coordinate.CreateNewLine(corners[0],corners[2]);
        highlighters_[1].setStyle("-fx-stroke: red;");
        highlighters_[1].setStrokeWidth(2);
        highlighters_[2] = Coordinate.CreateNewLine(corners[2],corners[3]);
        highlighters_[2].setStyle("-fx-stroke: red;");
        highlighters_[2].setStrokeWidth(2);
        highlighters_[3] = Coordinate.CreateNewLine(corners[1],corners[3]);
        highlighters_[3].setStyle("-fx-stroke: red;");
        highlighters_[3].setStrokeWidth(2);

        this.getChildren().addAll(highlighters_);
    }
}
