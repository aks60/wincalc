package constr;

import domain.Artikls;
import domain.Colslst;
import domain.Constructive;
import domain.ITSpecific;
import enums.MeasUnit;
import enums.TypeArtikl;
import model.ElemBase;

import java.io.*;
import java.util.*;

/**
 * Спецификация элемента окна
 */
public class Specification {

    public ElemBase elemOwnerSpecif = null; //элемент пораждающий спецификацию
    protected ArrayList<Specification> specificationList = new ArrayList(); //список составов, фарнитур и т.д.
    protected HashMap<Integer, String> hmParam = null; //параметры спецификации
    private Artikls articlesRec = null; //профиль в спецификации

    public String id = "0";  //ID
    public String areaId = "0"; //ID area
    public String elemId = "0"; //ID elem
    public String elemType = "0"; //TypeElem
    public String layout = "-"; //Расположение
    public String artikl = "-";  //Артикул
    public String name = "-";  //Наименование
    public int colorBase = 1005;  //Текстура
    public int colorInternal = 1005;  //Внутренняя
    public int colorExternal = 1005;  //Внешняя
    public float width = 0;  //Длина
    public float height = 0;  //Ширина
    public float weight = 0;  //масса
    public float anglCut1 = 0;  //Угол1
    public float anglCut2 = 0;  //Угол2
    public int count = 1;  //Кол. единиц
    public int unit = 0; //Ед.изм
    public float quantity = 0; //Количество без отхода
    public float wastePrc = 0;  //Процент отхода
    public float quantity2 = 0;  //Количество с отходом
    public float inPrice = 0;  //Собес-сть за ед. изм.
    public float outPrice = 0;  //Собес-сть за злемент с отходом
    public float inCost = 0; //Стоимость без скидки
    public float outCost = 0; //Стоимость со скидкой
    public float discount = 0;  //Скидка
    public float anglHoriz = 0; // Угол к горизонту

    /**
     * Конструктор для видимых эдементов окна
     *
     * @param id
     * @param elemBase
     */
    public Specification(String id, ElemBase elemBase) {
        this.id = id;
        this.elemOwnerSpecif = elemBase;
        this.hmParam = new HashMap();
    }

    /**
     * Конструктор для элементов спецификации окна
     *
     * @param art
     * @param elemBase
     * @param hmParam
     */
    public Specification(Artikls art, ElemBase elemBase, HashMap<Integer, String> hmParam) {
        this.id = elemBase.genId();
        this.elemOwnerSpecif = elemBase;
        this.hmParam = hmParam;
        setArticlRec(art);
    }

    public Specification(Specification spec) {
        this.id = spec.elemOwnerSpecif.genId();
        this.layout = spec.layout;
        this.artikl = spec.artikl;
        this.name = spec.name;
        this.colorBase = spec.colorBase;
        this.colorInternal = spec.colorInternal;
        this.colorExternal = spec.colorExternal;
        this.width = spec.width;
        this.height = spec.height;
        this.anglCut2 = spec.anglCut2;
        this.anglCut1 = spec.anglCut1;
        this.count = spec.count;
        this.unit = spec.unit;
        this.quantity = spec.quantity;
        this.wastePrc = spec.wastePrc;
        this.quantity2 = spec.quantity2;
        this.inPrice = spec.inPrice;
        this.outPrice = spec.outPrice;
        this.discount = spec.discount;
        this.anglHoriz = spec.anglHoriz;
        this.hmParam = spec.hmParam;
        this.articlesRec = spec.getArticRec();
    }

    public ArrayList<Specification> getSpecificationList() {
        return specificationList;
    }

    public void setArticlRec(Artikls artiklRec) {
        this.artikl = artiklRec.anumb;
        this.name = artiklRec.aname;
        this.wastePrc = artiklRec.aouts;
        this.unit = artiklRec.atypi;
        this.articlesRec = artiklRec;
        setAnglCut();
        //this.height = artiklRec.aheig; //TODO парадокс добавления ширины, надо разобраться
    }

    public Artikls getArticRec() {
        return articlesRec;
    }

    public void setColor(int colorBase, int colorInternal, int colorExternal) {
        this.colorBase = colorBase;
        this.colorInternal = colorInternal;
        this.colorExternal = colorExternal;
    }

    //TODO ВАЖНО необходимо по умолчанию устонавливать colorNone = 1005 - без цвета
    public void setColor(CalcConstructiv calcConstructiv, ElemBase elemBase, ITSpecific par_specif) {
        colorBase = calcConstructiv.determineColorCodeForArt(elemBase, 1, par_specif, this);
        colorInternal = calcConstructiv.determineColorCodeForArt(elemBase, 2, par_specif, this);
        colorExternal = calcConstructiv.determineColorCodeForArt(elemBase, 3, par_specif, this);
    }

    protected void setAnglCut() {

        if (TypeArtikl.FITTING.isType(articlesRec) ||
                TypeArtikl.KONZEVPROF.isType(articlesRec) ||
                TypeArtikl.MONTPROF.isType(articlesRec) ||
                TypeArtikl.FIKSPROF.isType(articlesRec)) {
            anglCut2 = 90;
            anglCut1 = 90;

        } else if (TypeArtikl.FITTING.isType(articlesRec)) {
            anglCut2 = 0;
            anglCut1 = 0;
        }
    }

    public void putHmParam(Integer key, String val) {
        hmParam.put(key, val);
    }

    public String getHmParam(Object def, int... p) {

        if (hmParam == null) System.out.println("ОШИБКА getHmParam() hmParamJson = null");

        for (int index = 0; index < p.length; ++index) {
            int key = p[index];
            String str = hmParam.get(Integer.valueOf(key));
            if (str != null) {
                return str;
            }
        }
        return String.valueOf(def);
    }

    @Override
    public boolean equals(Object specification) {
        Specification spec = (Specification) specification;

        return (id == spec.id && layout.equals(spec.layout) && artikl.equals(spec.artikl) && name.equals(spec.name)
                && colorBase == spec.colorBase && colorInternal == spec.colorInternal && colorExternal == spec.colorExternal
                && width == spec.width && height == spec.height && anglCut2 == spec.anglCut2 && anglCut1 == spec.anglCut1 &&
                quantity == spec.quantity && unit == spec.unit && wastePrc == spec.wastePrc && quantity2 == spec.quantity2
                && inPrice == spec.inPrice && outPrice == spec.outPrice && discount == spec.discount);
    }

    @Override
    public String toString() {
        Formatter f = new Formatter();
        return "Изделие=" + id + ", Расп...=" + layout + ", Артикул=" + artikl + ", Наименование=" + name + ", Текстура=" + colorBase + ", Внутренняя=" + colorInternal
                + ", Внешняя=" + colorExternal + ", Длина. мм=" + f.format("%.1f", width) + ", Ширина. мм=" + f.format("%.1f", height) + ", Угол1=" + String.format("%.2f", anglCut2) +
                ", Угол2=" + String.format("%.2f", anglCut1) + ", Кол.шт=" + count + ", Кол.без.отх=" + quantity + ", Отход=" + wastePrc + ", Кол.с.отх=" + quantity2
                + ", Собест.за.ед" + inPrice + ", Собест.с.отх" + outPrice + ", Скидка=" + discount;
    }

    public static void write_csv(ArrayList<Specification> spcList) {
        Writer writer = null;
        try {
            //File file = new File("C:\\Java\\IWinCalc\\out\\Specification.csv.");
            File file = new File("C:\\Okna\\wincalc\\src\\resource\\file\\Specification.csv.");
            writer = new BufferedWriter(new FileWriter(file));

            writer.write(new String(("TEST Изделие, Элемент, Артикул, Наименование, Текстура, Внутренняя, Внешняя, Длина. мм, " +
                    "Ширина. мм, Угол1, Угол2, Количество, Погонаж, Ед.изм, Ед.изм, Скидка, Скидка").getBytes("windows-1251"), "UTF-8"));
            for (Specification spc : spcList) {

                String str = spc.id + "," + spc.layout + "," + spc.artikl + "," + spc.name + "," + spc.colorBase + "," + spc.colorInternal + "," + spc.colorExternal
                        + "," + String.format("%.1f", spc.width) + String.format("%.1f", spc.height) + String.format("%.2f", spc.anglCut2)
                        + String.format("%.2f", spc.anglCut1) + spc.count + spc.width + spc.unit + spc.discount + "\n";

                str = new String(str.getBytes(), "windows-1251");

                writer.write(str);
            }
        } catch (Exception ex) {
            System.out.println("Ошибка Specification.write_csv() " + ex);
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (Exception ex2) {
                System.out.println("Ошибка Specification.write_csv() " + ex2);
            }
        }
    }

    public static void write_txt(Constructive c, ArrayList<Specification> specList) {

        //String path = "C:\\Java\\specific_compare.txt";
        //try (PrintStream printStream = new PrintStream(path, "windows-1251")) {
            int npp = 0;
            String format = "%-6s%-46s%-32s%-32s%-32s%-32s%-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s" +
                    "%-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s%-16s %n";
            Object str[] = {"Code", "Name", "Art", "BaseColor", "InsideColor", "OutsideColor", "Count", "Quantity",
                    "UM", "InPrice", "CostPrice", "OutPrice", "OutTotal", "Width", "Height", "Weight",
                    "Angle", "ComplType", "ElemID", "ElemType", "ObjectID", "ObjectType", "AreaID", "AreaType",
                    "AccessoryID", "PriceGRP", "PrintGroup", "CutAngle1", "CutAngle2", "Composite", "Усл.окна"};
            String str3 = new String(("Спецификация (" + specList.size() + " строк):").getBytes());
            //printStream.println(str3);
            //printStream.printf(format, str);
            System.out.printf(format, str);
            for (Specification s : specList) {

                Object str2[] = {String.valueOf(++npp), s.name, s.artikl, Colslst.get2(c, s.colorBase).cname,
                        Colslst.get2(c, s.colorInternal).cname, Colslst.get2(c, s.colorExternal).cname, String.valueOf(s.count), String.valueOf(s.quantity),
                        MeasUnit.getName(s.unit), "0", String.valueOf(s.inPrice), String.valueOf(s.outPrice), String.valueOf(s.inCost),
                        String.valueOf(s.width), String.valueOf(s.height), "0", "0", "0", String.valueOf(s.id), "0", "0", "0", "0", "0",
                        "0", "0", "0", String.valueOf(s.anglCut2), String.valueOf(s.anglCut1), "0", "0"};
                //printStream.printf(format, str2);
                System.out.printf(format, str2);
            }
            float totalVal = 0;
            for (Specification s : specList) {
                totalVal = totalVal + s.outCost;
            }
            String str4 = new String(("Суммарная цена = " + totalVal).getBytes());
            //printStream.printf("%-120s", str4);
            System.out.println(str4);

        //} catch (IOException ex) {    System.out.println("Ошибка Specification.write_txt() " + ex);   }
    }

    public static void write_txt2(Constructive c, ArrayList<Specification> specList) {

        Specification.sort(specList);
        int npp = 0;
        String format = "%-6s%-16s%-46s%-26s%-12s%-12s%-12s";
        Object str[] = {"Code", "Section", "Name", "Art", "areaId", "elemId", "owner"};
        System.out.printf(format, str);
        System.out.println();
        float total = 0;
        for (Specification s : specList) {
            Object elem = (s.elemOwnerSpecif != null) ? s.elemOwnerSpecif.getSpecificationRec().articlesRec.anumb : null;
            Object str2[] = {String.valueOf(++npp), s.layout, s.name, s.artikl, s.areaId, s.elemId, elem};
            total = total + s.weight;
            System.out.printf(format, str2);
            System.out.println();
        }
        System.out.println("Масса окна " + total + " кг.");
    }

    public static void sort(ArrayList<Specification> contacts) {
        Collections.sort(contacts, new Comparator<Specification>() {
            public int compare(Specification one, Specification other) {
                return one.name.compareTo(other.name);
            }
        });
    }

    public static void sort2(ArrayList<Specification> contacts) {
        Collections.sort(contacts, new Comparator<Specification>() {
            public int compare(Specification one, Specification other) {
                return (one.layout + one.name).compareTo(other.layout + other.name);
            }
        });
    }

    public static void sort3(ArrayList<Specification> contacts) {
        Collections.sort(contacts, new Comparator<Specification>() {
            public int compare(Specification one, Specification other) {
                return (one.artikl + one.layout).compareTo(other.artikl + other.layout);
            }
        });
    }

    public static ArrayList<Specification> group(ArrayList<Specification> specificationList1) {

        HashMap<String, Specification> hm = new HashMap();
        for (Specification spc : specificationList1) {

            String key = spc.id + spc.layout + spc.artikl + spc.name + spc.colorBase + spc.colorInternal + spc.colorExternal
                    + spc.width + spc.height + spc.anglCut2 + spc.anglCut1 + spc.unit + spc.quantity + spc.wastePrc
                    + spc.wastePrc + spc.quantity2 + spc.inPrice + spc.outPrice + spc.discount;
            Specification spc2 = hm.put(key, spc);
            if (spc2 != null) {
                Specification spc3 = hm.get(key);
                spc3.count = spc3.count + spc2.count; //TODO тут надо увеличивать и стоимост за кол. элементов
            }

        }
        ArrayList<Specification> specificationList2 = new ArrayList();
        for (Map.Entry<String, Specification> entry : hm.entrySet()) {
            specificationList2.add(entry.getValue());
        }
        return specificationList2;
    }
}
