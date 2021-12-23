package dataset;

import common.eProfile;
import common.eProperty;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JOptionPane;
//import startup.App;

/**
 * Соединение через PostgresSQL
 */
public class Conn {

    private static Conn instanceClass = null;
    protected static Connection connection = null;
    protected Statement statement = null;
    protected boolean autoCommit = false;
    public final static String driver = "org.firebirdsql.jdbc.FBDriver";
    public final static String fbserver = "jdbc:firebirdsql:";
    public String url = "";

    public static Conn init() {

        instanceClass = new Conn();
        return instanceClass;
    }

    public static Conn instanc() {
        if(instanceClass == null) {
            instanceClass = new Conn();
        }
        return instanceClass;
    }

    public void connection(Connection connection) {
        this.connection = connection;
    }

    public Connection connection() {
        return connection;
    }
    
    public void autoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            System.out.println("dataset.IConnect.setAutoCommit() " + e);
        }
    }

    /**
     * Соединение с БД
     */
    public eExcep createConnection(String server, String port, String base, String user, char[] password, String role) {
        try {
            if (Class.forName(driver) == null) {
                JOptionPane.showMessageDialog(eProfile.frame, "Ошибка загрузки файла драйвера",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            String url = fbserver + "//" + server + ":" + port + "/" + base;
            Properties props = new Properties();
            props.setProperty("user", user.toLowerCase());
            props.setProperty("password", String.valueOf(password));
            if (role != null) {  //&& user.equalsIgnoreCase("sysdba") == false) {
                props.setProperty("roleName", role);
            } 
            props.setProperty("encoding", "win1251");
            connection = DriverManager.getConnection(url, props);
            connection.setAutoCommit(true);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        } catch (ClassNotFoundException e) {
            return eExcep.findDrive;
        } catch (SQLException e) {
            System.err.println(e);
            return eExcep.getError(e.getErrorCode());
        }
        return eExcep.yesConn;
    }

    //Добавление нового пользователя   
    public void addUser(String user, char[] password, String role) {
        try {
            connection.createStatement().executeUpdate("create user " + user + " password '" + String.valueOf(password) + "'");
            connection.createStatement().executeUpdate("grant DEFROLE to " + user);
            connection.createStatement().executeUpdate("grant " + role + " to " + user);
            
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    //Удаление пользователя
    public void deleteUser(String user) {
        try {
            connection.createStatement().executeUpdate("REVOKE TEXNOLOG_RW FROM " + user);
            connection.createStatement().executeUpdate("REVOKE MANAGER_RW FROM " + user);
            connection.createStatement().executeUpdate("REVOKE TEXNOLOG_RO FROM " + user);
            connection.createStatement().executeUpdate("REVOKE MANAGER_RO FROM " + user);
            connection.createStatement().executeUpdate("DROP USER " + user);

        } catch (SQLException e) {
            System.err.println("Ошибка удаления пользователя" + e);
        }
    }

    //Изменение параметров пользователя
    public void modifyPassword(String user, char[] password) {
        try {
            String sql = "ALTER USER " + user + " PASSWORD '" + String.valueOf(password) + "'";
            connection.createStatement().executeUpdate(sql);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    //Генератор ключа ID
    public int genId(Field field) {
        try {
            int next_id = 0;
            Statement statement = connection.createStatement();
            String sql = "SELECT GEN_ID(gen_" + field.tname() + ", 1) FROM RDB$DATABASE";
            ResultSet rs = statement.executeQuery(sql);
            /*String mySeqv = table_name + "_id_seq";
            ResultSet rs = statement.executeQuery("SELECT nextval('" + mySeqv + "')");*/
            if (rs.next()) {
                next_id = rs.getInt("GEN_ID");
            }
            rs.close();
            return next_id;
        } catch (SQLException e) {
            System.err.println("Ошибка генерации ключа " + e);
            return 0;
        }
    }

    public String version() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT rdb$get_context('SYSTEM', 'ENGINE_VERSION') as version from rdb$database";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            String v = rs.getString("VERSION");
            rs.close();
            return v;

        } catch (SQLException e) {
            System.err.println("Ошибка получения версии " + e);
            return "";
        }
    }
}
