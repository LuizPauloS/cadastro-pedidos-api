package br.com.portfolio.lsilva.cadastropedidosapi.domain.entity;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.enumeration.Categoria;
import com.querydsl.core.annotations.QueryEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@QueryEntity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "produtos_servicos")
public class ProdutoServico extends BaseEntity {

    @NotNull(message = "Nome do produto ou serviço é obrigatório.")
    private String nome;

    private String descricao;

    @NotNull(message = "Categoria do produto ou serviço é obrigatório.")
    @Enumerated(EnumType.ORDINAL)
    private Categoria categoria;

    @NotNull(message = "Preco Unitário do produto ou serviço é obrigatório.")
    @DecimalMin(value = "0", message = "Preço não pode ser menor que 0.")
    private BigDecimal precoUnitario;

    @Min(value = 0, message = "Quantidade Estoque não pode ser menor que 0.")
    private int quantidadeEstoque;

    private boolean ativo = true;
}
