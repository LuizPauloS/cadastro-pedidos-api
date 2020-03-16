package br.com.portfolio.lsilva.cadastropedidosapi.service;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ProdutoServico;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IBaseService<T> {

    T save(T t);

    T update(T t, UUID uuid);

    void delete(UUID uuid);

    Optional<T> findByUUID(UUID uuid);

    Page<T> findAll(Predicate predicate, Pageable pageable);
}
