package br.com.portfolio.lsilva.cadastropedidosapi.domain.entity;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@Builder
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="produto_servico_id", nullable=false)
    private ProdutoServico produtoServico;

    private int quantidadeItem;
}
