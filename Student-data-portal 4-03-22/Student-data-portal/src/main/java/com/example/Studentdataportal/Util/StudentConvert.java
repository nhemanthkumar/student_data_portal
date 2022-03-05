package com.example.Studentdataportal.Util;

import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Entitis.StudentEntity;
import org.springframework.stereotype.Component;

@Component
public class StudentConvert {

    public StudentEntity convert2StudentEntity(StudentDO studentDO)
    {
       return StudentEntity.builder().rollnumber(studentDO.getId()).emailid(studentDO.getEmailid()).name(studentDO.getName())
                .batchid(studentDO.getBatchid()).section(studentDO.getSection()).yearofjoining(studentDO.getYearofjoining()).build();

    }

    public StudentDO convert2StudentDO(StudentEntity studententity)
    {
        return StudentDO.builder().id(studententity.getRollnumber()).emailid(studententity.getEmailid()).name(studententity.getName())
                .batchid(studententity.getBatchid()).section(studententity.getSection()).yearofjoining(studententity.getYearofjoining()).build();

    }

}
