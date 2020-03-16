package br.com.portfolio.lsilva.cadastropedidosapi.service;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.Pedido;

public interface IPedidoService<T> extends IBaseService<T> {

    Pedido updateValues(Pedido atual, Pedido original);

    Pedido calculateDiscountForProducts(Pedido pedido);
}
