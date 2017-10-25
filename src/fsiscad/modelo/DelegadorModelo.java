package fsiscad.modelo;

import fsiscad.modelo.ot.*;
import java.util.*;

/**
 * Delegador para a camada dos modelos (<i>pattern Bussiness Delegate</i>). Os
 * m�todos desta classe simplesmente repassam os par�metros recebidos para os
 * m�todos correspondentes na fachada da camada dos modelos, uma inst�ncia de
 * {@link FachadaModelo FachadaModelo} obtida atrav�s de uma chamada a 
 * <tt>getFachadaModelo</tt>. A fun��o do delegador � eliminar
 * depend�ncias entre as camadas de controle e interface visual e a tecnologia
 * de implementa��o usada na camada dos modelos. Deve ser usado, por exemplo,
 * quando a fachada � implementada como um <i>session bean</i>. Quando uma exce��o �
 * gerada durante a execu��o de um m�todo da fachada, a mesma � capturada e
 * passada como par�metro para o m�todo <tt>tratarErroXXX</tt> correspondente.
 * No caso das exce��es <tt>java.rmi.RemoteException</tt>, que podem ser
 * geradas se a fachada for implementada como um EJB remoto, estas devem ser
 * substitu�das por erros da aplica��o adequados. Neste caso, as subclasses de
 * <tt>DelegadorModelo</tt> devem redefinir as implementa��es dos m�todos de
 * tratamento de exce��o, que por default embutem as exce��es
 * <tt>RemoteException</tt> em inst�ncias de {@link ErroModelo ErroModelo}.
 * Este <i>pattern</i> � discutido em detalhes no livro <i>Core J2EE Patterns</i> de
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
     * @param molde molde para os ots a serem retornados
     *
     * @return lista de objetos de tranfer�ncia com o resultado da
     *         consulta
     *
     * @throws ErroModelo se uma nova exce��o for levantada
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
     * @param molde molde para os ots a serem retornados
     *
     * @return objeto de tranfer�ncia com o resultado da consulta
     *
     * @throws ErroModelo se uma nova exce��o for levantada
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
     * Trata uma exce��o gerada durante a execu��o de um dos m�todos
     * <tt>incluir</tt>. Levanta novamente o erro
     * recebido caso este seja um <tt>ErroModelo</tt>. Caso contr�rio, levanta
     * uma nova inst�ncia de <tt>ErroModelo</tt> contendo o erro recebido. Se
     * este m�todo for redefinido em uma subclasse e deixar de levantar um erro
     * em alguma situa��o,  o mesmo assume a responsabilidade de executar a
     * inclus�o.
     *
     * @param erro exce��o gerada pelo m�todo de inclus�o
     * @param usuario usuario logado
     * @param modelo modelo onde o item ser� inclu�do
     * @param ot ot contendo os dados do item
     *
     * @return objeto de tranfer�ncia com os dados do item inclu�do
     *
     * @throws ErroModelo se uma nova exce��o for levantada
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
     * Trata uma exce��o gerada durante a execu��o de um dos m�todos
     * <tt>alterar</tt>. Levanta novamente o erro
     * recebido caso este seja um <tt>ErroModelo</tt>. Caso contr�rio, levanta
     * uma nova inst�ncia de <tt>ErroModelo</tt> contendo o erro recebido. Se
     * este m�todo for redefinido em uma subclasse e deixar de levantar um erro
     * em alguma situa��o,  o mesmo assume a responsabilidade de executar a
     * altera��o.
     *
     * @param erro exce��o gerada pelo m�todo de altera��o
     * @param usuario usuario logado
     * @param modelo modelo onde o item ser� alterado
     * @param ot ot contendo os dados do item
     *
     * @return objeto de tranfer�ncia com os dados do item alterado
     *
     * @throws ErroModelo se uma nova exce��o for levantada
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
     * Trata uma exce��o gerada durante a execu��o de um dos m�todos
     * <tt>excluir</tt>. Levanta novamente o erro
     * recebido caso este seja um <tt>ErroModelo</tt>. Caso contr�rio, levanta
     * uma nova inst�ncia de <tt>ErroModelo</tt> contendo o erro recebido. Se
     * este m�todo for redefinido em uma subclasse e deixar de levantar um erro
     * em alguma situa��o, o mesmo assume a responsabilidade de executar a
     * exclus�o.
     *
     * @param erro exce��o gerada pelo m�todo de exclus�o
     * @param usuario usuario logado
     * @param modelo modelo onde o item ser� exclu�do
     * @param ot ot contendo os dados do item
     *
     * @throws ErroModelo se uma nova exce��o for levantada
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
     * Retorna a fachada que prov� acesso ao modelo.
     *
     * @return fachada que prov� acesso ao modelo
     *
     * @throws ErroModelo se ocorrer um erro na obten��o da fachada do
     *         modelo
     */
    protected abstract AcessoModelo getFachadaModelo()
    throws ErroModelo;
}
