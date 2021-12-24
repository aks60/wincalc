package common;

import builder.model.Com5t;
import java.util.LinkedList;

public class LinkedList2<E extends Com5t> extends LinkedList<E> {

    public LinkedList2() {
        super();
    }

    public E find(float id) {
        return this.stream().filter(it -> it.id() == id).findFirst().get();
    }
}
