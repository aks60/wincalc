package builder.script.test;

import builder.script.GsonElem;
import builder.script.GsonRoot;
import static builder.script.Winscript.rootGson;
import com.google.gson.GsonBuilder;
import enums.Layout;
import enums.Type;

public final class Alutex3 {

    public static String script(Integer prj, boolean model) {

        if (prj == 4) { //PUNIC = 0  Двери
            rootGson = new GsonRoot(prj, 1, 10, "ALUTECH\\ALT.W62\\Двери\\Внутрь(1)",
                    Layout.VERT, Type.DOOR, 900, 2100, 0, 0, 0);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT));
            rootGson.addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'artiklHandl':1472}"))
                    .addElem(new GsonElem(Type.GLASS));
        } else {
            return null;
        }
        if (model == true) {
            rootGson.propery(prj.toString(), -3, null);
        }

        return new GsonBuilder().create().toJson(rootGson);
    }
}
