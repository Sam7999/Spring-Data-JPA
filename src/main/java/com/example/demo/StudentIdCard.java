package com.example.demo;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name= "StudentIdCard")
@Table(name= "student_id_card",
        uniqueConstraints = {
                @UniqueConstraint(
                        name= "student_card_id_number_unique",
                        columnNames ="card_number" )
        })
public class StudentIdCard {
    @Id
    @SequenceGenerator(
            name= "student_card_id_sequence",
            sequenceName = "student_card_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_card_id_sequence"
    )
    @Column (
            name = "id",
            updatable = false
    )
    private Long id;
    @Column( name= "card_number",
    nullable = false,
    length = 15
    )
    private String cardNumber;

    public StudentIdCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public StudentIdCard(Long id, String cardNumber) {
        this.id = id;
        this.cardNumber = cardNumber;
    }

    public StudentIdCard() {
    }

    public StudentIdCard(String cardNumber, Student student) {
        this.cardNumber = cardNumber;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    @OneToOne (cascade = CascadeType.ALL,  // cascade propagate all the operations
    fetch = FetchType.EAGER) //fetch  for one to one the default is eager, when you load the student ID card it fetches the student.
                             //so you can remove FetchType.EAGER or leave it or change it to Lazy
                              // for one to many or many to many the default is Lazy

    @JoinColumn (
            name= "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_id_fk")
    )
    private Student student;

    @Override
    public String toString() {
        return "StudentIdCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", student=" + student +
                '}';
    }
}

