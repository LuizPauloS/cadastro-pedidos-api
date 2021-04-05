package br.com.portfolio.lsilva.cadastropedidosapi.domain.dto;

import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ItemPedido;
import br.com.portfolio.lsilva.cadastropedidosapi.domain.entity.ProdutoServico;
import br.com.portfolio.lsilva.cadastropedidosapi.domain.enumeration.Categoria;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProdutoServicoDTO implements Serializable {

    @NotNull(message = "Nome do produto ou serviço é obrigatório.")
    private String nome;

    private String descricao;

    @NotNull(message = "Categoria do produto ou serviço é obrigatório.")
    private Integer categoria;

    @NotNull(message = "Preco Unitário do produto ou serviço é obrigatório.")
    @DecimalMin(value = "0", message = "Preço não pode ser menor que 0.")
    private BigDecimal precoUnitario;

    @Min(value = 0, message = "Quantidade Estoque não pode ser menor que 0.")
    private int quantidadeEstoque;

    public ProdutoServico transformaParaObjeto(){
        return new ProdutoServico(nome, descricao, Categoria.findById(categoria).findFirst().get(),
                precoUnitario, quantidadeEstoque, true);
    }
}
