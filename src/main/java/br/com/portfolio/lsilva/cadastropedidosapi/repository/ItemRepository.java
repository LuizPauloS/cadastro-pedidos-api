package br.com.portfolio.lsilva.cadastropedidosapi.repository;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<ItemPedido, UUID>, QuerydslPredicateExecutor<ItemPedido> {

}
