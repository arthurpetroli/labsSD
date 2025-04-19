/**
 * Lab2
 * 
 * Autor: Arthur H de O Petroli e Séfora Davanso
 * Ultima atualizacao: 19/04/2025
 * 
 * Referencias: 
 * https://docs.oracle.com/javase/tutorial/essential/io
 */

 import java.io.BufferedReader;
 import java.io.DataInputStream;
 import java.io.DataOutputStream;
 import java.io.InputStreamReader;
 import java.net.Socket;
 
 public class Cliente {
 
     private static Socket socket;
     private static DataInputStream entrada;
     private static DataOutputStream saida;
 
     private final int porta = 1025;
     private final String host = "127.0.0.1";
 
     private String buildJson(String method, String[] args) {
         StringBuilder jsonBuilder = new StringBuilder();
         jsonBuilder.append("{\n\"method\":\"").append(method).append("\",\n\"args\":[");
         for (int i = 0; i < args.length; i++) {
             jsonBuilder.append("\"").append(args[i]).append("\"");
             if (i < args.length - 1) {
                 jsonBuilder.append(",");
             }
         }
         jsonBuilder.append("]\n}");
         return jsonBuilder.toString();
     }
 
     private boolean isNumeric(String str) {
         try {
             Integer.parseInt(str);
             return true;
         } catch (NumberFormatException e) {
             return false;
         }
     }
 
     private void menu(BufferedReader br) {
         try {
             while (true) {
                 System.out.println("\nEscolha uma opção:");
                 System.out.println("1 - Leitura");
                 System.out.println("2 - Escrita");
                 System.out.println("3 - Sair");
                 System.out.print("> ");
 
                 String opcao = br.readLine();
                 if (!isNumeric(opcao)) {
                     System.out.println("Opção inválida.");
                     continue;
                 }
 
                 int escolha = Integer.parseInt(opcao);
                 switch (escolha) {
                     case 1:
                         saida.writeUTF(buildJson("read", new String[]{""}));
                         System.out.println("Conteúdo do arquivo: " + entrada.readUTF());
                         break;
                     case 2:
                         System.out.println("Digite o conteúdo a ser escrito:");
                         String conteudo = br.readLine();
                         saida.writeUTF(buildJson("write", new String[]{conteudo}));
                         System.out.println("Resultado da escrita: " + entrada.readUTF());
                         break;
                     case 3:
                         saida.writeUTF("");
                         return;
                     default:
                         System.out.println("Opção inválida.");
                         break;
                 }
             }
         } catch (Exception e) {
             System.err.println("Erro no menu: " + e.getMessage());
         }
     }
 
     public void iniciar() {
         System.out.println("Cliente iniciado na porta: " + porta);
         try {
             socket = new Socket(host, porta);
             entrada = new DataInputStream(socket.getInputStream());
             saida = new DataOutputStream(socket.getOutputStream());
 
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             menu(br);
 
             socket.close();
             entrada.close();
             saida.close();
         } catch (Exception e) {
             System.err.println("Erro ao iniciar cliente: " + e.getMessage());
         }
     }
 
     public static void main(String[] args) {
         new Cliente().iniciar();
     }
 }
 