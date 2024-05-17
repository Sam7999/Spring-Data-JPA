package com.example.demo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity (name="studentA")
@Table (
        name = "studentA",
        uniqueConstraints = {
                @UniqueConstraint(name= "student_email_unique", columnNames ="email" )
        }

)

public class Student {

    @Id
    @SequenceGenerator(
            name= "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_sequence"
    )
    @Column (name = "id", updatable = false)
    private Long id;
    @Column (
            name = "first_name",
            nullable = false,
            columnDefinition = "VARCHAR(100)"

    )
    private String firstName;
    @Column (
            name = "last_name",
            nullable = false,
            columnDefinition = "VARCHAR(100)"
    )
    private String lastName;
    @Column (
            name = "email",
            nullable = false,
            columnDefinition = "VARCHAR(100)",
            unique = true
    )
    private String email;
    @Column(
            name= "age",
            nullable = false
    )
    private Integer age;
    @OneToOne (mappedBy = "student",// this will form a bidirectional relationship
                                     // when u load a student you also load the card and vice versa
    orphanRemoval = true, cascade = {CascadeType.PERSIST,
            CascadeType.REMOVE}) // allows to automatically remove child entities from the database

    private StudentIdCard studentIdCard;
    @OneToMany (mappedBy = "student", // CascadeType.REMOVE means when we delete a student we delete all children (books)
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST,
                    CascadeType.REMOVE},
            fetch = FetchType.LAZY  // always start with Lazy because not always you wanna fetch everything, so when we have a lot of data then it will slow down your application
                                   // if the application needs extra data we can make the application make a querry for the books when it needs irt instead of loading everything at once
    )
    private List<Book> books = new ArrayList<>();
//    @ManyToMany(cascade ={CascadeType.PERSIST,
//            CascadeType.REMOVE} ) // fetch type is lazy by default

//    @JoinTable(name = "enrolment", // this will create the enrollment table
//            joinColumns = @JoinColumn(
//                    name = "student_id",
//                    foreignKey = @ForeignKey(name = "enrolment_student_id_fk")),
//            inverseJoinColumns =@JoinColumn(
//                    name = "course_id",
//                    foreignKey = @ForeignKey(name = "enrolment_student_id_fk") ))
@OneToMany(cascade ={CascadeType.PERSIST,
        CascadeType.REMOVE},
        mappedBy = "student" ) // "student" is the one found in enrolment (private Student student)


private  List<Enrolment> enrolments = new ArrayList<>();


//    private  List<Course> courses = new ArrayList<>();

    public Student(
                   String firstName,
                   String lastName,
                   String email,
                   Integer age) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Student() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public void addBook (Book book) { // because we have a bidirectional relationship we have to add this method
        if (!this.books.contains(book)){
            this.books.add(book);
            book.setStudent(this); //  wehn we load the book we want to load the student and then when we load the student we want to load the associated book with it

        }
    }
    public void removeBook (Book book){
        if (this.books.contains(book)){
            this.books.remove(book);
            book.setStudent(null);
        }
    }

    public void setStudentIdCard(StudentIdCard studentIdCard) {
        this.studentIdCard = studentIdCard;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Enrolment> getEnrolments() {
        return enrolments;
    }

    public void addEnrolment(Enrolment enrolment){  // metghod to add enrollment
        if (!enrolments.contains(enrolment)){
            enrolments.add(enrolment);
        }
    }

    public void removeEnrolment(Enrolment enrolment){ // method to remove enrollment
            enrolments.remove(enrolment);
        }
    //    public List<Course> getCourses() {
//        return courses;
//    }
//
//    public void setCourses(List<Course> courses) {
//        this.courses = courses;
//    }
//    public void enrolToCourse(Course course){  // this is a method add a course in the enrollment table
//        courses.add(course);
//        course.getStudents().add(this);
//    }
//    public void unEnrolToCourse(Course course){ // this method is to remove course from the enrollment table
//        courses.remove(course);
//        course.getStudents().remove(this);
//    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
