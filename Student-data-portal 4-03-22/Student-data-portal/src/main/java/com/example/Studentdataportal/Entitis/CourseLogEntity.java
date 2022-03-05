package com.example.Studentdataportal.Entitis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseLogEntity {
    @Id
    @Column(name = "logid",nullable = false)
    private  String logid;

}
