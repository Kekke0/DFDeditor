package Model.VOs;

import Model.Coordinate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class VisualObject {
    private ImageView imageView_;
    private String ID;
    private String VisualID;
    private String Name;
    private Coordinate[] Corners= new Coordinate[4];

    public VisualObject(String ID, Coordinate maincorner) {
        this.ID = ID;
        setBasicCorners(maincorner);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(getImagePath())));
        imageView_ = new ImageView(image);
        resizeImageView();
    }

    public String getImagePath() {
        return "/Menu/inprogress.png";
    }

    public void setBasicCorners(Coordinate maincorner) {
        Coordinate[] corners = new Coordinate[4];
        corners[0]=new Coordinate(maincorner.getX(),maincorner.getY());
        corners[1]=new Coordinate(maincorner.getX()+100, maincorner.getY());
        corners[2]=new Coordinate(maincorner.getX(), maincorner.getY()+100);
        corners[3]=new Coordinate(maincorner.getX()+100, maincorner.getY()+100);
        setCorners(corners);
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public Coordinate[] getCorners() {
        return Corners;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCorners(Coordinate[] corners) {
        Corners = corners;
    }

    public Coordinate Distancing (Coordinate first, Coordinate second){
        return new Coordinate(first.getX()-second.getX(),
                first.getY()-second.getY());

    }


    public void ChangePosition(Coordinate maincorner) {
        Coordinate distance= Distancing(Corners[0],maincorner);
        for (int i = 0; i < 4; i++) {
            Corners[i].add(distance);
        }
        resizeImageView();
    }

    public void showConsol(){
    }
    public ImageView getImageView(){
        return imageView_;
    }

    public void resizeImageView(){
        imageView_.setX(this.Corners[0].getX());
        imageView_.setY(this.Corners[0].getY());
        imageView_.setFitHeight(this.Corners[2].getY()-this.Corners[0].getY());
        imageView_.setFitWidth(this.Corners[1].getX()-this.Corners[0].getX());
    }
}
