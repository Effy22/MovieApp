package com.bilgeadam.controller;

import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.FindAllResponseDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.entity.User;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(String name, String surname, String email, String password, String repassword) {
        return ResponseEntity.ok(userService.register(name, surname, email, password, repassword));
    }
    @GetMapping("/find-by-id")
    public ResponseEntity<Optional<User>> findById(Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> delete(Long id){
        //userı görüntülemek istiyorm statusu değişti mi değişmedi mi ondan user döndük
        return ResponseEntity.ok(userService.deleteById(id));
    }

    //basic login
    @PostMapping("/login")
    public ResponseEntity<User> login(String email, String password) {
        return ResponseEntity.ok(userService.login(email,password));
    }
    //dto register
    @PostMapping("/register-dto")
    public ResponseEntity<RegisterResponseDto> registerDto(@RequestBody RegisterRequestDto dto){
        return ResponseEntity.ok(userService.registerDto(dto));
    }
    @PostMapping("/login-dto")
    public ResponseEntity<LoginResponseDto> loginDto(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(userService.loginDto(dto));
    }

    @PostMapping("/login-mapper")
    public ResponseEntity<LoginResponseDto> loginMapper(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(userService.loginMapper(dto));
    }

    @PostMapping("/register-mapper")
    public ResponseEntity<RegisterResponseDto> registerMapper(@RequestBody RegisterRequestDto dto){
        return ResponseEntity.ok(userService.registerMapper(dto));
    }

    @GetMapping("/find-all-by-order-by-name-response")
    public ResponseEntity<List<FindAllResponseDto>> findAllByOrderByName(){
        return ResponseEntity.ok(userService.findAllByOrderByName());
    }

    @GetMapping("/exists-by-name-contains-ignore-case")
    public ResponseEntity<Boolean> existsByNameContainsIgnoreCase(String name) {
        return ResponseEntity.ok(userService.existsByNameContainsIgnoreCase(name));
    }
    @GetMapping("find-all-by-name-containing")
    public ResponseEntity<List<FindAllResponseDto>> findAllByNameContainingIgnoreCase(String value){
        return ResponseEntity.ok(userService.findAllByNameContainingIgnoreCase(value));
    }

    @GetMapping("find-optional-by-email")
    public ResponseEntity<User> findOptionalByEmail(String email){
        return ResponseEntity.ok(userService.findOptionalByEmail(email));
    }

    @GetMapping("/password-longer-than")
    public ResponseEntity<List<User>> passwordLongerThan(Integer number) {
        return ResponseEntity.ok(userService.passwordLongerThan(number));
    }
    @GetMapping("/find-all-by-email-ending-with")
    public ResponseEntity<List<User>> findAllByEmailEndingWith(String value) {
        return ResponseEntity.ok(userService.findAllByEmailEndingWith(value));
    }



}
