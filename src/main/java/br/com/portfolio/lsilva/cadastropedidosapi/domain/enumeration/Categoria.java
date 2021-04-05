package br.com.portfolio.lsilva.cadastropedidosapi.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Categoria {

    PRODUTO(0, "Produto"),
    SERVICO(1, "Serviço");

    private Integer id;
    private String nome;

    public static Stream<Categoria> findById(Integer id) {
        return Arrays.stream(Categoria.values()).filter(categoria -> categoria.getId().equals(id));
    }
}
