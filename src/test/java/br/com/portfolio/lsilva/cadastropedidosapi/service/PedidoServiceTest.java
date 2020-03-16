package br.com.portfolio.lsilva.cadastropedidosapi.service;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.Pedido;
import br.com.portfolio.lsilva.cadastropedidosapi.exception.NotFoundException;
import br.com.portfolio.lsilva.cadastropedidosapi.repository.PedidoRepository;
import br.com.portfolio.lsilva.cadastropedidosapi.service.imp.PedidoServiceImp;
import br.com.portfolio.lsilva.cadastropedidosapi.util.MockUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoServiceImp serviceImp;

    @Mock
    private PedidoRepository repository;

    private Pedido pedidoAberto;
    private Pedido pedidoAberto2;
    private List<Pedido> pedidos = new ArrayList<>();

    @Before
    public void init() {
        this.pedidoAberto = MockUtils.buildPedido(true);
        this.pedidoAberto2 = MockUtils.buildPedido(true);
        this.serviceImp.calculateDiscountForProducts(pedidoAberto);
        this.serviceImp.calculateDiscountForProducts(pedidoAberto2);
        this.pedidos.add(pedidoAberto);
        this.pedidos.add(pedidoAberto2);
    }

    @Test
    public void deveBuscarTodosPedidos() {
        Predicate predicate = new BooleanBuilder();
        Pageable pageable = PageRequest.of(0, 10);

        when(this.repository.findAll(predicate, pageable)).thenReturn(new PageImpl<>(pedidos));

        Page<Pedido> pedidosPage = this.serviceImp.findAll(predicate, pageable);

        assertNotNull(pedidosPage);
        assertFalse(pedidosPage.getContent().isEmpty());
        assertTrue(pedidosPage.getTotalElements() > 0);
        assertEquals(pedidosPage.getSize(), pedidos.size());
        assertEquals(pedidosPage.getContent().get(0), pedidos.get(0));
        assertEquals(pedidosPage.getContent().get(1).getId(), pedidos.get(1).getId());
    }

    @Test
    public void deveBuscarPedidoPorUuid() {
        when(this.repository.findById(pedidoAberto.getId())).thenReturn(Optional.of(pedidoAberto));

        Optional<Pedido> optional = this.serviceImp.findByUUID(pedidoAberto.getId());

        assertTrue(optional.isPresent());
        assertEquals(optional.get().getId(), pedidoAberto.getId());
        assertEquals(optional.get().getValorTotal(), pedidoAberto.getValorTotal());
        assertEquals(optional.get().getPercentualDesconto(), pedidoAberto.getPercentualDesconto());
        assertEquals(optional.get().getItensPedido().size(), pedidoAberto.getItensPedido().size());
    }

    @Test(expected = NotFoundException.class)
    public void naoDeveBuscarPedidoUuidNaoEncontrado() {
        when(this.repository.findById(pedidoAberto.getId())).thenReturn(Optional.empty());

        this.serviceImp.findByUUID(pedidoAberto.getId());
    }

    @Test
    public void deveSalvarPedido() {
        when(this.repository.save(pedidoAberto)).thenReturn(pedidoAberto);

        Pedido pedido = this.serviceImp.save(pedidoAberto);

        assertNotNull(pedido);
        assertNotNull(pedido.getId());
        assertEquals(10, pedido.getItensPedido().size());
    }

    @Test
    public void deveAtualizarPedido() {
        Pedido pedidoAtualizado = MockUtils.buildPedidoUuid(pedidoAberto.getId(), pedidoAberto.getPedidoAberto(),
                pedidoAberto.getDataPedido(), 20, pedidoAberto.getItensPedido());

        when(this.repository.findById(pedidoAtualizado.getId())).thenReturn(Optional.of(pedidoAberto));
        when(this.repository.save(pedidoAtualizado)).thenReturn(pedidoAtualizado);

        Pedido pedido = this.serviceImp.update(pedidoAtualizado, pedidoAtualizado.getId());

        assertNotNull(pedido);
        assertNotNull(pedido.getId());
        assertEquals(pedido.getValorTotal(), pedidoAtualizado.getValorTotal());
    }

    @Test(expected = NotFoundException.class)
    public void naoDeveAtualizarPedidoDeveOcorrerErroNaoEncontrado() {
        Pedido pedidoNovo = MockUtils.buildPedido(true);

        when(this.repository.findById(pedidoNovo.getId())).thenReturn(Optional.empty());

        this.serviceImp.update(pedidoNovo, pedidoNovo.getId());
    }

    @Test
    public void deveDeletarPedido() {
        when(this.repository.findById(pedidoAberto.getId())).thenReturn(Optional.of(pedidoAberto));
        doNothing().when(this.repository).delete(pedidoAberto);

        this.serviceImp.delete(pedidoAberto.getId());
    }

    @Test(expected = NotFoundException.class)
    public void naoDeveDeletarPedidoNaoEncontrado() {
        Pedido pedidoNovo = MockUtils.buildPedido(true);

        when(this.repository.findById(pedidoNovo.getId())).thenReturn(Optional.empty());

        this.serviceImp.delete(pedidoNovo.getId());
    }
}
