package br.com.portfolio.lsilva.cadastropedidosapi.domain.entity;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@Data
@ToString
@MappedSuperclass
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
}
