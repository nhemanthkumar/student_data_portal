package com.example.Studentdataportal.Controllers;

import com.example.Studentdataportal.DataObjects.StudentCourseDO;
import com.example.Studentdataportal.Services.StudentCourseSercices;
import com.example.Studentdataportal.Util.Helper;
import com.example.Studentdataportal.Util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/studentcourse")
public class StudentCourseController {
        @Autowired
        StudentCourseSercices studentCourseSercices;

    @PostMapping("/upload/{id}/{id1}/{id2}/{id3}")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable("file") MultipartFile file,@PathVariable("id") Long sem,@PathVariable("id1") String type,@PathVariable("id2")String dateString,@PathVariable("id3")String regulation) {
        String message = "";
        if (Helper.hasExcelFormat(file)) {

            //try {s
            System.out.println(dateString);
            studentCourseSercices.save(file,sem,type, dateString,regulation);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            // } //catch (Exception e) {
            //message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            // e.printStackTrace();
            //return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            // }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @PostMapping("/advancesupply/upload/{id}/{id1}/{id2}/{id3}")
    public ResponseEntity<ResponseMessage> advsupuploadFile(@PathVariable("file") MultipartFile file,@PathVariable("id") Long sem,@PathVariable("id1") String type,@PathVariable("id2")String dateString,@PathVariable("id3")String regulation) {
        String message = "";
        if (Helper.hasExcelFormat(file)) {

            //try {s
            System.out.println(dateString);
            studentCourseSercices.saveadvsup(file,sem,type, dateString,regulation);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            // } //catch (Exception e) {
            //message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            // e.printStackTrace();
            //return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            // }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }


    @PutMapping("/updatestudentcourse")
    public ResponseEntity<ResponseMessage> updateStudentCourse(@RequestBody StudentCourseDO studentCourseDO){
        studentCourseSercices.updateStudentCourse(studentCourseDO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/getAll")
        public ResponseEntity<List<StudentCourseDO>> getAll() {

                List<StudentCourseDO> studentCourseDOList = studentCourseSercices.getAll();
                if (studentCourseDOList.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(studentCourseDOList, HttpStatus.OK);

            }

    @GetMapping("/getAll/{id}")
    public ResponseEntity<List<StudentCourseDO>> getAll(@PathVariable String id) {

        List<StudentCourseDO> studentCourseDOList = studentCourseSercices.getAllbyid(id);
        if (studentCourseDOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(studentCourseDOList, HttpStatus.OK);

    }


}
