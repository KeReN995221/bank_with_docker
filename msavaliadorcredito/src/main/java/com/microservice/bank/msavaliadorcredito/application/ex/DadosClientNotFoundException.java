package com.microservice.bank.msavaliadorcredito.application.ex;

public class DadosClientNotFoundException extends Exception {
    public DadosClientNotFoundException() {
        super("Dados do cliente não encontrados");
    }
}
