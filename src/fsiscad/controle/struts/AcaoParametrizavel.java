package fsiscad.controle.struts;

import fsiscad.util.*;
import fsiscad.controle.*;
import java.lang.reflect.*;
import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;

/**
 * A��o que executa uma opera��o a partir do valor passado em um determinado
 * par�metro. A chave da opera��o pode ser passada para a a��o de duas formas:
 * <p>
 * 1. Definindo o atributo <tt>parameter</tt> da tag <tt>&lt;action&gt;</tt> do
 * mapeamento em <tt>struts-config.xml</tt>.
 * </p>
 * <p>
 * 2. Acrescentando o par�metro <tt>ChavesControleStruts.PARAM_ACAO</tt> na URL
 * usada para chamar a a��o a partir de uma p�gina JSP ou de um Servlet.
 * </p>
 * <p>
 * Para um dado mapeamento do Struts, uma chave de opera��o definida atrav�s da
 * primeira t�cnica sobrep�e uma chave definida atraves da segunda t�cnica. As
 * chaves s�o associadas �s respectivas opera��es atrav�s de chamadas ao
 * m�todo <tt>definirParametroOperacao</tt>. Tipicamente, as subclasses de
 * <tt>AcaoParametrizavel</tt> fazem estas configura��es nos seus
 * construtores. Se o m�todo <tt>execute</tt> for redefinido em uma subclasse,
 * a implementa��o deve fazer uma chamada a <tt> super.execute(...).</tt>
 * </p>
 */
public class AcaoParametrizavel extends Action {
    private Map<String, Method> mpOperacoes = new HashMap<String, Method>();
    
    /**
     * Retorna a chave da opera��o a ser executada.
     *
     * @param mapping mapeamento da a��o
     * @param request requisi��o HTTP
     *
     * @return chave da opera��o
     */
    protected final String getParametro(ActionMapping mapping, HttpServletRequest request) {
        String param = mapping.getParameter();
        return ((param == null) || param.equals("")) ? request.getParameter(ChavesControleStruts.PARAM_ACAO) : param;
    }
    
    /**
     * Associa esta chave de opera��o a este nome de m�todo. O nome de ser o de 
     * um m�todo implementado na pr�pria classe ou herdado de uma subclasse. Se n�o
     * existir um m�todo com o nome informado, um erro de execu��o ser� gerado.
     *
     * @param chaveOperacao chave da opera��o
     * @param nomeMetodo nome do m�todo a ser executado
     */
    protected final void definirParametroOperacao(String chaveOperacao, String nomeMetodo) {
        if (mpOperacoes.containsKey(chaveOperacao)) {
            throw new ErroExecucao("Chave '"+chaveOperacao+"' j� mapeada: "+getClass().getName());
        }
        try {
            Method metOper = getClass().getMethod(nomeMetodo, new Class[]{ActionMapping.class, ActionForm.class, HttpServletRequest.class, HttpServletResponse.class});
            mpOperacoes.put(chaveOperacao, metOper);
        } catch (NoSuchMethodException e) {
            throw new ErroExecucao("M�todo 'org.apache.struts.action.ActionForward "+
                    nomeMetodo+
                    "(org.apache.struts.action.ActionMapping p0, org.apache.struts.action.ActionForm p1, javax.servlet.http.HttpServletRequest p2, javax.servlet.http.HttpServletResponse p3)' n�o foi declarado: "+
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
                throw new ErroControle("Erro executando m�todo '"+metodo.getName()+"'", ite.getTargetException());
            } catch (Exception e) {
                throw new ErroControle("Erro executando m�todo '"+metodo.getName()+"'", e);
            }
        } else {
            throw new ErroExecucao("Chave de opera��o '"+chvOper+"' n�o mapeada: "+getClass().getName());
        }
    }
}
