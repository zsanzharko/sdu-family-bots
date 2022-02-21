package kz.sdu.entity;

import kz.sdu.enums.Faculty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "students")
public class Student extends AbstractBaseEntity{

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @Column(name = "student_id")
    private String studentID;   // student id

    @Column(name = "av_gpa")
    private float averageGPA;

    @Column(name = "faculty")
    private Faculty faculty;

    public Student(User user, String studentID, float averageGPA, Faculty faculty) {
        this.user = user;
        this.studentID = studentID;
        this.averageGPA = averageGPA;
        this.faculty = faculty;
    }

    public Student() {

    }
}