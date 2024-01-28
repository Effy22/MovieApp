package com.bilgeadam.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    //Movie'nin içndeki genre listesinin karşılığını yazmasak da (onetomany'nin)
    //bu bölümü alıp kendi tablosu oluşur çapraz tablonun Movies tutmamıza gerek yok!
}
