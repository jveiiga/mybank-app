package com.bank.controller;

import com.bank.model.Movimentacao;
import com.bank.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
@CrossOrigin(origins = "http://localhost:5173")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @PostMapping("/cadastro")
    public ResponseEntity<?> realizarMovimentacao(@RequestBody Movimentacao movimentacao) {
        try {
            movimentacaoService.realizarMovimentacao(movimentacao);
            return ResponseEntity.ok("Movimentação realizada com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
        }
    }

    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<Movimentacao>> listarMovimentacoesPorConta(@PathVariable Long contaId) {
        try {
            List<Movimentacao> movimentacoes = movimentacaoService.listarMovimentacoesPorConta(contaId);
            return ResponseEntity.ok(movimentacoes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
