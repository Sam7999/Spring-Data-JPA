package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository <Student, Long> {
    @Query("select s from studentA s where s.email= ?1") //studentA is the name of the entity table

    Optional<Student> findStudentByEmail(String email);
    @Query("select s from studentA s where s.firstName= ?1 and s.age>=?2") // @Query will override the method bellow, and we can then rename it without affecting the code
    List<Student> findStudentsByfirstNameEqualsAndAgeIsGreaterThanEqual(String firstName, Integer age);

    @Query(value = "select * from studenta where first_name= ?1 and age >=?2",nativeQuery = true) // this is to use native query language, we add ",nativeQuery = true" to make it work
    // we can replace =?1 with =:firstName and =?2 with =: age // ALSO we have to add @Param("firstName) String firstName, @Param("age") Integer age)
    List<Student> findStudentsByfirstNameEqualsAndAgeIsGreaterThanEqualNative(String firstName, Integer age);
    // using JPQL is better than native query because if you change the DB the native query will not work, but JPQL will always work because it is not db specific.
    @Transactional  // we add this so we can execute the next query without an error
    @Modifying // this tells the Spring Data that the query doesn't need to map anything from the database into entities. we are just modifying some data in our table
    @Query ("delete from studentA u where u.id = ?1")
    int deleteStudentById (Long id);

}
