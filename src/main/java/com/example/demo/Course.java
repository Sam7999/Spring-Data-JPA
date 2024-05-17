package com.example.demo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name= "Course")
@Table( name= "course")
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "course_sequence"

    )
    @Column(name = "id", updatable = false)

    private Long Id;
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(100)")

    private String courseName;
    @Column(name = "department", nullable = false, columnDefinition = "VARCHAR(100)")
    private String department;

    public Course(String courseName, String department) {
        this.courseName = courseName;
        this.department = department;
    }

    public Course() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @OneToMany(  // we change this from  @ManyToMany  to @OneToMany
            mappedBy = "course", // instead of mapped by courses we will have it mapped by course, it the one found in Enrollment (private course course ;)
            cascade = {CascadeType.PERSIST,
                    CascadeType.REMOVE})
    private List<Enrolment> enrolments = new ArrayList<>();


    public List<Enrolment> getEnrolments() {
        return enrolments;
    }

    public void setEnrolments(List<Enrolment> enrolments) {
        this.enrolments = enrolments;
    }

    public void addEnrolment(Enrolment enrolment) {  // metghod to add enrollment
        if (!enrolments.contains(enrolment)) {
            enrolments.add(enrolment);
        }
    }

    public void removeEnrolment(Enrolment enrolment) { // method to remove enrollment
        enrolments.remove(enrolment);

    }
}
