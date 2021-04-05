package br.com.portfolio.lsilva.cadastropedidosapi.domain.dto;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ItemPedido;
import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.Pedido;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PedidoDTO implements Serializable {

    private Double valorTotal;

    @NotNull(message = "Informar se o Pedido está aberto é obrigatório.")
    private Boolean pedidoAberto;

    @Valid
    private List<ItemPedidoDTO> itensPedido = new ArrayList<>();

    @Min(value = 0, message = "Percentual de desconto não pode ser menor que 0%.")
    @Max(value = 99, message = "Percentual de desconto não pode ser maior que 99%.")
    private double percentualDesconto;

    public Pedido transformaParaObjeto() {
        return new Pedido(valorTotal, pedidoAberto, LocalDateTime.now(), new ItemPedidoDTO()
                .transformaParaListaDeObjetos(itensPedido), percentualDesconto);
    }
}
