package com.bilgeadam.service;

import com.bilgeadam.dto.request.LoginRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.FindAllResponseDto;
import com.bilgeadam.dto.response.LoginResponseDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.entity.User;
import com.bilgeadam.mapper.UserMapper;
import com.bilgeadam.repository.UserRepository;
import com.bilgeadam.utility.EStatus;
import com.bilgeadam.utility.EUserType;
import com.bilgeadam.utility.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements ICrudService<User,Long> {

    private final UserRepository userRepository;



    @Override
    public User save(User user) {
        return null;
    }



    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public Iterable<User> saveAll(Iterable<User> t) {
        return null;
    }

    @Override
    public User delete(User user) {
        return null;
    }

    @Override
    public User deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            user.get().setStatus(EStatus.INACTIVE);
           return userRepository.save(user.get());
        }else{
            throw new NullPointerException("Kullanıcı bulunamadı");
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user;
        }else{
            throw new NullPointerException("Kullanıcı bulunamadı");
        }
    }

    @Override
    public List<User> findAll() {
        List<User> userList=userRepository.findAll();
        if(userList.isEmpty()){
            throw new NullPointerException("Liste boş");
            //önce iboşmu diye kontrol etmeli
        }
            return userList;
    }

    public User register(String name, String surname, String email, String password, String repassword){
        User registeredUser = User.builder()
                .name(name)
                .surname(surname)
                .password(password)
                .repassword(repassword)
                .build();
        // * * -> isBlank = true, " " isNull
        if(!password.equals(repassword) || password.isBlank() ){
            throw new RuntimeException("Şifreler aynı değildir");
          /*
          Exception -> Checked
          RuntimeException -> Unchecked Runtime Error. Çalışma zamanı hatası -> Program çalışırken gerçekleşir.
           */
        }else{
            return userRepository.save(registeredUser);
        }
    }
    //basic login
    public User login(String email, String password){

        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        if(user.isEmpty()){
            throw new RuntimeException("Kullanıcı bulunamadı");
        }else{
            return user.get();
        }

    }
    //dto register
    public RegisterResponseDto registerDto(RegisterRequestDto dto) {
        User user = User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .repassword(dto.getRepassword())
                .build();
        if(!user.getPassword().equals(user.getPassword()) || user.getPassword().isBlank()){
            throw new RuntimeException("Şifreler aynı değildir");
        }

        userRepository.save(user);
        return RegisterResponseDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();

        /**
         * User'ın içini doldurup bunu response'a aktardık direk dto'dan çekmedik respondedto'ya aktarırken
         * çünkü olayımız user. burdan ilerlemeliyiz her bilgisi yok dtonun
         */
    }
    //dto login
    public LoginResponseDto loginDto (LoginRequestDto dto){
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(dto.getEmail(),dto.getPassword());
        if(optionalUser.isEmpty()){
            throw new RuntimeException("Kullanıcı bulunamadı");
        }

        //optionalUser.get() -> User da olabilir yani optional olan entityi entitye çevirip de içine atabiliriz.
            return LoginResponseDto.builder()
                    .email(optionalUser.get().getEmail())
                    .build();

    }

    public LoginResponseDto loginMapper(LoginRequestDto dto) {
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(dto.getEmail(),dto.getPassword());
        if(optionalUser.isEmpty()){
            throw new RuntimeException("Kullanıcı bulunamadı");
        }
        return UserMapper.INSTANCE.fromUserToLoginResponseDto(optionalUser.get());
    }

    public RegisterResponseDto registerMapper(RegisterRequestDto dto) {
        User user= UserMapper.INSTANCE.fromRegisterRequestDtoToUser(dto);
        if(user.getEmail().equals("ba.admin@email.com")){
            user.setStatus(EStatus.ACTIVE);
            user.setUserType(EUserType.ADMIN);
        } else if (userRepository.findOptionalByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Bu email ile daha önce kayıt yapılmıştır");
        }
        if (!user.getPassword().equals(user.getRepassword()) || user.getPassword().isBlank()) {
            throw new RuntimeException("Sifreler ayni degildir.");
        }
        userRepository.save(user);
        return UserMapper.INSTANCE.fromUserToRegisterResponseDto(user);
    }

    public List<FindAllResponseDto> findAllByOrderByName (){
        return UserMapper.INSTANCE.fromUserListToResponseList(
                userRepository.findAllByOrderByName()
        );
    }

    public Boolean existsByNameContainsIgnoreCase(String name) {
        return userRepository.existsByNameContainsIgnoreCase(name);
    }
    public List<FindAllResponseDto> findAllByNameContainingIgnoreCase(String value) {
      return UserMapper.INSTANCE.fromUserListToResponseList(
              userRepository.findAllByNameContainingIgnoreCase(value)
      );

    }

    public User findOptionalByEmail(String email) {
        return userRepository.findOptionalByEmail(email).get();
    }
}
