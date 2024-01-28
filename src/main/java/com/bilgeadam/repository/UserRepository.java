package com.bilgeadam.repository;

import com.bilgeadam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
    List<User> findAllByOrderByName(); //1.Soru
    Boolean existsByNameContainsIgnoreCase(String name); //2.Soru
    List<User> findAllByNameContainingIgnoreCase(String value); //3.Soru
    Optional<User> findOptionalByEmail(String email); //4.Soru

    /**
     * passwordunun uzunluğu belirlediğimiz sayıdan büyük olanları getiren sorguyu yazınız.
     * Native Query ve JPQL ile yazalım, Native Query'de @Param anotasyonunu kullanalım.
     * emailin sonu kullanıcının girdiği değerlere göre biten emailleri listeleyiniz.
     */
    @Query(value = "select * from tbl_user as u where length(u.password)>:x ", nativeQuery = true)
    List<User> passwordLongerThan(@Param("x") Integer number);

    List<User> findAllByEmailEndingWith(String email);










}
