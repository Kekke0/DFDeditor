package ToolHandlers;

import Model.Coordinate;
import Model.EditingStage;
import Model.Layer;
import Model.Tool;
import Model.VOs.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Setter {

    public void SetToMouseMode(Layer layer){
        layer.setActiveTool(Tool.MOUSE);

        layer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    layer.Select(mouseEvent.getX(), mouseEvent.getY());
                } else if (mouseEvent.getButton()==MouseButton.SECONDARY){
                    if(layer.getSelected() != null && layer.getSelected().isInside(mouseEvent.getX(), mouseEvent.getY())){
                        try {
                            new EditingStage(layer.getSelected());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        layer.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (layer.getSelected()!=null) {
                    int corner = layer.IsinCornerGui(mouseEvent.getX(), mouseEvent.getY());
                    if (corner != -1) {
                        SetToResizeMode(layer, corner);
                        return;
                    }
                    if (layer.getSelected().isInside(mouseEvent.getX(), mouseEvent.getY())) {
                                Coordinate grabPosition = VisualObject.Distancing(layer.getSelected().getCorners()[0], new Coordinate(mouseEvent.getX(), mouseEvent.getY()));
                                grabPosition.reverse();
                                SetToDraggingMode(layer, grabPosition);
                    }
                }
            }
        });

        layer.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

    }

    public void SetToDraggingMode(Layer layer, Coordinate startingPosition){
        layer.setActiveTool(Tool.DRAG);

        layer.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                    Coordinate moveto = new Coordinate(mouseEvent.getX(), mouseEvent.getY());
                    moveto.add(startingPosition);
                    layer.ChangeSelectedPosition(moveto);
            }
        });

        layer.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SetToMouseMode(layer);
            }
        });

        layer.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SetToMouseMode(layer);
            }
        });

    }

    public void SetToResizeMode(Layer layer, int corner){
        layer.setActiveTool(Tool.RESIZE);

        layer.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                layer.ResizeSelected(corner,mouseEvent.getX(), mouseEvent.getY());
            }
        });

        layer.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SetToMouseMode(layer);
            }
        });

    }

    public void SetToAddMode(Layer layer, VisualObject object){
        layer.setActiveTool(Tool.ADD);

        layer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    layer.getChildren().remove(object.getImageView());

                    object.AddToLayer(layer);
                    layer.setSelected(object);
                    SetToMouseMode(layer);
                }
            }
        });

        layer.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                object.ChangePosition(new Coordinate(mouseEvent.getX(),mouseEvent.getY()));
            }
        });

        layer.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                layer.getChildren().add(object.getImageView());
            }
        });

        layer.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                layer.getChildren().remove(object.getImageView());
            }
        });
    }

    public void SetToNullMode(Layer layer){
        layer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

        layer.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });
    }

    /*
    public void setEventHandlers(Layer layer, String current_tool){
        layer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton()== MouseButton.PRIMARY) {
                    switch (current_tool) {
                        case "FlowAdder" -> {
                            Flow flw = new Flow(new Coordinate(mouseEvent.getX(),mouseEvent.getY()));
                            layer.addFlow(flw);
                            layer.setSelected(flw);

                            layer.getChildren().remove(getPreviewVO().getImageView());
                            setTool("Mouse");
                        }
                        case "Databadder" -> {
                            DataBase dtb = new DataBase(layer.getLevel() + "sa", new Coordinate(mouseEvent.getX(), mouseEvent.getY()));
                            layer.addVO(dtb);
                            layer.setSelected(dtb);

                            layer.getChildren().remove(getPreviewVO().getImageView());
                            setTool("Mouse");
                        }
                        case "ExternalAdder" -> {
                            ExternalElement extel = new ExternalElement(layer.getLevel() + "sa", new Coordinate(mouseEvent.getX(), mouseEvent.getY()));
                            layer.addVO(extel);
                            layer.setSelected(extel);

                            layer.getChildren().remove(getPreviewVO().getImageView());
                            setTool("Mouse");
                        }
                        case "ProcessAdder" -> {
                            Process proc = new Process(layer.getLevel() + "sa", new Coordinate(mouseEvent.getX(), mouseEvent.getY()));
                            layer.addVO(proc);
                            layer.setSelected(proc);

                            layer.getChildren().remove(getPreviewVO().getImageView());
                            setTool("Mouse");
                        }
                        case "Mouse" -> {
                            layer.Select(mouseEvent.getX(), mouseEvent.getY());
                        }
                        default -> {

                        }
                    }
                } else if (mouseEvent.getButton()==MouseButton.SECONDARY){
                    if(layer.getSelected()!= null && layer.getSelected().isInside(mouseEvent.getX(), mouseEvent.getY())){
                        try {
                            new EditinStage(layer.getSelected());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        layer.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (current_tool){
                    case "FlowAdder":
                    case "ExternalAdder":
                    case "ProcessAdder":
                    case "Databadder":
                        getPreviewVO().ChangePosition(new Coordinate(mouseEvent.getX(),mouseEvent.getY()));
                        break;
                    default :
                }
            }
        });

        layer.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (current_tool){
                    case "Resize"->{
                        layer.ResizeSelected(selectedCorner_,mouseEvent.getX(), mouseEvent.getY());
                    }
                    case "Mouse"->{
                        if (dragging){
                            Coordinate moveto = new Coordinate(mouseEvent.getX(), mouseEvent.getY());
                            moveto.add(grabPosition);
                            layer.ChangeSelectedPosition(moveto);
                        }
                    }
                    default -> {

                    }
                }
            }
        });

        layer.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (current_tool){
                    case "Mouse"->{
                        if (layer.getSelected()!=null){
                            int corner=layer.IsinCornerGui(mouseEvent.getX(), mouseEvent.getY());
                            if (corner!=-1){
                                selectedCorner_=corner;
                                setTool("Resize");
                                return;
                            }
                            if (layer.getSelected().isInside(mouseEvent.getX(), mouseEvent.getY())) {
                                dragging = true;
                                grabPosition = VisualObject.Distancing(layer.getSelected().getCorners()[0], new Coordinate(mouseEvent.getX(), mouseEvent.getY()));
                                grabPosition.reverse();
                            }
                        }
                    }
                    default -> {

                    }
                }
                //System.out.println("Tool: "+current_tool+" Dragging: "+dragging+", Selected corner: "+ selectedCorner_ );
            }
        });

        layer.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dragging = false;
                if (Objects.equals(current_tool, "Resize"))
                    setTool("Mouse");

            }
        });

        layer.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (current_tool){
                    case "Random"->{

                    }
                    case "Mouse"->{
                    }
                    default -> {
                        layer.getChildren().add(getPreviewVO().getImageView());
                    }
                }
            }
        });

        layer.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                switch (current_tool){
                    case "Random"->{

                    }
                    case "Mouse"->{
                    }
                    default -> {
                        layer.getChildren().remove(getPreviewVO().getImageView());
                    }
                }
                dragging= false;
            }
        });
    }
     */
}
