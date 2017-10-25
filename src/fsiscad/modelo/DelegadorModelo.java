package fsiscad.modelo;

import fsiscad.modelo.ot.*;
import java.util.*;

/**
 * Delegador para a camada dos modelos (<i>pattern Bussiness Delegate</i>). Os
 * métodos desta classe simplesmente repassam os parâmetros recebidos para os
 * métodos correspondentes na fachada da camada dos modelos, uma instância de
 * {@link FachadaModelo FachadaModelo} obtida através de uma chamada a 
 * <tt>getFachadaModelo</tt>. A função do delegador é eliminar
 * dependências entre as camadas de controle e interface visual e a tecnologia
 * de implementação usada na camada dos modelos. Deve ser usado, por exemplo,
 * quando a fachada é implementada como um <i>session bean</i>. Quando uma exceção é
 * gerada durante a execução de um método da fachada, a mesma é capturada e
 * passada como parâmetro para o método <tt>tratarErroXXX</tt> correspondente.
 * No caso das exceções <tt>java.rmi.RemoteException</tt>, que podem ser
 * geradas se a fachada for implementada como um EJB remoto, estas devem ser
 * substituídas por erros da aplicação adequados. Neste caso, as subclasses de
 * <tt>DelegadorModelo</tt> devem redefinir as implementações dos métodos de
 * tratamento de exceção, que por default embutem as exceções
 * <tt>RemoteException</tt> em instâncias de {@link ErroModelo ErroModelo}.
 * Este <i>pattern</i> é discutido em detalhes no livro <i>Core J2EE Patterns</i> de
 * Alur, Crupi e Malks.
 * 
 * @see FachadaModelo
 */
public abstract class DelegadorModelo extends AcessoModeloImpl {

    public final Collection<OT> consultarTodos(OT usuario, String modelo, OT molde)
    throws ErroModelo {
        try {
            return getFachadaModelo().consultarTodos(usuario, modelo, molde);
        } catch (Exception e) {
            return tratarErroConsultarTodos(e, usuario, modelo, molde);
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
     * @param molde molde para os ots a serem retornados
     *
     * @return lista de objetos de tranferência com o resultado da
     *         consulta
     *
     * @throws ErroModelo se uma nova exceção for levantada
     */
    protected Collection<OT> tratarErroConsultarTodos(Exception erro, OT usuario, String modelo, OT molde)
    throws ErroModelo {
        try {
            throw erro;
        } catch (ErroModelo em) {
            throw em;
        } catch (Exception e) {
            throw new ErroModelo("Erro inesperado", e);
        }
    }
    
    public final OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave, OT molde)
    throws ErroModelo {
        try {
            return getFachadaModelo().consultarPorChavePrimaria(usuario, modelo, chave, molde);
        } catch (Exception e) {
            return tratarErroConsultaPorChavePrimaria(e, usuario, modelo, chave, molde);
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
     * @param molde molde para os ots a serem retornados
     *
     * @return objeto de tranferência com o resultado da consulta
     *
     * @throws ErroModelo se uma nova exceção for levantada
     */
    protected OT tratarErroConsultaPorChavePrimaria(Exception erro, OT usuario, String modelo, OT chave, OT molde)
    throws ErroModelo {
        try {
            throw erro;
        } catch (ErroModelo em) {
            throw em;
        } catch (Exception e) {
            throw new ErroModelo("Erro inesperado", e);
        }
    }
    
    public final OT incluir(OT usuario, String modelo, OT ot)
    throws ErroModelo {
        try {
            return getFachadaModelo().incluir(usuario, modelo, ot);
        } catch (Exception e) {
            return tratarErroInclusao(e, usuario, modelo, ot);
        }
    }
    
    /**
     * Trata uma exceção gerada durante a execução de um dos métodos
     * <tt>incluir</tt>. Levanta novamente o erro
     * recebido caso este seja um <tt>ErroModelo</tt>. Caso contrário, levanta
     * uma nova instância de <tt>ErroModelo</tt> contendo o erro recebido. Se
     * este método for redefinido em uma subclasse e deixar de levantar um erro
     * em alguma situação,  o mesmo assume a responsabilidade de executar a
     * inclusão.
     *
     * @param erro exceção gerada pelo método de inclusão
     * @param usuario usuario logado
     * @param modelo modelo onde o item será incluído
     * @param ot ot contendo os dados do item
     *
     * @return objeto de tranferência com os dados do item incluído
     *
     * @throws ErroModelo se uma nova exceção for levantada
     */
    protected OT tratarErroInclusao(Exception erro, OT usuario, String modelo, OT ot)
    throws ErroModelo {
        try {
            throw erro;
        } catch (ErroModelo em) {
            throw em;
        } catch (Exception e) {
            throw new ErroModelo("Erro inesperado", e);
        }
    }
    
    public final OT alterar(OT usuario, String modelo, OT ot)
    throws ErroModelo {
        try {
            return getFachadaModelo().alterar(modelo, ot);
        } catch (Exception e) {
            return tratarErroAlteracao(e, usuario, modelo, ot);
        }
    }
    
    /**
     * Trata uma exceção gerada durante a execução de um dos métodos
     * <tt>alterar</tt>. Levanta novamente o erro
     * recebido caso este seja um <tt>ErroModelo</tt>. Caso contrário, levanta
     * uma nova instância de <tt>ErroModelo</tt> contendo o erro recebido. Se
     * este método for redefinido em uma subclasse e deixar de levantar um erro
     * em alguma situação,  o mesmo assume a responsabilidade de executar a
     * alteração.
     *
     * @param erro exceção gerada pelo método de alteração
     * @param usuario usuario logado
     * @param modelo modelo onde o item será alterado
     * @param ot ot contendo os dados do item
     *
     * @return objeto de tranferência com os dados do item alterado
     *
     * @throws ErroModelo se uma nova exceção for levantada
     */
    protected OT tratarErroAlteracao(Exception erro, OT usuario, String modelo, OT ot)
    throws ErroModelo {
        try {
            throw erro;
        } catch (ErroModelo em) {
            throw em;
        } catch (Exception e) {
            throw new ErroModelo("Erro inesperado", e);
        }
    }
    
    public final void excluir(OT usuario, String modelo, OT ot)
    throws ErroModelo {
        try {
            getFachadaModelo().excluir(usuario, modelo, ot);
        } catch (Exception e) {
            tratarErroExclusao(e, usuario, modelo, ot);
        }
    }
    
    /**
     * Trata uma exceção gerada durante a execução de um dos métodos
     * <tt>excluir</tt>. Levanta novamente o erro
     * recebido caso este seja um <tt>ErroModelo</tt>. Caso contrário, levanta
     * uma nova instância de <tt>ErroModelo</tt> contendo o erro recebido. Se
     * este método for redefinido em uma subclasse e deixar de levantar um erro
     * em alguma situação, o mesmo assume a responsabilidade de executar a
     * exclusão.
     *
     * @param erro exceção gerada pelo método de exclusão
     * @param usuario usuario logado
     * @param modelo modelo onde o item será excluído
     * @param ot ot contendo os dados do item
     *
     * @throws ErroModelo se uma nova exceção for levantada
     */
    protected void tratarErroExclusao(Exception erro, OT usuario, String modelo, OT ot)
    throws ErroModelo {
        try {
            throw erro;
        } catch (ErroModelo em) {
            throw em;
        } catch (Exception e) {
            throw new ErroModelo("Erro inesperado", e);
        }
    }
    
    /**
     * Retorna a fachada que provê acesso ao modelo.
     *
     * @return fachada que provê acesso ao modelo
     *
     * @throws ErroModelo se ocorrer um erro na obtenção da fachada do
     *         modelo
     */
    protected abstract AcessoModelo getFachadaModelo()
    throws ErroModelo;
}
