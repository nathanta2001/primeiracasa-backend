package org.nathan.primeiracasabackend.dto.request;



import jakarta.validation.constraints.*;
import lombok.*;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.ComodoItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.NecessidadeItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.TipoItem;
import org.nathan.primeiracasabackend.Enums.EnumsProduto.CategoriaProduto;
import org.nathan.primeiracasabackend.Enums.EnumsProduto.StatusProduto;

import java.util.UUID;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoRequestDTO {

    @NotNull(message = "A lista é obrigatória")
    private UUID Idlista;

    @NotBlank(message = "o nome do item é obrigatório")
    @Size(max = 100)
    private String nome;

    @NotNull(message = "A categoria é obrigatória")
    private CategoriaProduto categoria;

    @NotNull(message = "O status é obrigatório")
    private StatusProduto status;
}