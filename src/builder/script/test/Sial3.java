package builder.script.test;

import builder.script.GsonElem;
import builder.script.GsonRoot;
import static builder.script.Winscript.rootGson;
import com.google.gson.GsonBuilder;
import enums.Layout;
import enums.Type;

public final class Sial3 {

    public static String script(Integer prj, boolean model) {

        if (prj == 601001) {
            rootGson = new GsonRoot(prj, 1, 12, "СИАЛ\\КП45\\Окна",
                    Layout.VERT, Type.RECTANGL, 800, 1200, 25, 25, 25);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':285}"));

        } else if (prj == 601002) {
            rootGson = new GsonRoot(prj, 1, 32, "СИАЛ\\КП40\\Окна",
                    Layout.VERT, Type.RECTANGL, 900, 600, 25, 25, 25);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':2}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':285}"));

        } else if (prj == 601003) {
            rootGson = new GsonRoot(prj, 1, 32, "СИАЛ\\КП40\\Окна",
                    Layout.HORIZ, Type.RECTANGL, 1200, 1600, 30, 30, 30);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1200 / 2))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':3, 'sysfurnID':143}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':285}"));
            rootGson.addElem(new GsonElem(Type.IMPOST, "{'sysprofID':357}"))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1200 / 2))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':143}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':285}"));

        } else if (prj == 601004) {
            rootGson = new GsonRoot(prj, 1, 12, "СИАЛ\\КП45\\Окна\\",
                    Layout.HORIZ, Type.RECTANGL, 2010, 1600, 27, 27, 27);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 670))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':13}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':283}"));
            rootGson.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 670))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':283}"));
            rootGson.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 670))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':2, 'sysfurnID':13}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':283}"));

        } else if (prj == 601005) {
            rootGson = new GsonRoot(prj, 5, 12, "СИАЛ\\КП45\\Окна\\",
                    Layout.HORIZ, Type.RECTANGL, 1800, 2020, 30, 30, 30);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT));
            GsonElem area2 = rootGson.addArea(new GsonElem(Layout.VERT, Type.AREA, 900))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':147}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':286}"));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            GsonElem area3 = rootGson.addArea(new GsonElem(Layout.VERT, Type.AREA, 900))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 800))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':2, 'sysfurnID':147}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':286}"));
            area3.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 1220))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':286}"));

        } else if (prj == 601006) {
            rootGson = new GsonRoot(prj, 6, 12, "СИАЛ\\КП45\\Окна\\",
                    Layout.HORIZ, Type.RECTANGL, 3800, 1800, 37, 37, 37);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':147}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':286}"));
            rootGson.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 633.33f))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':286}"));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            rootGson.addArea(new GsonElem(Layout.VERT, Type.AREA, 633.33f))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':2, 'sysfurnID':147}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':286}"));
            rootGson.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 633.33f))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':147}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':286}"));
            rootGson.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 633.33f))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':286}"));
            rootGson.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 633.33f))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':2, 'sysfurnID':147}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':286}"));
            rootGson.addElem(new GsonElem(Type.IMPOST));

        } else if (prj == 601007) { // Двери
            rootGson = new GsonRoot(prj, 1, 5, "SIAL\\КП-45\\Распашные двери\\Дверь 1 ств",
                    Layout.VERT, Type.DOOR, 900, 2100, 24, 24, 24);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT));
            GsonElem stv = rootGson.addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'artiklHandl':98, 'artiklLoop':749, 'colorLoop':24}"));
            stv.addArea(new GsonElem(Layout.VERT, Type.AREA, 1200 - 47))
                    .addElem(new GsonElem(Type.GLASS));
            stv.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 900 + 47))
                    .addElem(new GsonElem(Type.GLASS));
            
        } else if (prj == 601008) { // Двери
            rootGson = new GsonRoot(prj, 1, 2, "SIAL\\КП-45\\Двери маятниковые\\Маятниковая 1-створчатая. SA:[CBOT 1040=23мм, CTOP 1040=100]",
                    Layout.VERT, Type.DOOR, 900, 2100, 33, 33, 33);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT, "{'sysprofID':160}"))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT, "{'sysprofID':160}"))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP, "{'sysprofID':160}"))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT));
            GsonElem stv = rootGson.addArea(new GsonElem(Layout.VERT, Type.STVORKA));
            stv.addArea(new GsonElem(Layout.VERT, Type.AREA, 1500))
                    .addElem(new GsonElem(Type.GLASS));
            stv.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 600))
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
