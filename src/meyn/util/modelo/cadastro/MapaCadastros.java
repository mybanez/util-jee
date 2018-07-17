package meyn.util.modelo.cadastro;

import java.io.*;
import java.util.*;
import javax.naming.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import meyn.util.*;
import meyn.util.modelo.*;

/**
 * Mapeamentos dos modelos da aplicação em componentes cadastro. Esta classe é
 * usada por {@link meyn.util.modelo.entidade.FabricaCadastro FabricaCadastro}
 * para carregar os mapeamentos entre os nomes lógicos dos modelos e os
 * componentes do tipo {@link meyn.util.modelo.entidade.Cadastro Cadastro} que
 * implementam as funcionalidades básicas de consulta e manutenção dos
 * modelos. O nome do arquivo XML a ser carregado com as informações de
 * mapeamento é buscado no contexto JNDI 'java:comp/env/fsc' usando-se 
 * a chave <tt>ChavesModelo.MAPA_CADASTROS</tt>. 
 * 
 * @see meyn.util.contexto.Contexto
 */
@SuppressWarnings("serial")
public final class MapaCadastros extends HashMap<String, InfoCadastro> {
	protected String getArquivoMapa() {
		String arquivo;
		try {
			Context ctx = new InitialContext();
			arquivo = (String)ctx.lookup(ChavesModelo.CONTEXTO_JNDI+ChavesModelo.MAPA_CADASTROS);
		} catch (NamingException e) {
			arquivo = "cadastros.xml";
		}
        return arquivo;
		
	}
	
    public MapaCadastros()
    throws IOException, ParserConfigurationException, SAXException, NamingException {
        ClassLoader carregador = Thread.currentThread().getContextClassLoader();
        String arquivo = getArquivoMapa();
        InputStream in = carregador.getResourceAsStream(arquivo);
        if (in == null) {
            throw new IOException("Arquivo não encontrado: " + arquivo);
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
                    InputSource isr =  new InputSource(carregador.getResourceAsStream("meyn/util/dtd/cadastros.dtd"));
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
                        put(modelo, new InfoCadastro(modelo, tipo));
                    } else {
                        throw new ErroExecucao("Tipo de cadastro não suportado: "+cadastro.getNodeName());
                    }
                }
            }
        }
    }
    
    /**
     * Retorna o mapa dos cadastros da aplicação.
     *
     * @return mapa dos cadastros da aplicação
     *
     * @throws ErroCadastro se um erro for gerado durante a inicialização do
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
