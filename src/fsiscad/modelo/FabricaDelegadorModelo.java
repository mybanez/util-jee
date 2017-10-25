package fsiscad.modelo;

import javax.naming.*;

/**
 * Suporte para a implementa��o da f�brica do delegador (<i>pattern Abstract
 * Factory</i>). Esta classe abstrata possui apenas o m�todo
 * <tt>getFabricaDelegadorModelo</tt>, que instancia dinamicamente a f�brica
 * do delegador a partir do nome de uma subclasse concreta de
 * <tt>FabricaDelegadorModelo</tt> recuperado do contexto da aplica��o J2EE. O 
 * nome da subclasse � buscado no contexto JNDI 'java:comp/env/fsc' usando-se 
 * a chave <tt>ChavesModelo.FABRICA_DELEGADOR</tt>. A inst�ncia da f�brica 
 * dever� possuir os m�todos de cria��o de delegador. 
 *
 * @see fsiscad.contexto.Contexto
 */
public class FabricaDelegadorModelo extends FabricaObjetoModelo {
    protected FabricaDelegadorModelo() {}
    
    /**
     * Retorna a f�brica do delegador. A inst�ncia da f�brica � criada
     * dinamicamente a partir do nome da classe da f�brica, buscado no contexto
     * da aplica��o J2EE usando-se a chave <tt>ChavesModelo.FABRICA_DELEGADOR</tt>.
     *
     * @return f�brica do delegador
     *
     * @throws ErroModelo se um erro for gerado durante a instancia��o da
     *         f�brica
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
            throw new ErroModelo("Erro obtendo f�brica do delegador", e);
        }
    }
}
