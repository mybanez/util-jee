package meyn.util.modelo;

/**
 * Cont�m as constantes necess�rias para a implementa��o da camada dos modelos da
 * <i>framework</i>.
 */
public interface ChavesModelo {
    String PACOTE = ChavesModelo.class.getPackage().getName()+".";
    //Contexto JNDI
    String CONTEXTO_JNDI = "java:comp/env/fsc/";
    //Par�metros no contexto JNDI
    String FABRICA_FACHADA = PACOTE+"FABRICA_FACHADA";
    String MAPA_CADASTROS = PACOTE+"MAPA_CADASTROS";
}
