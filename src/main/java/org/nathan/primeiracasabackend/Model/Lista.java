package org.nathan.primeiracasabackend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Builder
@Table(name="lista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Lista implements Serializable {

    private static final long serialVersionUID = 1L;


    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produto> produtos = new ArrayList<>();

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "pk_lista")
    private UUID id;

    //Como não faremos login não precisa da fk do usuario por enquanto
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    @Column(name = "fk_user")
//    private UUID id_user;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;


}
