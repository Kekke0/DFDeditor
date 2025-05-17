package com.openjfx.dfdeditor.Model.VOs;

import com.openjfx.dfdeditor.Model.Coordinate;
import com.openjfx.dfdeditor.Model.Layer;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LayerProcess extends SolidObject{
    private Line[] image_;

    public LayerProcess(Layer parent, Coordinate mainCorner) {
        super(parent, mainCorner);
    }

    @Override
    public void setBasicCorners(Coordinate maincorner) {
        Coordinate[] corners = new Coordinate[4];
        corners[0]=new Coordinate(maincorner.getX(),maincorner.getY());
        corners[1]=new Coordinate(maincorner.getX()+200, maincorner.getY());
        corners[2]=new Coordinate(maincorner.getX(), maincorner.getY()+100);
        corners[3]=new Coordinate(maincorner.getX()+200, maincorner.getY()+100);
        setCorners(corners);
        initializeImage(maincorner);
    }

    private void initializeImage(Coordinate mainCorner) {
        image_ = new Line[6];
        Coordinate[] corners = getCorners();

        image_[0] = Coordinate.CreateNewLine(corners[0],corners[1]);
        image_[1] = Coordinate.CreateNewLine(corners[1],corners[3]);
        image_[2] = Coordinate.CreateNewLine(corners[3],corners[2]);
        image_[3] = Coordinate.CreateNewLine(corners[2],corners[0]);

        image_[4] = Coordinate.CreateNewLine(corners[0],corners[1]);
        image_[4].setEndY(image_[4].getEndY()-20);
        image_[4].setStartY(image_[4].getStartY()-20);

        image_[5] = new Line(corners[0].getX()+40,corners[0].getY(),corners[0].getX()+40,corners[0].getY()+20);
    }

    @Override
    public void resizeImageView(){
        super.resizeImageView();
        Coordinate[] corners = getCorners();

        Coordinate.AlignLinetoCoordinates(image_[0],corners[0],corners[1]);
        Coordinate.AlignLinetoCoordinates(image_[1],corners[1],corners[3]);
        Coordinate.AlignLinetoCoordinates(image_[2],corners[3],corners[2]);
        Coordinate.AlignLinetoCoordinates(image_[3],corners[2],corners[0]);

        Coordinate.AlignLinetoCoordinates(image_[4], corners[0],corners[1]);
        image_[4].setEndY(image_[4].getEndY()+20);
        image_[4].setStartY(image_[4].getStartY()+20);

        image_[5].setStartX(corners[0].getX()+40);
        image_[5].setStartY(corners[0].getY());
        image_[5].setEndX(corners[0].getX()+40);
        image_[5].setEndY(corners[0].getY()+20);
    }

    @Override
    public void initializeTexts() {
        setTexts(new Text[2]);
        getTexts()[0]=new Text(getID());
        getTexts()[1]=new Text(getName());
    }

    @Override
    protected void placeTexts() {
        getTexts()[1].setFont(new Font(20));
        getTexts()[1].setX(this.getCorners()[0].getX() + (this.getCorners()[3].getX()-this.getCorners()[0].getX())*(1.3)/3);
        getTexts()[1].setY(this.getCorners()[0].getY() + 25);
        getTexts()[1].setStrokeWidth(100);

        getTexts()[0].setFont(new Font(20));
        getTexts()[0].setX(this.getCorners()[0].getX() + 10);
        getTexts()[0].setY(this.getCorners()[0].getY() + 25);
        getTexts()[0].setWrappingWidth(50);
    }

    @Override
    public Node[] getimage() {
        return image_;
    }

    @Override
    public String getImagePath() {
        return "/com/openjfx/dfdeditor/VObjects/Process.png";

    }
}
