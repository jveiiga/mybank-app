package com.bank.controller;

import com.bank.model.Conta;
import com.bank.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
@CrossOrigin(origins = "http://localhost:5173")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarConta(@RequestBody Conta conta, @RequestParam("pessoa_id") Long pessoaId) {
        try {
            Conta novaConta = contaService.salvarConta(conta, pessoaId);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarConta(@PathVariable Long id, @RequestBody Conta contaAtualizada) {
        try {
            Conta contaEditada = contaService.editarConta(id, contaAtualizada);
            return ResponseEntity.ok(contaEditada); // Retorna 200 OK com a conta editada
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao editar a conta");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarConta(@PathVariable Long id) {
        try {
            contaService.deletarConta(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content em caso de sucesso
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar a conta");
        }
    }

    @GetMapping
    public List<Conta> listarContas() {
        return contaService.buscarTodasContas();
    }

    @GetMapping("/pessoas/{pessoaId}")
    public List<Conta> listarContasPorPessoa(@PathVariable Long pessoaId) {
        return contaService.buscarContasPorPessoaId(pessoaId);
    }

}
