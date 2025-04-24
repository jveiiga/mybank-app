package com.bank.controller;

import com.bank.DTO.NewPessoaDTO;
import com.bank.DTO.PessoaDTO;
import com.bank.DTO.PessoaSemEnderecoDTO;
import com.bank.model.Pessoa;
import com.bank.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = "http://localhost:5173")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarPessoa(@RequestBody Pessoa pessoa) {
        try {
            Pessoa novaPessoa = pessoaService.salvarPessoa(pessoa);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        try {
            pessoa.setId(id); // Define o ID da pessoa a ser atualizada
            Pessoa pessoaAtualizada = pessoaService.salvarPessoa(pessoa);
            return ResponseEntity.ok(pessoaAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerPessoa(@PathVariable Long id) {
        try {
            pessoaService.deletarPessoa(id);
            return ResponseEntity.ok("Pessoa removida com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover pessoa");
        }
    }

    @GetMapping("/IdNomeCpf")
    public List<PessoaDTO> listarIdomeCpf() {
        return pessoaService.listarIdNomeCpf();
    }

    @GetMapping
    public ResponseEntity<?> listaPessoasSemSuasContas() {
        try {
            List<NewPessoaDTO> pessoas = pessoaService.listaPessoasSemSuasContas();
            return ResponseEntity.ok(pessoas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar pessoas.");
        }
    }

    @GetMapping("/contas")
    public ResponseEntity<?> listaPessoasSemEnderecoComContas() {
        try {
            List<PessoaSemEnderecoDTO> pessoas = pessoaService.listaPessoasSemEnderecoComContas();
            return ResponseEntity.ok(pessoas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar pessoas.");
        }
    }
}
