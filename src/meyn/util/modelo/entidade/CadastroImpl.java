package meyn.util.modelo.entidade;

import java.util.ArrayList;
import java.util.Collection;

import meyn.util.ErroExecucao;
import meyn.util.modelo.ot.OT;

/**
 * Suporte para implementa��es de cadastro.
 */
public abstract class CadastroImpl implements Cadastro {
    
    private String modelo;
    
	/**
     * Retorna o cadastro associado a este modelo.
     *
     * @param modelo nome l�gico do modelo
     *
     * @return cadastro associado a este modelo
     *
     * @throws ErroCadastro se ocorrer um erro na obten��o do cadastro
     */
    protected final static Cadastro getCadastro(String modelo)
    throws ErroCadastro {
        return FabricaCadastro.getCadastro(modelo);
    }
    
    /**
     * Retorna uma cole��o com os valores das propriedades chave deste ot. Verifica
     * se a chave atende a dois crit�rios: 
     * 1. Pelo menos o primeiro campo da chave deve ser n�o nulo.
     * 2. Se um campo da chave � nulo, todos os outros campos ap�s o mesmo
     * tamb�m devem ser nulos.
     *
     * @param ot objeto de tranfer�ncia
     *
     * @return cole��o com os valores das propriedades chave
     */
    protected final static Collection<Object> getValoresChaveOT(OT ot) {
        Collection<String> clNomesPropsChave = ot.getNomesPropriedadesChave();
        if ((clNomesPropsChave == null) || (clNomesPropsChave.size() == 0)) {
            throw new ErroExecucao("Chave prim�ria do ot indefinida");
        }
        Collection<Object> clValsChave = new ArrayList<Object>();
        boolean valorNulo = false;
        int cont = 0;
        for(String nome : clNomesPropsChave) {
            Object valor = ot.get(nome);
            if (valor == null) {
                //Primeiro campo n�o pode ser nulo
                if (cont++ == 0) {
                    throw new ErroExecucao("Chave prim�ria do ot nula");
                }
                valorNulo = true;
            } else {
                //Se um campo for nulo, todos os outros tamb�m devem ser 
                if (valorNulo == true) {
                    throw new ErroExecucao("Chave prim�ria do ot incompleta: "+ot.get());
                }
            }
            clValsChave.add(valor);
        }
        return clValsChave;
    }
    
    public final void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public final String getModelo() {
        return modelo;
    }
    
    public Collection<? extends OT> consultarTodos(OT usuario, Class<?> molde)
    throws ErroCadastro {
    	   throw new UnsupportedOperationException("consultarTodos");
    }    
    
    public OT consultarPorChavePrimaria(OT usuario, OT chave, Class<?> molde)
    throws ErroCadastro {
 	   throw new UnsupportedOperationException("consultarPorChavePrimaria");
    }
    
    public OT incluir(OT usuario, OT ot) 
    throws ErroCadastro {
 	   throw new UnsupportedOperationException("incluir");
    }
    
    public OT alterar(OT usuario, OT ot)
    throws ErroCadastro {
  	   throw new UnsupportedOperationException("alterar");
    }
    
    public void excluir(OT usuario, OT ot)
    throws ErroCadastro {
   	   throw new UnsupportedOperationException("excluir");
    }
}
