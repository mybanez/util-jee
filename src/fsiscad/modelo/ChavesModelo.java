package fsiscad.modelo;

/**
 * Contém as constantes necessárias para a implementação da camada dos modelos da
 * <i>framework</i>.
 */
public interface ChavesModelo {
    String NOME_PACOTE = ChavesModelo.class.getPackage().getName()+".";
    //Contexto JNDI
    String CONTEXTO_JNDI = "java:comp/env/fsc/";
    //Parâmetros no contexto JNDI
    String FABRICA_DELEGADOR = NOME_PACOTE+"FABRICA_DELEGADOR";
    String FABRICA_FACHADA = NOME_PACOTE+"FABRICA_FACHADA";
    String MAPA_CADASTROS = NOME_PACOTE+"MAPA_CADASTROS";
    String BLOCO_SEQUENCIA = NOME_PACOTE+"BLOCO_SEQUENCIA";
    String TENTATIVAS_SEQUENCIA = NOME_PACOTE+"TENTATIVAS_SEQUENCIA";
    String EJB_GERADOR_SEQUENCIA = NOME_PACOTE+"EJB_GERADOR_SEQUENCIA";
    String EJB_SEQUENCIA = NOME_PACOTE+"EJB_SEQUENCIA";
}
