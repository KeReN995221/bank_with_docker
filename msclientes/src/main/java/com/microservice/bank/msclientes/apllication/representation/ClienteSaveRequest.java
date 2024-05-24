package com.microservice.bank.msclientes.apllication.representation;

import com.microservice.bank.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteSaveRequest {
    private String nome;
    private String cpf;
    private Integer idade;

    public Cliente toModel(){
        return new Cliente(cpf, nome,  idade);
    }
}
