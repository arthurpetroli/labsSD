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

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IMensagem extends Remote {
    
    public Mensagem enviar(Mensagem mensagem) throws RemoteException;
    
}
