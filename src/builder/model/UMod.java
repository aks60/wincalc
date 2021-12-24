package builder.model;

import builder.making.Specific;
import domain.eArtikl;
import builder.param.ParamList;
import enums.UseUnit;
import builder.model.AreaStvorka;
import builder.model.ElemSimple;
import common.UCom;
import domain.eSetting;
import enums.Layout;
import java.util.HashMap;
import java.util.List;

class UMod {

    UMod(ElemSimple elem5e) {
    }

    //Укорочение мм от высоты ручки 
    public static float get_25013(Specific spcRec, Specific spcAdd) {

        String ps = spcAdd.getParam("null", 25013); //Укорочение от
        List<String> list = ParamList.find(25013).dict();  //[длины стороны, высоты ручки, сторона выс-ручки, половины стороны]             
        float dx = UCom.getFloat(spcAdd.getParam(0, 25030)); //"Укорочение, мм"

        if (list.get(0).equals(ps)) {
            return spcRec.width - dx;

        } else if (list.get(1).equals(ps)) {
            AreaStvorka stv = (AreaStvorka) spcAdd.elem5e.owner;
            return stv.handleHeight - dx;

        } else if (list.get(2).equals(ps)) {
            AreaStvorka stv = (AreaStvorka) spcAdd.elem5e.owner;
            return spcRec.width - stv.handleHeight - dx;

        } else if (list.get(3).equals(ps)) {
            return spcRec.width / 2 - dx;
        }
        return spcAdd.elem5e.length();
    }

    //Расчёт количества ед. с шагом
    public static float get_14050_24050_33050_38050(Specific spcRec, Specific spcAdd) {

        int step = Integer.valueOf(spcAdd.getParam(-1, 14050, 24050, 33050, 38050)); //Шаг, мм
        if (step != -1) {
            float width_begin = UCom.getFloat(spcAdd.getParam(0, 14040, 24040, 33040, 38040)); //Порог расчета, мм
            int count_step = Integer.valueOf(spcAdd.getParam(1, 14060, 24060, 33060, 38060)); //"Количество на шаг"
            float width_next = 0;
            if ("null".equals(spcAdd.getParam("null", 38004, 39005))) {
                width_next = spcRec.elem5e.length() - width_begin;

            } else if ("по периметру".equals(spcAdd.getParam("null", 38004, 39005))) {
                width_next = (spcRec.elem5e.width() * 2 + spcRec.elem5e.height() * 2) - width_begin;

            } else if ("по площади".equals(spcAdd.getParam("null", 38004, 39005))) {
                width_next = spcRec.elem5e.width() * spcRec.elem5e.height() - width_begin;

            } else if ("длина по коробке".equals(spcAdd.getParam("null", 38004, 39005))
                    && "null".equals(spcAdd.getParam("null", 38010, 39002))) {
                float length = 0;
                if ("1".equals(spcAdd.getParam("null", 38010, 39002))) {
                    length = spcRec.elem5e.root().mapFrame.get(Layout.BOTT).length();
                } else if ("2".equals(spcAdd.getParam("null", 38010, 39002))) {
                    length = spcRec.elem5e.root().mapFrame.get(Layout.RIGHT).length();
                } else if ("3".equals(spcAdd.getParam("null", 38010, 39002))) {
                    length = spcRec.elem5e.root().mapFrame.get(Layout.TOP).length();
                } else if ("4".equals(spcAdd.getParam("null", 38010, 39002))) {
                    length = spcRec.elem5e.root().mapFrame.get(Layout.LEFT).length();
                }
                width_next = length - width_begin;
            }
            int count = (int) width_next / step;
            if (count_step == 1) {
                if (count < 1) {
                    return 1;
                }
                return (width_next % step > 0) ? ++count : count;
            } else {
                int count2 = (int) width_next / step;
                int count3 = (int) (width_next % step) / (step / count_step);
                return ((width_next % step) % (step / count_step) > 0) ? count2 * count_step + count3 + 1 : count2 * count_step + count3;
            }
        }
        return 0;
    }

    //Расчёт количества ед. с шагом
    public static float get_11050(Specific spcAdd, ElemJoining elemJoin) {

        int step = Integer.valueOf(spcAdd.getParam(-1, 11050)); //Шаг, мм
        if (step != -1) {
            float width_begin = UCom.getFloat(spcAdd.getParam(0, 11040)); //Порог расчета, мм
            int count_step = Integer.valueOf(spcAdd.getParam(1, 11060)); //"Количество на шаг"
            ElemSimple elem5e = null;
            float width_next = 0;

            if ("Да".equals(spcAdd.getParam("Нет", 11010, 12010))) {
                elem5e = elemJoin.elem1;

            } else if ("Да".equals(spcAdd.getParam("Нет", 11020, 12020))) {
                elem5e = elemJoin.elem2;

            } else {
                elem5e = elemJoin.elem1; //по умолч.
            }
            if ("большей".equals(spcAdd.getParam("", 11072, 12072))) {
                float length = (elem5e.width() > elem5e.height()) ? elem5e.width() : elem5e.height();
                width_next = length - width_begin;

            } else if ("меньшей".equals(spcAdd.getParam("", 11072, 12072))) {
                float length = (elem5e.width() < elem5e.height()) ? elem5e.width() : elem5e.height();
                width_next = length - width_begin;

            } else if ("общей".equals(spcAdd.getParam("", 11072, 12072))) {
                float length = elemJoin.elem2.width();
                width_next = length - width_begin;

            } else {
                width_next = elem5e.width() - width_begin;
            }
            int count = (int) width_next / step;
            if (count_step == 1) {
                if (count < 1) {
                    return 1;
                }
                return (width_next % step > 0) ? ++count : count;
            } else {
                int count2 = (int) width_next / step;
                int count3 = (int) (width_next % step) / (step / count_step);
                return ((width_next % step) % (step / count_step) > 0) ? count2 * count_step + count3 + 1 : count2 * count_step + count3;
            }
        }
        return 0;
    }

    //Количество ед.
    public static float get_11030_12060_14030_15040_25060_33030_34060_38030_39060(Specific spсRec, Specific spcAdd) {
        return UCom.getFloat(spcAdd.getParam(spcAdd.count,
                11030, 12060, 14030, 15040, 25060, 33030, 34060, 38030, 39060));
    }

    //Поправка, мм
    public static float get_12050_15050_34051_39020(Specific spcRec, Specific spcAdd) {
        if (UseUnit.METR.id == spcAdd.artiklRec.getInt(eArtikl.unit)) { //пог.м.
            return UCom.getFloat(spcAdd.getParam(0, 12050, 15050, 34050, 34051, 39020)); //Поправка, мм
        }
        return spcAdd.width;
    }

    //Длина, мм
    public static float get_12065_15045_25040_34070_39070(Specific spcRec, Specific spcAdd) {
        if (UseUnit.METR.id == spcAdd.artiklRec.getInt(eArtikl.unit)) { //пог.м.
            return UCom.getFloat(spcAdd.getParam(spcAdd.width, 12065, 15045, 25040, 34070, 39070)); //Длина, мм 
        }
        return spcAdd.width;
    }

    //Длина, мм
    //public static float get_
    //Коэффициент, [ * коэф-т ]
    public static float get_12030_15030_25035_34030_39030(Specific spcRec, Specific spcAdd) {
        return UCom.getFloat(spcAdd.getParam("1", 12030, 15030, 25035, 34030, 39030));
    }

    //Коэффициент, [ / коэф-т ]
    public static float get_12040_15031_25036_34040_39040(Specific spcRec, Specific spcAdd) {
        return UCom.getFloat(spcAdd.getParam("1", 12040, 15031, 25036, 34040, 39040));
    }

    //Othe
    public static float get_11030_12060_14030_15040_24030_25060_33030_34060_38030_39060(Specific spcRec, Specific spcAdd) {
        return UCom.getFloat(spcAdd.getParam(spcAdd.quant1,
                11030, 12060, 14030, 15040, 24030, 25060, 33030, 34060, 38030, 39060));
    }

    //Задать Угол_реза_1/Угол_реза_2, °
    public static void get_34077_39077(Specific spcAdd) {
        if ("ps3".equals(eSetting.find(2))) {
            if (spcAdd.getParam("-1", 34077).equals("-1") == false) {
                spcAdd.anglCut1 = UCom.getFloat(spcAdd.getParam("-1", 34077));
            }
            if (spcAdd.getParam("-1", 34078).equals("-1") == false) {
                spcAdd.anglCut2 = UCom.getFloat(spcAdd.getParam("-1", 34078));
            }
        } else {
            if (spcAdd.getParam("-1", 34077, 39077).equals("-1") == false) {
                String[] arr = spcAdd.getParam("-1", 34077, 39077).split("/");
                if (arr[0].equals("*") == false) {
                    spcAdd.anglCut1 = UCom.getFloat(arr[0]);
                }
                if (arr[1].equals("*") == false) {
                    spcAdd.anglCut2 = UCom.getFloat(arr[1]);
                }
            }
        }
    }

    //Ставить однократно
    public static float get_11070_12070_33078_34078(Specific spcAdd) {
        if ("Да".equals(spcAdd.getParam("Нет", 11070, 12070, 33078, 34078))) {
            return 1;
        } else {
            return spcAdd.count;
        }
    }

    //Углы реза
    public static void get_12075_34075_39075(ElemSimple elem5e, Specific spcAdd) {
        String txt = spcAdd.getParam("null", 12075, 34075, 39075);

        if ("по контейнерам".equals(txt)) {
            spcAdd.anglCut1 = elem5e.anglCut[0];
            spcAdd.anglCut2 = elem5e.anglCut[1];

        } else if ("установить (90° x 90°)".equals(txt)) {
            spcAdd.anglCut1 = 90;
            spcAdd.anglCut2 = 90;

        } else if ("установить (90° x 45°)".equals(txt)) {
            spcAdd.anglCut1 = 90;
            spcAdd.anglCut2 = 45;

        } else if ("установить (45° x 45°)".equals(txt)) {
            spcAdd.anglCut1 = 45;
            spcAdd.anglCut2 = 45;
        }
    }

    //Высоту сделать длиной 
    public static void get_40007(Specific spcAdd) {
        if ("Да".equals(spcAdd.getParam("null", 40007))) {
            float height = spcAdd.height;
            spcAdd.height = spcAdd.width;
            spcAdd.width = height;
        }
    }

    //Округлять количество до ближайшего
    public static float get_39063(Specific spcAdd) {
        String txt = spcAdd.getParam("null", 39063);

        if ("меньшего целого числа".equals(txt)) {
            return (float) Math.floor(spcAdd.count);

        } else if ("большего целого числа".equals(txt)) {
            return (float) Math.ceil(spcAdd.count);

        } else if ("большего чётного числа".equals(txt)) {
            return (float) Math.round(spcAdd.count);

        } else if ("большего нечётного числа".equals(txt)) {
            return (float) Math.round(spcAdd.count) + 1;
        }
        return spcAdd.count;
    }

}
