package com.example.Studentdataportal.Services;

import com.example.Studentdataportal.DataObjects.StudentCourseDO;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.example.Studentdataportal.Repositorys.CourseRepository;
import com.example.Studentdataportal.Repositorys.StudentCourseRepository;
import com.example.Studentdataportal.Repositorys.StudentRepository;
import com.example.Studentdataportal.Util.Helper;
import com.example.Studentdataportal.Util.StudentCourseConvert;
import com.example.Studentdataportal.exception.EmptyFieldException;
import com.example.Studentdataportal.exception.NoDataSentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentCourseSercices {
        @Autowired
        StudentCourseRepository studentCourseRepository;
        @Autowired
        StudentCourseConvert studentCourseConvert;
        @Autowired
        StudentRepository studentRepository;
        @Autowired
        CourseRepository courseRepository;

        public void save(MultipartFile file, Long sem, String type,String date,String regulation) {

            try {
                List<StudentCourseEntity> studentCourseEntityList = Helper.excelToDb(file.getInputStream(),studentRepository,courseRepository,studentCourseRepository,sem,type, date,regulation);
              //  System.out.println(studentCourseEntityList);
                studentCourseRepository.saveAll(studentCourseEntityList);
            } catch (IOException e) {
                throw new RuntimeException("fail to store excel data: " + e.getMessage());
            }
        }

    public void saveadvsup(MultipartFile file, Long sem, String type,String data,String regulation) {
        try {
            List<StudentCourseEntity> studentCourseEntityList = Helper.excelToscEntityadvsup(file.getInputStream(),studentRepository,courseRepository,studentCourseRepository,sem,type, data,regulation);
            //  System.out.println(studentCourseEntityList);

            for(int i=0; i<studentCourseEntityList.size() ; i++){
                StudentCourseEntity studentCourseEntity = studentCourseRepository.getByStudentidAndCourseid(studentCourseEntityList.get(i).getStudentid(),studentCourseEntityList.get(i).getCourseid());
                studentCourseEntity.setGrade(studentCourseEntityList.get(i).getGrade());
                studentCourseEntity.setSemester(studentCourseEntityList.get(i).getSemester());
                studentCourseEntity.setExamdate(studentCourseEntityList.get(i).getExamdate());
                studentCourseRepository.save(studentCourseEntity);
            }
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }


        public List<StudentCourseDO> getAll() {
            List<StudentCourseDO> studentCourseDOList =new ArrayList<>();
            List<StudentCourseEntity> studentCourseEntityList = studentCourseRepository.findAll();

            for(int i=0;i<studentCourseEntityList.size();i++)
            {
                studentCourseDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(i)));
            }
            return studentCourseDOList;

        }

    public List<StudentCourseDO> getAllbyid(String id) {
        List<StudentCourseDO> studentCourseDOList =new ArrayList<>();
        if(!studentCourseRepository.existsByStudentid(studentRepository.getByRollnumber(id)))
        {
            throw new NoSuchElementException();
        }
        List<StudentCourseEntity> studentCourseEntityList = studentCourseRepository.findAllByStudentid(studentRepository.getByRollnumber(id));

        for(int i=0;i<studentCourseEntityList.size();i++)
        {
            studentCourseDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(i)));
        }
        return studentCourseDOList;

    }
        public void updateStudentCourse(StudentCourseDO studentCourseDO){

            if(studentCourseDO.getStudentid()==null||studentCourseDO.getStudentid().isEmpty()||studentCourseDO.getCourseid()==null||studentCourseDO.getCourseid().isEmpty())
            {
                throw new EmptyFieldException();
            }
            StudentEntity studentEntity= studentRepository.getByRollnumber(studentCourseDO.getStudentid());
            CourseEntity courseEntity= courseRepository.getByCourseid(studentCourseDO.getCourseid());

            if(!studentCourseRepository.existsByStudentidAndCourseid(studentEntity,courseEntity)){
                throw new NoSuchElementException();
            }
               StudentCourseEntity studentCourseEntity =  studentCourseRepository.findByStudentidAndCourseid(studentEntity,courseEntity);

            //System.out.println(studentCourseEntity);
            if(studentCourseDO.getGrade()!=null){
                studentCourseEntity.setGrade(studentCourseDO.getGrade());
                studentCourseRepository.save(studentCourseEntity);
            }
            if(studentCourseDO.getSemester()!=0){
                studentCourseEntity.setSemester(studentCourseDO.getSemester());
                studentCourseRepository.save(studentCourseEntity);
            }
            if(studentCourseDO.getGrade()==null && studentCourseDO.getSemester()==0){
                throw new NoDataSentException();
            }
        }

}
