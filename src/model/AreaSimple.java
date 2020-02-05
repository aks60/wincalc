package model;

import constr.Specification;
import domain.Colslst;
import domain.Sysproa;
import enums.LayoutArea;
import enums.TypeElem;
import enums.TypeProfile;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Первый наследник главного(root) окна.
 * Инкапсулирует общее поведение всех окон.
 */
public class AreaSimple extends ElemBase {

    protected IWindows iwin = null; //главный класс
    private LayoutArea layout = LayoutArea.FULL; //порядок расположения компонентов в окне
    private LinkedList<ElemBase> childList = new LinkedList(); //список компонентов в окне
    protected EnumMap<LayoutArea, ElemFrame> hmElemFrame = new EnumMap<>(LayoutArea.class); //список рам в окне

    public AreaSimple(String id) {
        super(id);
    }

    /**
     * Конструктор парсинга скрипта
     */
    public AreaSimple(IWindows iwin, AreaSimple root, AreaSimple owner, String id, LayoutArea layout, float width, float height) {
        this(owner, id, layout, width, height, 1, 1, 1);
        this.iwin = iwin;
        setRoot(root);
        //Коррекция размера стеклопакета(створки) арки.
        //Уменьшение на величину добавленной подкладки над импостом.
        if (owner != null && TypeElem.ARCH == owner.getTypeElem()
                && owner.getChildList().size() == 2 && TypeElem.IMPOST == owner.getChildList().get(1).getTypeElem()) {
            float dh = owner.getChildList().get(1).getArticlesRec().aheig / 2;
            setDimension(x1, y1, x2, y2 - dh);
        }
    }

    /**
     * Конструктор root окна
     */
    public AreaSimple(AreaSimple owner, String id, LayoutArea layout, float width, float height, int colorBase, int colorInternal, int colorExternal) {
        super(id);
        this.owner = owner;
        this.layout = layout;
        this.width = width;
        this.height = height;
        this.colorBase = colorBase;
        this.colorInternal = colorInternal;
        this.colorExternal = colorExternal;
        initDimension(owner);
    }

    private void initDimension(AreaSimple owner) {
        if (owner != null) {
            //Заполним по умолчанию
            if (LayoutArea.VERTICAL.equals(owner.getLayout())) { //сверху вниз
                setDimension(owner.x1, owner.y1, owner.x2, owner.y1 + height);

            } else if (LayoutArea.HORIZONTAL.equals(owner.getLayout())) { //слева направо
                setDimension(owner.x1, owner.y1, owner.x1 + width, owner.y2);
            }
            //Проверим есть ещё ареа перед текущей, т.к. this area ущё не создана начнём с конца
            for (int index = owner.getChildList().size() - 1; index >= 0; --index) {
                if (owner.getChildList().get(index) instanceof AreaSimple) {
                    ElemBase prevArea = owner.getChildList().get(index);

                    if (LayoutArea.VERTICAL.equals(owner.getLayout())) { //сверху вниз
                        setDimension(prevArea.x1, prevArea.y2, owner.x2, prevArea.y2 + height);

                    } else if (LayoutArea.HORIZONTAL.equals(owner.getLayout())) { //слева направо
                        setDimension(prevArea.x2, prevArea.y1, prevArea.x2 + width, owner.y2);
                    }
                    break; //как только нашел сразу выход
                }
            }
        } else { //для root area
            x2 = x1 + width;
            y2 = y1 + height;
        }
    }

    public IWindows getIwin() {
        return iwin;
    }

    public LayoutArea getLayout() {
        return layout;
    }

    public void addElem(ElemBase element) {
        childList.add(element);
    }

    public ElemFrame addRama(ElemFrame elemRama) {
        hmElemFrame.put(elemRama.getLayout(), elemRama);
        return elemRama;
    }

    /**
     * Функция по умолчанию, если нет переопределения
     */
    public void passJoinRama() {
    }

    /*public HashMap<Integer, Object[]> getHmParamDef() {
        return getRoot().iwin.hmParamDef;
    }*/

    public EnumMap<LayoutArea, ElemFrame> getHmElemFrame() {
        return hmElemFrame;
    }

    public HashMap<String, ElemJoinig> getHmJoinElem() {
        return getRoot().iwin.getHmJoinElem();
    }

    public void setSpecifElement(ElemFrame elemFrame, Sysproa sysproaRec) {
    }

    @Override
    public void addSpecifSubelem(Specification specification) {
    }

    @Override
    public LinkedList<ElemBase> getChildList() {
        return childList;
    }

    @Override
    public TypeElem getTypeElem() {
        return TypeElem.AREA;
    }

    @Override
    public TypeProfile getTypeProfile() {
        return null;
    }

    /**
     * Обход(схлопывание) соединений рамы
     */
    public void passJoinArea(HashMap<String, ElemJoinig> hmJoin) {

       /* if(getAdjoinedElem(LayoutArea.TOP) instanceof AreaSimple || getAdjoinedElem(LayoutArea.LEFT) instanceof AreaSimple) {
            return; //примыкающие ареи не могут порождать соединения
        }*/
        /*if(this != null) System.out.println("ТЕСТОВАЯ ЗАПЛАТКА");
        if(height < 160) {
            return;
        }*/
        ElemJoinig elemJoinVal = null;
        String key1 = String.valueOf(x1) + ":" + String.valueOf(y1);
        String key2 = String.valueOf(x2) + ":" + String.valueOf(y1);
        String key3 = String.valueOf(x2) + ":" + String.valueOf(y2);
        String key4 = String.valueOf(x1) + ":" + String.valueOf(y2);

        //if (getAdjoinedElem(LayoutArea.TOP) instanceof AreaSimple) {
        elemJoinVal = hmJoin.get(key1);
        if (elemJoinVal == null) {
            hmJoin.put(key1, new ElemJoinig(getConst()));
            elemJoinVal = hmJoin.get(key1);
        }
        if (elemJoinVal.elemJoinRight == null) elemJoinVal.elemJoinRight = getAdjoinedElem(LayoutArea.TOP);
        if (elemJoinVal.elemJoinBottom == null) elemJoinVal.elemJoinBottom = getAdjoinedElem(LayoutArea.LEFT);

        elemJoinVal = hmJoin.get(key2);
        if (elemJoinVal == null) {
            hmJoin.put(key2, new ElemJoinig(getConst()));
            elemJoinVal = hmJoin.get(key2);
        }
        if (elemJoinVal.elemJoinLeft == null) elemJoinVal.elemJoinLeft = getAdjoinedElem(LayoutArea.TOP);
        if (elemJoinVal.elemJoinBottom == null) elemJoinVal.elemJoinBottom = getAdjoinedElem(LayoutArea.RIGHT);
        //}
        elemJoinVal = hmJoin.get(key3);
        if (elemJoinVal == null) {
            hmJoin.put(key3, new ElemJoinig(getConst()));
            elemJoinVal = hmJoin.get(key3);
        }
        if (elemJoinVal.elemJoinTop == null) elemJoinVal.elemJoinTop = getAdjoinedElem(LayoutArea.RIGHT);
        if (elemJoinVal.elemJoinLeft == null) elemJoinVal.elemJoinLeft = getAdjoinedElem(LayoutArea.BOTTOM);

        elemJoinVal = hmJoin.get(key4);
        if (elemJoinVal == null) {
            hmJoin.put(key4, new ElemJoinig(getConst()));
            elemJoinVal = hmJoin.get(key4);
        }
        if (elemJoinVal.elemJoinTop == null) elemJoinVal.elemJoinTop = getAdjoinedElem(LayoutArea.LEFT);
        if (elemJoinVal.elemJoinRight == null) elemJoinVal.elemJoinRight = getAdjoinedElem(LayoutArea.BOTTOM);
    }

    /**
     * Получить примыкающий элемент
     * (используется при нахождении элементов соединений)
     */
    protected ElemBase getAdjoinedElem(LayoutArea layoutSide) {

        LinkedList<ElemBase> listElem = getAreaElemList();
        for (int index = 0; index < listElem.size(); ++index) {

            ElemBase elemBase = listElem.get(index);
            if (elemBase.id != id) continue; //пропускаем если другая ареа

            EnumMap<LayoutArea, ElemFrame> hm = getRoot().hmElemFrame;
            if (index == 0 && owner.equals(getRoot()) && layoutSide == LayoutArea.TOP && owner.getLayout() == LayoutArea.VERTICAL && getRoot().getTypeElem() == TypeElem.ARCH) {
                return hm.get(TypeElem.ARCH);
            } else if (owner.equals(getRoot()) && layoutSide == LayoutArea.TOP && owner.getLayout() == LayoutArea.HORIZONTAL && getRoot().getTypeElem() == TypeElem.ARCH) {
                return hm.get(TypeElem.ARCH);
            }

            if (owner.equals(getRoot()) && owner.getLayout() == LayoutArea.VERTICAL) {
                if (layoutSide == LayoutArea.TOP) {
                    return (index == 0) ? hm.get(layoutSide) : listElem.get(index - 1);
                } else if (layoutSide == LayoutArea.BOTTOM) {
                    return (index == listElem.size() - 1) ? hm.get(layoutSide) : listElem.get(index + 1);
                } else {
                    return getRoot().hmElemFrame.get(layoutSide);
                }

            } else if (owner.equals(getRoot()) && owner.getLayout() == LayoutArea.HORIZONTAL) {
                if (layoutSide == LayoutArea.LEFT) {
                    return (index == 0) ? hm.get(layoutSide) : listElem.get(index - 1);
                } else if (layoutSide == LayoutArea.RIGHT) {
                    return (index == listElem.size() - 1) ? hm.get(layoutSide) : listElem.get(index + 1);
                } else {
                    return getRoot().hmElemFrame.get(layoutSide);
                }

            } else {
                if (owner.getLayout() == LayoutArea.VERTICAL) {
                    if (layoutSide == LayoutArea.TOP) {
                        return (index == 0) ? owner.getAdjoinedElem(layoutSide) : listElem.get(index - 1);
                    } else if (layoutSide == LayoutArea.BOTTOM) {
                        return (index == listElem.size() - 1) ? owner.getAdjoinedElem(layoutSide) : listElem.get(index + 1);
                    } else {
                        return owner.getAdjoinedElem(layoutSide);
                    }
                } else {
                    if (layoutSide == LayoutArea.LEFT) {
                        return (index == 0) ? owner.getAdjoinedElem(layoutSide) : listElem.get(index - 1);
                    } else if (layoutSide == LayoutArea.RIGHT) {
                        return (index == listElem.size() - 1) ? owner.getAdjoinedElem(layoutSide) : listElem.get(index + 1);
                    } else {
                        return owner.getAdjoinedElem(layoutSide);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Список элементов окна
     *
     * @param type Тип элемента
     * @param <E>  Тип возвращаемого элемента
     * @return Список элементов в контейнере
     */
    public <E> LinkedList<E> getElemList(TypeElem... type) {
        if (type == null) type = new TypeElem[]{TypeElem.FRAME_BOX, TypeElem.FRAME_STV, TypeElem.IMPOST, TypeElem.GLASS};
        LinkedList<ElemBase> arrElem = new LinkedList();
        LinkedList<E> outElem = new LinkedList();
        for (Map.Entry<LayoutArea, ElemFrame> elemRama : getRoot().getHmElemFrame().entrySet()) {
            arrElem.add(elemRama.getValue());
        }
        for (ElemBase elemBase : getRoot().getChildList()) { //первый уровень
            arrElem.add(elemBase);
            if (elemBase instanceof AreaSimple) {
                for (Map.Entry<LayoutArea, ElemFrame> elemRama : ((AreaSimple) elemBase).getHmElemFrame().entrySet()) {
                    arrElem.add(elemRama.getValue());
                }
                for (ElemBase elemBase2 : elemBase.getChildList()) { //второй уровень
                    arrElem.add(elemBase2);
                    if (elemBase2 instanceof AreaSimple) {
                        for (Map.Entry<LayoutArea, ElemFrame> elemRama : ((AreaSimple) elemBase2).getHmElemFrame().entrySet()) {
                            arrElem.add(elemRama.getValue());
                        }
                        for (ElemBase elemBase3 : elemBase2.getChildList()) { //третий уровень
                            arrElem.add(elemBase3);
                            if (elemBase3 instanceof AreaSimple) {
                                for (Map.Entry<LayoutArea, ElemFrame> elemRama : ((AreaSimple) elemBase3).getHmElemFrame().entrySet()) {
                                    arrElem.add(elemRama.getValue());
                                }
                                for (ElemBase elemBase4 : elemBase3.getChildList()) { //четвёртый уровень
                                    arrElem.add(elemBase4);
                                    if (elemBase4 instanceof AreaSimple) {
                                        for (Map.Entry<LayoutArea, ElemFrame> elemRama : ((AreaSimple) elemBase4).getHmElemFrame().entrySet()) {
                                            arrElem.add(elemRama.getValue());
                                        }
                                        for (ElemBase elemBase5 : elemBase4.getChildList()) { //пятый уровень
                                            arrElem.add(elemBase5);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //Цикл по входному списку элементов
        for (int index = 0; index < type.length; ++index) {
            TypeElem type2 = type[index];
            for (ElemBase elemBase : arrElem) {
                if (elemBase.getTypeElem() == type2) {
                    E elem = (E) elemBase;
                    outElem.add(elem);
                }
            }
        }
        return outElem;
    }

    public void drawWin(float scale, byte[] buffer, boolean line) {
        try {
            getIwin().setScale(scale);
            BufferedImage image = getIwin().getImg();
            Graphics2D gc = (Graphics2D) image.getGraphics();
            gc.setColor(java.awt.Color.WHITE);
            gc.fillRect(0, 0, image.getWidth(), image.getHeight());

            //Прорисовка стеклопакетов
            LinkedList<ElemGlass> elemGlassList = getElemList(TypeElem.GLASS);
            elemGlassList.stream().forEach(el -> el.drawElemList());

            //Прорисовка импостов
            LinkedList<ElemImpost> elemImpostList = getElemList(TypeElem.IMPOST);
            elemImpostList.stream().forEach(el -> el.drawElemList());

            //Прорисовка рам
            drawTopFrame();
            hmElemFrame.get(LayoutArea.BOTTOM).drawElemList();
            hmElemFrame.get(LayoutArea.LEFT).drawElemList();
            hmElemFrame.get(LayoutArea.RIGHT).drawElemList();

            //Прорисовка створок
            LinkedList<AreaStvorka> elemStvorkaList = getElemList(TypeElem.FULLSTVORKA);
            elemStvorkaList.stream().forEach(el -> el.drawElemList());

            if (line == true) {
                //Прорисовка размера
                this.drawLineLength();
                LinkedList<AreaSimple> areaList = getElemList(TypeElem.AREA);
                areaList.stream().forEach(el -> el.drawLineLength());
            }

            //Рисунок в память
            ByteArrayOutputStream bosFill = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bosFill);
            buffer = bosFill.toByteArray();

            if (IWindows.production == false) {
                File outputfile = new File("CanvasImage.png");
                ImageIO.write(image, "png", outputfile);
            }

        } catch (Exception s) {
            System.err.println("Ошибка AreaSimple.drawWin() " + s);
        }
    }

    /**
     * Прорисовка размеров окна
     */
    public void drawLineLength() {

        float h = getIwin().getHeightAdd() - getIwin().getHeight();
        if (this == getRoot()) {  //главный контейнер
            float moveV = 180;
            lineLength2(String.format("%.0f", y2 - y1 + h), (int) (x2 + moveV), (int) (y1 - h), (int) (x2 + moveV), (int) y2); //высота окна
            lineLength2(String.format("%.0f", x2 - x1), (int) x1, (int) (y2 + moveV), (int) x2, (int) (y2 + moveV));  //ширина окна

        } else {  //вложенный контейнер
            float moveV = (this.owner == getRoot()) ? 120 : 60;
            if (this.height > 160 && this.width > 160) {
                if (owner.getChildList().size() > 1 && owner.getLayout() == LayoutArea.VERTICAL) {
                    lineLength2(String.format("%.0f", y2 - y1), (int) (x2 + moveV), (int) y1, (int) (x2 + moveV), (int) y2);
                } else if (owner.getChildList().size() > 1 && owner.getLayout() == LayoutArea.HORIZONTAL) {
                    lineLength2(String.format("%.0f", x2 - x1), (int) x1, (int) (y2 + moveV), (int) x2, (int) (y2 + moveV));
                }
            }
        }
    }

    private void lineLength2(String txt, int x1, int y1, int x2, int y2) {
        float h = getRoot().getIwin().getHeightAdd() - getRoot().getIwin().getHeight();
        Graphics2D gc = getRoot().getIwin().getImg().createGraphics();
        gc.setColor(java.awt.Color.BLACK);
        gc.setFont(new java.awt.Font("Serif", java.awt.Font.BOLD, 40));
        strokeLine(x1, y1, x2, y2, Color.BLACK, 2);
        if (x1 == x2) {
            strokeLine(x1 - 24, y1, x1 + 24, y1, Color.BLACK, 2);
            strokeLine(x2 - 24, y2, x2 + 24, y2, Color.BLACK, 2);
            strokeLine(x1, y1, x1 + 12, y1 + 24, Color.BLACK, 2);
            strokeLine(x1, y1, x1 - 12, y1 + 24, Color.BLACK, 2);
            strokeLine(x2, y2, x2 + 12, y2 - 24, Color.BLACK, 2);
            strokeLine(x2, y2, x2 - 12, y2 - 24, Color.BLACK, 2);
            gc.rotate(Math.toRadians(270), x1 + 28, y1 + (y2 - y1) / 2 + h);
            gc.drawString(txt, x1 + 28, y1 + (y2 - y1) / 2 + h);
        } else {
            strokeLine(x1, y1 - 24, x1, y1 + 24, Color.BLACK, 2);
            strokeLine(x2, y2 - 24, x2, y2 + 24, Color.BLACK, 2);
            strokeLine(x1, y1, x1 + 24, y1 - 12, Color.BLACK, 2);
            strokeLine(x1, y1, x1 + 24, y1 + 12, Color.BLACK, 2);
            strokeLine(x2, y2, x2 - 24, y2 - 12, Color.BLACK, 2);
            strokeLine(x2, y2, x2 - 24, y2 + 12, Color.BLACK, 2);
            gc.rotate(Math.toRadians(0), x1 + (x2 - x1) / 2, y2 + 28 + h);
            gc.drawString(txt, x1 + (x2 - x1) / 2, y2 + 28 + h);
        }
    }


    private void drawTopFrame() {

        if (TypeElem.ARCH == this.getTypeElem()) {
            //TODO для прорисовки арки добавил один градус, а это не айс!
            //Прорисовка арки
            ElemFrame ef = hmElemFrame.get(LayoutArea.ARCH);
            float dz = ef.articlesRec.aheig;
            double r = ((AreaArch) owner.getRoot()).radiusArch;
            int rgb = Colslst.get2(getRoot().getConst(), ef.colorInternal).cview;
            double ang1 = 90 - Math.toDegrees(Math.asin(width / (r * 2)));
            double ang2 = 90 - Math.toDegrees(Math.asin((width - 2 * dz) / ((r - dz) * 2)));
            strokeArc(width / 2 - r, 0, r * 2, r * 2, ang1, (90 - ang1) * 2 + 1, ArcType.OPEN, 0, 3); //прорисовка на сцену
            strokeArc(width / 2 - r + dz, dz, (r - dz) * 2, (r - dz) * 2, ang2, (90 - ang2) * 2 + 1, ArcType.OPEN, 0, 3); //прорисовка на сцену
            strokeArc(width / 2 - r + dz / 2, dz / 2, (r - dz / 2) * 2, (r - dz / 2) * 2, ang2, (90 - ang2) * 2 + 1, ArcType.OPEN, rgb, dz - 4); //прорисовка на сцену
            
        } else {
            hmElemFrame.get(LayoutArea.TOP).drawElemList();
        }
    }

    public void print() {
        System.out.println(TypeElem.AREA + " owner.id=" + owner.id + ", id=" + id + ", x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2);
    }
}


