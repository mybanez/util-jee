package fsiscad.modelo.ot;

import java.util.*;

/**
 * Suporte para implementações de objetos de transferência mapeados. Estes objetos
 * usam internamente um mapa para armazenar as propriedades e não definem métodos de
 * acesso <tt>get</tt> e <tt>set</tt> específicos para as leitura e escrita das
 * propriedades, implementando apenas {@link fsiscad.beans.AcessoPropriedades
 * AcessoPropriedades}.
 *
 * @see FabricaOT
 */
public class OTMapeado extends OTImpl {
    private Map<String, Object> mpProps = new HashMap<String, Object>();
    
    /**
     * Ver {@link OTImpl#OTImpl() OTImpl()}.
     */
    public OTMapeado() {}
    
    /**
     * Ver {@link OTImpl#OTImpl(Collection) OTImpl(Collection)}.
     */
    public OTMapeado(Collection<String> clNomesProps) {
        super(clNomesProps);
    }
    
    /**
     * Ver {@link OTImpl#OTImpl(Collection,Collection) OTImpl(Collection,Collection)}.
     */
    public OTMapeado(Collection<String> clNomesPropsChave, Collection<String> clNomesProps) {
        super(clNomesPropsChave, clNomesProps);
    }
    
    /**
     * Cria um objeto de transferência com as propriedades e valores 
     * definidos neste mapa.
     *
     * @param mpProps nomes e valores das propriedades
     */
    public OTMapeado(Map<String, Object> mpProps) {
        super(new ArrayList(mpProps.keySet()));
        set(mpProps);                
    }
    
    /**
     * Cria um objeto de transferência com os propriedades chave definidas nesta 
     * coleção e com as propriedades e valores definidos neste mapa.
     *
     * @param clNomesPropsChave nomes das propriedades que compõem a
     *        chave primária
     * @param mpProps nomes e valores das propriedades
     */
    public OTMapeado(Collection<String> clNomesPropsChave, Map<String, Object> mpProps) {
        super(clNomesPropsChave, new ArrayList(mpProps.keySet()));
        set(mpProps);                
    }
    
    /*       SUPORTE PARA MANIPULAÇÃO DE ATRIBUTOS         */
    
    public final static Object get(OT ot, String nome) {
        validarPropriedade(ot, nome);
        return ((OTMapeado)ot).mpProps.get(nome);
    }
    
    public final static void set(OT ot, String nome, Object valor) {
        validarPropriedade(ot, nome);
        ((OTMapeado)ot).mpProps.put(nome, valor);
    }
    
    /*          INTERFACE AcessoPropriedades           */
    
    public final Object get(String nome) {
        return get(this, nome);
    }
    
    public final void set(String nome, Object valor) {
        set(this, nome, valor);
    }
}
