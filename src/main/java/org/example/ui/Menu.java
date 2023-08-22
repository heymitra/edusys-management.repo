package org.example.ui;

import org.example.entity.*;
import org.example.entity.enumeration.UserRoleEnum;
import org.example.util.ApplicationContext;
import org.example.util.Constant;
import org.example.util.SecurityContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    static Scanner scanner = new Scanner(System.in);
    public static boolean stop = false;

    public void run() throws SQLException {
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

    public void register() throws SQLException {
        Printer.printMsg(Constant.SET_USERNAME_PASSWORD, true);

        Printer.printMsg(Constant.ASK_FOR_USERNAME, false);
        String username = scanner.next().trim();
        while (!ApplicationContext.getUserCredentialService().isUsernameAvailable(username)) {
            Printer.printMsg(Constant.DUPLICATED_USERNAME, true);
            Printer.printMsg(Constant.ANOTHER_USERNAME, false);
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
        int semester = 0;
        while (true) {
            switch (new Scanner(System.in).next().trim()) {
                case "1":
                    userRoleEnum = UserRoleEnum.STUDENT;
                    semester = 1;
                    setRegistrationInfo(name, surname, userRoleEnum, position, semester, userCredential);
                    studentMenu();
                    break;
                case "2":
                    userRoleEnum = UserRoleEnum.PROFESSOR;
                    Printer.printMenu(Constant.POSITION_MENU, Constant.POSITION);
                    Printer.printWarning(Constant.ASK_CHOICE);
                    int choice = Integer.parseInt(new Scanner(System.in).next().trim());
                    position = userRoleEnum.getPositions()[choice];
                    setRegistrationInfo(name, surname, userRoleEnum, position, semester, userCredential);
                    professorMenu();
                    break;
                case "3":
                    userRoleEnum = UserRoleEnum.EMPLOYEE;
                    setRegistrationInfo(name, surname, userRoleEnum, position, semester, userCredential);
                    employeeMenu();
                    break;
                default:
                    Printer.printWarning(Constant.INVALID_INPUT);
                    continue;
            }
            break;
        }
    }

    public void setRegistrationInfo(String name, String surname, UserRoleEnum userRoleEnum, String position, int semester, UserCredential userCredential) {
        UserInfo userInfo = new UserInfo(name, surname, userRoleEnum, position, semester, userCredential);

        ApplicationContext.getUserInfoService().save(userInfo);

        SecurityContext.username = userCredential.getUsername();
        SecurityContext.userInfo = userInfo;
    }

    public void login() throws SQLException {
        Printer.printMsg(Constant.ASK_FOR_USERNAME, false);
        String username = scanner.next().trim();
        while (ApplicationContext.getUserCredentialService().isUsernameAvailable(username)) {
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

        Printer.printMsg(Constant.WELCOME_BACK + userInfo.getName(), true);
        SecurityContext.username = username;
        SecurityContext.userInfo = userInfo;

        switch (userInfo.getRole()) {
            case STUDENT:
                studentMenu();
                break;
            case EMPLOYEE:
                employeeMenu();
                break;
            case PROFESSOR:
                professorMenu();
                break;
        }
    }

    private void studentMenu() throws SQLException {
        Printer.printWarning(Constant.STUDENT_ACCESS);
        while (true) {
            Printer.printMenu(Constant.STUDENT_MENU, Constant.MENU);
            Printer.printWarning(Constant.ASK_CHOICE);
            switch (new Scanner(System.in).next().trim()) {
                case "1":
                    viewProfile();
                    break;
                case "2":
                    viewCourseList();
                    break;
                case "3":
                    selectCourse();
                    break;
                case "4":
                    viewTakenCourseList();
                    break;
                case "5":
                    logout();
                    break;
                default:
                    invalidInput();
                    break;
            }
        }
    }

    private void employeeMenu() {

    }

    private void professorMenu() {

    }

    private void viewProfile() {
        System.out.println(SecurityContext.userInfo);
        if (SecurityContext.userInfo.getRole() == UserRoleEnum.PROFESSOR)
            System.out.println("\t" + SecurityContext.userInfo.getProfessorPosition());
        else if (SecurityContext.userInfo.getRole() == UserRoleEnum.STUDENT)
            System.out.println("\t" + SecurityContext.userInfo.getStudentSemester());
    }

    private void viewCourseList() {
        System.out.println(ApplicationContext.getAvailableCourseService().loadAll());
    }

    private void selectCourse() throws SQLException {
        UserInfo student = SecurityContext.userInfo;
        double semesterGPA = calculateGPA(student.getId(), student.getStudentSemester() - 1);
        int currentCredits = ApplicationContext.getSelectedCourseService().getTakenCredits(student.getId(), student.getStudentSemester());

        int maxCredits = (student.getStudentSemester() == 0 || semesterGPA < 18) ? 20 : 24;
        int remainedCredits = maxCredits - currentCredits;

        Printer.printMsg(Constant.MAX_CREDITS_ALLOWED + maxCredits, true);
        Printer.printMsg(Constant.SELECT_COURSE, true);

        while (remainedCredits > 0) {
            Printer.printMsg(Constant.REMAINED_CREDITS + remainedCredits, true);
            Printer.printMsg(Constant.ENTER_COURSE_ID, false);

            String courseID = scanner.next();

            if (courseID.trim().equalsIgnoreCase("exit")) {
                studentMenu();
            }

            Optional<AvailableCourse> courseOptional = ApplicationContext.getAvailableCourseService().findById(Long.valueOf(courseID));
            if (courseOptional.isPresent()) {
                AvailableCourse availableCourse = courseOptional.get();
                String courseTitle = availableCourse.getCourseTitle();
                int currentSemester  = student.getStudentSemester();

                if (ApplicationContext.getSelectedCourseService().hasTakenCourseInCurrentSemester(student.getId(), courseTitle, currentSemester)) {
                    Printer.printWarning(Constant.DUPLICATED_COURSE);
                    selectCourse();
                } else if (currentSemester != 1 && ApplicationContext.getSelectedCourseService().hasPassedCourseInPreviousSemesters(student.getId(), courseTitle, currentSemester)) {
                    Printer.printWarning(Constant.PASSED_COURSE);
                    selectCourse();
                }
                int courseCredit = availableCourse.getCredit();
                if (remainedCredits >= courseCredit) {
                    remainedCredits -= courseCredit;

                    SelectedCourse selectedCourse = new SelectedCourse();
                    selectedCourse.setCourse(availableCourse);
                    selectedCourse.setStudentInfo(student);
                    selectedCourse.setStudentSemester(currentSemester);
                    ApplicationContext.getSelectedCourseService().save(selectedCourse);
                    Printer.printMsg(Constant.COURSE_TAKEN, true);
                } else {
                    Printer.printMsg(Constant.CREDIT_LIMIT, true);
                }
            } else
                invalidInput(); //course is not present
        }
    }

    private Double calculateGPA(Long studentId, int semester) {
        return ApplicationContext.getSelectedCourseService().calculateGPA(studentId, semester);
    }

    private void viewTakenCourseList() {
        UserInfo student = SecurityContext.userInfo;
        List<CourseInfoDTO> courseInfoDTOS = ApplicationContext.getSelectedCourseService().viewTakenCourseListByStudent(student.getId(), student.getStudentSemester());
        if (courseInfoDTOS.isEmpty())
            Printer.printMsg(Constant.NO_COURSE, true);
        else
            System.out.println(courseInfoDTOS);
    }

    private void logout() throws SQLException {
        SecurityContext.username = null;
        SecurityContext.userInfo = null;
        run();
    }

    private void invalidInput() {
        Printer.printWarning(Constant.INVALID_INPUT);
    }
}
