package org.example.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.repository.AvailableCourseRepository;
import org.example.repository.SelectedCourseRepository;
import org.example.repository.UserCredentialRepository;
import org.example.repository.UserInfoRepository;
import org.example.repository.impl.AvailableCourseRepositoryImpl;
import org.example.repository.impl.SelectedCourseRepositoryImpl;
import org.example.repository.impl.UserCredentialRepositoryImpl;
import org.example.repository.impl.UserInfoRepositoryImpl;
import org.example.service.AvailableCourseService;
import org.example.service.SelectedCourseService;
import org.example.service.UserCredentialService;
import org.example.service.UserInfoService;
import org.example.service.impl.AvailableCourseServiceImpl;
import org.example.service.impl.SelectedCourseServiceImpl;
import org.example.service.impl.UserCredentialServiceImpl;
import org.example.service.impl.UserInfoServiceImpl;

public class ApplicationContext {
    private static EntityManager em;
    private static EntityManagerFactory emf;

    private static UserCredentialRepository userCredentialRepository;
    private static UserCredentialService userCredentialService;

    private static UserInfoRepository userInfoRepository;
    private static UserInfoService userInfoService;

    private static AvailableCourseRepository availableCourseRepository;
    private static AvailableCourseService availableCourseService;

    private static SelectedCourseRepository selectedCourseRepository;
    private static SelectedCourseService selectedCourseService;

    static {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();

        userCredentialRepository = new UserCredentialRepositoryImpl(em);
        userCredentialService = new UserCredentialServiceImpl(userCredentialRepository);

        userInfoRepository = new UserInfoRepositoryImpl(em);
        userInfoService = new UserInfoServiceImpl(userInfoRepository);

        availableCourseRepository = new AvailableCourseRepositoryImpl(em);
        availableCourseService = new AvailableCourseServiceImpl(availableCourseRepository);

        selectedCourseRepository = new SelectedCourseRepositoryImpl(em);
        selectedCourseService = new SelectedCourseServiceImpl(selectedCourseRepository);
    }

    public static UserCredentialService getUserCredentialService() {
        return userCredentialService;
    }

    public static UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public static AvailableCourseService getAvailableCourseService() {
        return availableCourseService;
    }

    public static SelectedCourseService getSelectedCourseService() {
        return selectedCourseService;
    }
}
