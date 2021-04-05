package br.com.portfolio.lsilva.cadastropedidosapi.domain.dto;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO implements Serializable {

    @Valid
    private ProdutoServicoDTO produtoServico;

    @Min(value = 1, message = "Quantidade de Item por Pedido deve ser de no mínimo 1.")
    private int quantidadeItem;

    public List<ItemPedido> transformaParaListaDeObjetos(List<ItemPedidoDTO> itensPedidosDTO){
        return itensPedidosDTO.stream().map(this::transformaParaObjeto).collect(Collectors.toList());
    }

    private ItemPedido transformaParaObjeto(ItemPedidoDTO itemPedidoDTO){
        return new ItemPedido(itemPedidoDTO.getProdutoServico().transformaParaObjeto(), itemPedidoDTO.getQuantidadeItem());
    }
}
