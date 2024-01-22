import java.sql.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Conectando ao banco de dados...");
        conectar();
    }

    public static Connection conectar(){
        String status = "Nada aconteceu ainda...";
        String mysqlhost = "127.0.0.1";
        String mysqlDb = "mysql_connector";
        String mysqlUser = "root";
        String mysqlPassword = "123456";
        String mysqlPort = "3306";
        String mysqlUrl = "jdbc:mysql://" + mysqlhost + ":" + mysqlPort+ "/" + mysqlDb + "?user=" + mysqlUser + "&password=" + mysqlPassword;
        Connection conn = null;

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(mysqlUrl);
            status = "Conexao realizada com sucesso.";
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e){
            status = "Ops! Algo de errado não está certo com a conexao com o banco de dados MySQL! mensagem do servidor: "+ e;
        }
        System.out.println(status);
        return conn;
    }


}