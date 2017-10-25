package fsiscad.contexto;

import java.util.*;

/**
 * Contexto de execução de uma aplicação. Múltiplos contextos podem ser criados e  
 * associados a chaves genéricas. A chave identifica unicamente uma instância de 
 * contexto <b>no escopo do <i>class loader</i> desta classe</b>. O contexto em 
 * si é um conjunto arbitrário de vínculos entre chaves <tt>String</tt> e objetos.
 * <p>
 * Esta classe é utilizada pela <i>framework</i> para armazenamento e recuperação 
 * de objetos de controle internos sem incorrer nos problemas de performance inerentes 
 * ao uso do contexto JNDI. Se a <i>framework</i> for carregada como biblioteca pelo 
 * <i>class loader</i> de sistema, o espaço de chaves de contexto é o mesmo para todas 
 * as aplicações. Ainda assim, serão mantidos contextos (espaços de nomes) distintos 
 * para os diferentes módulos das aplicações J2EE disponibilizadas em uma mesma máquina 
 * virtual, independentemente da organização hierárquica dos <i>class loaders</i> do 
 * container J2EE. Isto é possível porque as classes da <i>framework</i> fazem chamadas 
 * ao método {@link Contexto#getContextoCarregador() getContextoCarregador()}. 
 * <b>Este método adota o <i>context class loader</i> como chave de recuperação do 
 * contexto</b>, o que garante a existência de contextos de execução separados por 
 * módulo de aplicação. Este artifício não faz diferença quando as classes da 
 * <i>framework</i> são carregadas junto com os módulos das aplicações.
 */
public final class Contexto {
    private static Map<Object, Contexto> mpContextos = new HashMap<Object, Contexto>();
    private Map<String, Object> mpVinculos = new HashMap<String, Object>();
    
    private Contexto() {}
    
    /**
     * Retorna o contexto da aplicação usado pela <i>framework</i>, associado 
     * ao <i>context class loader</i>.
     *
     * @return contexto da aplicação associado ao <i>context class loader</i>
     */
    public static Contexto getContextoCarregador() {
        ClassLoader carregador = Thread.currentThread().getContextClassLoader();
        if (!contextoDefinido(carregador)) {
            try {
                definirContexto(carregador, new Contexto());
            } catch(ErroContextoJaDefinido e) {
                //Pode ocorrer se o contexto for definido após o teste
            } 
        }
        return buscarContexto(carregador);
    }
    
    /**
     * Testa se um contexto de execução já foi definido para esta chave.
     *
     * @param chave chave do contexto
     *
     * @return <tt>true</tt> se o contexto já foi definido
     */
    public static boolean contextoDefinido(Object chave) {
        return mpContextos.containsKey(chave);
    }
    
    /**
     * Recupera o contexto de execução definido para esta chave.
     *
     * @param chave chave do contexto
     *
     * @return contexto de execução
     */
    public static Contexto buscarContexto(Object chave) {
        if (!contextoDefinido(chave)) {
            throw new ErroContextoNaoDefinido(chave.toString());
        }
        return mpContextos.get(chave);
    }
    
    /**
     * Define este contexto de execução para esta chave.
     *
     * @param chave chave do contexto
     * @param contexto contexto de execução
     */
    public static synchronized void definirContexto(Object chave, Contexto contexto) {
        if (contextoDefinido(chave)) {
            throw new ErroContextoJaDefinido(chave.toString());
        }
        mpContextos.put(chave, contexto);
    }
    
    /**
     * Remove o contexto de execução definido para esta chave.
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
     * Redefine o contexto de execução para esta chave. Se um contexto já havia
     * sido definido, o mesmo será substituído pelo novo contexto.
     *
     * @param chave chave do contexto
     * @param contexto contexto de execução
     */
    public static synchronized void redefinirContexto(Object chave, Contexto contexto) {
        mpContextos.put(chave, contexto);
    }
    
    /**
     * Testa se um item já foi definido para esta chave.
     *
     * @param chave chave
     *
     * @return <tt>true</tt> se o item já foi definido
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
     * Redefine o item associado a esta chave. Se um item já havia sido definido,
     * o mesmo será substituído pelo novo item.
     *
     * @param chave chave
     * @param item novo item
     */
    public synchronized void redefinir(String chave, Object item) {
        mpVinculos.put(chave, item);
    }
    
    /**
     * Retorna uma lista com todos os vínculos do contexto.
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
