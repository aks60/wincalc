package model;

import constr.Specification;
import domain.Colslst;
import domain.Constructive;
import enums.MeasUnit;
import jxl.Sheet;
import jxl.Workbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.*;

public class Main extends javax.swing.JFrame {

    private javax.swing.JButton btnClose = new javax.swing.JButton();
    private javax.swing.JPanel panSpecif = new javax.swing.JPanel(new java.awt.BorderLayout());
    private javax.swing.JPanel panFooter = new javax.swing.JPanel(new java.awt.BorderLayout());
    private javax.swing.JScrollPane jScrollPane = new javax.swing.JScrollPane();
    private javax.swing.JLabel labTotal = new javax.swing.JLabel();
    private javax.swing.JTable jTable;
    AreaSimple rootArea = null;

    public static void main(String args[]) {
        System.out.println("Тестирование - guid");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Ошибка Main.main() " + ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main frame = new Main();
                frame.setLocation(300, 100);
                try {
                    IWindows.production = false;

                    IWindows iwin = new IWindows(Constructive.getConstructive((short) 177));

                    frame.rootArea = iwin.create(iwin.productParamsJson);
                    JTable jtable = frame.initTable();
                    Constructive constructive = iwin.getConstr();

                    LinkedList<ElemBase> elemList = frame.rootArea.getElemList(null);
                    ArrayList<Specification> specList = new ArrayList();
                    for (ElemBase elemBase : elemList) {
                        specList.addAll(elemBase.getSpecificationList());
                    }
                    int row = 0;
                    Specification.sort(specList);
                    float totalJAR = 0;
                    for (Specification s : specList) {
                        Object val[] = {++row, s.id, s.element, s.artikl, s.name, Colslst.get2(constructive, s.colorBase).cname,
                            Colslst.get2(constructive, s.colorInternal).cname, Colslst.get2(constructive, s.colorExternal).cname,
                            (s.width == 0) ? "" : str(s.width), (s.height == 0) ? "" : str(s.height), str(s.anglCut1),
                            str(s.anglCut2), String.valueOf(s.count), MeasUnit.getName(s.unit),
                             str(s.quantity), str(s.wastePrc),
                             str(s.quantity2), str(s.inPrice), str(s.outPrice), str(s.inCost), str(s.discount), str(s.outCost)};

                        totalJAR = totalJAR + s.inCost;
                        ((DefaultTableModel) jtable.getModel()).addRow(val);
                    }
                    DecimalFormat fm = new DecimalFormat(".##");
                    frame.labTotal.setText("Итого: " + fm.format(totalJAR) + " руб." + "   Проект №" + iwin.getPrj());

                } catch (Exception e) {
                    System.out.println("Ошибка Main.run() " + e);
                }
                frame.setVisible(true);
            }
        });
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = java.sql.DriverManager
                    .getConnection("jdbc:firebirdsql:localhost/3050:E:\\Okna\\Profstroy\\PROFSTROY4.FDB?encoding=win1251", "sysdba", "masterkey");

        } catch (Exception e) {
        }
        return conn;
    }

    private javax.swing.JTable initTable() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 800));
        jTable = new javax.swing.JTable(new javax.swing.table.DefaultTableModel(null, new String[]{
            "npp", "id", "Расположение", "Артикул", "Наименование", "Текстура", "Внутренняя",
            "Внещняя", "Длина", "Щирина", "Угол1", "Угол2", "<html>Кол.<br>единиц", "<html>Ед.<br>измер.",
            "<html>Колич.<br>без отх.", "<html>Проц.<br>отхода", "<html>Колич.<br>с отх.",
            "<html>Собест.<br>за ед.изм.", "<html>Собест.<br>с отх.", "<html>Собест.<br>без ск.",
            "Скидка", "Итого"
        }) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false, false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable.setAutoCreateRowSorter(true);
        jTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane.setViewportView(jTable);
        panSpecif.add(jScrollPane, java.awt.BorderLayout.CENTER);

        int col_width[] = {30, 30, 80, 140, 250, 80, 80, 80, 60, 60, 50, 50, 40, 70, 70, 54, 60, 70, 70, 62, 50, 70};
        for (int index = 0; index < col_width.length; index++) {
            jTable.getColumnModel().getColumn(index).setPreferredWidth(col_width[index]);
        }

        jTable.getTableHeader().setPreferredSize(new Dimension(100, 32));
        getContentPane().add(panSpecif, java.awt.BorderLayout.CENTER);
        btnClose.setText("Exit");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(panFooter);
        panFooter.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labTotal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 837, Short.MAX_VALUE)
                                .addComponent(btnClose)
                                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnClose)
                                        .addComponent(labTotal))
                                .addContainerGap())
        );
        getContentPane().add(panFooter, BorderLayout.SOUTH);
        pack();
        return jTable;
    }

    private static String str(Object v) {
        if (v instanceof Float || v instanceof Double) {
            return String.valueOf(String.format("%.2f", v));
        }
        return (String) v;
    }

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    public static void compareIWin(ArrayList<Specification> spcList, String prj, boolean frame) {

        //TODO нужна синхронизация функции
        Float iwinTotal = 0f, jarTotal = 0f;
        String path = "src\\resource\\p" + prj + ".xls";
        Specification.sort(spcList);
        Map<String, Float> hmDll = new LinkedHashMap();
        Map<String, Float> hmJar = new LinkedHashMap();
        Map<String, String> hmJarArt = new LinkedHashMap();
        for (Specification spc : spcList) {

            String key = spc.name.trim().replaceAll("[\\s]{1,}", " ");
            Float val = (hmJar.get(key) == null) ? 0.f : hmJar.get(key);
            hmJar.put(key, val + spc.inCost);
            hmJarArt.put(key, spc.artikl);
        }
        Workbook w;
        File inputWorkbook = new File(path);
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            Sheet sheet = w.getSheet(0);
            for (int i = 5; i < sheet.getRows(); i++) {

                String art = sheet.getCell(1, i).getContents().trim();
                String key = sheet.getCell(2, i).getContents().trim().replaceAll("[\\s]{1,}", " ");
                String val = sheet.getCell(10, i).getContents();
                if (key.isEmpty() || art.isEmpty() || val.isEmpty()) {
                    continue;
                }

                val = val.replaceAll("[\\s|\\u00A0]+", "");
                val = val.replace(",", ".");
                Float val2 = (hmDll.get(key) == null) ? 0.f : hmDll.get(key);
                try {
                    Float val3 = Float.valueOf(val) + val2;
                    hmDll.put(key, val3);
                    hmJarArt.put(key, art);
                } catch (Exception e) {
                    System.out.println("Ошибка Main.compareIWin " + e);
                    continue;
                }
            }
            if (frame == true) {
                System.out.println();
                System.out.printf("%-64s%-24s%-16s%-16s%-16s", new Object[]{"Name", "Artikl", "Dll", "Jar", "Delta"});
                System.out.println();
                for (Map.Entry<String, Float> entry : hmDll.entrySet()) {
                    String key = entry.getKey();
                    Float val1 = entry.getValue();
                    Float val2 = (hmJar.get(key) == null) ? 0.f : hmJar.get(key);
                    hmJar.remove(key);
                    System.out.printf("%-64s%-24s%-16.2f%-16.2f%-16.2f", new Object[]{key, hmJarArt.get(key), val1, val2, Math.abs(val1 - val2)});
                    System.out.println();
                    jarTotal = jarTotal + val2;
                    iwinTotal = iwinTotal + val1;
                }
                if (hmJar.isEmpty() == false) {
                    System.out.printf("%-72s%-24s%-20s", new Object[]{"Name", "Artikl", "Value"});
                    System.out.println();
                }
                for (Map.Entry<String, Float> entry : hmJar.entrySet()) {
                    String key = entry.getKey();
                    Float value3 = entry.getValue();
                    System.out.printf("%-72s%-24s%-16.2f", "Лишние: " + key, hmJarArt.get(key), value3);
                    System.out.println();
                    jarTotal = jarTotal + value3;
                }
            } else {
                /* for (Map.Entry<String, Float> entry : hmDll.entrySet()) {
                    String key = entry.getKey();
                    Float val1 = entry.getValue();
                    Float val2 = (hmJar.get(key) == null) ? 0.f : hmJar.get(key);
                    hmJar.remove(key);
                    jarTotal = jarTotal + val2;
                    iwinTotal = iwinTotal + val1;
                }
                for (Map.Entry<String, Float> entry : hmJar.entrySet()) {
                    String key = entry.getKey();
                    Float value3 = entry.getValue();
                    jarTotal = jarTotal + value3;
                }*/
            }
            System.out.printf("%-18s%-18s%-18s%-12s", "Prj=" + prj, "iwin=" + String.format("%.2f", iwinTotal), "jar="
                    + String.format("%.2f", jarTotal), "dx=" + String.format("%.2f", Math.abs(iwinTotal - jarTotal)));
            System.out.println();

        } catch (Exception e2) {
            System.out.println("Ошибка Main.compareIWin " + e2);
        }
    }

    public static void print_joining(HashMap<String, ElemJoinig> hmJoinElem) {

        System.out.println("BEGIN_JOIN");
        for (Map.Entry<String, ElemJoinig> entry : hmJoinElem.entrySet()) {
            System.out.println("id=" + entry.getValue().id
                    + " JOIN=" + entry.getValue().varJoin + "  POINT:" + entry.getKey() + ", "
                    + getSId("LEFT", entry.getValue().elemJoinLeft) + " , "
                    + getSId("RIGHT", entry.getValue().elemJoinRight) + ", "
                    + getSId("TOOP", entry.getValue().elemJoinTop) + ", "
                    + getSId("BOTTOM", entry.getValue().elemJoinBottom)); // + ", " +
            //"art1:" + entry.getValue().joinElement1.articlesRec.anumb + ", " +
            //"art2:" + entry.getValue().joinElement2.articlesRec.anumb);
        }
        System.out.println("END_JOIN");
    }

    public static String getSId(String side, ElemBase el) {
        if (el == null) {
            return side + ":0";
        }
        return side + ":" + String.valueOf(el.id);
    }
}
