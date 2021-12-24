package dataset;

import common.UCom;
import dataset.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Record<E> extends ArrayList<E> {

    public static boolean DIRTY = false;
    private Table table = null;

    public Record() {
        super();
    }

    public Record(Table table) {
        super();
        this.table = table;
    }

    public Record(int initialCapacity) {
        super(initialCapacity);
    }

    public Record(E... arr) {
        super();
        for (E val : arr) {
            this.add(val);
        }
    }

    //ИЗМЕНЕНИЯ СТАТУСА ЗАПИСИ
    public E set(int index, E element) {
        if (index != 0 && Query.SEL.equals(get(0))) {
            super.set(0, (E) Query.UPD);
            DIRTY = true;
        }
        return super.set(index, element);
    }

    //ИЗМЕНЕНИЯ СТАТУСА ЗАПИСИ
    public E set(Field field, E element) {
        return (E) set(field.ordinal(), element);
    }

    //ИЗМЕНЕНИЯ СТАТУСА ЗАПИСИ
    public E setNo(int index, E element) {
        return super.set(index, element);
    }

    //ЗАПИСЬ БЕЗ ИЗМЕНЕНИЯ СТАТУСА
    public E setNo(Field field, E element) {
        return super.set(field.ordinal(), element);
    }

    public Object get(Field field) {
        return super.get(field.ordinal());
    }

    public String getStr(int index) {
        return (super.get(index) == null) ? "" : String.valueOf(super.get(index));
    }

    public int getInt(int index) {
        try {
            Object obj = super.get(index);
            return (obj == null) ? -1 : Integer.parseInt(String.valueOf(obj));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public float getFloat(int index) {
        try {
            Object obj = super.get(index);
            return (obj == null) ? -1 : UCom.getFloat(String.valueOf(obj));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public double getDbl(int index) {
        try {
            Object obj = super.get(index);
            return (obj == null) ? -1 : Double.valueOf(String.valueOf(obj));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public Date getDate(int index) {
        return (super.get(index) == null) ? null : (Date) super.get(index);
    }

    public String getStr(Field field) {
        return (super.get(field.ordinal()) == null) ? "" : String.valueOf(super.get(field.ordinal()));
    }

    public int getInt(Field field) {
        return getInt(field, -1);
    }

    public int getInt(Field field, int def) {
        try {
            Object obj = super.get(field.ordinal());
            return (obj == null) ? -1 : Integer.valueOf(String.valueOf(obj));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public float getFloat(Field field) {
        Object obj = super.get(field.ordinal());
        return getFloat(field, -1);
    }

    public float getFloat(Field field, float def) {
        Object obj = super.get(field.ordinal());
        return (obj == null) ? def : UCom.getFloat(String.valueOf(obj));
    }

    public double getDbl(Field field) {
        Object obj = super.get(field.ordinal());
        return (obj == null) ? -1 : Double.valueOf(String.valueOf(obj));
    }

    public Date getDate(Field field) {
        return (super.get(field.ordinal()) == null) ? null : (Date) super.get(field.ordinal());
    }

    public boolean equals(Object obj) {
        return (this.get(1) == ((Record) obj).get(1));
    }

    //Проверка на корректность ввода
    public String validateRec(ArrayList<Field> fields) {
        for (int index = 1; index < fields.size(); index++) {
            MetaField meta = fields.get(index).meta();
            Object value = super.get(fields.get(index).ordinal());
            Object mes = meta.validateField(value);
            if (mes != null) {
                return mes.toString();
            }
        }
        return null;
    }
}
