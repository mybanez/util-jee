package fsiscad.util;

import java.util.*;
import org.w3c.dom.*;

/**
 * Utilitário com rotinas para processamento XML.
 */
public final class UtilXML {
    private static class ListaDeNos implements NodeList {
        List<Node> nodes = new ArrayList<Node>();
        
        public Node item(int i) {
            return nodes.get(i);
        }
        
        public int getLength() {
            return nodes.size();
        }
    }
    
    private UtilXML() {}
    
    /**
     * Retira todos os nós de comentários de uma lista de nós de um documento
     * DOM.
     *
     * @param lista de nós
     *
     * @return lista de nós recebida sem comentários
     */
    public static NodeList ignorarComentarios(NodeList lista) {
        if (lista == null) {
            return null;
        }
        ListaDeNos res = new ListaDeNos();
        for (int i = 0; i < lista.getLength(); i++) {
            Node n = lista.item(i);
            if (n.getNodeType() != Node.COMMENT_NODE) {
                res.nodes.add(n);
            }
        }
        return res;
    }
}
