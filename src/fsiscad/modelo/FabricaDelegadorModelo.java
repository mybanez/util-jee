package fsiscad.modelo;

import javax.naming.*;

/**
 * Suporte para a implementação da fábrica do delegador (<i>pattern Abstract
 * Factory</i>). Esta classe abstrata possui apenas o método
 * <tt>getFabricaDelegadorModelo</tt>, que instancia dinamicamente a fábrica
 * do delegador a partir do nome de uma subclasse concreta de
 * <tt>FabricaDelegadorModelo</tt> recuperado do contexto da aplicação J2EE. O 
 * nome da subclasse é buscado no contexto JNDI 'java:comp/env/fsc' usando-se 
 * a chave <tt>ChavesModelo.FABRICA_DELEGADOR</tt>. A instância da fábrica 
 * deverá possuir os métodos de criação de delegador. 
 *
 * @see fsiscad.contexto.Contexto
 */
public class FabricaDelegadorModelo extends FabricaObjetoModelo {
    protected FabricaDelegadorModelo() {}
    
    /**
     * Retorna a fábrica do delegador. A instância da fábrica é criada
     * dinamicamente a partir do nome da classe da fábrica, buscado no contexto
     * da aplicação J2EE usando-se a chave <tt>ChavesModelo.FABRICA_DELEGADOR</tt>.
     *
     * @return fábrica do delegador
     *
     * @throws ErroModelo se um erro for gerado durante a instanciação da
     *         fábrica
     */
    public final static FabricaDelegadorModelo getFabricaDelegadorModelo()
    throws ErroModelo {
        try {
            FabricaDelegadorModelo fabrica = (FabricaDelegadorModelo)getInstanciaEmCache(ChavesModelo.FABRICA_DELEGADOR);
            if (fabrica == null) {
                Context ctx = new InitialContext();
                String classeFabr = (String)ctx.lookup(ChavesModelo.CONTEXTO_JNDI+ChavesModelo.FABRICA_DELEGADOR);
                fabrica = (FabricaDelegadorModelo)getInstanciaEmCache(ChavesModelo.FABRICA_DELEGADOR, classeFabr);
            }
            return fabrica;
        } catch (Exception e) {
            throw new ErroModelo("Erro obtendo fábrica do delegador", e);
        }
    }
}
