package org.nathan.primeiracasabackend.dto.response;


import lombok.*;
import org.nathan.primeiracasabackend.Enums.EnumsProduto.CategoriaProduto;
import org.nathan.primeiracasabackend.Enums.EnumsProduto.StatusProduto;

import java.util.UUID;


@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDTO {

    private UUID id;
    private String nome;
    private CategoriaProduto categoria;
    private StatusProduto status;
    private UUID idLista;
    private String fotoBase64;

}