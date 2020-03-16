package br.com.portfolio.lsilva.cadastropedidosapi.service.imp;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ItemPedido;
import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ProdutoServico;
import br.com.portfolio.lsilva.cadastropedidosapi.exception.NotFoundException;
import br.com.portfolio.lsilva.cadastropedidosapi.repository.ItemRepository;
import br.com.portfolio.lsilva.cadastropedidosapi.service.IItemPedidoService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemPedidoServiceImp implements IItemPedidoService<ItemPedido> {

    private final ItemRepository repository;

    @Autowired
    public ItemPedidoServiceImp(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public ItemPedido save(ItemPedido itemPedido) {
        return this.repository.save(itemPedido);
    }

    @Override
    public ItemPedido update(ItemPedido itemPedido, UUID uuid) {
        Optional<ItemPedido> optional = this.repository.findById(uuid);
        if (optional.isPresent()) {
            itemPedido = updateValues(itemPedido, optional.get());
            return this.repository.save(itemPedido);
        }
        throw new NotFoundException("Item Pedido não encontrado na base de dados para atualização. " +
                "Verifique e tente novamente.");
    }

    @Override
    public void delete(UUID uuid) {
        Optional<ItemPedido> optional = this.repository.findById(uuid);
        if (optional.isPresent()) {
            this.repository.delete(optional.get());
        } else {
            throw new NotFoundException("Item Pedido de id " + uuid + " não encontrado para deletar " +
                    "da base de dados.");
        }
    }

    @Override
    public Optional<ItemPedido> findByUUID(UUID uuid) {
        return Optional.ofNullable(this.repository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Item Pedido de id " + uuid + " não encontrado.")));
    }

    @Override
    public Page<ItemPedido> findAll(Predicate predicate, Pageable pageable) {
        return this.repository.findAll(predicate, pageable);
    }

    @Override
    public ItemPedido updateValues(ItemPedido atual, ItemPedido original) {
//        original.setPedido(atual.getPedido() != null ?
//                atual.getPedido() : original.getPedido());
//        original.setProdutoServico(atual.getProdutoServico() != null ?
////                atual.getProdutoServico() : original.getProdutoServico());
//        original.setQuantidadePedido(atual.getQuantidadePedido() != null ?
//                atual.getQuantidadePedido() : original.getQuantidadePedido());
//        original.setSubtotal(atual.getSubtotal() != null && atual.getSubtotal() > 0?
//                atual.getSubtotal() : original.getSubtotal());
        return original;
    }
}
