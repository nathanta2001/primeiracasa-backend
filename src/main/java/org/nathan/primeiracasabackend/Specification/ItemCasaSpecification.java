package org.nathan.primeiracasabackend.Specification;

import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.ComodoItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.NecessidadeItem;
import org.nathan.primeiracasabackend.Enums.EnumsItemCasa.TipoItem;
import org.nathan.primeiracasabackend.Model.ItemCasa;
import org.nathan.primeiracasabackend.dto.request.ProdutoRequestDTO;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ItemCasaSpecification {

    public static Specification<ItemCasa> porNome(String nome) {
        return (root, query, cb) -> nome == null ? null :
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<ItemCasa> porComodo(ComodoItem comodo) {
        return (root, query, cb) -> comodo == null ? null :
                cb.equal(root.get("comodo"), comodo);
    }

    public static Specification<ItemCasa> porTipo(TipoItem tipo) {
        return (root, query, cb) -> tipo == null ? null :
                cb.equal(root.get("tipo"), tipo);
    }

    public static Specification<ItemCasa> porNecessidade(NecessidadeItem necessidade) {
        return (root, query, cb) -> necessidade == null ? null :
                cb.equal(root.get("necessidade"), necessidade);
    }

    public static Specification<ItemCasa> porPrecoMinimo(BigDecimal precoMin) {
        return (root, query, cb) -> precoMin == null ? null :
                cb.greaterThanOrEqualTo(root.get("preco"), precoMin);
    }

    public static Specification<ItemCasa> porPrecoMaximo(BigDecimal precoMax) {
        return (root, query, cb) -> precoMax == null ? null :
                cb.lessThanOrEqualTo(root.get("preco"), precoMax);
    }

}