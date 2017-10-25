package fsiscad.controle.struts;

import javax.servlet.http.*;
import org.apache.struts.action.*;

/**
 * Ação parametrizável que associa chaves pré-definidas a operações de
 * cadastro. Os seguintes mapeamentos são criados:
 * <pre>
 *    ChavesControleStruts.PA_CONSULTAR_POR_CHAVE_PRIMARIA -> processarConsultaPorChavePrimaria
 *    ChavesControleStruts.PA_CONSULTAR_TODOS              -> processarConsultaTodos
 *    ChavesControleStruts.PA_INCLUIR                      -> processarInclusao
 *    ChavesControleStruts.PA_EXCLUIR                      -> processarExclusao
 *    ChavesControleStruts.PA_ALTERAR                      -> processarAlteracao
 * </pre>
 * <p>
 * Uma subclasse de <tt>AcaoCadastro</tt> precisa apenas redefinir os métodos
 * <tt>processarXXX</tt> para os quais se deseja fornecer implementação. Por
 * exemplo, no trecho de código JSP abaixo, se a URL <tt>incluir.do</tt>
 * estiver mapeada para uma ação deste tipo, a adição do parâmetro
 * <tt>ChavesControleStruts.PARAM_ACAO</tt> faz com que o método
 * <tt>processarInclusao</tt> da classe seja executado quando o formulário for
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
     * Cria uma instância da ação com as operações de cadastro padrão
     * pré-definidas.
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
     * Processa requisição de consulta por chave primária. Os parâmetros e o tipo
     * de retorno são os mesmos do método <tt>execute</tt> da classe
     * <tt>Action</tt>
     */
    public ActionForward processarConsultaPorChavePrimaria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        return null;
    }
    
    /**
     * Processa requisição de consulta de todos os itens. Os parâmetros e o tipo
     * de retorno são os mesmos do método <tt>execute</tt> da classe
     * <tt>Action</tt>
     */
    public ActionForward processarConsultaTodos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        return null;
    }
    
    /**
     * Processa requisição de inclusão. Os parâmetros e o tipo de retorno são os
     * mesmos do método <tt>execute</tt> da classe <tt>Action</tt>
     */
    public ActionForward processarInclusao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        return null;
    }
    
    /**
     * Processa requisição de exclusão. Os parâmetros e o tipo de retorno são os
     * mesmos do método <tt>execute</tt> da classe <tt>Action</tt>
     */
    public ActionForward processarExclusao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        return null;
    }
    
    /**
     * Processa requisição de alteração. Os parâmetros e o tipo de retorno são os
     * mesmos do método <tt>execute</tt> da classe <tt>Action</tt>
     */
    public ActionForward processarAlteracao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        return null;
    }
}
