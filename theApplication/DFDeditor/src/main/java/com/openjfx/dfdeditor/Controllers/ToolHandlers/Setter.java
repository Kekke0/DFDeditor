package com.openjfx.dfdeditor.Controllers.ToolHandlers;

import com.openjfx.dfdeditor.Model.*;
import com.openjfx.dfdeditor.Model.VOs.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Objects;

public class Setter {

    public void SetToMouseMode(Layer layer){
        layer.setActiveTool(Tool.MOUSE);
        layer.HighlightInnerLines(null);

        layer.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {}
        });

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

        if(Objects.equals(layer.getSelected().getTypeString(), "FL")){
            SetToConnectMode(layer,corner);
            return;
        }

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

    public void SetToConnectMode(Layer layer, int corner){
        if(!Objects.equals(layer.getSelected().getTypeString(), "FL")){
            SetToMouseMode(layer);
            return;
        }

        layer.setActiveTool(Tool.CONNECT);

        layer.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SolidObject connected = layer.IsInsideOfConnectableObject(mouseEvent.getX(), mouseEvent.getY());
                Flow connecting = (Flow) layer.getSelected();
                if (connected == null){
                    layer.ResizeSelected(corner,mouseEvent.getX(), mouseEvent.getY());
                    connecting.RemoveConnection(corner);
                    return;
                }

                Connection cn = Connection.createConnectionTo(connecting,corner,connected,new Coordinate(mouseEvent.getX(), mouseEvent.getY()));
                layer.AlignGuiToCorners();
                cn.AlignToConnected();
            }
        });

        layer.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                VisualObject connected = layer.IsInsideOfConnectableObject(mouseEvent.getX(), mouseEvent.getY());
                if (connected != null){
                    layer.setSelected(connected);
                }
                SetToMouseMode(layer);
            }
        });

    }

    public void SetToAddMode(Layer layer, VisualObject object){
        layer.setActiveTool(Tool.ADD);
        layer.setSelected(null);

        layer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    layer.getChildren().remove(object.getImageView());

                    object.AddToLayer(layer);
                    layer.setSelected(object);
                    SetToMouseMode(layer);
                } else if (mouseEvent.getButton()==MouseButton.SECONDARY){
                    SetToMouseMode(layer); // Exiting the Add Mode
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
}
