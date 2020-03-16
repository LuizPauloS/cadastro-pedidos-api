package br.com.portfolio.lsilva.cadastropedidosapi.service.imp;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.Pedido;
import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ProdutoServico;
import br.com.portfolio.lsilva.cadastropedidosapi.exception.NotFoundException;
import br.com.portfolio.lsilva.cadastropedidosapi.repository.PedidoRepository;
import br.com.portfolio.lsilva.cadastropedidosapi.repository.ProdutoServicoRepository;
import br.com.portfolio.lsilva.cadastropedidosapi.service.IProdutoServicoService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProdutoServicoServiceImp implements IProdutoServicoService<ProdutoServico> {

    private final PedidoRepository pedidoRepository;
    private final ProdutoServicoRepository produtoServicoRepository;

    @Autowired
    public ProdutoServicoServiceImp(PedidoRepository pedidoRepository,
                                    ProdutoServicoRepository produtoServicoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoServicoRepository = produtoServicoRepository;
    }

    @Override
    public ProdutoServico save(ProdutoServico produtoServico) {
        return this.produtoServicoRepository.save(produtoServico);
    }

    @Override
    public ProdutoServico update(ProdutoServico produtoServico, UUID uuid) {
        Optional<ProdutoServico> optional = this.produtoServicoRepository.findById(uuid);
        if (optional.isPresent()) {
            produtoServico = updateValuesProdutoServico(produtoServico, optional.get());
            return this.produtoServicoRepository.save(produtoServico);
        }
        throw new NotFoundException("Produto/Serviço não encontrado na base de dados para atualização. " +
                "Verifique e tente novamente.");
    }

    @Override
    public void delete(UUID uuid) {
        Optional<ProdutoServico> optional = this.produtoServicoRepository.findById(uuid);
        if (optional.isPresent()) {
            this.produtoServicoRepository.delete(optional.get());
        } else {
            throw new NotFoundException("Produto/Serviço de id " + uuid + " não encontrado para deletar " +
                    "da base de dados.");
        }
    }

    @Override
    public Optional<ProdutoServico> findByUUID(UUID uuid) {
        return Optional.ofNullable(this.produtoServicoRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Produto/Serviço de id " + uuid + " não encontrado.")));
    }

    @Override
    public Page<ProdutoServico> findAll(Predicate predicate, Pageable pageable) {
        return this.produtoServicoRepository.findAll(predicate, pageable);
    }

    public ProdutoServico updateValuesProdutoServico(ProdutoServico atual,
                                                     ProdutoServico original) {
        original.setNome(atual.getNome() != null ?
                atual.getNome() : original.getNome());
        original.setDescricao(atual.getDescricao() != null ?
                atual.getDescricao() : original.getDescricao());
        original.setCategoria(atual.getCategoria() != null ?
                atual.getCategoria() : original.getCategoria());
        original.setPrecoUnitario(atual.getPrecoUnitario() != null && !atual.getPrecoUnitario().equals(BigDecimal.ZERO) ?
                atual.getPrecoUnitario() : original.getPrecoUnitario());
        original.setQuantidadeEstoque(atual.getQuantidadeEstoque() > 0 ?
                atual.getQuantidadeEstoque() : original.getQuantidadeEstoque());
        return original;
    }

}
