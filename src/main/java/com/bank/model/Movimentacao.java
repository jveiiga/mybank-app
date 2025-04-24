package com.bank.model;

import javax.persistence.*;
import com.bank.model.enums.TipoMovimentacao;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Alterado de Long para Integer

    @ManyToOne(optional = false)
    private Conta conta;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    public Long getId() {  // Alterado de Long para Integer
        return this.id;
    }

    public void setId(Long id) {  // Alterado de Long para Integer
        this.id = id;
    }

    public Conta getConta() {
        return this.conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return this.data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public TipoMovimentacao getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }
}
