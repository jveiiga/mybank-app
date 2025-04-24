package com.bank.DTO;

public class ContaDTO {
    private Long id;
    private String numero;

    public ContaDTO(Long id, String numero) {
        this.id = id;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }
}
