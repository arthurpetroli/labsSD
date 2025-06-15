package lab5;

/**
 * Nome: Principal2.java
 * Descricao: Consulta ao Web Services de CEP (ViaCEP)
 * Author: Arthur Petroli e Sefora Davanso
 * Ultima atualizacao: 15/06/2025
 */

public class Principal2 {
    public static void main(String[] args) {
        System.out.println("Principal: envia mensagem para o servidor");
        
        SoapClient sc = new SoapClient();
        
        // Requisicao1 - Consulta CEP da residência
        System.out.println("\n---Requisicao de Consulta de CEP---");
        // Substitua pelo seu CEP
        sc.setCEP("86812600");
        sc.consultarCEP();
      
        System.out.println("Fim!");
    }
}
