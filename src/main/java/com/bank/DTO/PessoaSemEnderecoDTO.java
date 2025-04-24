package com.bank.DTO;

import java.util.List;

public class PessoaSemEnderecoDTO {
    private Long id;
    private String nome;
    private String cpf;
    private List<ContaDTO> contas;

    public PessoaSemEnderecoDTO(Long id, String nome, String cpf, List<ContaDTO> contas) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.contas = contas;
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

    public List<ContaDTO> getContas() {
        return contas;
    }
}
