package Model.VOs;

import Model.Coordinate;
import Model.Layer;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

public abstract class VisualObject {
    private ImageView imageView_;
    private Layer Parent_;
    private String ID;
    private String VisualID;
    private String Name;
    private Coordinate[] Corners= new Coordinate[4];

    private Text[] Texts;
    public VisualObject(){};

    public VisualObject(Layer parent,String ID, Coordinate maincorner) {
        this.ID = ID;
        this.Parent_ = parent;
        setBasicCorners(maincorner);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(getImagePath())));
        imageView_ = new ImageView(image);
        inicalizeTexts();
        resizeImageView();
    }


    public Layer GetLayer(){
        return null;
    }
    public Layer getParent() {
        return Parent_;
    }

    public void ChangePosition(Coordinate maincorner) {
        if (maincorner.getX()<0) maincorner.setX(0);
        if (maincorner.getY()<0) maincorner.setY(0);
        Coordinate distance= Distancing(getCorners()[0],maincorner);
        for (Coordinate corner: getCorners()) {
            corner.add(distance);
        }
        resizeImageView();
    }

    public void resizeImageView(){
        getImageView().setX(this.Corners[0].getX());
        getImageView().setY(this.Corners[0].getY());
        getImageView().setFitHeight(this.Corners[2].getY()-this.Corners[0].getY());
        getImageView().setFitWidth(this.Corners[1].getX()-this.Corners[0].getX());
        placeTexts();
    }

    public boolean isDissociable() {
        return false;
    }

    public void setDissociable(boolean dissociable){}

    protected void placeTexts() {
        Texts[1].setFont(new Font(20));
        Texts[1].setX(this.getCorners()[0].getX() + (this.getCorners()[3].getX()-this.getCorners()[0].getX())/1.5);
        Texts[1].setY(this.getCorners()[0].getY() + (this.getCorners()[3].getY()-this.getCorners()[0].getY())/3);
    }

    public void inicalizeTexts() {
        Texts= new Text[2];
        Texts[0]=new Text("");
        Texts[1]=new Text("");
    }

    public Text[] getTexts() {
        return Texts;
    }

    public void setTexts(Text[] texts) {
        Texts = texts;
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
        Texts[0].setText(ID);
    }

    public void setName(String name) {
        Name = name;
        Texts[1].setText(Name);
    }

    public void setCorners(Coordinate[] corners) {
        Corners = corners;
    }

    public static Coordinate Distancing (Coordinate first, Coordinate second){
        return new Coordinate(second.getX()-first.getX(),
                second.getY()-first.getY());

    }

    public void setImage() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(getImagePath())));
        imageView_.setImage(image);
    }

    public void showConsol(){
    }

    public ImageView getImageView(){
        return imageView_;
    }

    public void setImageView(ImageView imageView){
        imageView_=imageView_;
    }

    public boolean isInside(double x, double y){
        return x >= getCorners()[0].getX() &&
                x <= getCorners()[3].getX() &&
                y >= getCorners()[0].getY() &&
                y <= getCorners()[3].getY();
    }

    public void ResizeCorners(int corner, Double x, Double y) {

        Coordinate distance= Distancing(getCorners()[corner],new Coordinate(x, y));
        switch (corner){
            case 0->{
                if (y<= getCorners()[2].getY()-50){
                    getCorners()[1].setY(getCorners()[1].getY()+distance.getY());
                    getCorners()[corner].setY(y);
                }
                if(x<=getCorners()[1].getX()-90) {
                    getCorners()[2].setX(getCorners()[2].getX() + distance.getX());
                    getCorners()[corner].setX(x);
                }
            }
            case 1->{
                if (y<= getCorners()[3].getY()-50){
                    getCorners()[0].setY(getCorners()[0].getY()+distance.getY());
                    getCorners()[corner].setY(y);
                }
                if(x>=getCorners()[0].getX()+90) {
                    getCorners()[3].setX(getCorners()[3].getX() + distance.getX());
                    getCorners()[corner].setX(x);
                }
            }
            case 2->{
                if (y>= getCorners()[0].getY()+50) {
                    getCorners()[3].setY(getCorners()[3].getY()+distance.getY());
                    getCorners()[corner].setY(y);
                }
                if(x<=getCorners()[3].getX()-90){
                    getCorners()[0].setX(getCorners()[0].getX()+distance.getX());
                    getCorners()[corner].setX(x);
                }

            }
            case 3->{
                if (y>= getCorners()[0].getY()+50) {
                    getCorners()[2].setY(getCorners()[2].getY() + distance.getY());
                    getCorners()[corner].setY(y);
                }
                if (x>=getCorners()[3].getX()+90) {
                    getCorners()[1].setX(getCorners()[1].getX() + distance.getX());
                    getCorners()[corner].setX(x);
                }
            }
            default -> {

            }
        }


        resizeImageView();
    }

    public ContextMenu getContextMenu(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem Edit = new MenuItem("Edit");

        contextMenu.getItems().addAll(Edit);
        return contextMenu;
    }

    public String getTypeString(){
        return "VO";
    }

    public void AddToLayer(Layer layer) {
        layer.addVO(this);
    }
}
