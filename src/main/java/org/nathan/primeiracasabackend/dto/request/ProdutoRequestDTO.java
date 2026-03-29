package org.nathan.primeiracasabackend.dto.request;



import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.nathan.primeiracasabackend.Enums.EnumsProduto.CategoriaProduto;
import org.nathan.primeiracasabackend.Enums.EnumsProduto.StatusProduto;

import java.util.UUID;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoRequestDTO {

    @JsonProperty("idLista")
    @NotNull(message = "A lista é obrigatória")
    private UUID idLista;

    @NotBlank(message = "o nome do item é obrigatório")
    @Size(max = 100)
    private String nome;

    @NotNull(message = "A categoria é obrigatória")
    private CategoriaProduto categoria;

    @NotNull(message = "O status é obrigatório")
    private StatusProduto status;

    private String fotoBase64;
}