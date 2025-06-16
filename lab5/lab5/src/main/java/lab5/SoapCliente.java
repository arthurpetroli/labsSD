package lab5;

/**
 * Nome: SoapClient.java
 * Descricao: Classe para consultar o Web Service SOAP dos Correios 
 * Author: Arthur Petroli e Sefora Davanso
 * Ultima atualizacao: 16/06/2025
 * 
 */

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPConnection;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPPart;

public class SoapCliente {

    private String CEP;
    
    public void setCEP(String CEP){
        this.CEP = CEP;
    }
    
    public void callSoapWebService(String soapEndpointUrl, String soapAction) {
        try {
            // Criar conexao SOAP 
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Enviar SOAP Message para o server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Imprimir resposta
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.out.println("ERRO:");
            System.out.println(e.getMessage());
        }
    }

    private SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        // Criar mensagem SOAP
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        // Criar envelope SOAP
        createSoapEnvelope(soapMessage);

        // Adicionar cabeçalho de ação SOAP
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        // Exibir mensagem
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

    private void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // Namespace do serviço dos Correios
        String myNamespace = "cli";
        String myNamespaceURI = "http://cliente.bean.master.sigep.bsb.correios.com.br/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("consultaCEP", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("cep");
        soapBodyElem1.addTextNode(CEP);
    }
}