package com.example.demo;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository studentRepository,
            StudentIdCardRepository studentIdCardRepository){
        return args -> {
            Faker nadia = new Faker();
                String firstName= nadia.name().firstName();
                String lastName = nadia.name().lastName();
                String email = String.format("%s.%s@generated.sa",firstName, lastName);
                Student student = new Student(
                        firstName,
                        lastName,
                        email,
                        nadia.number().numberBetween(17,57));

                student.addBook(
                        new Book("Clean Code", LocalDateTime.now().minusDays(4)));

            student.addBook(
                    new Book("Think and Grow Rich", LocalDateTime.now()));

            student.addBook(
                    new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));


            StudentIdCard studentIdCard =  new StudentIdCard(
                    "123456789",student);
            studentIdCardRepository.save(studentIdCard);

            studentRepository.findById(1L).ifPresent(s ->{System.out.println("fetch books lazy ..."); // this is showing the left join queries to get student and card
                List<Book> books = student.getBooks(); // we are calling the books when we call the get method because it's lazy loading
                books.forEach(book -> {
                    System.out.println(
                            s.getfirstName()+ " borrowed "+ book.getBookName());

                });

            } );

//
//            studentIdCardRepository.findById(1L).
//                    ifPresent(System.out::println);
//            studentRepository.deleteById(1L);


//            generateRandomStudents(studentRepository);// Command+option+m to extract the method
////            sorting(studentRepository);
//            //
//
//            PageRequest pageRequest = PageRequest.of(
//                    0,
//                    5,
//                    Sort.by("firstName").ascending()); // we add a page request and we identify the page number and size, we also added sorting to our paging
//            Page<Student> page = studentRepository.findAll(pageRequest);// paste the page request inside and we extract a variable (cmd,option, v) to be added before studentrep
//            System.out.println(page);
            // we can add a break point in sysout and run in debug mode and then click on Evaluate expression to have inspect the result and we will have methods
        };

    }

//    private static void sorting(StudentRepository studentRepository) {
//        Sort firstName = Sort.by(Sort.Direction.ASC, "firstName")
//                .and(Sort.by("age").descending()); //sorting the students ASC, and we sort the age too
//        studentRepository.findAll(firstName) //extract by variable and we name it sort
//                .forEach(student -> System.out.println(student.getfirstName()+ " "+ student.getAge()));
//    }

    private static void generateRandomStudents(StudentRepository studentRepository) {
        Faker nadia = new Faker();
        for (int i = 0; i < 20; i++) {
            String firstName= nadia.name().firstName();
            String lastName = nadia.name().lastName();
            String email = String.format("%s.%s@generated.sa",firstName, lastName);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    nadia.number().numberBetween(17,57));
            studentRepository.save(student);


            //            Student maria = new Student(
//                    "Maria",
//                    "Garcia",
//                    "maria.gacia@gmail.com",
//                    21
//            );
//            Student maria2 = new Student(
//                    "Maria",
//                    "Garcia",
//                    "maria2.gacia@gmail.com",
//                    25
//            );
//            Student ahmed = new Student(
//                    "Ahmed",
//                    "Ali",
//                    "ahmed.ali@gmail.com",
//                    27
//            );
//            System.out.println("Adding maria and ahmed");
//            studentRepository.saveAll(List.of(maria, ahmed,maria2));
//
//            studentRepository
//                    .findStudentByEmail("ahmed.ali@gmail.com")
//                    .ifPresentOrElse
//                            (System.out::println,
//                            ()->System.out.println("Student with email ahmed.ali@gmail.com not found "));
//            studentRepository.findStudentsByfirstNameEqualsAndAgeIsGreaterThanEqualNative ("Maria",
//                    21).forEach(System.out::println);
//
//            System.out.println("Deleting Maria 2");;
//            System.out.println(studentRepository.deleteStudentById(3L)); // we implemented our own deleting method

//            System.out.print("Number of students:");
//            System.out.println(studentRepository.count());
//            studentRepository
//                    .findById(3L)
//                    .ifPresentOrElse
//                            (System.out::println,
//                                    () -> System.out.println("Student with ID 3 not found"));
//
//            System.out.println("Select all students");
//
//            List<Student> students = studentRepository.findAll();
//            students.forEach(System.out::println);
//
//            System.out.println("Delete Maria");
//
//            studentRepository.deleteById(1L);
//
//            System.out.println("Number of students:");
//            System.out.println(studentRepository.count());
        }
    }

}
