package com.br.gutjahr.pedidos.model.app;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pedido_item")
@JsonInclude(Include.NON_NULL)
public class PedidoItem extends CrudBaseModel<Integer> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Informe a quantidade")
    @Column(precision = 10, scale = 2)
    private BigDecimal quantidade;
    @NotNull(message = "Informe o preço unitário")
    @Column(precision = 10, scale = 2, name = "preco_unitario")
    private BigDecimal precoUnitario;
    @Column(precision = 10, scale = 2)
    private BigDecimal total;
    @ManyToOne(optional=false,fetch=FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
    @ManyToOne(optional=false,fetch=FetchType.LAZY)
	@JoinColumn(name="pedido_id")
	private Pedido pedido;

    public PedidoItem() {
        quantidade = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
    }
}
