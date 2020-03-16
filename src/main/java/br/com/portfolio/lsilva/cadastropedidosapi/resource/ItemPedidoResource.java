package br.com.portfolio.lsilva.cadastropedidosapi.resource;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ItemPedido;
import br.com.portfolio.lsilva.cadastropedidosapi.service.imp.ItemPedidoServiceImp;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/itens-pedidos")
public class ItemPedidoResource {

    private final ItemPedidoServiceImp serviceImp;

    @Autowired
    public ItemPedidoResource(ItemPedidoServiceImp serviceImp) {
        this.serviceImp = serviceImp;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(@QuerydslPredicate(root = ItemPedido.class) Predicate predicate,
                                     Pageable pageable) {
        return ResponseEntity.ok(this.serviceImp.findAll(predicate, pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> findByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(this.serviceImp.findByUUID(uuid));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ItemPedido itemPedido) {
        return ResponseEntity.ok(this.serviceImp.save(itemPedido));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@RequestBody ItemPedido itemPedido, @PathVariable UUID uuid) {
        return ResponseEntity.ok(this.serviceImp.update(itemPedido, uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {
        this.serviceImp.delete(uuid);
        return ResponseEntity.ok("Item Pedido de id " + uuid + " deletado com sucesso.");
    }
}
