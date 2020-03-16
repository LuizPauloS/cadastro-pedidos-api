package br.com.portfolio.lsilva.cadastropedidosapi.service;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ItemPedido;

public interface IItemPedidoService<T> extends IBaseService<T> {

    ItemPedido updateValues(ItemPedido atual, ItemPedido original);
}
