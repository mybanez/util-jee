package meyn.util.modelo.ot;

import java.util.*;

import meyn.util.beans.*;

/**
 * Suporte para implementações de objetos de transferência. Esta classe assume
 * que a interface de acesso padrão às propriedades é {@link meyn.util.beans.AcessoPropriedades
 * AcessoPropriedades}.
 *
 * @see FabricaOT
 */
@SuppressWarnings("serial")
public abstract class OTImpl implements OT {
    private Collection<String> clNomesProps;
    
    /**
     * Cria um objeto de transferência.
     */
    public OTImpl() {
        this(Collections.<String>emptyList());
    }
    
    /**
     * Cria um objeto de transferência com as propriedades definidas nesta coleção.
     *
     * @param clNomesProps nomes das propriedades
     */
    public OTImpl(Collection<String> clNomesProps) {
        this.clNomesProps = clNomesProps;        
    }
    
    /**
     * Retorna os nomes das propriedades do ot.
     */
    public final Collection<String> getNomesPropriedades() {
        return clNomesProps;
    }
    
    /**
     * Define os nomes das propriedades do ot.
     *
     * @param clNomesProps coleção com os nomes das propriedades do ot
     */
    public final void setNomesPropriedades(Collection<String> clNomesProps) {
        this.clNomesProps = clNomesProps;
    }
    
    /**
     * Retorna {@link meyn.util.beans.AcessoPropriedades
     * AcessoPropriedades} como o tipo da interface de acesso às propriedades
     * do ot.
     *
     * @return tipo {@link meyn.util.beans.AcessoPropriedades
     * AcessoPropriedades}
     */
    public Class<?> getTipoAcessoPropriedades() {
        return AcessoPropriedades.class;
    }

    /*       SUPORTE PARA MANIPULAÇÃO DE ATRIBUTOS         */
    
    /**
     * Neste ot, valida a propriedade com este nome.
     * 
     * @param ot objeto de transferência
     * @param nome nome da propriedade a ser validada
     *
     * @throws ErroPropriedadeOTNaoDefinida se a propriedade não estiver definida
     *        neste OT
     */
    public final static void validarPropriedade(OT ot, String nome) {
        if (!ot.getNomesPropriedades().contains(nome)) {
            throw new ErroPropriedadeOTNaoDefinida(nome);
        }
    }
    
    /**
     * Implementação estática da lógica do método {@link OTImpl#get() get()}.
     */
    public final static Map<String, Object> get(OT ot) {
        return get(ot, ot.getNomesPropriedades());
    }
    
    /**
     * Implementação estática da lógica do método {@link OTImpl#get(Collection) get(Collection)}.
     */
    public final static Map<String, Object> get(OT ot, Collection<String> clNomesProps) {
        Map<String, Object> res = new HashMap<String, Object>();
        for (String nome : clNomesProps) {
            res.put(nome, ot.get(nome));
        }
        return res;
    }
    
    /**
     * Implementação estática da lógica do método {@link OTImpl#set(Map) get(Map)}.
     */
    public final static void set(OT ot, Map<String, Object> mpProps) {
        Collection<Map.Entry<String, Object>> itens = mpProps.entrySet();
        for (Map.Entry<String, Object> item : itens) {
            ot.set(item.getKey(), item.getValue());
        }
    }
    
    /*          INTERFACE AcessoPropriedades           */
    
    public final Map<String, Object> get() {
        return get(this);
    }
    
    public final Map<String, Object> get(Collection<String> clNomesProps) {
        return get(this, clNomesProps);
    }
    
    public final void set(Map<String, Object> mpProps) {
        set(this, mpProps);
    }
}
