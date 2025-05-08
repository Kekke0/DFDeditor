package Model.VOs;

import DataConverting.Model.JSONVisualObject;
import Model.Coordinate;
import Model.Layer;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

public abstract class VisualObject {
    private ImageView imageView_;
    private final Layer layer_;
    private Coordinate[] Corners= new Coordinate[4];
    private Warning warning;
    private Text[] Texts;

    private String ID;
    private String Name;

    public VisualObject(Layer layer, Coordinate maincorner) {
        this.layer_ = layer;
        setBasicCorners(maincorner);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(getImagePath())));
        imageView_ = new ImageView(image);
        initializeTexts();
        resizeImageView();
    }

    public void ChangePosition(Coordinate newPozition) {
        if (newPozition.getX()<0) newPozition.setX(0);
        if (newPozition.getY()<0) newPozition.setY(0);
        Coordinate distance= Distancing(getCorners()[0],newPozition);
        for (Coordinate corner: getCorners()) {
            corner.add(distance);
        }
        resizeImageView();
    }

    public Layer GetLayer(){
        return null;
    }
    public Layer getLayer() {
        return layer_;
    }

    public void resizeImageView(){}

    public boolean isDissociable() {
        return false;
    }

    public void setDissociable(boolean dissociable){}

    protected void placeTexts() {
        Texts[1].setFont(new Font(20));
        Texts[1].setX(this.getCorners()[0].getX() + (this.getCorners()[3].getX()-this.getCorners()[0].getX())/1.5);
        Texts[1].setY(this.getCorners()[0].getY() + (this.getCorners()[3].getY()-this.getCorners()[0].getY())/3);
    }

    public void initializeTexts() {
        setTexts(new Text[2]);
        getTexts()[0]=new Text("");
        getTexts()[1]=new Text("");
    }

    public Text[] getTexts() {
        return Texts;
    }

    public abstract Node[] getimage();

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

    public void ResizeCorners(int corner, Double x, Double y) {}

    public ContextMenu getContextMenu(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem Edit = new MenuItem("Edit");

        contextMenu.getItems().addAll(Edit);
        return contextMenu;
    }

    public JSONVisualObject transformToJVO() throws IOException {
        throw new UnsupportedOperationException("This Object doesnt have a JSON form, so it should not be transformed.");
    }

    public String getTypeString(){
        return "VO";
    }

    public void  AddToLayer(Layer layer) {}

    public int Check(){
        this.warning = null;
        return 0;
    }

    public void setWarning(Warning warning) {
        this.warning = warning;
    }

    public Warning getWarning() {
        return this.warning;
    }

    public void addWarningMsg(String msg){
        if (this.warning==null){
            this.warning=new Warning(this);
        }
        this.warning.addMessage(msg);
    }
}
