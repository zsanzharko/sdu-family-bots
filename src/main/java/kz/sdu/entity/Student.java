package kz.sdu.entity;

import kz.sdu.entity.abstractBase.AbstractBaseEntity;
import kz.sdu.enums.Faculty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "students")
public class Student extends AbstractBaseEntity {

    @OneToOne(
            mappedBy = "student",
            optional = false
    )
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
