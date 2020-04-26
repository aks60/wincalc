package domain;

//Базовый класс для таблиц параметров составов, заполнений ...
public interface ITParam {

    public int pnumb();
    public int znumb();
    public String ptext();
    default String texCode(Constructive constr) {
        return "ITParam.texCode()";
    }
}
