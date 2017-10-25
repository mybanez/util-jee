package fsiscad.contexto;

import java.util.*;

/**
 * Contexto de execu��o de uma aplica��o. M�ltiplos contextos podem ser criados e  
 * associados a chaves gen�ricas. A chave identifica unicamente uma inst�ncia de 
 * contexto <b>no escopo do <i>class loader</i> desta classe</b>. O contexto em 
 * si � um conjunto arbitr�rio de v�nculos entre chaves <tt>String</tt> e objetos.
 * <p>
 * Esta classe � utilizada pela <i>framework</i> para armazenamento e recupera��o 
 * de objetos de controle internos sem incorrer nos problemas de performance inerentes 
 * ao uso do contexto JNDI. Se a <i>framework</i> for carregada como biblioteca pelo 
 * <i>class loader</i> de sistema, o espa�o de chaves de contexto � o mesmo para todas 
 * as aplica��es. Ainda assim, ser�o mantidos contextos (espa�os de nomes) distintos 
 * para os diferentes m�dulos das aplica��es J2EE disponibilizadas em uma mesma m�quina 
 * virtual, independentemente da organiza��o hier�rquica dos <i>class loaders</i> do 
 * container J2EE. Isto � poss�vel porque as classes da <i>framework</i> fazem chamadas 
 * ao m�todo {@link Contexto#getContextoCarregador() getContextoCarregador()}. 
 * <b>Este m�todo adota o <i>context class loader</i> como chave de recupera��o do 
 * contexto</b>, o que garante a exist�ncia de contextos de execu��o separados por 
 * m�dulo de aplica��o. Este artif�cio n�o faz diferen�a quando as classes da 
 * <i>framework</i> s�o carregadas junto com os m�dulos das aplica��es.
 */
public final class Contexto {
    private static Map<Object, Contexto> mpContextos = new HashMap<Object, Contexto>();
    private Map<String, Object> mpVinculos = new HashMap<String, Object>();
    
    private Contexto() {}
    
    /**
     * Retorna o contexto da aplica��o usado pela <i>framework</i>, associado 
     * ao <i>context class loader</i>.
     *
     * @return contexto da aplica��o associado ao <i>context class loader</i>
     */
    public static Contexto getContextoCarregador() {
        ClassLoader carregador = Thread.currentThread().getContextClassLoader();
        if (!contextoDefinido(carregador)) {
            try {
                definirContexto(carregador, new Contexto());
            } catch(ErroContextoJaDefinido e) {
                //Pode ocorrer se o contexto for definido ap�s o teste
            } 
        }
        return buscarContexto(carregador);
    }
    
    /**
     * Testa se um contexto de execu��o j� foi definido para esta chave.
     *
     * @param chave chave do contexto
     *
     * @return <tt>true</tt> se o contexto j� foi definido
     */
    public static boolean contextoDefinido(Object chave) {
        return mpContextos.containsKey(chave);
    }
    
    /**
     * Recupera o contexto de execu��o definido para esta chave.
     *
     * @param chave chave do contexto
     *
     * @return contexto de execu��o
     */
    public static Contexto buscarContexto(Object chave) {
        if (!contextoDefinido(chave)) {
            throw new ErroContextoNaoDefinido(chave.toString());
        }
        return mpContextos.get(chave);
    }
    
    /**
     * Define este contexto de execu��o para esta chave.
     *
     * @param chave chave do contexto
     * @param contexto contexto de execu��o
     */
    public static synchronized void definirContexto(Object chave, Contexto contexto) {
        if (contextoDefinido(chave)) {
            throw new ErroContextoJaDefinido(chave.toString());
        }
        mpContextos.put(chave, contexto);
    }
    
    /**
     * Remove o contexto de execu��o definido para esta chave.
     *
     * @param chave chave do contexto
     */
    public static synchronized void removerContexto(Object chave) {
        if (!contextoDefinido(chave)) {
            throw new ErroContextoNaoDefinido(chave.toString());
        }
        mpContextos.remove(chave);
    }
    
    /**
     * Redefine o contexto de execu��o para esta chave. Se um contexto j� havia
     * sido definido, o mesmo ser� substitu�do pelo novo contexto.
     *
     * @param chave chave do contexto
     * @param contexto contexto de execu��o
     */
    public static synchronized void redefinirContexto(Object chave, Contexto contexto) {
        mpContextos.put(chave, contexto);
    }
    
    /**
     * Testa se um item j� foi definido para esta chave.
     *
     * @param chave chave
     *
     * @return <tt>true</tt> se o item j� foi definido
     */
    public boolean itemDefinido(String chave) {
        return mpVinculos.containsKey(chave);
    }
    
    /**
     * Busca o item definido para esta chave.
     *
     * @param chave chave
     *
     * @return item
     */
    public Object buscar(String chave) {
        if (!itemDefinido(chave)) {
            throw new ErroItemContextoNaoDefinido(chave);
        }
        return mpVinculos.get(chave);
    }
    
    /**
     * Define este item para esta chave.
     *
     * @param chave chave
     * @param item item
     */
    public synchronized void definir(String chave, Object item) {
        if (itemDefinido(chave)) {
            throw new ErroItemContextoJaDefinido(chave);
        }
        mpVinculos.put(chave, item);
    }
    
    /**
     * Remove o item definido para esta chave.
     *
     * @param chave chave
     */
    public synchronized void remover(String chave) {
        if (!itemDefinido(chave)) {
            throw new ErroItemContextoNaoDefinido(chave);
        }
        mpVinculos.remove(chave);
    }
    
    /**
     * Redefine o item associado a esta chave. Se um item j� havia sido definido,
     * o mesmo ser� substitu�do pelo novo item.
     *
     * @param chave chave
     * @param item novo item
     */
    public synchronized void redefinir(String chave, Object item) {
        mpVinculos.put(chave, item);
    }
    
    /**
     * Retorna uma lista com todos os v�nculos do contexto.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Collection<Map.Entry<String, Object>> itens = mpVinculos.entrySet();
        for (Map.Entry<String, Object> item : itens) {
            sb.append("(" + item.getKey() + ", " + item.getValue() + ")\n");
        }
        return sb.toString();
    }
}
