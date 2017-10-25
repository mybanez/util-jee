package fsiscad.controle.struts;

import javax.servlet.http.*;
import org.apache.struts.action.*;

/**
 * A��o parametriz�vel que associa chaves pr�-definidas a opera��es de
 * cadastro. Os seguintes mapeamentos s�o criados:
 * <pre>
 *    ChavesControleStruts.PA_CONSULTAR_POR_CHAVE_PRIMARIA -> processarConsultaPorChavePrimaria
 *    ChavesControleStruts.PA_CONSULTAR_TODOS              -> processarConsultaTodos
 *    ChavesControleStruts.PA_INCLUIR                      -> processarInclusao
 *    ChavesControleStruts.PA_EXCLUIR                      -> processarExclusao
 *    ChavesControleStruts.PA_ALTERAR                      -> processarAlteracao
 * </pre>
 * <p>
 * Uma subclasse de <tt>AcaoCadastro</tt> precisa apenas redefinir os m�todos
 * <tt>processarXXX</tt> para os quais se deseja fornecer implementa��o. Por
 * exemplo, no trecho de c�digo JSP abaixo, se a URL <tt>incluir.do</tt>
 * estiver mapeada para uma a��o deste tipo, a adi��o do par�metro
 * <tt>ChavesControleStruts.PARAM_ACAO</tt> faz com que o m�todo
 * <tt>processarInclusao</tt> da classe seja executado quando o formul�rio for
 * submetido.
 * <pre>
 *   &lt;form method="FORM"
 *    action="incluir.do?&lt;%=ChavesControleStruts.PARAM_ACAO%&gt;=&lt;%=ChavesControleStruts.PA_INCLUIR%&gt;"&gt;
 *   ...
 *   &lt;form&gt;
 * </pre>
 * </p>
 */
public class AcaoCadastro extends AcaoParametrizavel {
    /**
     * Cria uma inst�ncia da a��o com as opera��es de cadastro padr�o
     * pr�-definidas.
     */
    public AcaoCadastro() {
        definirParametroOperacao(ChavesControleStruts.PA_CONSULTAR_POR_CHAVE_PRIMARIA,
                "processarConsultaPorChavePrimaria");
        definirParametroOperacao(ChavesControleStruts.PA_CONSULTAR_TODOS,
                "processarConsultaTodos");
        definirParametroOperacao(ChavesControleStruts.PA_INCLUIR,
                "processarInclusao");
        definirParametroOperacao(ChavesControleStruts.PA_EXCLUIR,
                "processarExclusao");
        definirParametroOperacao(ChavesControleStruts.PA_ALTERAR,
                "processarAlteracao");
    }
    
    /**
     * Processa requisi��o de consulta por chave prim�ria. Os par�metros e o tipo
     * de retorno s�o os mesmos do m�todo <tt>execute</tt> da classe
     * <tt>Action</tt>
     */
    public ActionForward processarConsultaPorChavePrimaria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        return null;
    }
    
    /**
     * Processa requisi��o de consulta de todos os itens. Os par�metros e o tipo
     * de retorno s�o os mesmos do m�todo <tt>execute</tt> da classe
     * <tt>Action</tt>
     */
    public ActionForward processarConsultaTodos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        return null;
    }
    
    /**
     * Processa requisi��o de inclus�o. Os par�metros e o tipo de retorno s�o os
     * mesmos do m�todo <tt>execute</tt> da classe <tt>Action</tt>
     */
    public ActionForward processarInclusao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        return null;
    }
    
    /**
     * Processa requisi��o de exclus�o. Os par�metros e o tipo de retorno s�o os
     * mesmos do m�todo <tt>execute</tt> da classe <tt>Action</tt>
     */
    public ActionForward processarExclusao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        return null;
    }
    
    /**
     * Processa requisi��o de altera��o. Os par�metros e o tipo de retorno s�o os
     * mesmos do m�todo <tt>execute</tt> da classe <tt>Action</tt>
     */
    public ActionForward processarAlteracao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        return null;
    }
}
