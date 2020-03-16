package br.com.portfolio.lsilva.cadastropedidosapi.resource;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ProdutoServico;
import br.com.portfolio.lsilva.cadastropedidosapi.service.imp.ProdutoServicoServiceImp;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/produtos-servicos")
public class ProdutoServicoResource {

    private final ProdutoServicoServiceImp serviceImp;

    @Autowired
    public ProdutoServicoResource(ProdutoServicoServiceImp serviceImp) {
        this.serviceImp = serviceImp;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(@QuerydslPredicate(root = ProdutoServico.class) Predicate predicate,
                                     Pageable pageable) {
        return ResponseEntity.ok(this.serviceImp.findAll(predicate, pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> findByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(this.serviceImp.findByUUID(uuid));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ProdutoServico produtoServico) {
        return ResponseEntity.ok(this.serviceImp.save(produtoServico));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@RequestBody ProdutoServico produtoServico, @PathVariable UUID uuid) {
        return ResponseEntity.ok(this.serviceImp.update(produtoServico, uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {
        this.serviceImp.delete(uuid);
        return ResponseEntity.ok("Produto/Serviço de id " + uuid + " deletado com sucesso.");
    }
}
