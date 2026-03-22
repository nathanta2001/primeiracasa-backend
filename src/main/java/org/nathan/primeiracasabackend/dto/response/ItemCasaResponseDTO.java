package org.nathan.primeiracasabackend.dto.response;

import lombok.*;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.ComodoItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.NecessidadeItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.TipoItem;

import java.math.BigDecimal;
import java.util.UUID;


@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemCasaResponseDTO {

    private UUID id;
    private String nome;
    private BigDecimal preco;
    private TipoItem tipo;
    private NecessidadeItem necessidade;
    private ComodoItem comodo;
}