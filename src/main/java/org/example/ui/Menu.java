package org.example.ui;

import org.example.entity.UserCredential;
import org.example.entity.UserInfo;
import org.example.entity.enumeration.UserRoleEnum;
import org.example.util.ApplicationContext;
import org.example.util.Constant;
import org.example.util.SecurityContext;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    static Scanner scanner = new Scanner(System.in);
    public static boolean stop = false;

    public static void run() throws SQLException {
        Printer.printWarning(Constant.SAY_HI);
        while (true) {
            Printer.printMenu(Constant.INITIAL_MENU, Constant.HOME_PAGE);
            Printer.printWarning(Constant.ASK_CHOICE);
            switch (new Scanner(System.in).next().trim()) {
                case "1":
                    register();
                    break;
                case "2":
                    login();
                    break;
                case "3": {
                    stop = true;
                    Printer.printWarning(Constant.SAY_BYE);
                    System.exit(0);
                    break;
                }
                default:
                    invalidInput();
                    break;
            }
        }
    }

    public static void register() throws SQLException {
        Printer.printMsg(Constant.SET_USERNAME_PASSWORD, true);

        Printer.printMsg(Constant.ASK_FOR_USERNAME, false);
        String username = scanner.next().trim();
        while (ApplicationContext.getUserCredentialService().isUsernameAvailable(username)) {
            Printer.printMsg(Constant.DUPLICATED_USERNAME, true);
            Printer.printMsg(Constant.ARESK_FOR_USERNAME, false);
            username = scanner.next().trim();
        }

        Printer.printMsg(Constant.ASK_FOR_PASSWORD, false);
        String password = scanner.next().trim();

        UserCredential userCredential = new UserCredential(username, password);
        UserCredential addedUser = ApplicationContext.getUserCredentialService().save(userCredential);


        if (addedUser == null) {
            Printer.printMsg(Constant.REGISTRATION_FAILURE, true);
            run();
        }

        Printer.printMsg(Constant.LOGIN_MESSAGE, true);
        Printer.printMsg(Constant.ASK_FOR_NAME, false);
        String name = scanner.next().trim();
        Printer.printMsg(Constant.ASK_FOR_SURNAME, false);
        String surname = scanner.next().trim();

        Printer.printMenu(Constant.ROLE_MENU, Constant.ROLE);
        Printer.printWarning(Constant.ASK_CHOICE);

        UserRoleEnum userRoleEnum;
        String position = null;
        while (true) {
            switch (new Scanner(System.in).next().trim()) {
                case "1":
                    userRoleEnum = UserRoleEnum.STUDENT;
                    break;
                case "2":
                    userRoleEnum = UserRoleEnum.PROFESSOR;
                    Printer.printMenu(Constant.POSITION_MENU, Constant.POSITION);
                    Printer.printWarning(Constant.ASK_CHOICE);
                    int choice = Integer.parseInt(new Scanner(System.in).next().trim());
                    position = userRoleEnum.getPositions()[choice];
                    break;
                case "3":
                    userRoleEnum = UserRoleEnum.EMPLOYEE;
                    break;
                default:
                    Printer.printWarning(Constant.INVALID_INPUT);
                    continue;
            }
            break;
        }

        UserInfo userInfo = new UserInfo(name, surname, userRoleEnum, position, userCredential);

        ApplicationContext.getUserInfoService().save(userInfo);

        SecurityContext.username = username;
    }

    public static void login() throws SQLException {
        Printer.printMsg(Constant.ASK_FOR_USERNAME, false);
        String username = scanner.next().trim();
        while (!ApplicationContext.getUserCredentialService().isUsernameAvailable(username)) {
            Printer.printMsg(Constant.WRONG_USERNAME, true);
            Printer.printMenu(Constant.WRONG_USERNAME_MENU, Constant.LINE);
            while (true) {
                switch (new Scanner(System.in).next().trim()) {
                    case "1":
                        login();
                        break;
                    case "2":
                        register();
                        break;
                    default:
                        invalidInput();
                        continue;
                }
                break;
            }
        }

        Printer.printMsg(Constant.ASK_FOR_PASSWORD, false);
        String password = scanner.next().trim();

        UserInfo userInfo = ApplicationContext.getUserCredentialService().authenticateUser(username, password);
        if (userInfo == null) {
            Printer.printMsg(Constant.WRONG_PASSWORD, true);
            run();
        }

        Printer.printMsg(Constant.WELCOME_BACK + userInfo.getName(),true);
        SecurityContext.username = username;

        switch (userInfo.getRole()) {
            case STUDENT:
                studentMenu(userInfo);
                break;
            case EMPLOYEE:
                employeeMenu(userInfo);
                break;
            case PROFESSOR:
                professorMenu(userInfo);
                break;
        }
    }

    private static void studentMenu(UserInfo student) {

    }

    private static void employeeMenu(UserInfo employee) {

    }

    private static void professorMenu(UserInfo professor) {

    }



    private static void invalidInput() {
        System.out.println(Constant.INVALID_INPUT);
    }
}
