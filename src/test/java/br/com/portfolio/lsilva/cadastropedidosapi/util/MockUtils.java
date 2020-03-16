package br.com.portfolio.lsilva.cadastropedidosapi.util;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ItemPedido;
import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.Pedido;
import br.com.portfolio.lsilva.cadastropedidosapi.domain.enumeration.Categoria;
import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ProdutoServico;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

public class MockUtils {

    public static ProdutoServico buildProdutoServico(String nome, String descricao, Categoria categoria,
                                                     BigDecimal precoUnitario, Integer estoque) {
        ProdutoServico produtoServico = ProdutoServico.builder()
                .nome(nome)
                .descricao(descricao)
                .categoria(categoria)
                .precoUnitario(precoUnitario)
                .quantidadeEstoque(estoque)
                .build();

        produtoServico.setId(UUID.randomUUID());

        return produtoServico;
    }

    public static ProdutoServico buildProdutoServicoUuid(UUID uuid, String nome, String descricao, Categoria categoria,
                                                         BigDecimal precoUnitario, Integer estoque) {
        ProdutoServico produtoServico = ProdutoServico.builder()
                .nome(nome)
                .descricao(descricao)
                .categoria(categoria)
                .precoUnitario(precoUnitario)
                .quantidadeEstoque(estoque)
                .build();

        produtoServico.setId(uuid);

        return produtoServico;
    }

    private static List<ItemPedido> buildListItensPedido() {
        List<ItemPedido> itensPedido = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            itensPedido.add(buildItemPedido(ProdutoServico.builder()
                    .ativo(true)
                    .categoria(Categoria.values()[new Random().nextInt(Categoria.values().length)])
                    .nome("Nome de Teste " + i)
                    .descricao("Descrição de Teste " + i)
                    .quantidadeEstoque(new Random().nextInt(10))
                    .precoUnitario(new BigDecimal(BigInteger.valueOf(new Random().nextInt(100)), 2))
                    .build()));
        }
        return itensPedido;
    }

    public static ItemPedido buildItemPedido(ProdutoServico produtoServico) {
        ItemPedido itemPedido = ItemPedido.builder()
                .produtoServico(produtoServico)
                .quantidadeItem(new Random().nextInt(10))
                .build();

        itemPedido.setId(UUID.randomUUID());
        return itemPedido;
    }


    public static Pedido buildPedido(boolean pedidoAberto) {
        Pedido pedido = Pedido.builder()
                .pedidoAberto(pedidoAberto)
                .dataPedido(LocalDateTime.now())
                .percentualDesconto(new Random().nextInt(100))
                .itensPedido(buildListItensPedido())
                .build();

        pedido.setId(UUID.randomUUID());
        return pedido;
    }

    public static Pedido buildPedidoUuid(UUID uuid, boolean pedidoAberto, LocalDateTime dataPedido,
                                                 double percentualDesconto, List<ItemPedido> itensPedido) {
        Pedido pedido = Pedido.builder()
                .pedidoAberto(pedidoAberto)
                .dataPedido(dataPedido)
                .percentualDesconto(percentualDesconto)
                .itensPedido(itensPedido)
                .build();

        pedido.setId(uuid);

        return pedido;
    }
}
