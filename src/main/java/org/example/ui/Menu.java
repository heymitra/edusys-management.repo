package org.example.ui;

import org.example.entity.*;
import org.example.entity.DTO.CourseInfoDTO;
import org.example.entity.DTO.OfferedCoursesByProfessorDTO;
import org.example.entity.DTO.UserInfoDTO;
import org.example.entity.DTO.StudentInfoDTO;
import org.example.entity.enumeration.UserRoleEnum;
import org.example.util.ApplicationContext;
import org.example.util.Constant;
import org.example.util.SecurityContext;

import java.sql.SQLException;
import java.util.*;

public class Menu {
    static Scanner scanner = new Scanner(System.in);
    public static boolean stop = false;

    public void start() throws SQLException {
        Printer.printWarning(Constant.SAY_HI);
        run();
    }

    public void run() throws SQLException {
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

        String username = getUsername();

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
        String name = scanner.nextLine().trim();
        Printer.printMsg(Constant.ASK_FOR_SURNAME, false);
        String surname = scanner.nextLine().trim();

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
                    position = setPosition();
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

    private String getUsername() {
        Printer.printMsg(Constant.ASK_FOR_USERNAME, false);
        String username = scanner.next().trim();
        while (!ApplicationContext.getUserCredentialService().isUsernameAvailable(username)) {
            Printer.printMsg(Constant.DUPLICATED_USERNAME, true);
            Printer.printMsg(Constant.ANOTHER_USERNAME, false);
            username = scanner.next().trim();
        }
        return username;
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
                setCurrentTerm();
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
        Printer.printWarning(Constant.ACCESS + "student: ");
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

    private void professorMenu() throws SQLException {
        Printer.printWarning(Constant.ACCESS + "professor: ");
        while (true) {
            Printer.printMenu(Constant.PROFESSOR_MENU, Constant.MENU);
            Printer.printWarning(Constant.ASK_CHOICE);
            switch (new Scanner(System.in).next().trim()) {
                case "1":
                    viewProfile();
                    break;
                case "2":
                    recordGrade();
                    break;
                case "3":
                    payrollSlip(SecurityContext.userInfo.getId());
                    break;
                case "4":
                    logout();
                    break;
                default:
                    invalidInput();
                    break;
            }
        }
    }

    private void employeeMenu() throws SQLException {
        Printer.printWarning(Constant.ACCESS + "employee: ");
        while (true) {
            Printer.printMenu(Constant.EMPLOYEE_MENU, Constant.MENU);
            Printer.printWarning(Constant.ASK_CHOICE);
            switch (new Scanner(System.in).next().trim()) {
                case "1":
                    Printer.printMsg(Constant.STUDENT_EDIT, true);
                    editUsers(UserRoleEnum.STUDENT);
                    break;
                case "2":
                    Printer.printMsg(Constant.PROFESSOR_EDIT, true);
                    editUsers(UserRoleEnum.PROFESSOR);
                    break;
                case "3":
                    Printer.printMsg(Constant.EMPLOYEE_EDIT, true);
                    editUsers(UserRoleEnum.EMPLOYEE);
                    break;
                case "4":
                    Printer.printMsg(Constant.COURSE_EDIT, true);
                    accessToCourse();
                    break;
                case "5":
                    payrollSlip(getUser(UserRoleEnum.PROFESSOR));
                    break;
                case "6":
                    logout();
                    break;
                default:
                    invalidInput();
                    break;
            }
        }
    }

    private void editUsers(UserRoleEnum role) throws SQLException {

        while (true) {
            Printer.printMenu(Constant.PERSON_EDIT_MENU, Constant.MENU);
            Printer.printWarning(Constant.ASK_CHOICE);
            switch (new Scanner(System.in).next().trim()) {
                case "1":
                    addUser(role);
                    break;
                case "2":
                    deleteUser(role);
                    break;
                case "3":
                    editeUser(role);
                    break;
                case "4":
                    employeeMenu();
                    break;
                default:
                    invalidInput();
                    break;
            }
        }
    }

    private void addUser(UserRoleEnum role) throws SQLException {
        UserCredential userCredential = new UserCredential();
        UserInfo userInfo = new UserInfo();
        String username = getUsername();
        Printer.printMsg(Constant.ASK_FOR_PASSWORD, false);
        String password = new Scanner(System.in).next().trim();
        Printer.printMsg(Constant.ASK_FOR_NAME, false);
        String name = new Scanner(System.in).nextLine().trim();
        Printer.printMsg(Constant.ASK_FOR_SURNAME, false);
        String surname = new Scanner(System.in).nextLine().trim();
        userCredential.setUsername(username);
        userCredential.setPassword(password);
        userInfo.setName(name);
        userInfo.setSurname(surname);
        userInfo.setRole(role);
        String position = null;
        if (Objects.equals(role.toString(), "PROFESSOR")) {
            position = setPosition();
        }
        int currentTerm = 0;
        if (Objects.equals(role.toString(), "STUDENT")) {
            Printer.printMsg(Constant.ASK_SEMESTER, false);
            currentTerm = new Scanner(System.in).nextInt();
        }
        userInfo.setProfessorPosition(position);
        userInfo.setStudentTerm(currentTerm);
        UserCredential savedUserCredential = ApplicationContext.getUserCredentialService().save(userCredential);
        userInfo.setUserCredential(savedUserCredential);
        ApplicationContext.getUserInfoService().save(userInfo);
        employeeMenu();
    }

    private void deleteUser(UserRoleEnum role) {
        Long toBeDeletedID = getUser(role);
        if (toBeDeletedID == null) {
            Printer.printWarning(Constant.USER_NOT_FOUND);
            return;
        }
        if (role == UserRoleEnum.EMPLOYEE && Objects.equals(toBeDeletedID, SecurityContext.userInfo.getId())) {
            Printer.printWarning(Constant.DELETE_YOURSELF);
            return;
        }
        Optional<UserInfo> userOptional = ApplicationContext.getUserInfoService().findById(toBeDeletedID);
        if (userOptional.isPresent()) {
            ApplicationContext.getUserInfoService().delete(userOptional.get());
        } else {
            Printer.printWarning(Constant.USER_NOT_FOUND);
        }
    }

    private void editeUser(UserRoleEnum role) throws SQLException {
        Long toBeDeletedID = getUser(role);
        if (toBeDeletedID == null) {
            employeeMenu();
        }
        Optional<UserInfo> userOptional = ApplicationContext.getUserInfoService().findById(toBeDeletedID);
        if (userOptional.isPresent()) {
            UserInfo toBeEditedUser = userOptional.get();

            Printer.printMsg(Constant.ASK_FOR_NAME, false);
            Printer.printMsg(Constant.TO_SKIP, false);
            String name = new Scanner(System.in).nextLine().trim();
            if (!name.equals("-")) {
                toBeEditedUser.setName(name);
            }
            Printer.printMsg(Constant.ASK_FOR_SURNAME, false);
            Printer.printMsg(Constant.TO_SKIP, false);
            String surname = new Scanner(System.in).nextLine().trim();
            if (!surname.equals("-")) {
                toBeEditedUser.setName(surname);
            }
//            Printer.printMenu(Constant.ROLE_MENU, Constant.ROLE);
//            Printer.printWarning(Constant.ASK_CHOICE);
            UserRoleEnum userRoleEnum;
            String position;
            int semester;

            switch (role) {
                case STUDENT:
                    Printer.printMsg(Constant.ASK_SEMESTER, false);
                    Printer.printMsg(Constant.TO_SKIP, false);
                    semester = new Scanner(System.in).nextInt();
                    toBeEditedUser.setStudentTerm(semester);
                    break;
                case PROFESSOR:
                    position = setPosition();
                    toBeEditedUser.setProfessorPosition(position);
                    break;
                default:
                    break;
            }

            ApplicationContext.getUserInfoService().update(toBeEditedUser);
        } else {
            Printer.printWarning(Constant.USER_NOT_FOUND);
            employeeMenu();
        }
    }

    private String setPosition() {
        String position = null;
        while (position == null) {
            Printer.printMenu(Constant.POSITION_MENU, Constant.POSITION);
            Printer.printWarning(Constant.ASK_CHOICE);
            try {
                int choice = Integer.parseInt(new Scanner(System.in).next().trim());
                position = UserRoleEnum.PROFESSOR.getPositions()[choice - 1];
            } catch (NumberFormatException nfe) {
                Printer.printWarning(Constant.NUMBER_FORMAT);
            } catch (IndexOutOfBoundsException iob) {
                Printer.printWarning(Constant.OUT_OF_BOUND);
            }
        }
        return position;
    }

    private void accessToCourse() throws SQLException {
        while (true) {
            Printer.printMenu(Constant.COURSE_EDIT_MENU, Constant.MENU);
            Printer.printWarning(Constant.ASK_CHOICE);
            switch (new Scanner(System.in).next().trim()) {
                case "1":
                    addCourse();
                    break;
                case "2":
                    deleteCourse();
                    break;
                case "3":
                    editCourse();
                    break;
                case "4":
                    employeeMenu();
                    break;
                default:
                    invalidInput();
                    break;
            }
        }
    }

    private void deleteCourse() throws SQLException {
        Long toBeDeletedCourseId = getCourseID();
        if (toBeDeletedCourseId == null) return;

        Optional<AvailableCourse> courseOptional = ApplicationContext.getAvailableCourseService().findById(toBeDeletedCourseId);
        if (courseOptional.isPresent()) {
            ApplicationContext.getAvailableCourseService().delete(courseOptional.get());
        } else {
            Printer.printWarning(Constant.COURSE_NOT_FOUND);
            accessToCourse();
        }
    }

    private Long getCourseID() {
        List<AvailableCourse> availableCourses = ApplicationContext.getAvailableCourseService().loadAll();
        if (availableCourses.isEmpty()) {
            Printer.printWarning(Constant.NO_AVAILABLE_COURSE);
            return null;
        }
        System.out.println(availableCourses);
        Printer.printMsg(Constant.ASK_COURSE_ID, false);
        Long courseId = new Scanner(System.in).nextLong();

        boolean courseIdExists = false;

        for (AvailableCourse course : availableCourses) {
            if (course.getId().equals(courseId)) {
                courseIdExists = true;
                break;
            }
        }

        if (!courseIdExists) {
            Printer.printWarning(Constant.COURSE_NOT_FOUND);
            return null;
        }
        return courseId;
    }

    private void editCourse() {
        Long toBeEditedCourseID = getCourseID();
        if (toBeEditedCourseID == null) return;
        Optional<AvailableCourse> courseOptional = ApplicationContext.getAvailableCourseService().findById(toBeEditedCourseID);
        if (courseOptional.isPresent()) {
            AvailableCourse toBeEditedCourse = courseOptional.get();

            Printer.printMsg(Constant.ASK_TITLE, false);
            Printer.printMsg(Constant.TO_SKIP, false);
            String title = new Scanner(System.in).nextLine().trim();
            if (!title.equals("-")) {
                toBeEditedCourse.setCourseTitle(title);
            }

            Printer.printMsg(Constant.ASK_CREDIT, false);
            int credit = 0;
            try {
                credit = new Scanner(System.in).nextInt();
                if (credit <= 0) {
                    Printer.printWarning(Constant.INVALID_CREDIT);
                } else {
                    toBeEditedCourse.setCredit(credit);
                }
            } catch (NumberFormatException nfe) {
                Printer.printWarning(Constant.INVALID_CREDIT);
            } catch (InputMismatchException ime) {
                Printer.printWarning(Constant.INVALID_INPUT);
            }

            Long professorID = getUser(UserRoleEnum.PROFESSOR);
            if (professorID == null) {
                Printer.printWarning(Constant.NO_PROFESSOR);
            }
            Optional<UserInfo> professorOptional = ApplicationContext.getUserInfoService().findById(professorID);
            professorOptional.ifPresent(toBeEditedCourse::setProfessorInfo);

            ApplicationContext.getAvailableCourseService().update(courseOptional.get());
        } else {
            Printer.printWarning(Constant.COURSE_NOT_FOUND);
        }
    }

    private void addCourse() {
        AvailableCourse availableCourse = new AvailableCourse();

        Printer.printMsg(Constant.ASK_TITLE, false);
        String courseTitle = new Scanner(System.in).nextLine().trim();

        Printer.printMsg(Constant.ASK_CREDIT, false);
        int credit = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                Printer.printMsg(Constant.ASK_CREDIT, false);
                credit = new Scanner(System.in).nextInt();
                isValidInput = true;
            } catch (InputMismatchException e) {
                invalidInput();
                // Clear the invalid input from the scanner
                new Scanner(System.in).nextLine();
            }
        }

//        Printer.printMsg(Constant.ASK_SEMESTER, false);
//        String semester = new Scanner(System.in).next().trim();;
//        availableCourse.setSemester(semester);


        Long professorId = getUser(UserRoleEnum.PROFESSOR);
        if (professorId == null) {
            Printer.printWarning(Constant.NO_PROFESSOR);
        }

        Optional<UserInfo> professorInfoOptional = ApplicationContext.getUserInfoService().findById(professorId);
        if (professorInfoOptional.isPresent()) {
            availableCourse.setProfessorInfo(professorInfoOptional.get());
        } else {
            Printer.printWarning(Constant.USER_NOT_FOUND);
        }

        availableCourse.setCourseTitle(courseTitle);
        availableCourse.setCredit(credit);

        ApplicationContext.getAvailableCourseService().save(availableCourse);
    }

    private Long getUser(UserRoleEnum role) {
        List<UserInfoDTO> users = ApplicationContext.getUserInfoService().findUsersByRole(role);
        if (users.isEmpty()) {
            Printer.printWarning(Constant.USER_NOT_FOUND);
            return null;
        }

        System.out.println(users);

        Long result = null;
        boolean userExists = false;

        while (!userExists) {
            try {
                Printer.printMsg(Constant.ASK_USER_ID, false);
                result = new Scanner(System.in).nextLong();

                for (UserInfoDTO user : users) {
                    if (user.getId().equals(result)) {
                        userExists = true;
                        break;
                    }
                }

                if (!userExists) {
                    Printer.printWarning(Constant.USER_NOT_FOUND);
                }
            } catch (InputMismatchException ime) {
                Printer.printWarning(Constant.INVALID_INPUT);
                // Clear the input buffer
                new Scanner(System.in).nextLine();
            }
        }

        return result;
    }

    private void viewProfile() {
        System.out.println(SecurityContext.userInfo);
        if (SecurityContext.userInfo.getRole() == UserRoleEnum.PROFESSOR)
            System.out.println("\t\tposition: " + SecurityContext.userInfo.getProfessorPosition());
        else if (SecurityContext.userInfo.getRole() == UserRoleEnum.STUDENT)
            System.out.println("\t\tsemester: " + SecurityContext.userInfo.getStudentTerm());
    }

    private void viewCourseList() {
        System.out.println(ApplicationContext.getAvailableCourseService().loadAll());
    }

    private void selectCourse() throws SQLException {
        UserInfo student = SecurityContext.userInfo;
        int currentSemester = student.getStudentTerm();
        double semesterGPA = calculateGPA(student.getId(), currentSemester - 1);
        int currentCredits = ApplicationContext.getSelectedCourseService().getTakenCredits(student.getId(), currentSemester);

        int maxCredits = (student.getStudentTerm() == 0 || semesterGPA < 18) ? 20 : 24;
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

            Optional<AvailableCourse> courseOptional;

            try {
                courseOptional = ApplicationContext.getAvailableCourseService().findById(Long.valueOf(courseID));
            } catch (NumberFormatException nfe) {
                courseOptional = Optional.empty();
            }

            if (courseOptional.isPresent()) {
                AvailableCourse availableCourse = courseOptional.get();
                String courseTitle = availableCourse.getCourseTitle();

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
                    SelectedCourse save = ApplicationContext.getSelectedCourseService().save(selectedCourse);
                    if (save == null) {
                        Printer.printWarning(Constant.ERROR_IN_TAKING_COURSE);
                        selectCourse();
                    }
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
        List<CourseInfoDTO> courseInfoDTOS = ApplicationContext.getSelectedCourseService().viewTakenCourseListByStudent(student.getId(), student.getStudentTerm());
        if (courseInfoDTOS.isEmpty())
            Printer.printWarning(Constant.NO_COURSE_TAKEN);
        else
            System.out.println(courseInfoDTOS);
    }

    private void recordGrade() throws SQLException {
        UserInfo professor = SecurityContext.userInfo;
        List<OfferedCoursesByProfessorDTO> offeredCourses = ApplicationContext.getAvailableCourseService().findCoursesByProfessorId(professor.getId());

        if (offeredCourses.isEmpty()) {
            Printer.printWarning(Constant.NO_COURSE_ASSIGNED);
            professorMenu();
        }
        System.out.println(offeredCourses);
        Printer.printMsg(Constant.ASK_COURSE_ID, false);

        Long courseId = 0L;

        try {
            courseId = new Scanner(System.in).nextLong();
        } catch (InputMismatchException ime) {
            Printer.printWarning(Constant.COURSE_NOT_FOUND);
            recordGrade();
        }

        boolean courseExists = false;
        for (OfferedCoursesByProfessorDTO course : offeredCourses) {
            if (course.getCourseId().equals(courseId)) {
                courseExists = true;
                break;
            }
        }

        if (courseExists) {
            recorder(courseId);
        } else {
            Printer.printWarning(Constant.COURSE_NOT_FOUND);
            recordGrade();
        }
    }


    private void recorder(Long courseId) throws SQLException {
        List<StudentInfoDTO> students = ApplicationContext.getSelectedCourseService().findStudentsByCourseId(courseId);
        if (students.isEmpty()) {
            Printer.printWarning(Constant.NO_STUDENT);
            professorMenu();
        }
        System.out.println(students);
        Printer.printMsg(Constant.ASK_STUDENT_ID, false);

        Long studentId = 0L;
        try {
            studentId = new Scanner(System.in).nextLong();
        } catch (InputMismatchException ime) {
            Printer.printWarning(Constant.USER_NOT_FOUND);
            recorder(courseId);
        }

        boolean studentExists = false;
        for (StudentInfoDTO student : students) {
            if (student.getStudentInfoId().equals(studentId)) {
                studentExists = true;
                break;
            }
        }

        if (studentExists) {
            StudentInfoDTO selectedStudent = new StudentInfoDTO();
            for (StudentInfoDTO student : students) {
                if (student.getStudentInfoId().equals(studentId)) {
                    selectedStudent = student;
                    break;
                }
            }
            if (selectedStudent.isEvaluated()) {
                Printer.printWarning(Constant.ALREADY_EVALUATED);
                recorder(courseId);
            }

            double grade = -1; // Initialize to a value outside the valid range

            while (grade < 0 || grade > 20) {
                Printer.printMsg(Constant.ASK_GRADE, false);

                try {
                    if (scanner.hasNextDouble()) {
                        grade = scanner.nextDouble();

                        if (grade < 0 || grade > 20) {
                            Printer.printMsg(Constant.OUT_OF_RANGE, true);
                        }
                    } else {
                        Printer.printMsg(Constant.INVALID_GRADE, true);
                        scanner.next(); // Clear the invalid input from the scanner
                    }
                } catch (InputMismatchException e) {
                    Printer.printMsg(Constant.INVALID_GRADE, true);
                    scanner.next(); // Clear the invalid input from the scanner
                }
            }


            SelectedCourse selectedCourse = ApplicationContext.getSelectedCourseService().findToBeEvaluatedRecord(studentId, courseId);
            selectedCourse.setEvaluated(true);
            selectedCourse.setGrade(grade);
            selectedCourse.setPassed(grade >= 10);

            ApplicationContext.getSelectedCourseService().update(selectedCourse);
        } else {
            Printer.printWarning(Constant.USER_NOT_FOUND);
            recorder(courseId);
        }
    }

    private void payrollSlip(Long professorInfoId) {
        Long professorCredits = ApplicationContext.getAvailableCourseService().getProfessorCredits(professorInfoId);
        Optional<UserInfo> professorOptional = ApplicationContext.getUserInfoService().findById(professorInfoId);
        int salary = 0;
        UserInfo professor = new UserInfo();
        if (professorOptional.isPresent()) {
            professor = professorOptional.get();
            String position = professor.getProfessorPosition();

            salary = (int) (1000000 * professorCredits);
            salary = (int) (1000000 * professorCredits);

            if (Objects.equals(position, "FACULTY")) {
                salary += 5000000;
            }
        }
        System.out.println(professor);
        Printer.printMsg(Constant.SALARY + salary, true);
    }

    private void setCurrentTerm() {
        int currentTerm = SecurityContext.userInfo.getStudentTerm();
        Long studentId = SecurityContext.userInfo.getId();
        boolean areAllEvaluated = ApplicationContext.getSelectedCourseService().areAllEvaluated(studentId, currentTerm);
        if (areAllEvaluated) {
            currentTerm += 1;
            SecurityContext.userInfo.setStudentTerm(currentTerm);
            SecurityContext.userInfo = ApplicationContext.getUserInfoService().update(SecurityContext.userInfo);
        }
    }

//    private void selectUser(UserRoleEnum role) {
//
//    }

    private void logout() throws SQLException {
        SecurityContext.username = null;
        SecurityContext.userInfo = null;
        run();
    }

    private void invalidInput() {
        Printer.printWarning(Constant.INVALID_INPUT);
    }
}


