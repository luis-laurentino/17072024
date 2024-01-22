import java.util.*;
import java.sql.*;
public class DeleteMySQL {
    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Scanner scnLogin, scnSenha, scnSair, scnEnter, scnExcluirOuSair, scnConfirmarOuSair;
        scnLogin = scnSenha = scnSair = scnEnter = scnExcluirOuSair = scnConfirmarOuSair = new Scanner(System.in);
        boolean sair, logado;
        sair = logado = false;
        String strLogin, strSenha, strSqlDeleteLogin, idLogin, nome, senha, SouN, strExcluirOuSair, strSair, strEnter, strSqlSelectLoginSenha, strConfirmarDelete;
        idLogin = nome = senha = "";
        Connection conn = null;
        ResultSet rsLoginSenha;
        Statement stmSelectLoginSenha, stmDeleteLogin;

        System.out.println("Bem vindo ao sistema de exclusão de login.");
        System.out.println("Para entrar no sistema, é necessário digitar seu login e senha respectiva.");
        System.out.println("Tecle Enter para continuar...");
        strEnter = scnEnter.nextLine();
        System.out.println(strEnter);

        try {
            while (logado == false) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Digite seu login abaixo e tecle Enter: ");
                strLogin = scnLogin.nextLine();

                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Digite sua senha abaixo e tecle Enter: ");
                strSenha = scnSenha.nextLine();

                strSqlSelectLoginSenha = String.format("select * from `mysql_connector`.`tbl_login` where `login` = '%s' and `senha` = '%s';", strLogin, strSenha);
                conn = App.conectar();
                stmSelectLoginSenha = conn.createStatement();
                rsLoginSenha = stmSelectLoginSenha.executeQuery(strSqlSelectLoginSenha);
                while(rsLoginSenha.next()) {
                    idLogin = rsLoginSenha.getString("id");
                    nome = rsLoginSenha.getString("nome");
                    senha = rsLoginSenha.getString("senha");
                } // encerra while
                rsLoginSenha.close();
                System.out.print("\033[H\033[2J");
                System.out.flush();
                if (idLogin != "") {
                    System.out.println("Usuário [" + strLogin + "] logado com sucesso.");
                    System.out.println("Tecle Enter para continuar...");
                    strEnter = scnEnter.nextLine();
                    System.out.println(strEnter);
                    logado = true;
                } else {
                    System.out.println("Login e/ou senha inválidos. Digite abaixo [1] para tentar novamente ou [2] para sair.");
                    strSair = scnSair.nextLine();
                    if (strSair.equals("2")) {
                        logado = true;
                        sair = true;
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("Saída concluída com sucesso!");
                    }
                }
            }
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.err.println("Ops! Não foi possível logar por um erro no banco de dados.");
            System.err.println("Erro do banco de dados: " + e);
            sair = true;
        } // encerra catch

        try {
            while (sair == false) { // while 1
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Digite abaixo [1] para excluir seu cadastro completo ou [2] para sair, depois tecle Enter.");
                strExcluirOuSair = scnExcluirOuSair.nextLine();

                switch (strExcluirOuSair) { // switch/case 1
                    case "1":
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("Esta ação é irreversível! Digite [s] para confirmar ou [n] para cancelar: ");
                        strConfirmarDelete = scnConfirmarOuSair.nextLine();

                        if (strConfirmarDelete.equals("s") || strConfirmarDelete.equals("S")) {
                            strSqlDeleteLogin = "delete from `mysql_connector`.`tbl_login` where `id` = " + idLogin + ";";
                            stmDeleteLogin = conn.createStatement();
                            stmDeleteLogin.addBatch(strSqlDeleteLogin);
                            stmDeleteLogin.executeBatch();
                            stmDeleteLogin.close(); // este tem que fechar aqui mesmo
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            System.out.println("Cadastro excluído com sucesso!");
                            sair = true;
                        }
                        break;
                    case "2":
                        sair = true;
                        idLogin = "";
                        nome = "";
                        senha = "";
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("Deslogado com sucesso!");
                        break;
                    default:
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.err.println("Ops! opção inválida. Retornando...");
                        break;
                } // encerra switch/case 1
            } // encerra while 1
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.err.println("Ops! ocorreu o erro " + e);
        } // encera catch
        scnEnter.close();
        scnLogin.close();
        scnSenha.close();
        scnExcluirOuSair.close();
        scnConfirmarOuSair.close();
    } // encerra método main
} // encerra classe UpdateMySQL

