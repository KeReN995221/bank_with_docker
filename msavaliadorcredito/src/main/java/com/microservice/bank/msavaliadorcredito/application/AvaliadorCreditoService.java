package com.microservice.bank.msavaliadorcredito.application;

import com.microservice.bank.msavaliadorcredito.application.ex.DadosClientNotFoundException;
import com.microservice.bank.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import com.microservice.bank.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import com.microservice.bank.msavaliadorcredito.domain.model.*;
import com.microservice.bank.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.microservice.bank.msavaliadorcredito.infra.clients.ClienteResourceClient;
import com.microservice.bank.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartapPublisher;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClientNotFoundException, ErroComunicacaoMicroservicesException {
        try{
            ResponseEntity<DadosCliente> dadosClientesResponse = clientesClient.getCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);
            return SituacaoCliente
                    .builder()
                    .cliente(dadosClientesResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();

        }catch (FeignException.FeignClientException e){
             int status = e.status();
             if (HttpStatus.NOT_FOUND.value() == status) {
                 throw new DadosClientNotFoundException();
             }
             throw new ErroComunicacaoMicroservicesException(e.getMessage(),status);
        }
    }


    public RetornoAvalicaoCliente realizarAvalicao(String cpf, Long renda) throws DadosClientNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClientesResponse = clientesClient.getCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRenda(renda);
            List<Cartao> cartoes = cartoesResponse.getBody();

            var listaCartoesAprovados = cartoes.stream().map(cartao -> {
                DadosCliente dadosCliente = dadosClientesResponse.getBody();
                BigDecimal limiteBasico = cartao.getLimite();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());

                var fator =  idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);
                return aprovado;
            }).collect(Collectors.toList());

            return new RetornoAvalicaoCliente(listaCartoesAprovados);

        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClientNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(),status);
        }

    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
        try {
            emissaoCartapPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        }catch (Exception e){
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }

}
