package com.bank.service;

import com.bank.DTO.ContaDTO;
import com.bank.DTO.NewPessoaDTO;
import com.bank.DTO.PessoaDTO;
import com.bank.DTO.PessoaSemEnderecoDTO;
import com.bank.model.Conta;
import com.bank.model.Pessoa;
import com.bank.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public Pessoa salvarPessoa(Pessoa pessoa) {
        // Validação: Nome deve ser obrigatório, iniciar com maiúsculas e sem números
        if (pessoa.getNome() == null || !pessoa.getNome().matches("^[A-Za-zÀ-ÿ ]+$")) {
            throw new IllegalArgumentException("O nome é obrigatório e deve conter apenas letras.");
        }
        pessoa.setNome(formatarNome(pessoa.getNome()));

        // Validação: CPF deve ser obrigatório e conter apenas números
        if (pessoa.getCpf() == null || !pessoa.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("O CPF é obrigatório e deve conter 11 números.");
        }

        // Validação: Endereço é obrigatório
        if (pessoa.getEndereco() == null || pessoa.getEndereco().isBlank()) {
            throw new IllegalArgumentException("O endereço é obrigatório.");
        }

        return pessoaRepository.save(pessoa);
    }

    private String formatarNome(String nome) {
        nome = nome.trim()
                .replaceAll("\\s+", " ")
                .toLowerCase();

        Pattern pattern = Pattern.compile("(^|\\s)([a-z])");
        Matcher matcher = pattern.matcher(nome);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1) + matcher.group(2).toUpperCase());
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    public List<PessoaDTO> listarIdNomeCpf() {
        return pessoaRepository.findAll()
                .stream()
                .map(p -> new PessoaDTO(p.getId(), p.getNome(), p.getCpf()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletarPessoa(Long id) {
        pessoaRepository.deleteById(id);
    }

    public List<NewPessoaDTO> listaPessoasSemSuasContas() {
        return pessoaRepository.findAll()
                .stream()
                .map(p -> new NewPessoaDTO(p.getId(), p.getNome(), p.getCpf(), p.getEndereco()))
                .collect(Collectors.toList());
    }

    public List<PessoaSemEnderecoDTO> listaPessoasSemEnderecoComContas() {
        return pessoaRepository.findAll()
                .stream()
                .map(p -> new PessoaSemEnderecoDTO(
                        p.getId(),
                        p.getNome(),
                        p.getCpf(),
                        p.getContas().stream()
                                .map(c -> new ContaDTO(c.getId(), c.getNumeroConta()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }


}
