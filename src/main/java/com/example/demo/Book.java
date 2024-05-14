package com.example.demo;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "Book")
@Table (name = "book")

public class Book {

    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName ="book_sequence",
            allocationSize = 1
    )

    @GeneratedValue (
            strategy = SEQUENCE,
            generator = "book_sequence"

    )
    @Column (name = "id", updatable = false)

    private Long bookId;
    @Column (name ="book_name", nullable = false, columnDefinition = "VARCHAR(100)" )


    private String bookName;
    @Column(name = "created_at",nullable = false, columnDefinition = "TIMESTAMP")

    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn (
            name = "student_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_book_fk"))
    private Student student;

    public Book() {
    }

//    public Book(String bookName, LocalDateTime createdAt, Student student) {
//        this.bookName = bookName;
//        this.createdAt = createdAt;
//        this.student = student;

    public Book(String bookName, LocalDateTime createdAt) {
        this.bookName = bookName;
        this.createdAt = createdAt;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", createdAt=" + createdAt +
                ", student=" + student +
                '}';
    }
}


