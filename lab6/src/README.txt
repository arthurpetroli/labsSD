Peer.java eh o ServerImpl.java dos laboratorios anteriores, porem
agora cada Peer.java pode se conectar diretamente a outro Peer.java. 
  

Peer.java eh, ao mesmo tempo, servidor e cliente. (P2P)
Mas, ClienteRMI nao pode invocar novamente ServidorImpl (loop)
(ClienteRMI agora eh usado apenas pelo Peer.java, que eh o peer.) 

TODO: Adicionar 1 (uma) das seguintes funcionalidades:
1) Pelo menos 1 (um) novo peer deve ser adicionado no enum PeerLista. DONE -->  src/PeerLista.java /linha 25
2) Interface grafica DONE --> src/Peer.java /linha 152
3) Implementar unbind no Peer.java  DONE --> src/Peer.java /linha 193
4) Peer.java deve ser capaz de escolher o peer. DONE --> src/Peer.java /linha 159