package fsiscad.modelo.cadastro;

import fsiscad.modelo.*;
import fsiscad.util.*;
import java.io.*;
import java.util.*;
import javax.naming.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * Mapeamentos dos modelos da aplica��o em componentes cadastro. Esta classe �
 * usada por {@link fsiscad.modelo.cadastro.FabricaCadastro FabricaCadastro}
 * para carregar os mapeamentos entre os nomes l�gicos dos modelos e os
 * componentes do tipo {@link fsiscad.modelo.cadastro.Cadastro Cadastro} que
 * implementam as funcionalidades b�sicas de consulta e manuten��o dos
 * modelos. O nome do arquivo XML a ser carregado com as informa��es de
 * mapeamento � buscado no contexto JNDI 'java:comp/env/fsc' usando-se 
 * a chave <tt>ChavesModelo.MAPA_CADASTROS</tt>. 
 * 
 * @see fsiscad.contexto.Contexto
 */
public final class MapaCadastros extends HashMap<String, InfoCadastro> {
    public MapaCadastros()
    throws IOException, ParserConfigurationException, SAXException, NamingException {
        Context ctx = new InitialContext();
        String arquivo = (String)ctx.lookup(ChavesModelo.CONTEXTO_JNDI+ChavesModelo.MAPA_CADASTROS);
        ClassLoader carregador = Thread.currentThread().getContextClassLoader();
        InputStream in = carregador.getResourceAsStream(arquivo);
        if (in == null) {
            throw new IOException("Arquivo n�o encontrado: " + arquivo);
        }
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        fabrica.setValidating(true);
        fabrica.setIgnoringElementContentWhitespace(true);
        fabrica.setIgnoringComments(true);
        DocumentBuilder parser = fabrica.newDocumentBuilder();
        parser.setEntityResolver(new EntityResolver() {
            public InputSource resolveEntity(String publicId, String systemId) {
                if (systemId.endsWith("cadastros.dtd")) {
                    ClassLoader carregador = Thread.currentThread().getContextClassLoader();
                    InputSource isr =  new InputSource(carregador.getResourceAsStream("dtd/cadastros.dtd"));
                    return isr;
                }
                return null;
            }
        });
        Document documento = parser.parse(in);
        NodeList nosRaiz = documento.getChildNodes();
        for (int i = 0; i < nosRaiz.getLength(); i++) {
            if (nosRaiz.item(i).getNodeName().equals("cadastros")) {
                NodeList cadastros = UtilXML.ignorarComentarios(nosRaiz.item(i).getChildNodes());
                for (int j = 0; j < cadastros.getLength(); j++) {
                    Node cadastro = cadastros.item(j);
                    NodeList infoModelo = UtilXML.ignorarComentarios(cadastro.getChildNodes());
                    if (cadastro.getNodeName().equals("local")) {
                        String modelo = infoModelo.item(0).getFirstChild().getNodeValue();
                        String tipo = infoModelo.item(1).getFirstChild().getNodeValue();
                        put(modelo, new InfoCadastroLocal(modelo, tipo));
                    } else {
                        String modelo = infoModelo.item(0).getFirstChild().getNodeValue();
                        String home = infoModelo.item(1).getFirstChild().getNodeValue();
                        String nomeJNDI = infoModelo.item(2).getFirstChild().getNodeValue();
                        put(modelo, new InfoCadastroEntityLocal(modelo, home, nomeJNDI));
                    }
                }
            }
        }
    }
    
    /**
     * Retorna o mapa dos cadastros da aplica��o.
     *
     * @return mapa dos cadastros da aplica��o
     *
     * @throws ErroCadastro se um erro for gerado durante a inicializa��o do
     *         mapa
     */
    public static MapaCadastros getMapaCadastros()
    throws ErroCadastro {
        try {
            return (MapaCadastros)FabricaObjetoModelo.getInstanciaEmCache(ChavesModelo.MAPA_CADASTROS, MapaCadastros.class.getName());
        } catch (Exception e) {
            throw new ErroCadastro("Erro carregando mapa dos cadastros", e);
        }
    }
}
