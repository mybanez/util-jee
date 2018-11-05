package meyn.util.modelo;

/**
 * Contém as constantes necessárias para a implementação da camada dos modelos da
 * <i>framework</i>.
 */
public interface ChavesModelo {
    String PACOTE = ChavesModelo.class.getPackage().getName()+".";
    //Contexto JNDI
    String CONTEXTO_JNDI = "java:comp/env/fsc/";
    //Parâmetros no contexto JNDI
    String FABRICA_FACHADA = PACOTE+"FABRICA_FACHADA";
    String MAPA_CADASTROS = PACOTE+"MAPA_CADASTROS";
}
