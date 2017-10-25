package fsiscad.modelo.cadastro;

import fsiscad.util.*;
import fsiscad.modelo.ot.*;
import java.util.*;

/**
 * Suporte para implementa��es de cadastro.
 */
public abstract class CadastroImpl implements Cadastro {
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
    protected final static Collection getValoresChaveOT(OT ot) {
        Collection<String> clNomesPropsChave = ot.getNomesPropriedadesChave();
        if ((clNomesPropsChave == null) || (clNomesPropsChave.size() == 0)) {
            throw new ErroExecucao("Chave prim�ria do ot indefinida");
        }
        Collection clValsChave = new ArrayList();
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
}
