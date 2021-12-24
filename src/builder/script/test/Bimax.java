package builder.script.test;

import builder.script.GsonElem;
import builder.script.GsonRoot;
import static builder.script.Winscript.rootGson;
import com.google.gson.GsonBuilder;
import enums.Layout;
import enums.Type;

public final class Bimax {

    /**
     * PUNIC:427779 - KBE / KBE 58 / 3 НЕПРЯМОУГОЛЬНЫЕ ОКНА/ДВЕРИ / АРКИ
     * PUNIC:416791 - Rehau / Delight / 3 ТРАПЕЦИИ
     */
    public static String script(Integer prj, boolean model) {

        if (prj == 601001) { //PUNIC = 427817
            rootGson = new GsonRoot(prj, 1, 8, "KBE 58\\1 ОКНА\\Открывание внутрь (ств. Z77)",
                    Layout.VERT, Type.RECTANGL, 900, 1300, 1009, 10009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':1634}"))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 601002) { //PUNIC = 427818
            rootGson = new GsonRoot(prj, 1, 29, "Montblanc\\Nord\\1 ОКНА",
                    Layout.HORIZ, Type.RECTANGL, 1300, 1400, 1009, 10009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1300 / 2))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':3, 'sysfurnID':860}"))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1300 / 2))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':860}"))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 601003) { //PUNIC = 427819
            rootGson = new GsonRoot(prj, 1, 81, "Darrio\\DARRIO 200\\1 ОКНА",
                    Layout.VERT, Type.RECTANGL, 1440, 1700, 1009, 1009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 400))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            GsonElem area = rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1300));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 720))
                    .addArea(new GsonElem(Layout.HORIZ, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':389}"))
                    .addElem(new GsonElem(Type.GLASS));
            area.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 720))
                    .addArea(new GsonElem(Layout.HORIZ, Type.STVORKA, "{'typeOpen':3, 'sysfurnID':819}"))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 601004) { //PUNIC = 427820
            rootGson = new GsonRoot(prj, 1, 8, "KBE 58\\1 ОКНА\\Открывание внутрь (ств. Z77)",
                    Layout.VERT, Type.RECTANGL, 1440, 1700, 1009, 1009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT));
            rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 400))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            GsonElem area = rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1300));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 720))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':1634}"))
                    .addElem(new GsonElem(Type.GLASS));
            area.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 720))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':1633}"))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 601005) { //PUNIC = 
            rootGson = new GsonRoot(prj, 1, 8, "KBE\\KBE 58\\1 ОКНА\\Открывание внутрь (ств. Z77)",
                    Layout.HORIZ, Type.RECTANGL, 1600, 1700, 1009, 1009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT));
            rootGson.addArea(new GsonElem(Layout.VERT, Type.AREA, 800))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':1634}"))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 800))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':1633}"))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 601006) { //PUNIC =
            rootGson = new GsonRoot(prj, 1, 110, "RAZIO\\RAZIO 58 N\\1 ОКНА",
                    Layout.HORIZ, Type.RECTANGL, 900, 1400, 1009, 1009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':5746}")); //или 'R4x10x4x10x4'

        } else if (prj == 601007) { //PUNIC =
            rootGson = new GsonRoot(prj, 1, 87, "NOVOTEX\\Techno 58\\1 ОКНА",
                    Layout.VERT, Type.RECTANGL, 1100, 1400, 1009, 10018, 10018);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 300))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            GsonElem area = rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1100));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 550))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':1537}"))
                    .addElem(new GsonElem(Type.GLASS));
            area.addElem(new GsonElem(Type.IMPOST));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 550))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':1536}"))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 601008) { //PUNIC =
            rootGson = new GsonRoot(prj, 1, 99, "Rehau\\Blitz new\\1 ОКНА",
                    Layout.HORIZ, Type.RECTANGL, 1200, 1700, 1009, 28014, 21057);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 600))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            GsonElem area = rootGson.addArea(new GsonElem(Layout.VERT, Type.AREA, 600));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 550))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':534}"))
                    .addElem(new GsonElem(Type.GLASS));
            area.addElem(new GsonElem(Type.IMPOST));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 1150))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 601009) { //PUNIC =
            rootGson = new GsonRoot(prj, 1, 54, "KBE\\KBE Эксперт\\1 ОКНА\\Открывание внутрь (ств. Z77)",
                    Layout.HORIZ, Type.RECTANGL, 700, 1400, 1009, 1009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':4663}")); //или '4x12x4x12x4'
        } else if (prj == 999) { //PUNIC =
            rootGson = new GsonRoot(prj, 1, 54, "KBE\\KBE Эксперт\\1 ОКНА\\Открывание внутрь (ств. Z77)",
                    Layout.VERT, Type.RECTANGL, 700, 1400, 1009, 1009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':4663}"));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            rootGson.addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':4663}"));

            //Тут просочилась ручка не подходящая по параметру. Возможно ошибка ПрофСтроя4
        } else if (prj == 601010) { //PUNIC =  
            rootGson = new GsonRoot(prj, 1, 54, "KBE\\KBE Эксперт\\1 ОКНА\\Открывание внутрь (ств. Z77)",
                    Layout.HORIZ, Type.RECTANGL, 1300, 1400, 1009, 1009, 1009, "{'ioknaParam':[-8558]}");
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'sysprofID':1121, 'typeOpen':1,'sysfurnID':2335}")) //,'artiklHandl':2159,'colorHandl':1009}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':4663}"));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            rootGson.addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'sysprofID':1121, 'typeOpen':4,'sysfurnID':2916}")) //,'artiklHandl':5058,'colorHandl':1009}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':4663}"));

            //Нерешёння проблема со штапиком
        } else if (prj == 604004) { //PUNIC =
            rootGson = new GsonRoot(prj, 1, 37, "Rehau\\Delight\\1 ОКНА",
                    Layout.VERT, Type.ARCH, 1300, 1700, 1050, 1009, 1009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 650))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST, "{'sysprofID':3246}"));
            GsonElem area = rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1050));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addElem(new GsonElem(Type.GLASS));
            area.addElem(new GsonElem(Type.IMPOST, "{'sysprofID':3246}"));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':91}"))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 700027) {  //punic = 427872 штульповое
            rootGson = new GsonRoot(prj, 1, 198, "Montblanc / Eco / 1 ОКНА (штульп)",
                    Layout.HORIZ, Type.RECTANGL, 1300, 1400, 1009, 1009, 1009, "{'ioknaParam':[-8252]}");
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 450))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':2915}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':3293}"));
            rootGson.addElem(new GsonElem(Type.SHTULP))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 850))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':2913}"))
                    .addElem(new GsonElem(Type.GLASS, "{'artglasID':4267}"));

        } else if (prj == 508634) { //PUNIC =
            rootGson = new GsonRoot(prj, 1, 303, "Rehau\\Delight\\4 ОКНА(ФИГУРНАЯ СТВОРКА)",
                    Layout.HORIZ, Type.RECTANGL, 900, 1400, 1009, 1009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 508777) {  //PUNIC =  Стойка - ригель, надо тестировать
            rootGson = new GsonRoot(prj, 1, 303, "Rehau\\Delight\\4 ОКНА(ФИГУРНАЯ СТВОРКА)",
                    Layout.HORIZ, Type.RECTANGL, 900, 1400, 1009, 1009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 604005) { //PUNIC = 427833
            rootGson = new GsonRoot(prj, 1, 135, "Wintech\\Termotech 742\\1 ОКНА",
                    Layout.VERT, Type.ARCH, 1300, 1500, 1200, 1009, 10009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 300))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            GsonElem area = rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1200));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':2745}"))
                    .addElem(new GsonElem(Type.GLASS));
            area.addElem(new GsonElem(Type.IMPOST));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':2744}"))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 604006) { //PUNIC = 427832
            rootGson = new GsonRoot(prj, 1, 135, "Wintech\\Termotech 742\\1 ОКНА",
                    Layout.VERT, Type.ARCH, 1100, 1600, 1220, 1009, 1009, 10012);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 380))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            GsonElem area = rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1220));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 550))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':2745}"))
                    .addElem(new GsonElem(Type.GLASS));
            area.addElem(new GsonElem(Type.IMPOST));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 550))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':2744}"))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 604007) { //PUNIC = 427831
            rootGson = new GsonRoot(prj, 1, 99, "Rehau\\Blitz new\\1 ОКНА",
                    Layout.VERT, Type.ARCH, 1400, 1700, 1300, 1009, 1009, 10001);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 400))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            GsonElem area = (GsonElem) rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1300));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 700))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':1, 'sysfurnID':535}"))
                    .addElem(new GsonElem(Type.GLASS));
            area.addElem(new GsonElem(Type.IMPOST));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 700))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':3, 'sysfurnID':534}"))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 604008) { //PUNIC = 427830
            rootGson = new GsonRoot(prj, 1, 8, "KBE\\KBE 58\\1 ОКНА\\Открывание внутрь (ств. Z77)",
                    Layout.VERT, Type.ARCH, 1300, 1500, 1200, 1009, 10009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 300))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            GsonElem area = (GsonElem) rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1200));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':3, 'sysfurnID':-1}"))
                    .addElem(new GsonElem(Type.GLASS));
            area.addElem(new GsonElem(Type.IMPOST));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':3, 'sysfurnID':-1}"))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 604009) { //PUNIC =
            rootGson = new GsonRoot(prj, 1, 8, "KBE\\KBE 58\\1 ОКНА\\Открывание внутрь (ств. Z77)",
                    Layout.VERT, Type.ARCH, 1300, 1500, 1200, 1009, 10009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 300))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1200))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 604010) { //PUNIC = 427825
            rootGson = new GsonRoot(prj, 1, 29, "Montblanc\\Nord\\1 ОКНА",
                    Layout.VERT, Type.ARCH, 1300, 1700, 1400, 1009, 10009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 300))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            GsonElem area = (GsonElem) rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1400));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addElem(new GsonElem(Type.GLASS));
            area.addElem(new GsonElem(Type.IMPOST));
            GsonElem area2 = (GsonElem) area.addArea(new GsonElem(Layout.VERT, Type.AREA, 650));
            area2.addArea(new GsonElem(Layout.VERT, Type.AREA, 557))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':701}"))
                    .addElem(new GsonElem(Type.GLASS));
            area2.addElem(new GsonElem(Type.IMPOST));
            area2.addArea(new GsonElem(Layout.VERT, Type.AREA, 843))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 508983) { //PUNIC = 427779
            rootGson = new GsonRoot(prj, 1, 17, "KBE\\KBE 58\\3 НЕПРЯМОУГОЛЬНЫЕ ОКНА/ДВЕРИ\\АРКИ",
                    Layout.VERT, Type.ARCH, 1300, 1300, 1000, 1010, 10000, 10000);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 300))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            GsonElem area = rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 1000));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addElem(new GsonElem(Type.GLASS));
            area.addElem(new GsonElem(Type.IMPOST));
            area.addArea(new GsonElem(Layout.VERT, Type.AREA, 650))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':316}"))
                    .addElem(new GsonElem(Type.GLASS));

//        } else if (prj == 495647) { // PUNIC = 414087
//            //Арт.554008   Коробка 60мм  Белая, коричневая и карамель.
//            //Арт.554018  Створка Z57
//            //Арт.560607 615   Штапик 14.7
//            //Арт.865530 Уплотнитель притвора
//            rootGson = new GsonRoot(prj, 1, 303, "Rehau\\Delight\\3 ТРАПЕЦИИ",
//                    Layout.HORIZ, Type.RECTANGL, 900, 1400, 1009, 1009, 1009);
//            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT));
//            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT));
//            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP));
//            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT));
//            rootGson.addElem(new GsonElem(Type.GLASS)); 
            // 
            //ТРАПЕЦИИ
            //
        } else if (prj == 777002) { //Трапеции
            rootGson = new GsonRoot(prj, 1, 8, "KBE\\KBE 58\\1 ОКНА\\Открывание внутрь (ств. Z77)",
                    Layout.VERT, Type.TRAPEZE, 1300, 1500, 1200, 1009, 10009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 777001) { //Трапеции
            rootGson = new GsonRoot(prj, 1, 8, "KBE\\KBE 58\\1 ОКНА\\*Открывание внутрь (ств. Z77)",
                    Layout.VERT, Type.TRAPEZE, 1300, 1200, 1500, 1009, 10009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 777000) { //Трапеции
            rootGson = new GsonRoot(prj, 1, 8, "KBE\\KBE 58\\1 ОКНА\\*Открывание внутрь (ств. Z77)",
                    Layout.VERT, Type.TRAPEZE, 1300, 1200, 1500, 1009, 10009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 400))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 1100))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 605001) { //PUNIC = 427850  Трапеции
            rootGson = new GsonRoot(prj, 1, 8, "KBE\\KBE 58\\1 ОКНА\\*Открывание внутрь (ств. Z77)",
                    Layout.VERT, Type.TRAPEZE, 1300, 1500, 1200, 1009, 10009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 300))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 1200))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 508916) { //PUNIC = 427708  Трапеции
            rootGson = new GsonRoot(prj, 2, 8, "KBE\\KBE 58\\1 ОКНА\\*Открывание внутрь (ств. Z77)",
                    Layout.VERT, Type.TRAPEZE, 900, 1400, 1000, 1009, 10005, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 400))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 1000))
                    .addArea(new GsonElem(Layout.VERT, Type.STVORKA, "{'typeOpen':4, 'sysfurnID':1633}"))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 700014) { //PUNIC = 427856  Двери
            rootGson = new GsonRoot(prj, 1, 66, "Rehau\\Brilliant\\4 ДВЕРИ ВХОДНЫЕ\\Дверь наружу",
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

        } else if (prj == 700009) { //PUNIC = 427847 Двери
            rootGson = new GsonRoot(prj, 2, 330, "Darrio\\Двери DARRIO\\Дверь внутрь",
                    Layout.VERT, Type.DOOR, 900, 2000, 1009, 10004, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT));
            GsonElem stv = rootGson.addArea(new GsonElem(Layout.VERT, Type.STVORKA));
            stv.addArea(new GsonElem(Layout.VERT, Type.AREA, 1400))
                    .addElem(new GsonElem(Type.GLASS));
            stv.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 600))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 508841) { //427653 Двери
            rootGson = new GsonRoot(prj, 2, 330, "KBE / KBE Эксперт / 6 ВХОДНЫЕ ДВЕРИ / Дверь наружу",
                    Layout.VERT, Type.DOOR, 900, 2100, 1009, 1009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT));
            GsonElem stv = rootGson.addArea(new GsonElem(Layout.VERT, Type.STVORKA));
            stv.addArea(new GsonElem(Layout.VERT, Type.AREA, 1300))
                    .addElem(new GsonElem(Type.GLASS));
            stv.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.VERT, Type.AREA, 800))
                    .addElem(new GsonElem(Type.GLASS));

///////////////////////////////////////////////////////////////////////////////// 
        } else if (prj == 777) { //TEST
            rootGson = new GsonRoot(prj, 1, 8, "KBE\\KBE 58\\1 ОКНА\\*Открывание внутрь (ств. Z77)",
                    Layout.VERT, Type.RECTANGL, 1200, 1400, 1009, 1009, 1009);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT));
            GsonElem area1 = rootGson.addArea(new GsonElem(Layout.VERT, Type.AREA, 600));
            area1.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 200))
                    .addElem(new GsonElem(Type.GLASS));
            area1.addElem(new GsonElem(Type.IMPOST));
            area1.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 400))
                    .addElem(new GsonElem(Type.GLASS));
            rootGson.addElem(new GsonElem(Type.IMPOST));
            GsonElem area2 = rootGson.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 800));
            area2.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 200))
                    .addElem(new GsonElem(Type.GLASS));
            area2.addElem(new GsonElem(Type.IMPOST));

            GsonElem area3 = area2.addArea(new GsonElem(Layout.VERT, Type.AREA, 300));
            area3.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 400))
                    .addElem(new GsonElem(Type.GLASS));
            area3.addElem(new GsonElem(Type.IMPOST))
                    .addArea(new GsonElem(Layout.HORIZ, Type.AREA, 400))
                    .addElem(new GsonElem(Type.GLASS));

            area2.addElem(new GsonElem(Type.IMPOST));
            area2.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 400))
                    .addElem(new GsonElem(Type.GLASS));
            area2.addElem(new GsonElem(Type.IMPOST));
            area2.addArea(new GsonElem(Layout.HORIZ, Type.AREA, 300))
                    .addElem(new GsonElem(Type.GLASS));

        } else if (prj == 444) { //PUNIC = 427856  Двери
            rootGson = new GsonRoot(prj, 1, 10, "ALUTECH\\ALT.W62\\Двери\\Внутрь(1)",
                    Layout.VERT, Type.DOOR, 900, 2100, 0, 0, 0);
            rootGson.addElem(new GsonElem(Type.FRAME_SIDE, Layout.LEFT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.RIGHT))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.TOP))
                    .addElem(new GsonElem(Type.FRAME_SIDE, Layout.BOTT));
            rootGson.addArea(new GsonElem(Layout.VERT, Type.STVORKA))
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
