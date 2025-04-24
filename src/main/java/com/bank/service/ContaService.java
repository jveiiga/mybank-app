package com.bank.service;

import com.bank.model.Conta;
import com.bank.model.Pessoa;
import com.bank.repository.ContaRepository;
import com.bank.repository.PessoaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public Conta salvarConta(Conta conta, Long pessoaId) {
        // Validação: Pessoa deve existir
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));

        // Validação: Número da conta deve conter apenas números
        if (conta.getNumeroConta() == null || !conta.getNumeroConta().matches("\\d+")) {
            throw new IllegalArgumentException("O número da conta deve conter apenas números.");
        }

        boolean contaExiste = contaRepository.existsByNumeroConta(conta.getNumeroConta());
        if (contaExiste) {
            throw new IllegalArgumentException("Já existe uma conta com esse número.");
        }

        conta.setPessoa(pessoa);

        // Inicializa saldo como zero se não for informado
        if (conta.getSaldo() == null) {
            conta.setSaldo(BigDecimal.ZERO);
        }

        return contaRepository.save(conta);
    }

    @Transactional
    public Conta editarConta(Long contaId, Conta contaAtualizada) {
        Conta contaExistente = contaRepository.findById(contaId)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        // Validação: Número da conta deve conter apenas números
        if (contaAtualizada.getNumeroConta() == null || !contaAtualizada.getNumeroConta().matches("\\d+")) {
            throw new IllegalArgumentException("O número da conta deve conter apenas números.");
        }

        contaExistente.setNumeroConta(contaAtualizada.getNumeroConta()); // Atualiza o número da conta
        return contaRepository.save(contaExistente); // Salva as alterações no banco
    }

    @Transactional
    public void deletarConta(Long contaId) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));
        contaRepository.delete(conta);
    }

    public List<Conta> buscarContasPorPessoaId(Long pessoaId) {
        return contaRepository.findByPessoaId(pessoaId);
    }

    public List<Conta> buscarTodasContas() {
        return contaRepository.findAll();
    }
}
