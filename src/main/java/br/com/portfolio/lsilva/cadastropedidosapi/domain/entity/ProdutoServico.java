package br.com.portfolio.lsilva.cadastropedidosapi.domain.entity;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.enumeration.Categoria;
import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
@Builder
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "produtos_servicos")
public class ProdutoServico extends BaseEntity {

    private String nome;

    private String descricao;

    @Enumerated(EnumType.ORDINAL)
    private Categoria categoria;

    private BigDecimal precoUnitario;

    private int quantidadeEstoque;

    private boolean ativo = true;
}
