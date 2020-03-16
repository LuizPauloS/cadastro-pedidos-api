package br.com.portfolio.lsilva.cadastropedidosapi.service;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ProdutoServico;

public interface IProdutoServicoService<T> extends IBaseService<T> {

     ProdutoServico updateValuesProdutoServico(ProdutoServico atual, ProdutoServico original);
}
