package builder.making;

import dataset.Record;
import domain.eArtikl;
import domain.eColor;
import enums.UseUnit;
import enums.TypeArtikl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import builder.model.ElemSimple;

/**
 * Спецификация элемента окна
 */
public class Specific {

    public ArrayList<Specific> spcList = new ArrayList();  //список составов, фурнитур и т.д.
    public HashMap<Integer, String> mapParam = null;  //параметры спецификации
    public ElemSimple elem5e = null;  //элемент пораждающий спецификацию (контейнер)
    public Record variantRec = null;  //вариант в конструктиве
    public Record detailRec = null;  //детализация в конструктиве
    public Record artiklRec = null;  //артикул в детализации конструктива

    public float id = -1; //ID
    public String place = "---";  //Место расмешения
    public String name = "-";  //Наименование
    public String artikl = "-";  //Артикул
    public int colorID1 = -3;  //Осн.текстура
    public int colorID2 = -3;  //Внутр.текстура
    public int colorID3 = -3;  //Внешн.текстура
    public float width = 0;  //Длина
    public float height = 0;  //Ширина
    public float weight = 0;  //Масса
    public float anglCut1 = 0;  //Угол1
    public float anglCut2 = 0;  //Угол2
    public float anglHoriz = 0; // Угол к горизонту    
    public float count = 1;  //Кол. единиц

    public int unit = 0;  //Ед.изм   
    public float wastePrc = 0;  //Процент отхода    
    public float quant1 = 0;  //Количество без отхода
    public float quant2 = 0;  //Количество с отходом
    public float price1 = 0;  //Себест. за ед. без отхода     
    public float price2 = 0;  //Себест. за ед. с отходом
    public float cost1 = 0;  //Стоимость без скидки
    public float cost2 = 0;  //Стоимость со скидкой

    public Specific(float id, ElemSimple elem5e) {
        ++elem5e.iwin.genId;
        this.id = id;
        this.elem5e = elem5e;
        this.mapParam = new HashMap();
    }

    public Specific(Record detailRec, Record artiklRec, ElemSimple elem5e, HashMap<Integer, String> mapParam) {
        this.id = ++elem5e.iwin.genId;
        this.elem5e = elem5e;
        this.mapParam = mapParam;
        this.detailRec = detailRec;
        setArtiklRec(artiklRec);
    }

    public Specific(Specific spec) {
        this.id = spec.id; //++spec.elem5e.iwin.genId;
        this.place = spec.place;
        this.artikl = spec.artikl;
        this.artiklRec = spec.artiklRec;
        this.detailRec = spec.detailRec;
        this.name = spec.name;
        this.colorID1 = spec.colorID1;
        this.colorID2 = spec.colorID2;
        this.colorID3 = spec.colorID3;
        this.width = spec.width;
        this.height = spec.height;
        this.weight = spec.weight;
        this.anglCut1 = spec.anglCut1;
        this.anglCut2 = spec.anglCut2;
        this.count = spec.count;
        this.unit = spec.unit;
        this.quant1 = spec.quant1;
        this.wastePrc = spec.wastePrc;
        this.quant2 = spec.quant2;
        this.price1 = spec.price1;
        this.price2 = spec.price2;
        this.cost1 = spec.cost1;
        this.cost2 = spec.cost2;
        this.anglHoriz = spec.anglHoriz;
        this.mapParam = spec.mapParam;
        this.elem5e = spec.elem5e;
    }

    public Vector getVector(int npp) {
        return new Vector(Arrays.asList(npp, id, elem5e.id(), place, artikl, name, eColor.find(colorID1).getStr(eColor.name), eColor.find(colorID2).getStr(eColor.name),
                eColor.find(colorID3).getStr(eColor.name), width, height, weight, anglCut1, anglCut2, anglHoriz,
                count, UseUnit.getName(unit), wastePrc, quant1, quant2, price1, price2, cost1, cost2));
    }

    public void setArtiklRec(Record artiklRec) {
        this.artikl = artiklRec.getStr(eArtikl.code);
        this.name = artiklRec.getStr(eArtikl.name);
        this.wastePrc = artiklRec.getFloat(eArtikl.otx_norm);
        this.unit = artiklRec.getInt(eArtikl.unit); //atypi;
        this.artiklRec = artiklRec;
        setAnglCut();
    }

    public void setColor(int side, int colorID) {
        if (side == 1) {
            colorID1 = colorID;
        } else if (side == 2) {
            colorID2 = colorID;
        } else if (side == 3) {
            colorID3 = colorID;
        }
    }

    protected void setAnglCut() {
        if (TypeArtikl.X109.isType(artiklRec)
                || TypeArtikl.X135.isType(artiklRec)
                || TypeArtikl.X117.isType(artiklRec)
                || TypeArtikl.X136.isType(artiklRec)) {
            anglCut2 = 90;
            anglCut1 = 90;

        } else if (TypeArtikl.X109.isType(artiklRec)) {
            anglCut2 = 0;
            anglCut1 = 0;
        }
    }

    public String getParam(Object def, int... p) {

        if (mapParam != null) {
            for (int index = 0; index < p.length; ++index) {
                int key = p[index];
                String str = mapParam.get(Integer.valueOf(key));
                if (str != null) {
                    return str;
                }
            }
        }
        return String.valueOf(def);
    }

    public static void write_csv(ArrayList<Specific> spcList) {
        Writer writer = null;
        try {
            File file = new File("C:\\Java\\IWinCalc\\out\\Specification.csv.");
            writer = new BufferedWriter(new FileWriter(file));

            writer.write(new String(("TEST Изделие, Элемент, Артикул, Наименование, Текстура, Внутренняя, Внешняя, Длина. мм, "
                    + "Ширина. мм, Угол1, Угол2, Количество, Погонаж, Ед.изм, Ед.изм, Скидка, Скидка").getBytes("windows-1251"), "UTF-8"));
            for (Specific spc : spcList) {

                String str = spc.id + "," + spc.place + "," + spc.artikl + "," + spc.name + "," + spc.colorID1 + "," + spc.colorID2 + "," + spc.colorID3
                        + "," + String.format("%.1f", spc.width) + String.format("%.1f", spc.height) + String.format("%.2f", spc.anglCut2)
                        + String.format("%.2f", spc.anglCut1) + spc.count + spc.width + spc.unit + "\n";

                str = new String(str.getBytes(), "windows-1251");

                writer.write(str);
            }
        } catch (Exception ex) {
            System.err.println("Ошибка:Specification.write_csv() " + ex);
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (Exception ex2) {
                System.err.println("Ошибка:Specification.write_csv() " + ex2);
            }
        }
    }

    public static void write_txt(ArrayList<Specific> specList) {
        try {
            int npp = 0;
            String format = "%-4s%-8s%-60s%-26s%-12s%-12s%-12s";
            Object str[] = {"Npp", "Place", "Name", "Artikl", "areaId", "elemId", "owner"};
            System.out.printf(format, str);
            System.out.println();
            float total = 0;
            for (Specific s : specList) {
                Object str2[] = {String.valueOf(++npp), s.place, s.name, s.artikl,
                    s.elem5e.owner.id(), s.elem5e.id(), s.elem5e.spcRec.artiklRec.get(eArtikl.code)};
                total = total + s.weight;
                System.out.printf(format, str2);
                System.out.println();
            }
            System.out.println("Масса окна " + total + " кг.");
        } catch (Exception e) {
            System.err.println("Ошибка:Specification.write_txt2() " + e);
        }
    }
    
    public String toString() {
        return artikl + " - " + name;
    }
}
