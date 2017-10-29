package utljee.modelo;

/**
 * Cont�m as constantes necess�rias para a implementa��o da camada dos modelos da
 * <i>framework</i>.
 */
public interface ChavesModelo {
    String NOME_PACOTE = ChavesModelo.class.getPackage().getName()+".";
    //Contexto JNDI
    String CONTEXTO_JNDI = "java:comp/env/fsc/";
    //Par�metros no contexto JNDI
    String FABRICA_DELEGADOR = NOME_PACOTE+"FABRICA_DELEGADOR";
    String FABRICA_FACHADA = NOME_PACOTE+"FABRICA_FACHADA";
    String MAPA_CADASTROS = NOME_PACOTE+"MAPA_CADASTROS";
}
