
/**
 * Lab05: Sistema P2P
 * 
* Author: Arthur Petroli e Sefora Davanso
 * Ultima atualizacao: 15/06/2025
 * 
 * Referencias: 
 * https://docs.oracle.com/javase/tutorial/essential/io
 * http://fortunes.cat-v.org/
 */
import java.awt.*;
import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.util.Scanner;

public class Peer implements IMensagem {

	ArrayList<PeerLista> alocados;

	public Peer() {
		alocados = new ArrayList<>();
	}

	// Cliente: invoca o metodo remoto 'enviar'
	// Servidor: invoca o metodo local 'enviar'
	@Override
	public Mensagem enviar(Mensagem mensagem) throws RemoteException {
		Mensagem resposta;
		try {
			System.out.println("Mensagem recebida: " + mensagem.getMensagem());
			resposta = new Mensagem(parserJSON(mensagem.getMensagem()));
		} catch (Exception e) {
			e.printStackTrace();
			resposta = new Mensagem("{\n" + "\"result\": false\n" + "}");
		}
		return resposta;
	}

	public String parserJSON(String json) {
		String result = "false";

		String fortune = "-1";

		String[] v = json.split(":");
		System.out.println(">>>" + v[1]);
		String[] v1 = v[1].split("\"");
		System.out.println(">>>" + v1[1]);
		if (v1[1].equals("write")) {
			String[] p = json.split("\\[");
			System.out.println(p[1]);
			String[] p1 = p[1].split("]");
			System.out.println(p1[0]);
			String[] p2 = p1[0].split("\"");
			System.out.println(p2[1]);
			fortune = p2[1];

			// Write in file
			Principal pv2 = new Principal();
			pv2.write(fortune);
		} else if (v1[1].equals("read")) {
			// Read file
			Principal pv2 = new Principal();
			fortune = pv2.read();
		}

		result = "{\n" + "\"result\": \"" + fortune + "\"" + "}";
		System.out.println(result);

		return result;
	}

	public void iniciar() {

		try {
			// Adquire aleatoriamente um ID do PeerList
			List<PeerLista> listaPeers = new ArrayList<>();
			for (PeerLista peer : PeerLista.values())
				listaPeers.add(peer);

			Registry servidorRegistro;
			try {
				servidorRegistro = LocateRegistry.createRegistry(1099);
			} catch (java.rmi.server.ExportException e) { // Registro jah iniciado
				System.out.print("Registro jah iniciado. Usar o ativo.\n");
			}

			servidorRegistro = LocateRegistry.getRegistry(); // Registro eh unico para todos os peers
			String[] listaAlocados = servidorRegistro.list();
			for (int i = 0; i < listaAlocados.length; i++)
				System.out.println(listaAlocados[i] + " ativo.");
				
			do {
			// PeerLista peer = listaPeers.get(sr.nextInt(listaPeers.size()));
			exibirPeers(listaPeers);
			
			// para user escolher o peer
			PeerLista peer = escolherPeer(listaPeers);
			
			int tentativas = 0;
			boolean repetido = true;
			boolean cheio = false;
			// SecureRandom sr = new SecureRandom();
			while (repetido && !cheio) {
				repetido = false;
				// peer = listaPeers.get(sr.nextInt(listaPeers.size()));
				for (int i = 0; i < listaAlocados.length && !repetido; i++) {

					if (listaAlocados[i].equals(peer.getNome())) {
						// System.out.println(peer.getNome() + " ativo. Tentando proximo...");
						repetido = true;
						tentativas = i + 1;
					}

				}
				// System.out.println(tentativas+" "+listaAlocados.length);

				// Verifica se o registro estah cheio (todos alocados)
				if (listaAlocados.length > 0 && // Para o caso inicial em que nao ha servidor alocado,
												// caso contrario, o teste abaixo sempre serah true
						tentativas == listaPeers.size()) {
					cheio = true;
				}
			}

			if (cheio) {
				System.out.println("Sistema cheio. Tente mais tarde.");
				System.exit(1);
			}

			IMensagem skeleton = (IMensagem) UnicastRemoteObject.exportObject(this, 0); // 0: sistema operacional indica
																						// a porta (porta anonima)

			servidorRegistro.rebind(peer.getNome(), skeleton);
			System.out.print(peer.getNome() + " Servidor RMI: Aguardando conexoes...");

			// ---Cliente RMI
			new ClienteRMI().iniciarCliente(peer);
			desconectar(peer, listaPeers);
		 	}while(true);


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public PeerLista escolherPeer(List<PeerLista> listaPeers) {
		try{
			// Solicita ao usuário que escolha um peer
			Scanner scanner = new Scanner(System.in);
			System.out.print("\n Escolha um peer (digite o número correspondente): ");
			int escolha = scanner.nextInt();
			PeerLista peer = listaPeers.get(escolha);
			return peer;
		}catch(Exception e){
			System.out.println("Opção inválida. Tente novamente.");
			return escolherPeer(listaPeers);
		}
	}

	public void exibirPeers(List<PeerLista> listaPeers) {
		// verfica se tem peers ativos
		if (listaPeers.isEmpty()) { 
			System.out.println("Nenhum peer ativo no momento."); 
			return; 
		}

		// [INTERFACE COM SWING]
		/* JFrame frame = new JFrame("Peers Ativos");
		 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 frame.setSize(300, 200);
		 DefaultListModel<String> listModel = new DefaultListModel<>();
		 int i = 0;
		 for (PeerLista peer : listaPeers) {
		 listModel.addElement(i+") "+ peer.getNome());
		 i = i+1;
		 }
		 JList<String> peerList = new JList<>(listModel);
		 JScrollPane scrollPane = new JScrollPane(peerList);
		 frame.add(scrollPane, BorderLayout.CENTER);
		 frame.setVisible(true);
        */
		// [INTERFACE MOSTRANDO NO TERMINAL]
		int i = 0;
		System.out.println(" ");
		System.out.println("Peers Ativos");
		// printa os peers da lista de peers
		for (PeerLista peer : listaPeers) {
			System.out.println(i + ") " + peer.getNome());
			i = i + 1;
		}
	}

	public void desconectar(PeerLista peer, List<PeerLista> listaPeers ) {
		try {
			// pega o registro RMI do servidor
			Registry servidorRegistro = LocateRegistry.getRegistry();
			// faz unbind do peer 
			servidorRegistro.unbind(peer.getNome());
			// remove o objeto do sistema RMI 
			UnicastRemoteObject.unexportObject(this, false);
			// remove o peer da lista de peers
			listaPeers.remove(peer);	
			System.out.println("Desconectando peer ... ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		Peer servidor = new Peer();
		// servidor.exibirPeers();
		servidor.iniciar();

	}
}