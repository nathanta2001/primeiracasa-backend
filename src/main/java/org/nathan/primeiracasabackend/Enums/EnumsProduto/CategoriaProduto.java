package org.nathan.primeiracasabackend.Enums.EnumsProduto;


import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public enum CategoriaProduto {

    MERCEARIA("Itens de dispensa em geral"),
    HORTIFRUTI("Frutas, legumes e verduras"),
    ACOUGUE("Carnes e proteínas"),
    LATICINIOS("Leites, queijos e derivados"),
    PADARIA("Pães, bolos e salgados"),
    BEBIDAS("Sucos, refrigerantes e alcoólicos"),
    HIGIENE("Cuidados pessoais"),
    LIMPEZA("Produtos para limpeza da casa"),
    PET_SHOP("Itens para animais de estimação"),
    CONGELADOS("Pratos prontos e polpas"),
    GRAOS_E_CEREAIS("Arroz, feijão, massas"),
    UTILITARIOS("Lâmpadas, pilhas e descartáveis"),
    OUTROS("Itens não categorizados");

    private final String descricao;
}
