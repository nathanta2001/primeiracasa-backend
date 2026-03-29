package org.nathan.primeiracasabackend.dto.request;


import jakarta.validation.constraints.*;
import lombok.*;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.ComodoItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.NecessidadeItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.TipoItem;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCasaRequestDTO {

    @NotBlank(message = "o nome do item é obrigatório")
    @Size(max = 100)
    private String nome;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "O tipo é obrigatório")
    private TipoItem tipo;

    @NotNull(message = "A necessidade é obrigatória")
    private NecessidadeItem necessidade;

    @NotNull(message = "O comodo é obrigatório")
    private ComodoItem comodo;

    private String fotoBase64;

}
