package com.example.demo;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity (name = "Enrolment")
@Table (  name = "enrolment")
public class Enrolment {
    @EmbeddedId  //
    private EnrolmentId enrolmentId; // we want to embed it as our ID and  we have that as Embeddable in the Enrollment Id class

    @ManyToOne
    @MapsId ("studentId")  // this says that this student is part of the id. || this "studentId" is the one in EnrolmentId (private Long studentId;)
    @JoinColumn (name = "student_id",foreignKey = @ForeignKey (name = "enrolment_student_id_fk")) // this is the actual property in our db
    private Student student;

    @ManyToOne
    @MapsId ("courseId")
    @JoinColumn (name = "course_id", foreignKey = @ForeignKey (name = "enrolment_course_id_fk"))
    private  Course course;

    @Column ( name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
    @Column (name = "teacher",
    nullable = false,
    columnDefinition = "VARCHAR(100)")

    private String teacher;

    public Enrolment(EnrolmentId enrolmentId, Student student, Course course, LocalDateTime createdAt, String teacher) {
        this.enrolmentId = enrolmentId;
        this.student = student;
        this.course = course;
        this.createdAt = createdAt;
        this.teacher = teacher;

    }

    public Enrolment(Student student, Course course,LocalDateTime createdAt,String teacher) {
        this.student = student;
        this.course = course;
        this.createdAt = createdAt;
        this.teacher = teacher;
    }

    public Enrolment() {
    }

    public EnrolmentId getEnrolmentId() {
        return enrolmentId;
    }

    public void setEnrolmentId(EnrolmentId enrolmentId) {
        this.enrolmentId = enrolmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Enrolment{" +
                "enrolmentId=" + enrolmentId +
                ", student=" + student +
                ", course=" + course +
                '}';
    }
}
