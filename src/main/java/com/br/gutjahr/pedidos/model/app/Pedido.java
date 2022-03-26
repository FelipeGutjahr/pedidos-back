package com.br.gutjahr.pedidos.model.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.br.gutjahr.pedidos.model.managment.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pedido")
@JsonInclude(Include.NON_NULL)
public class Pedido extends CrudBaseModel<Integer> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message="Informe a data do pedido")
    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name="data")
    private Calendar data;
    @NotNull(message = "Informe o cliente")
    @ManyToOne()
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;
    @OneToMany(mappedBy="pedido",cascade=CascadeType.ALL)
	private List<PedidoItem> itens = new ArrayList<>();

    public Pedido() {}
}
