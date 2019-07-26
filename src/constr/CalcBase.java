package constr;

import domain.Artsvst;
import domain.Colslst;
import domain.Constructive;
import domain.ITParam;
import enums.ParamJson;
import model.AreaSimple;
import model.ElemBase;
import model.IWindows;

import java.util.*;

public class CalcBase {

    protected IWindows iwin = null;
    protected AreaSimple root = null; //главное окно
    protected int nuni = -1; //id профиля

    //В прфстрое используюеся только 0, 4, 10, 12 параметры
    protected static final int PAR0 = 0;   //не проверять форму
    protected static final int PAR4 = 4;   //профиль с радиусом
    protected static final int PAR10 = 10; //не прямоугольное, не арочное заполнение
    protected static final int PAR12 = 12; //не прямоугольное заполнение с арками
    protected Constructive constr = null;


    public CalcBase(AreaSimple root) {

        this.root = root;
        this.iwin = root.getIwin();
        this.constr = root.getIwin().getConstr();
        this.nuni = root.getIwin().getNuni();
    }

    //Проверяет, должен ли применяться заданный тариф мат-ценности для заданной текстуры
    protected boolean IsArtTariffAppliesForColor(Artsvst artsvstRec, Colslst colslstRec) {
        if (artsvstRec.clnum < 0) {    //этот тариф задан для группы текстур

            if ((-1 * colslstRec.cgrup) == artsvstRec.clnum) {
                return true;
            }
        } else {  //проверяем не только colorCode, а еще и colorNumber
            if (colslstRec.ccode == artsvstRec.clcod) {
                return true;

            } else if (colslstRec.cnumb == artsvstRec.clnum) {
                return true;
            }
        }
        return false;
    }

    protected boolean CanBeUsedAsOutsideColor(Artsvst artsvst) { //cways = 1-внутр. 2-внешн. 4-основн.
        return (artsvst.cways & 2) != 0 || CanBeUsedAsBaseColor(artsvst);
    }

    protected boolean CanBeUsedAsInsideColor(Artsvst artsvst) { //cways = 1-внутр. 2-внешн. 4-основн.
        return (artsvst.cways & 1) != 0 || CanBeUsedAsBaseColor(artsvst);
    }

    protected boolean CanBeUsedAsBaseColor(Artsvst artsvst) { //cways = 1-внутр. 2-внешн. 4-основн.
        return (artsvst.cways & 4) != 0;
    }

    protected boolean DblNotZero(Object p) {
        float p2 = (float) p;
        return p2 > 0.00005;
    }

    protected Integer[] parserInt(String str) {

        ArrayList<Integer> arrList = new ArrayList();
        char symmetry = str.charAt(str.length() - 1);
        if (symmetry == '@') {
            str = str.substring(0, str.length() - 1);
        }
        String[] arr = str.split(";");
        if (arr.length == 1) {
            arr = arr[0].split("-");
            if (arr.length == 1) {
                return new Integer[]{Integer.valueOf(arr[0])};
            } else {
                arrList.add(Integer.valueOf(arr[0]));
                arrList.add(Integer.valueOf(arr[1]));
            }
        } else {
            for (int index = 0; index < arr.length; index++) {
                String[] arr2 = arr[index].split("-");
                if (arr2.length == 2) {
                    arrList.add(Integer.valueOf(arr2[0]));
                    arrList.add(Integer.valueOf(arr2[1]));
                }
            }
        }
        return arrList.stream().toArray(Integer[]::new);
    }

    protected Float[] parserFloat(String str) {

        ArrayList<Float> arrList = new ArrayList();
        str = str.replace(",", ".");
        char symmetry = str.charAt(str.length() - 1);
        if (symmetry == '@') {
            str = str.substring(0, str.length() - 1);
        }
        String[] arr = str.split(";");
        if (arr.length == 1) {
            arr = arr[0].split("-");
            if (arr.length == 1) {
                return new Float[]{Float.valueOf(arr[0])};
            } else {
                arrList.add(Float.valueOf(arr[0]));
                arrList.add(Float.valueOf(arr[1]));
            }
        } else {
            for (int index = 0; index < arr.length; index++) {
                String[] arr2 = arr[index].split("-");
                if (arr2.length == 2) {
                    arrList.add(Float.valueOf(arr2[0]));
                    arrList.add(Float.valueOf(arr2[1]));
                }
            }
        }
        return arrList.stream().toArray(Float[]::new);
    }

    protected static boolean compareFloat(String ptext, float value) {

        if (ptext == null) return true;
        ptext = ptext.replace(",", "."); //парсинг параметра
        char symmetry = ptext.charAt(ptext.length() - 1);
        if (symmetry == '@') {
            ptext = ptext.substring(0, ptext.length() - 1);
        }
        String[] arr = ptext.split(";");
        List<String> arrList = Arrays.asList(arr);
        for (String str : arrList) {

            String[] p = str.split("-");
            if (p.length == 1) {
                Float valueOne = Float.valueOf(p[0]);
                if (value == valueOne) return true;

            } else if (p.length == 2) {
                Float valueMin = Float.valueOf(p[0]);
                Float valueMax = Float.valueOf(p[1]);
                if (valueMin <= value && valueMax >= value) return true;
            }
        }
        return false;
    }

    protected static boolean compareInt(String ptext, int value) {

        if (ptext == null) return true;
        ptext = ptext.replace(",", "."); //парсинг параметра
        char symmetry = ptext.charAt(ptext.length() - 1);
        if (symmetry == '@') {
            ptext = ptext.substring(0, ptext.length() - 1);
        }
        String[] arr = ptext.split(";");
        List<String> arrList = Arrays.asList(arr);

        for (String str : arrList) {
            String[] p = str.split("-");
            if (p.length == 1) {
                Integer valueOne = Integer.valueOf(p[0]);
                if (value == valueOne) return true;
            } else if (p.length == 2) {
                Integer valueMin = Integer.valueOf(p[0]);
                Integer valueMax = Integer.valueOf(p[1]);
                if (valueMin <= value && valueMax >= value) return true;
            }
        }
        return false;
    }

    protected boolean compareColor(Integer[] arr, Integer color) {
        if (arr.length == 1) {
            int arr1 = arr[0];
            return (arr1 == color);
        } else {
            for (int index1 = 0; index1 < arr.length; index1 = index1 + 2) {
                int arr1 = arr[index1];
                int arr2 = arr[index1 + 1];
                if (arr1 <= color && color <= arr2) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void test_param(int[] paramArr) {

        HashMap<String, ArrayList> hm = new HashMap();
        for (int index = 0; index < paramArr.length; ++index) {
            Integer param = paramArr[index];
            String code = (String.valueOf(param).length() == 4) ? String.valueOf(param).substring(1, 4) : String.valueOf(param).substring(2, 5);
            if (hm.get(code) == null) {
                ArrayList<Integer> value = new ArrayList();
                value.add(Integer.valueOf(code));
                value.add(param);
                hm.put(code, value);
            } else {
                ArrayList arr = hm.get(code);
                arr.add(param);
            }
        }
        ArrayList<ArrayList<Integer>> arr = new ArrayList();
        for (Map.Entry<String, ArrayList> el : hm.entrySet()) {
            arr.add(el.getValue());
        }
        arr.sort(new Comparator<ArrayList<Integer>>() {

            public int compare(ArrayList a, ArrayList b) {
                return (Integer) a.get(0) - (Integer) b.get(0);
            }
        });
        for (ArrayList el : arr)
            System.out.println(el);
    }
}
