package builder.script.test;

import builder.script.GsonElem;
import builder.script.GsonRoot;
import static builder.script.Winscript.rootGson;
import com.google.gson.GsonBuilder;
import enums.Layout;
import enums.Type;

public final class Alutech3 {

    public static String script(Integer prj, boolean model) {

        if (prj == 3) { //PUNIC = 427856  Двери
            rootGson = new GsonRoot(prj, 3, 66, "ALUTECH\\ALT.43\\Двери\\Наружу(2)",
                    Layout.VERT, Type.DOOR, 900, 2100, 1009, 1009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT));
            GsonElem stv = rootGson.addArea(new GsonElem(Layout.VERT, Type.STVORKA));
            stv.addArea(new GsonElem(Layout.VERT, Type.AREA, 600))
                    .addElem(new GsonElem(Type.GLASS));
            stv.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 1500))
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
