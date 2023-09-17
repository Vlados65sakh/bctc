package bctc.back.controller;

import bctc.back.data.model.Student;
import bctc.back.data.model.User;
import bctc.back.data.student.StudentRepository;
import bctc.back.data.user.UserRepository;
import bctc.back.exception.UserAlreadyExistsException;
import bctc.back.security.AuthRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;
    final StudentRepository studentRepository;

    @PostMapping("/public-test")
    public ResponseEntity<?>publicTest(@Validated @RequestBody AuthRequestDto request) {
        return ResponseEntity.ok("That's all good man");
    }

    @PostMapping("/signup")
    public ResponseEntity<?>registerUser(@Validated @RequestBody AuthRequestDto request){
        Optional<User> foundUser = userRepository.findByUsername(request.username());
        if (foundUser.isPresent()) throw new UserAlreadyExistsException();

        String encodedPassword = passwordEncoder.encode(request.password());

        switch (request.role()){
            case STUDENT -> {
                Student newStudent = new Student();
                newStudent.setAccountPassword(encodedPassword);
                newStudent.setUsername(request.username());
                studentRepository.save(newStudent);
                return ResponseEntity.ok(newStudent);
            }
            default -> {
                throw new RuntimeException("Unknown role provided");
            }
        }

//        return ResponseEntity.ok(newUser);
    }
}
