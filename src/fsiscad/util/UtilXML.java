package fsiscad.util;

import java.util.*;
import org.w3c.dom.*;

/**
 * Utilit�rio com rotinas para processamento XML.
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
     * Retira todos os n�s de coment�rios de uma lista de n�s de um documento
     * DOM.
     *
     * @param lista de n�s
     *
     * @return lista de n�s recebida sem coment�rios
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
