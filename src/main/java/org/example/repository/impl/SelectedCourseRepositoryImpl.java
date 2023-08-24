package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.base.repository.impl.BaseRepositoryImpl;
import org.example.entity.DTO.CourseInfoDTO;
import org.example.entity.DTO.StudentInfoDTO;
import org.example.entity.SelectedCourse;
import org.example.repository.SelectedCourseRepository;

import java.util.List;

public class SelectedCourseRepositoryImpl extends BaseRepositoryImpl<SelectedCourse, Long>
        implements SelectedCourseRepository {

    public SelectedCourseRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public SelectedCourse save(SelectedCourse entity) {
        entityManager.persist(entity);
        return entity;
    }

    public Class<SelectedCourse> getEntityClass() {
        return SelectedCourse.class;
    }

    @Override
    public Double calculateGPA(Long studentId, int semester) {
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT SUM(s.grade * s.course.credit), SUM(s.course.credit) " +
                        "FROM SelectedCourse s " +
                        "WHERE s.studentInfo.id = :studentId AND s.studentSemester = :semester",
                Object[].class
        );

        query.setParameter("studentId", studentId);
        query.setParameter("semester", semester);

        Object[] result = query.getSingleResult();
        if (result[1] != null && (Long) result[1] > 0) {
            double totalWeightedGrade = (Double) result[0];
            double totalCreditHours = (Long) result[1];
            return totalWeightedGrade / totalCreditHours;
        } else
            return 0.0; // Return 0 if there are no courses or total credit hours is 0.
    }

    @Override
    public int getTakenCredits(Long studentId, int semester) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT SUM(s.course.credit) FROM SelectedCourse s " +
                        "WHERE s.studentInfo.id = :studentId AND s.studentSemester = :semester",
                Long.class
        );

        query.setParameter("studentId", studentId);
        query.setParameter("semester", semester);

        Long result = query.getSingleResult();
        return result != null ? result.intValue() : 0; // Return result if not null, otherwise return 0
    }

    @Override
    public boolean hasPassedCourseInPreviousSemesters(Long studentId, String courseTitle, int currentSemester) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(sc) FROM SelectedCourse sc " +
                        "WHERE sc.studentInfo.id = :studentId AND sc.course.courseTitle = :courseTitle AND sc.studentSemester < :currentSemester AND sc.passed = true",
                Long.class
        );
        query.setParameter("studentId", studentId);
        query.setParameter("courseTitle", courseTitle);
        query.setParameter("currentSemester", currentSemester);

        return query.getSingleResult() > 0;
    }

    @Override
    public boolean hasTakenCourseInCurrentSemester(Long studentId, String courseTitle, int currentSemester) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(sc) FROM SelectedCourse sc " +
                        "WHERE sc.studentInfo.id = :studentId AND sc.course.courseTitle = :courseTitle AND sc.studentSemester = :currentSemester",
                Long.class
        );
        query.setParameter("studentId", studentId);
        query.setParameter("courseTitle", courseTitle);
        query.setParameter("currentSemester", currentSemester);

        return query.getSingleResult() > 0;
    }

    @Override
    public List<CourseInfoDTO> viewTakenCourseListByStudent(Long studentId, int currentSemester) {
        TypedQuery<CourseInfoDTO> query = entityManager.createQuery(
                "SELECT NEW org.example.entity.DTO.CourseInfoDTO(ac.courseTitle, ac.professorInfo.surname, ac.credit, sc.grade, sc.passed) " +
                        "FROM SelectedCourse sc " +
                        "JOIN sc.course ac " +
                        "JOIN ac.professorInfo prof " +
                        "WHERE sc.studentInfo.id = :studentId AND sc.studentSemester = :currentSemester",
                CourseInfoDTO.class
        );

        query.setParameter("studentId", studentId);
        query.setParameter("currentSemester", currentSemester);

        return query.getResultList();
    }

    @Override
    public List<StudentInfoDTO> findStudentsByCourseId(Long courseId) {
        TypedQuery<StudentInfoDTO> query = entityManager.createQuery(
                "SELECT NEW org.example.entity.DTO.StudentInfoDTO(u.name, u.surname, s.studentInfo.id, s.grade, s.evaluated) " +
                        "FROM SelectedCourse s " +
                        "JOIN UserInfo u ON s.studentInfo.id = u.id " +
                        "WHERE s.course.id = :courseId",
                StudentInfoDTO.class
        );

        query.setParameter("courseId", courseId);

        return query.getResultList();
    }

    @Override
    public SelectedCourse findToBeEvaluatedRecord(Long studentId, Long courseId) {
        TypedQuery<SelectedCourse> query = entityManager.createQuery(
                "SELECT sc FROM SelectedCourse sc " +
                        "WHERE sc.studentInfo.id = :studentId AND sc.course.id = :courseId",
                SelectedCourse.class
        );

        query.setParameter("studentId", studentId);
        query.setParameter("courseId", courseId);

        return query.getSingleResult();
    }

    @Override
    public boolean areAllEvaluated(Long studentId, int currentTerm) {
        TypedQuery<Boolean> query = entityManager.createQuery(
                "SELECT CASE WHEN COUNT(sc) = 0 THEN false ELSE " +
                        "CASE WHEN COUNT(sc) = SUM(CASE WHEN sc.evaluated = true THEN 1 ELSE 0 END) THEN true ELSE false END END " +
                        "FROM SelectedCourse sc " +
                        "WHERE sc.studentInfo.id = :studentId " +
                        "AND sc.studentSemester = :currentTerm",
                Boolean.class
        );

        query.setParameter("studentId", studentId);
        query.setParameter("currentTerm", currentTerm);

        return query.getSingleResult();
    }
}
