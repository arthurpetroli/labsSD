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

public enum PeerLista {

    PEER1 {
        @Override
        public String getNome() {
            return "PEER1";
        }
    },
    PEER2 {
        @Override
        public String getNome() {
            return "PEER2";
        }
    },
    PEER3 {
        public String getNome() {
            return "PEER3";
        }
    },
    PEER4 {
        public String getNome() {
            return "PEER4";
        }
    };

    public String getNome() {
        return "NULO";
    }
}
