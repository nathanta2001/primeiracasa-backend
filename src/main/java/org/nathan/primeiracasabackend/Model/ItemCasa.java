package org.nathan.primeiracasabackend.Model;


import jakarta.persistence.*;
import lombok.*;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.ComodoItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.NecessidadeItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.TipoItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Builder
@Table(name="item_casa")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemCasa implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "pk_item")
    private UUID id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoItem tipo;

    @Column(name = "necessidade", nullable = false)
    @Enumerated(EnumType.STRING)
    private NecessidadeItem necessidade;

    @Column(name = "comodo", nullable = false)
    @Enumerated(EnumType.STRING)
    private ComodoItem comodo;
}
