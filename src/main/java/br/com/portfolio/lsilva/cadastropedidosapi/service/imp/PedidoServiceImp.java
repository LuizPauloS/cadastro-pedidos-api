package br.com.portfolio.lsilva.cadastropedidosapi.service.imp;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ItemPedido;
import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.Pedido;
import br.com.portfolio.lsilva.cadastropedidosapi.domain.enumeration.Categoria;
import br.com.portfolio.lsilva.cadastropedidosapi.exception.BadRequestException;
import br.com.portfolio.lsilva.cadastropedidosapi.exception.NotFoundException;
import br.com.portfolio.lsilva.cadastropedidosapi.repository.PedidoRepository;
import br.com.portfolio.lsilva.cadastropedidosapi.service.IPedidoService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoServiceImp implements IPedidoService<Pedido> {

    private final PedidoRepository repository;

    @Autowired
    public PedidoServiceImp(PedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pedido save(Pedido pedido) {
        pedido = calculateDiscountForProducts(pedido);
        return this.repository.save(pedido);
    }

    @Override
    public Pedido update(Pedido pedido, UUID uuid) {
        Optional<Pedido> optional = this.repository.findById(uuid);
        if (optional.isPresent()) {
            pedido = updateValues(pedido, optional.get());
            return this.repository.save(pedido);
        }
        throw new NotFoundException("Pedido não encontrado na base de dados para atualização. " +
                "Verifique e tente novamente.");
    }

    @Override
    public void delete(UUID uuid) {
        Optional<Pedido> optional = this.repository.findById(uuid);
        if (optional.isPresent()) {
            this.repository.delete(optional.get());
        } else {
            throw new NotFoundException("Pedido de id " + uuid + " não encontrado para deletar " +
                    "da base de dados.");
        }
    }

    @Override
    public Optional<Pedido> findByUUID(UUID uuid) {
        return Optional.ofNullable(this.repository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Pedido de id " + uuid + " não encontrado.")));
    }

    @Override
    public Page<Pedido> findAll(Predicate predicate, Pageable pageable) {
        return this.repository.findAll(predicate, pageable);
    }

    @Override
    public Pedido updateValues(Pedido atual, Pedido original) {
        original.setItensPedido(atual.getItensPedido() != null && !atual.getItensPedido().isEmpty() ?
                atual.getItensPedido() : original.getItensPedido());
        original.setDataPedido(atual.getDataPedido() != null ?
                atual.getDataPedido() : original.getDataPedido());
        original.setPercentualDesconto(atual.getPercentualDesconto() > 0 &&
                (atual.getPercentualDesconto() != original.getPercentualDesconto()) ?
                atual.getPercentualDesconto() : original.getPercentualDesconto());
        original.setPedidoAberto(atual.getPedidoAberto() != null ?
                atual.getPedidoAberto() : original.getPedidoAberto());
        if ((atual.getPercentualDesconto() != original.getPercentualDesconto()) &&
                !atual.getItensPedido().isEmpty()) {
            original = this.calculateDiscountForProducts(atual);
        }
        return original;
    }

    @Override
    public Pedido calculateDiscountForProducts(Pedido pedido) {
        double descontoPedido, valorPedido, totalPedido = 0;
        if (pedido.getPercentualDesconto() > 0) {
            for (ItemPedido itemPedido : pedido.getItensPedido()) {
                validItemPedido(itemPedido);
                if (Categoria.PRODUTO.equals(itemPedido.getProdutoServico().getCategoria())) {
                    valorPedido = getTotalPedido(itemPedido);
                    descontoPedido = getDescontoPedido(pedido, valorPedido);
                    totalPedido += BigDecimal.valueOf(valorPedido)
                            .subtract(BigDecimal.valueOf(descontoPedido)).doubleValue();
                } else {
                    totalPedido += getTotalPedido(itemPedido);
                }
            }
            pedido.setValorTotal(getTotalPedidoFormatado(totalPedido));
        }
        return pedido;
    }

    private void validItemPedido(ItemPedido itemPedido) {
        if (!itemPedido.getProdutoServico().isAtivo()) {
            throw new BadRequestException("Não é possível criar um Pedido com um Produto/Serviço desativado.");
        }
    }

    private Double getTotalPedidoFormatado(double totalPedido) {
        return BigDecimal.valueOf(totalPedido).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    private double getTotalPedido(ItemPedido itemPedido) {
        return itemPedido.getProdutoServico().getPrecoUnitario().multiply(BigDecimal
                .valueOf(itemPedido.getQuantidadeItem())).doubleValue();
    }

    private double getDescontoPedido(Pedido pedido, double totalPedido) {
        return BigDecimal.valueOf(totalPedido)
                .multiply(BigDecimal.valueOf(pedido.getPercentualDesconto()))
                .divide(new BigDecimal(100)).doubleValue();
    }
}
