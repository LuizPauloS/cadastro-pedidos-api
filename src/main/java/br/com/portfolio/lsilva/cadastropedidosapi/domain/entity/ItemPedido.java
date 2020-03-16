package br.com.portfolio.lsilva.cadastropedidosapi.domain.entity;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;

@Data
@Entity
@Builder
@QueryEntity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemPedido extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="produto_servico_id", nullable=false)
    private ProdutoServico produtoServico;

    @Min(value = 1, message = "Quantidade de Item por Pedido deve ser de no mínimo 1.")
    private int quantidadeItem;
}
