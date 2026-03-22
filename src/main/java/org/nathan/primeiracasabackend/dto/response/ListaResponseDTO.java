package org.nathan.primeiracasabackend.dto.response;


import lombok.*;
import java.util.UUID;


@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListaResponseDTO {

    private UUID id;
    private String nome;
}