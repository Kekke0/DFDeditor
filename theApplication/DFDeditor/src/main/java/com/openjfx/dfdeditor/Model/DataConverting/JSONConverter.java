package com.openjfx.dfdeditor.Model.DataConverting;

import com.openjfx.dfdeditor.Model.DataConverting.Model.JSONVisualObject;
import com.openjfx.dfdeditor.Model.Coordinate;
import com.openjfx.dfdeditor.Model.Layer;
import com.openjfx.dfdeditor.Model.VOs.*;

import java.io.IOException;

import com.openjfx.dfdeditor.Model.VOs.Process;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConverter {

    public static String VOtoJSON(VisualObject object) throws IOException {
        return new ObjectMapper().writeValueAsString(object.transformToJVO());
    }


    public static void JSONtoVO(Layer parent,String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONVisualObject map
                = objectMapper.readValue(json, new TypeReference<>(){});

        JVOToVO(parent, map);
    }

    public static VisualObject JVOToVO(Layer parent, JSONVisualObject map) throws IOException {
        Coordinate firstCorner = map.getCorners()[0];

        String ID =map.getID();
        String Name =map.getName();

        VisualObject importedVO = null;

        switch (map.getType()){
            case "PR"->{
                Process proc = new Process(parent, firstCorner);
                proc.setID(ID);
                proc.setName(Name);
                proc.setOrgUnit((String) map.getOrgUnit());
                proc.setDissociable(map.getDissociable());
                for (JSONVisualObject jvo: map.getLowerLayer()) {
                    JVOToVO(parent,jvo).AddToLayer(proc.getLowerLayer());
                }
                importedVO = proc;

            }
            case "DB"->{
                DataBase db = new DataBase(parent, firstCorner);
                db.setID(ID);
                db.setName(Name);
                db.setMultiplied(map.getMultiplied());
                db.setPhysical(map.getDissociable());
                importedVO = db;
            }
            case "EE"->{
                ExternalElement ee = new ExternalElement(parent, firstCorner);
                ee.setName(Name);
                ee.setID(ID);
                ee.setMultiplied((map.getMultiplied()));
                importedVO = ee;
            }
            case "FL"->{
                Flow fl = new Flow(parent,firstCorner);
                fl.setName(Name);
                fl.setPhysical(map.getDissociable());
                importedVO = fl;
            }
            case "OP"->{
                OpenProcess op = new OpenProcess(parent,firstCorner);
                op.setName(Name);
                op.setID(ID);
                importedVO = op;
            }
            default->{
                throw new IOException("Unrecognized 'Type' in save file! ("+ map.getType() +")");
            }
        }
        importedVO.AddToLayer(parent);
        parent.setSelected(importedVO);
        parent.ChangeSelectedPosition(map.getCorners()[0]);
        for (int i = 0; i < map.getCorners().length; i++) {
            importedVO.ResizeCorners(i, map.getCorners()[i].getX(), map.getCorners()[i].getY());
        }
        parent.setSelected(null);

        return importedVO;
    }
}
