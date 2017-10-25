package fsiscad.modelo;

import javax.naming.*;

/**
 * Suporte para a implementação da fábrica da fachada (<i>pattern Abstract
 * Factory</i>). Esta classe abstrata possui apenas o método
 * <tt>getFabricaFachadaModelo</tt>, que instancia dinamicamente a fábrica
 * da fachada a partir do nome de uma subclasse concreta de
 * <tt>FabricaFachadaModelo</tt> recuperado do contexto da aplicação J2EE. O 
 * nome da subclasse é buscado no contexto JNDI 'java:comp/env/fsc' usando-se 
 * a chave <tt>ChavesModelo.FABRICA_FACHADA</tt>. A instância da fábrica deverá 
 * possuir os métodos de criação de fachada. 
 * 
 * @see fsiscad.contexto.Contexto
 */
public class FabricaFachadaModelo extends FabricaObjetoModelo {
    protected FabricaFachadaModelo() {}
    
    /**
     * Retorna a fábrica da fachada. A instância da fábrica é criada
     * dinamicamente a partir do nome da classe da fábrica, buscado no contexto
     * da aplicação J2EE usando-se a chave <tt>ChavesModelo.FABRICA_FACHADA</tt>.
     *
     * @return fábrica da fachada
     *
     * @throws ErroModelo se um erro for gerado durante a instanciação da
     *         fábrica
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
            throw new ErroModelo("Erro obtendo fábrica da fachada", e);
        }
    }
}
