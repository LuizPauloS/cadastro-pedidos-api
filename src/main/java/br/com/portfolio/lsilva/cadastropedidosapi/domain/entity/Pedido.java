package br.com.portfolio.lsilva.cadastropedidosapi.domain.entity;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
public class Pedido extends BaseEntity {

    private Double valorTotal;

    private Boolean pedidoAberto;

    private LocalDateTime dataPedido = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemPedido> itensPedido = new ArrayList<>();

    private double percentualDesconto;

    public List<ItemPedido> getItensPedido() {
        return new ArrayList<>(itensPedido);
    }
}
