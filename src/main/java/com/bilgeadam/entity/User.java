package com.bilgeadam.entity;

import com.bilgeadam.utility.EStatus;
import com.bilgeadam.utility.EUserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_user") //bunu yazmak zorundayız!! usera özel
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String surname;
    @Email
    private String email;
    @Column(length = 50)
    private String phone;
    @Column(length = 32)
    private String password;
    @Column(length = 32)
    private String repassword;

    @ElementCollection //manytomany yerine bu şekilde tutmamız daha düzgün olur karışmaz.
    private List<Long> favMovies; //buraya movieıD Listesi gelcek aslında hafif db kullanımı için!!
    @ElementCollection
    private List<Long> favGenres;

    @ElementCollection //One user can make many comments.Ekisden one tomany'di
    private List<Long> comments;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EUserType userType=EUserType.USER; //Default değerler koyduk
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EStatus status=EStatus.PENDING; //Default değerler koyduk


}
