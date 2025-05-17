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


    public static VisualObject JSONtoVO(Layer parent,String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONVisualObject map
                = objectMapper.readValue(json, new TypeReference<>(){});

        VisualObject importedVO = JVOToVO(parent, map);

        return importedVO;
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
                importedVO = db;
            }
            case "EE"->{
                ExternalElement ee = new ExternalElement(parent, firstCorner);
                ee.setName(Name);
                ee.setMultiplied((map.getMultiplied()));
                importedVO = ee;
            }
            case "FL"->{
                Flow fl = new Flow(parent,firstCorner);
                fl.setName(Name);
                importedVO = fl;
            }
            default->{
                throw new IOException("Unrecognized 'Type' in save file! ("+ map.getType() +")");
            }
        }

        importedVO.setCorners(map.getCorners());

        return importedVO;
    }
}
