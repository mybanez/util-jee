package fsiscad.modelo;

import javax.naming.*;

/**
 * Suporte para a implementa��o da f�brica da fachada (<i>pattern Abstract
 * Factory</i>). Esta classe abstrata possui apenas o m�todo
 * <tt>getFabricaFachadaModelo</tt>, que instancia dinamicamente a f�brica
 * da fachada a partir do nome de uma subclasse concreta de
 * <tt>FabricaFachadaModelo</tt> recuperado do contexto da aplica��o J2EE. O 
 * nome da subclasse � buscado no contexto JNDI 'java:comp/env/fsc' usando-se 
 * a chave <tt>ChavesModelo.FABRICA_FACHADA</tt>. A inst�ncia da f�brica dever� 
 * possuir os m�todos de cria��o de fachada. 
 * 
 * @see fsiscad.contexto.Contexto
 */
public class FabricaFachadaModelo extends FabricaObjetoModelo {
    protected FabricaFachadaModelo() {}
    
    /**
     * Retorna a f�brica da fachada. A inst�ncia da f�brica � criada
     * dinamicamente a partir do nome da classe da f�brica, buscado no contexto
     * da aplica��o J2EE usando-se a chave <tt>ChavesModelo.FABRICA_FACHADA</tt>.
     *
     * @return f�brica da fachada
     *
     * @throws ErroModelo se um erro for gerado durante a instancia��o da
     *         f�brica
     */
    public final static FabricaFachadaModelo getFabricaFachadaModelo()
    throws ErroModelo {
        try {
            FabricaFachadaModelo fabrica = (FabricaFachadaModelo)getInstanciaEmCache(ChavesModelo.FABRICA_FACHADA);
            if (fabrica == null) {
                Context ctx = new InitialContext();
                String classeFabr = (String)ctx.lookup(ChavesModelo.CONTEXTO_JNDI+ChavesModelo.FABRICA_FACHADA);
                fabrica = (FabricaFachadaModelo)getInstanciaEmCache(ChavesModelo.FABRICA_FACHADA, classeFabr);
            }
            return fabrica;
        } catch (Exception e) {
            throw new ErroModelo("Erro obtendo f�brica da fachada", e);
        }
    }
}
