package com.example.Studentdataportal.Repositorys;

import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourseEntity,Long> {

    StudentCourseEntity findByStudentidAndCourseid(StudentEntity studentId, CourseEntity courseId);

    public  boolean existsByStudentidAndCourseid(StudentEntity studentId, CourseEntity courseId);

    public  boolean existsByStudentid(StudentEntity studentId);


    List<StudentCourseEntity>findAllByStudentid(StudentEntity studentid);

    StudentCourseEntity getByStudentidAndCourseid(StudentEntity studentId, CourseEntity courseId);
}
