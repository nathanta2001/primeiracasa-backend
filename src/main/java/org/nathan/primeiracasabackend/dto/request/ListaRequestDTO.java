package org.nathan.primeiracasabackend.dto.request;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaRequestDTO {

    @NotBlank(message = "o nome da Lista é obrigatório")
    @Size(max = 100)
    private String nome;

}