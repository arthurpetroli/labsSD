package lab5;

/**
 * Nome: SoapClient.java
 * Descricao: Classe para consultar CEP via ViaCEP
 * Author: Arthur Petroli e Sefora Davanso
 * Ultima atualizacao: 15/06/2025
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SoapClient {

    private String CEP;
    
    public void setCEP(String CEP){
        this.CEP = CEP;
    }
    
    public void consultarCEP() {
        try {
            // Criar a URL para o ViaCEP (que não requer autenticação)
            String url = "https://viacep.com.br/ws/" + CEP + "/json/";
            
            // Criar conexão HTTP
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            
            // Exibir URL de requisição
            System.out.println("Request HTTP Message:");
            System.out.println("GET " + url);
            
            // Ler resposta
            int responseCode = connection.getResponseCode();
            System.out.println("\nResponse HTTP Code: " + responseCode);
            System.out.println("Response Message:");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            // Exibir resultado
            System.out.println(formatarJSON(response.toString()));
            
        } catch (Exception e) {
            System.out.println("ERRO:");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Método para formatar o JSON para melhor visualização
    private String formatarJSON(String json) {
        json = json.replace("{", "{\n  ");
        json = json.replace("}", "\n}");
        json = json.replace(",", ",\n  ");
        return json;
    }
}
