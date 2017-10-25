package fsiscad.modelo;

import fsiscad.modelo.ot.*;
import java.util.*;

/**
 * Delegador para a camada dos modelos implementados como <i>entity beans</i>
 * (<i>pattern Bussiness Delegate</i>). Os métodos desta classe simplesmente 
 * repassam os parâmetros recebidos para os métodos correspondentes na fachada 
 * da camada dos modelos, uma instância de {@link FachadaModeloEntity FachadaModeloEntity}.
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
     * Trata uma exceção gerada durante a execução de um dos métodos
     * <tt>consultarTodos</tt>. Levanta novamente o erro recebido caso este 
     * seja um <tt>ErroModelo</tt>. Caso contrário, levanta uma nova instância 
     * de <tt>ErroModelo</tt> contendo o erro recebido. Se este método for 
     * redefinido em uma subclasse e deixar de levantar um erro em alguma situação, 
     * o mesmo assume a responsabilidade de executar a consulta.
     * 
     * @param erro exceção gerada pelo método de consulta
     * @param usuario usuario logado
     * @param modelo modelo a ser consultado
     * @param mpMoldes moldes para os ots a serem retornados
     *
     * @return lista de objetos de tranferência com o resultado da
     *         consulta
     *
     * @throws ErroModelo se uma nova exceção for levantada
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
     * Trata uma exceção gerada durante a execução de um dos métodos
     * <tt>consultarPorChavePrimaria</tt>. Levanta
     * novamente o erro recebido caso este seja um <tt>ErroModelo</tt>. Caso
     * contrário, levanta  uma nova instância de <tt>ErroModelo</tt> contendo o
     * erro recebido. Se este método for redefinido em uma subclasse e deixar de
     * levantar um erro em alguma situação,  o mesmo assume a responsabilidade
     * de executar a consulta.
     *
     * @param erro exceção gerada pelo método de consulta
     * @param usuario usuario logado
     * @param modelo modelo a ser consultado
     * @param chave ot contendo a chave de consulta
     *        do item
     * @param mpMoldes moldes para os ots a serem retornados
     *
     * @return objeto de tranferência com o resultado da consulta
     *
     * @throws ErroModelo se uma nova exceção for levantada
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
