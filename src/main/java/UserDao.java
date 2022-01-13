import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class UserDao {

    public static void main(String[] args) throws SQLException {


        menu();

    }

    public static void insertUser() throws SQLException {
        System.out.println("\nWelcome. Create new account.");
        System.out.println("Type e-mail.");
        Scanner scanCredentials = new Scanner(System.in);
        String email = scanCredentials.nextLine();
        System.out.println("Type login.");
        String login = scanCredentials.nextLine();
        System.out.println("Type password");
        String password = BCrypt.hashpw(scanCredentials.nextLine(), BCrypt.gensalt());

        try (Connection connection = DbUtil.connect("workshop2")) {
            DbUtil.insertData(connection, "users", email, login, password);
            System.out.println("New user added.");
        }

    }

    public static void removeUser() throws SQLException {
        System.out.println("\nType id of user to remove.");
        Scanner scanID = new Scanner(System.in);
        int id = scanID.nextInt();

        try (Connection connection = DbUtil.connect("workshop2")) {
            DbUtil.remove(connection, "users", id);
            System.out.println("User " + id + " removed.");

        }

    }

    public static void listUser() throws SQLException {
        System.out.println("\nType ID of user to list credentials.");
        Scanner scanID = new Scanner(System.in);
        int id = scanID.nextInt();

        try (Connection connection = DbUtil.connect("workshop2")) {
            DbUtil.list(connection, "users", id);
        }

    }

    public static void listAllUsers() throws SQLException {
        try (Connection connection = DbUtil.connect("workshop2")) {
            DbUtil.listAll(connection, "users");
        }

    }

    public static void changeUserData() throws SQLException {
        Scanner scan2 = new Scanner(System.in);
        System.out.println("\nType ID of user to change data");
        int id = scan2.nextInt();
        System.out.println("\nWhich data do you want to update: \nemail - type e\nusername - type u\npassword - type p\ntype anything to go back to menu");
        Scanner scan1 = new Scanner(System.in);
        String scanOperation = scan1.nextLine();
        try (Connection connection = DbUtil.connect("workshop2")) {
            if ("e".equals(scanOperation)) {
                System.out.println("\nType new email");
                DbUtil.changeData(connection, "email", id,  scan1.nextLine());
            } else if ("u".equals(scanOperation)) {
                System.out.println("\nType new username");
                DbUtil.changeData(connection, "username", id,  scan1.nextLine());
            } else if ("p".equals(scanOperation)) {
                System.out.println("Type new password");
                DbUtil.changeData(connection, "password", id,  BCrypt.hashpw(scan1.nextLine(), BCrypt.gensalt()));
            } else {
                menu();
            }
        }

    }

    public static void anyOtherAction() throws SQLException{
        System.out.println("\nWould you like to perform any other action? (press y/n)");
        Scanner menu =  new Scanner(System.in);
        String str =  menu.nextLine();
        switch (str){
            case "y":
                menu();
                break;
            case "n":
                break;
        }
    }
    public String hashPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());

    }

    public static void menu() throws SQLException {
        System.out.println("\nMENU:\ninsert new user - press i\nremove existing user - press r\nchange data - press c\nlist user - l\nlist all users - la\nexit - press e");
        Scanner menu =  new Scanner(System.in);
        String str =  menu.nextLine();
        switch (str){
            case "i":
                insertUser();
                break;
            case "r":
                removeUser();
                break;
            case "c":
                changeUserData();
                break;
            case "l":
                listUser();
                break;
            case "la":
                listAllUsers();
            case "e":
                break;
        }
        anyOtherAction();

    }


}
