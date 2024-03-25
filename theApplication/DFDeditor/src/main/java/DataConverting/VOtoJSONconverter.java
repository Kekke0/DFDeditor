package DataConverting;

import DataConverting.Model.JSONVisualObject;
import Model.Coordinate;
import Model.Layer;
import Model.VOs.*;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VOtoJSONconverter {

    public static String VOtoJSON(VisualObject object) throws IOException {
        return new ObjectMapper().writeValueAsString(object.transformToJVO());
    }


    public static VisualObject JSONtoVO(Layer parent,String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONVisualObject map
                = objectMapper.readValue(json, new TypeReference<JSONVisualObject>(){});

        VisualObject importedVO = JVOToVO(parent, map);

        return importedVO;
    }

    public static VisualObject JVOToVO(Layer parent, JSONVisualObject map) throws IOException {
        Coordinate firstcorner = map.getCorners()[0];

        String ID =map.getID();
        String Name =map.getName();

        VisualObject importedVO = null;

        switch (map.getType()){
            case "PR"->{
                VProcess proc = new VProcess(parent,ID, firstcorner);
                proc.setName(Name);
                proc.setOrgUnit((String) map.getOrgUnit());
                proc.setDissociable(map.getDissociable());
                for (JSONVisualObject jvo: map.getLowerLayer()) {
                    JVOToVO(parent,jvo).AddToLayer(proc.getLowerLayer());
                }
                importedVO = proc;

            }
            case "DB"->{
                DataBase db = new DataBase(parent,ID, firstcorner);
                db.setName(Name);
                db.setMultiplied(map.getMultiplied());
                importedVO = db;
            }
            case "EE"->{
                ExternalElement ee = new ExternalElement(parent,ID, firstcorner);
                ee.setName(Name);
                ee.setMultiplied((map.getMultiplied()));
                importedVO = ee;
            }
            case "FL"->{
                Flow fl = new Flow(parent,firstcorner);
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
