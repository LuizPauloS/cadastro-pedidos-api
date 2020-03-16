package br.com.portfolio.lsilva.cadastropedidosapi.domain.entity;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.enumeration.Categoria;
import com.querydsl.core.annotations.QueryEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@QueryEntity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pedido extends BaseEntity {

    private Double valorTotal;

    @NotNull(message = "Informar se o Pedido está aberto é obrigatório.")
    private Boolean pedidoAberto;

    private LocalDateTime dataPedido = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemPedido> itensPedido = new ArrayList<>();

    @Min(value = 0, message = "Percentual de desconto não pode ser menor que 0%.")
    @Max(value = 99, message = "Percentual de desconto não pode ser maior que 99%.")
    private double percentualDesconto;

    public List<ItemPedido> getItensPedido() {
        return new ArrayList<>(itensPedido);
    }
}
