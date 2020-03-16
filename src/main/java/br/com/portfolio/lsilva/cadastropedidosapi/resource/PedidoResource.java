package br.com.portfolio.lsilva.cadastropedidosapi.resource;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.Pedido;
import br.com.portfolio.lsilva.cadastropedidosapi.service.imp.PedidoServiceImp;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

    private final PedidoServiceImp serviceImp;

    @Autowired
    public PedidoResource(PedidoServiceImp serviceImp) {
        this.serviceImp = serviceImp;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(@QuerydslPredicate(root = Pedido.class) Predicate predicate,
                                     Pageable pageable) {
        return ResponseEntity.ok(this.serviceImp.findAll(predicate, pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> findByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(this.serviceImp.findByUUID(uuid));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Pedido pedido) {
        return ResponseEntity.ok(this.serviceImp.save(pedido));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@RequestBody Pedido pedido, @PathVariable UUID uuid) {
        return ResponseEntity.ok(this.serviceImp.update(pedido, uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {
        this.serviceImp.delete(uuid);
        return ResponseEntity.ok("Pedido de id " + uuid + " deletado com sucesso.");
    }
}
