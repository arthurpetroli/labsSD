package lab5;

/**
 * Nome: Principal2.java
 * Descricao: Consulta ao Web Services SOAP dos Correios
 * Author: Arthur Petroli e Sefora Davanso
 * Ultima atualizacao: 16/06/2025
 * 
 */

public class Principal2 {
    public static void main(String[] args) {
        System.out.println("Principal: envia mensagem para o servidor");
        
        // URL do serviço SOAP dos Correios
        String soapEndpointUrl = "https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente";
        String soapAction = "http://cliente.bean.master.sigep.bsb.correios.com.br/AtendeCliente/consultaCEP";
        
        SoapCliente sc = new SoapCliente();
        
        // Requisição de consulta de CEP
        System.out.println("\n---Requisicao1---");
  
        sc.setCEP("86812600");
        sc.callSoapWebService(soapEndpointUrl, soapAction);
      
        System.out.println("Fim!");
    }
}