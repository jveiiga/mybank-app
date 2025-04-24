package com.bank.DTO;

public class NewPessoaDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String endereco;

    public NewPessoaDTO(Long id, String nome, String cpf, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
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

    public String getEndereco() {
        return endereco;
    }
}
