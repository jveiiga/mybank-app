package com.bank.DTO;

public class PessoaDTO {
    private Long id;
    private String nome;
    private String cpf;

    public PessoaDTO(Long id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}
