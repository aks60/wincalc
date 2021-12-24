package dataset;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table extends ArrayList<Record> {

    protected Query root = null;
    protected HashMap<String, Query> mapQuery = new HashMap<String, Query>();
    protected ArrayList<Field> fields = new ArrayList<Field>(); 

    private static SimpleDateFormat fd = new SimpleDateFormat("dd.MM.yyyy");

    public Table() {
    }

    public List<Field> fields() {
        return fields;
    }
    
    public void removeRec(int index) {
        for (Map.Entry<String, Query> entry : mapQuery.entrySet()) {
            Table table = entry.getValue();
            table.remove(index);
        }
    }

    public void set(Object value, int index, Field field) {
        Object value2 = get(index, field);
        if (value2 != null && value != null && value2.equals(value)) {
            return;
        }
        Record record = get(index);
        record.set(field.ordinal(), value);
    }

    public Object get(int index, Field field) {
        if (index != -1) {
            Record record = get(index);
            return (record == null) ? null : record.get(field);
        }
        return null;
    }

    public <T> T getAs(int index, Field field) {
        Object obj = get(index, field);
        obj = (obj == null) ?-1 :obj;
        return (T) obj;
    }
    
    public <T> T getAs(int index, Field field, Object def) {
        Object obj = get(index, field);
        return (obj == null) ? (T) def : (T) obj;
    }
}
