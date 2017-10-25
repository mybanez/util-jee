package fsiscad.controle.struts;

import fsiscad.util.*;
import fsiscad.controle.*;
import java.lang.reflect.*;
import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;

/**
 * Ação que executa uma operação a partir do valor passado em um determinado
 * parâmetro. A chave da operação pode ser passada para a ação de duas formas:
 * <p>
 * 1. Definindo o atributo <tt>parameter</tt> da tag <tt>&lt;action&gt;</tt> do
 * mapeamento em <tt>struts-config.xml</tt>.
 * </p>
 * <p>
 * 2. Acrescentando o parâmetro <tt>ChavesControleStruts.PARAM_ACAO</tt> na URL
 * usada para chamar a ação a partir de uma página JSP ou de um Servlet.
 * </p>
 * <p>
 * Para um dado mapeamento do Struts, uma chave de operação definida através da
 * primeira técnica sobrepõe uma chave definida atraves da segunda técnica. As
 * chaves são associadas às respectivas operações através de chamadas ao
 * método <tt>definirParametroOperacao</tt>. Tipicamente, as subclasses de
 * <tt>AcaoParametrizavel</tt> fazem estas configurações nos seus
 * construtores. Se o método <tt>execute</tt> for redefinido em uma subclasse,
 * a implementação deve fazer uma chamada a <tt> super.execute(...).</tt>
 * </p>
 */
public class AcaoParametrizavel extends Action {
    private Map<String, Method> mpOperacoes = new HashMap<String, Method>();
    
    /**
     * Retorna a chave da operação a ser executada.
     *
     * @param mapping mapeamento da ação
     * @param request requisição HTTP
     *
     * @return chave da operação
     */
    protected final String getParametro(ActionMapping mapping, HttpServletRequest request) {
        String param = mapping.getParameter();
        return ((param == null) || param.equals("")) ? request.getParameter(ChavesControleStruts.PARAM_ACAO) : param;
    }
    
    /**
     * Associa esta chave de operação a este nome de método. O nome de ser o de 
     * um método implementado na própria classe ou herdado de uma subclasse. Se não
     * existir um método com o nome informado, um erro de execução será gerado.
     *
     * @param chaveOperacao chave da operação
     * @param nomeMetodo nome do método a ser executado
     */
    protected final void definirParametroOperacao(String chaveOperacao, String nomeMetodo) {
        if (mpOperacoes.containsKey(chaveOperacao)) {
            throw new ErroExecucao("Chave '"+chaveOperacao+"' já mapeada: "+getClass().getName());
        }
        try {
            Method metOper = getClass().getMethod(nomeMetodo, new Class[]{ActionMapping.class, ActionForm.class, HttpServletRequest.class, HttpServletResponse.class});
            mpOperacoes.put(chaveOperacao, metOper);
        } catch (NoSuchMethodException e) {
            throw new ErroExecucao("Método 'org.apache.struts.action.ActionForward "+
                    nomeMetodo+
                    "(org.apache.struts.action.ActionMapping p0, org.apache.struts.action.ActionForm p1, javax.servlet.http.HttpServletRequest p2, javax.servlet.http.HttpServletResponse p3)' não foi declarado: "+
                    getClass().getName());
        }
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws ErroControle {
        String chvOper = getParametro(mapping, request);
        Method metodo = mpOperacoes.get(chvOper);
        if (metodo != null) {
            try {
                return (ActionForward) metodo.invoke(this, new Object[] { mapping, form, request, response });
            } catch (InvocationTargetException ite) {
                throw new ErroControle("Erro executando método '"+metodo.getName()+"'", ite.getTargetException());
            } catch (Exception e) {
                throw new ErroControle("Erro executando método '"+metodo.getName()+"'", e);
            }
        } else {
            throw new ErroExecucao("Chave de operação '"+chvOper+"' não mapeada: "+getClass().getName());
        }
    }
}
