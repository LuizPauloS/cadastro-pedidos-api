package br.com.portfolio.lsilva.cadastropedidosapi.service;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.enumeration.Categoria;
import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ProdutoServico;
import br.com.portfolio.lsilva.cadastropedidosapi.exception.NotFoundException;
import br.com.portfolio.lsilva.cadastropedidosapi.repository.ProdutoServicoRepository;
import br.com.portfolio.lsilva.cadastropedidosapi.service.imp.ProdutoServicoServiceImp;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProdutoServicoServiceTest {

    @InjectMocks
    private ProdutoServicoServiceImp serviceImp;

    @Mock
    private ProdutoServicoRepository repository;

    private ProdutoServico produto;
    private ProdutoServico servico;
    private List<ProdutoServico> produtosServicos;

    @Before
    public void init() {
        this.produto = MockUtils.buildProdutoServico("Creme Dental", "Creme dental colgate 100ml",
                Categoria.PRODUTO, BigDecimal.valueOf(2.50), 10);
        this.servico = MockUtils.buildProdutoServico("Palestra",
                "Palestra sobre melhor forma de usar o creme dental colgate", Categoria.SERVICO,
                BigDecimal.valueOf(500.0),0);
        this.produtosServicos = Arrays.asList(produto, servico);
    }

    @Test
    public void deveBuscarTodosProdutosServicos() {
        Predicate predicate = new BooleanBuilder();
        Pageable pageable = PageRequest.of(0, 10);

        when(this.repository.findAll(predicate, pageable)).thenReturn(new PageImpl<>(produtosServicos));

        Page<ProdutoServico> produtosServicosPage = this.serviceImp.findAll(predicate, pageable);

        assertNotNull(produtosServicosPage);
        assertFalse(produtosServicosPage.getContent().isEmpty());
        assertTrue(produtosServicosPage.getTotalElements() > 0);
        assertEquals(produtosServicosPage.getSize(), produtosServicos.size());
        assertEquals(produtosServicosPage.getContent().get(0), produtosServicos.get(0));
        assertEquals(produtosServicosPage.getContent().get(1).getId(), produtosServicos.get(1).getId());
    }

    @Test
    public void deveBuscarProdutoServicoPorUuid() {
        when(this.repository.findById(servico.getId())).thenReturn(Optional.of(servico));

        Optional<ProdutoServico> optional = this.serviceImp.findByUUID(servico.getId());

        assertTrue(optional.isPresent());
        assertEquals(optional.get().getNome(), servico.getNome());
        assertNotEquals(optional.get().getId(), produto.getId());
        assertEquals(optional.get().getCategoria(), servico.getCategoria());
        assertNotEquals(optional.get().getDescricao(), produto.getDescricao());
    }

    @Test(expected = NotFoundException.class)
    public void naoDeveBuscarProdutoServicoUuidNaoEncontrado() {
        when(this.repository.findById(servico.getId())).thenReturn(Optional.empty());

        this.serviceImp.findByUUID(servico.getId());
    }

    @Test
    public void deveSalvarProdutoServico() {
        when(this.repository.save(produto)).thenReturn(produto);

        ProdutoServico produtoServico = this.serviceImp.save(produto);

        assertNotNull(produtoServico);
        assertNotNull(produtoServico.getId());
    }

    @Test
    public void deveAtualizarProdutoServico() {
        ProdutoServico produtoServicoAtualizado = MockUtils.buildProdutoServicoUuid(produto.getId(),
                produto.getNome(), produto.getDescricao(), produto.getCategoria(), produto.getPrecoUnitario(),
                produto.getQuantidadeEstoque());
        produtoServicoAtualizado.setPrecoUnitario(BigDecimal.valueOf(9.50));
        produtoServicoAtualizado.setQuantidadeEstoque(9);

        when(this.repository.findById(produtoServicoAtualizado.getId())).thenReturn(Optional.of(produto));
        when(this.repository.save(produtoServicoAtualizado)).thenReturn(produtoServicoAtualizado);

        ProdutoServico produtoServico = this.serviceImp.update(produtoServicoAtualizado, produto.getId());

        assertNotNull(produtoServico);
        assertNotNull(produtoServico.getId());
        assertEquals(produtoServico.getPrecoUnitario(), produtoServicoAtualizado.getPrecoUnitario());
    }

    @Test(expected = NotFoundException.class)
    public void naoDeveAtualizarProdutoServicoDeveOcorrerErroNaoEncontrado() {
        ProdutoServico produtoNovo = MockUtils.buildProdutoServico("Condicionador Nivea Men",
                "Condicionador Nivea Men para cabelos com oleosidade", Categoria.SERVICO,
                BigDecimal.valueOf(10.0), 200);

        when(this.repository.findById(produtoNovo.getId())).thenReturn(Optional.empty());

        this.serviceImp.update(produtoNovo, produtoNovo.getId());
    }

    @Test
    public void deveDeletarProdutoServico() {
        when(this.repository.findById(produto.getId())).thenReturn(Optional.of(produto));
        doNothing().when(this.repository).delete(produto);

        this.serviceImp.delete(produto.getId());
    }

    @Test(expected = NotFoundException.class)
    public void naoDeveDeletarProdutoServicoNaoEncontrado() {
        ProdutoServico produtoNovo = MockUtils.buildProdutoServico("Condicionador Nivea Men",
                "Condicionador Nivea Men para cabelos com oleosidade", Categoria.SERVICO,
                BigDecimal.valueOf(10.0), 200);

        when(this.repository.findById(produtoNovo.getId())).thenReturn(Optional.empty());

        this.serviceImp.delete(produtoNovo.getId());
    }
}
