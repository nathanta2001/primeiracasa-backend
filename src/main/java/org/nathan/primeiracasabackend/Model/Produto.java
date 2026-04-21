package org.nathan.primeiracasabackend.Model;

import jakarta.persistence.*;
import lombok.*;
import org.nathan.primeiracasabackend.Enums.EnumsProduto.CategoriaProduto;
import org.nathan.primeiracasabackend.Enums.EnumsProduto.StatusProduto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;


@Entity
@Builder
@Table(name="produto")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "fk_lista", nullable = false)
    private Lista lista;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id_produto")
    private UUID id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "categoria", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoriaProduto categoria;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusProduto status;

    @Column(columnDefinition = "TEXT")
    private String fotoBase64;

}
