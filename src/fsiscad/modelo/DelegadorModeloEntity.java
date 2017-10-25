package fsiscad.modelo;

import fsiscad.modelo.ot.*;
import java.util.*;

/**
 * Delegador para a camada dos modelos implementados como <i>entity beans</i>
 * (<i>pattern Bussiness Delegate</i>). Os m�todos desta classe simplesmente 
 * repassam os par�metros recebidos para os m�todos correspondentes na fachada 
 * da camada dos modelos, uma inst�ncia de {@link FachadaModeloEntity FachadaModeloEntity}.
 */
public abstract class DelegadorModeloEntity extends DelegadorModelo implements AcessoModeloEntity {
    
    public Collection<OT> consultarTodos(String modelo, Map<String, OT> mpMoldes)
    throws ErroModelo {
        return consultarTodos(null, modelo, mpMoldes);
    }

    public final Collection<OT> consultarTodos(OT usuario, String modelo, Map<String, OT> mpMoldes)
    throws ErroModelo {
        try {
            return ((AcessoModeloEntity)getFachadaModelo()).consultarTodos(usuario, modelo, mpMoldes);
        } catch (Exception e) {
            return tratarErroConsultarTodos(e, usuario, modelo, mpMoldes);
        }
    }
    
    /**
     * Trata uma exce��o gerada durante a execu��o de um dos m�todos
     * <tt>consultarTodos</tt>. Levanta novamente o erro recebido caso este 
     * seja um <tt>ErroModelo</tt>. Caso contr�rio, levanta uma nova inst�ncia 
     * de <tt>ErroModelo</tt> contendo o erro recebido. Se este m�todo for 
     * redefinido em uma subclasse e deixar de levantar um erro em alguma situa��o, 
     * o mesmo assume a responsabilidade de executar a consulta.
     * 
     * @param erro exce��o gerada pelo m�todo de consulta
     * @param usuario usuario logado
     * @param modelo modelo a ser consultado
     * @param mpMoldes moldes para os ots a serem retornados
     *
     * @return lista de objetos de tranfer�ncia com o resultado da
     *         consulta
     *
     * @throws ErroModelo se uma nova exce��o for levantada
     */
    protected Collection<OT> tratarErroConsultarTodos(Exception erro, OT usuario, String modelo, Map<String, OT> mpMoldes)
    throws ErroModelo {
        try {
            throw erro;
        } catch (ErroModelo em) {
            throw em;
        } catch (Exception e) {
            throw new ErroModelo("Erro inesperado", e);
        }
    }
    
    public OT consultarPorChavePrimaria(String modelo, OT chave, Map<String, OT> mpMoldes)
    throws ErroModelo {
        return consultarPorChavePrimaria(null, modelo, chave, mpMoldes);
    }
    
    public final OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave, Map<String, OT> mpMoldes)
    throws ErroModelo {
        try {
            return ((AcessoModeloEntity)getFachadaModelo()).consultarPorChavePrimaria(usuario, modelo, chave, mpMoldes);
        } catch (Exception e) {
            return tratarErroConsultaPorChavePrimaria(e, usuario, modelo, chave, mpMoldes);
        }
    }
    
    /**
     * Trata uma exce��o gerada durante a execu��o de um dos m�todos
     * <tt>consultarPorChavePrimaria</tt>. Levanta
     * novamente o erro recebido caso este seja um <tt>ErroModelo</tt>. Caso
     * contr�rio, levanta  uma nova inst�ncia de <tt>ErroModelo</tt> contendo o
     * erro recebido. Se este m�todo for redefinido em uma subclasse e deixar de
     * levantar um erro em alguma situa��o,  o mesmo assume a responsabilidade
     * de executar a consulta.
     *
     * @param erro exce��o gerada pelo m�todo de consulta
     * @param usuario usuario logado
     * @param modelo modelo a ser consultado
     * @param chave ot contendo a chave de consulta
     *        do item
     * @param mpMoldes moldes para os ots a serem retornados
     *
     * @return objeto de tranfer�ncia com o resultado da consulta
     *
     * @throws ErroModelo se uma nova exce��o for levantada
     */
    protected OT tratarErroConsultaPorChavePrimaria(Exception erro, OT usuario, String modelo, OT chave, Map<String, OT> mpMoldes)
    throws ErroModelo {
        try {
            throw erro;
        } catch (ErroModelo em) {
            throw em;
        } catch (Exception e) {
            throw new ErroModelo("Erro inesperado", e);
        }
    }
}
