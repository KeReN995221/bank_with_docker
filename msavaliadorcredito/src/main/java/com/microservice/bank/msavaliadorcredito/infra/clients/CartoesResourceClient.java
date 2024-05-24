package com.microservice.bank.msavaliadorcredito.infra.clients;

import com.microservice.bank.msavaliadorcredito.domain.model.Cartao;
import com.microservice.bank.msavaliadorcredito.domain.model.CartaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam String cpf);

    @GetMapping(params = "renda")
    ResponseEntity <List<Cartao>> getCartoesRenda(@RequestParam("renda") Long renda);
}
