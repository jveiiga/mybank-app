package com.bank.service;

import com.bank.model.Conta;
import com.bank.model.Movimentacao;
import com.bank.model.enums.TipoMovimentacao;
import com.bank.repository.ContaRepository;
import com.bank.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Transactional
    public void realizarMovimentacao(Movimentacao mov) {
        // Validação: Conta deve existir
        Conta conta = contaRepository.findById(mov.getConta().getId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        // Validação: Valor da movimentação deve ser positivo
        if (mov.getValor() == null || mov.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da movimentação deve ser maior que zero.");
        }

        // Verificar tipo de movimentação
        if (mov.getTipo() == TipoMovimentacao.RETIRADA) {
            if (conta.getSaldo().compareTo(mov.getValor()) < 0) {
                throw new IllegalArgumentException("Saldo insuficiente.");
            }
            conta.setSaldo(conta.getSaldo().subtract(mov.getValor()));
            mov.setValor(mov.getValor().negate()); // Retiradas como valores negativos
        } else if (mov.getTipo() == TipoMovimentacao.DEPOSITO) {
            conta.setSaldo(conta.getSaldo().add(mov.getValor()));
        } else {
            throw new IllegalArgumentException("Tipo de movimentação inválido.");
        }

        mov.setData(LocalDateTime.now());
        movimentacaoRepository.save(mov);
        contaRepository.save(conta);
    }

    public List<Movimentacao> listarMovimentacoesPorConta(Long contaId) {
        // Validação: Verificar se a conta existe
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));
        // Retornar as movimentações relacionadas à conta
        return movimentacaoRepository.findByContaId(contaId);
    }
}
