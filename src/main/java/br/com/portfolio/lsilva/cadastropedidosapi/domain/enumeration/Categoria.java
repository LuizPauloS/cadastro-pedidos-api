package br.com.portfolio.lsilva.cadastropedidosapi.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Categoria {

    PRODUTO(0, "Produto"),
    SERVICO(1, "Serviço");

    private Integer id;
    private String nome;
}
