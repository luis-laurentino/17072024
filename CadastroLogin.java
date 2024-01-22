import java.sql.*;
import java.util.*;

public class CadastroLogin {

    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Bem-vindo ao SISTEMA");
        menuPrincipal();
    }

    public static void menuPrincipal() {
        Scanner scnMenuEscolhido = new Scanner(System.in);
        String menuEscolhido;

        System.out.println("\nEscolha uma opção:");
        System.out.println("1. Cadastrar");
        System.out.println("2. Realizar Login");
        menuEscolhido = scnMenuEscolhido.nextLine();

        if (menuEscolhido.equals("1")) {
            menuCadastro();
        }
        if (menuEscolhido.equals("2")) {
            String loginUsuario = telaLogin();
            alteracaoCadastro(loginUsuario);
        } else {
            System.out.println("Opção inválida. Selecione uma das opções válidas.");
            menuPrincipal();
        }
        scnMenuEscolhido.close();
    }

    public static void voltarMenu() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        String voltarMenu;
        Scanner scnVoltarMenu = new Scanner(System.in);

        System.out.println("\nDeseja voltar ao menu inicial ou Sair do programa?");
        System.out.println("Escolha uma opção:");
        System.out.println("1. Ir ao Menu principal");
        System.out.println("2. Sair do programa");
        voltarMenu = scnVoltarMenu.nextLine();

        if (voltarMenu.equals("1")) {
            menuPrincipal();
        }
        if (voltarMenu.equals("2")) {
            System.out.println("Ok. Encerrando programa");
        } else {
            System.out.println("Opção inválida. Selecione uma das opções válidas.");
            voltarMenu();
        }

        scnVoltarMenu.close();
    }

    public static void alteracaoCadastro(String loginUsuario) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Scanner scnMenuAlteracao;

        String menuAlteracao;

        System.out.println("\nEscolha uma opção:");
        System.out.println("1. Alterar Nome");
        System.out.println("2. Alterar Senha");

        scnMenuAlteracao = new Scanner(System.in);
        menuAlteracao = scnMenuAlteracao.nextLine();

        if (menuAlteracao.equals("1")) {
            alterarNome(loginUsuario);
        }
        if (menuAlteracao.equals("2")) {
            alterarSenha(loginUsuario);
        } else {
            System.out.println("Opção inválida. Selecione uma opção válida.");
            alteracaoCadastro(loginUsuario);
        }

        scnMenuAlteracao.close();
    }

    public static void alterarNome(String loginUsuario) {
        Connection conn = App.conectar();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Scanner scnUpdateNome;

        String novoNome;

        try {
            System.out.println("\nDigite o novo nome: ");
            scnUpdateNome = new Scanner(System.in);
            novoNome = scnUpdateNome.next();

            String strSqlUpdateNome = "UPDATE `mysql_conector`.`tbl_login` SET `nome` = '" + novoNome + "' WHERE `login` = '" + loginUsuario + "';";
            try (Statement stmSqlUpdateNome = conn.createStatement()) {
                stmSqlUpdateNome.addBatch(strSqlUpdateNome);
                stmSqlUpdateNome.executeBatch();
                stmSqlUpdateNome.close();
            }

            System.out.println("Nome atualizado com sucesso.");
            voltarMenu();

            scnUpdateNome.close();

        } catch (Exception e) {
            System.err.println("Ops! Ocorreu um erro: " + e);
        }
    }

    public static void alterarSenha(String loginUsuario) {
        Connection conn = App.conectar();
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scnUpdateSenha;

        String novaSenha;

        try {
            System.out.println("Após alterar sua senha você será deslogado do sistema.\n");
            System.out.println("\nDigite a nova senha: ");
            scnUpdateSenha = new Scanner(System.in);
            novaSenha = scnUpdateSenha.next();

            String strSqlUpdateSenha = "UPDATE `mysql_conector`.`tbl_login` SET `senha` = '" + novaSenha + "' WHERE `login` = '" + loginUsuario + "';";

            Statement stmSqlUpdateSenha = conn.createStatement();
            stmSqlUpdateSenha.addBatch(strSqlUpdateSenha);
            stmSqlUpdateSenha.executeBatch();

            System.out.println("Senha atualizada com sucesso. Retornando ao Menu Principal.");
            menuPrincipal();

            scnUpdateSenha.close();

        } catch (Exception e) {
            System.err.println("Ops! Ocorreu um erro: " + e);
        }
    }

    public static void menuCadastro() {
        Connection conn = App.conectar();
        System.out.print("\033[H\033[2J");
        System.out.flush();

        try {
            Scanner scnCadLogin, scnCadNome, scnCadSenha;

            String strLogin, strNome, strSenha;

            System.out.println("Digite o login que será cadastrado: ");
            scnCadLogin = new Scanner(System.in);
            strLogin = scnCadLogin.nextLine();
            System.out.println("Digite o nome que será cadastrado: ");
            scnCadNome = new Scanner(System.in);
            strNome = scnCadNome.nextLine();
            System.out.println("Digite a senha que será cadastrada: ");
            scnCadSenha = new Scanner(System.in);
            strSenha = scnCadSenha.nextLine();

            String strSqlInsert = "INSERT INTO `mysql_conector`.`tbl_login` (`login`, `nome`, `senha`) VALUES ('" + strLogin + "','" + strNome + "','" + strSenha + "');";

            Statement stmSqlInsert = conn.createStatement();
            stmSqlInsert.addBatch(strSqlInsert);
            stmSqlInsert.executeBatch();

            System.out.println("Ok! Login inserido com sucesso\n");
            System.out.println("Retornando ao Menu Principal.");
            menuPrincipal();

            scnCadLogin.close();
            scnCadNome.close();
            scnCadSenha.close();

        } catch (Exception e) {
            System.err.println("Ops! Ocorreu um erro: " + e);
            System.out.println("Não foi possível realizar o cadastro devido a um erro. ");
            System.out.println("Retornando ao Menu Principal.");
            menuPrincipal();
        }
    }

    public static String telaLogin() {
        Connection conn = App.conectar();
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scnLoginAcesso, scnSenhaAcesso;

        String strBuscaLogin, strBuscaPw;

        try {
            System.out.println("\nDigite o login de acesso: ");
            scnLoginAcesso = new Scanner(System.in);
            strBuscaLogin = scnLoginAcesso.nextLine();

            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("\nDigite a senha de acesso: ");
            scnSenhaAcesso = new Scanner(System.in);
            strBuscaPw = scnSenhaAcesso.nextLine();

            System.out.print("\033[H\033[2J");
            System.out.flush();

            String strSqlSelect = "SELECT * FROM `mysql_conector`.`tbl_login` WHERE `login` = '" + strBuscaLogin + "' AND `senha` = '" + strBuscaPw + "';";

            scnLoginAcesso.close();
            scnSenhaAcesso.close();

            Statement stmSqlSelect = conn.createStatement();
            ResultSet rsSqlSelect = stmSqlSelect.executeQuery(strSqlSelect);

            if (rsSqlSelect.next()) {
                System.out.println("Login bem-sucedido!\n");
                return strBuscaLogin;
            } else {
                System.out.println("Login ou senha incorretos.");
                return telaLogin();
            }

        } catch (Exception e) {
            System.err.println("Ops! Ocorreu um erro: " + e);
            return null;
        }
    }
}