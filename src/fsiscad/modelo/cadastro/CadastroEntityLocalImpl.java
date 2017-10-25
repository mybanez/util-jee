package fsiscad.modelo.cadastro;

import fsiscad.modelo.ot.ErroPropriedadeOTNaoDefinida;
import fsiscad.util.*;
import fsiscad.beans.*;
import fsiscad.modelo.*;
import fsiscad.modelo.cadastro.beans.*;
import fsiscad.modelo.ot.*;
import java.beans.*;
import java.lang.reflect.*;
import java.util.*;
import javax.ejb.*;

/**
 * Cadastro implementado como um <i>entity bean</i>. Os seguintes requisitos devem
 * ser atendidos por uma subclasse de <tt>CadastroEntityLocalImpl</tt>:
 * <p>
 * <b>1.</b> O método <tt>getEntityContext</tt> deve ser redefinido para
 * retornar o contexto do <i>entity bean</i> ({@link javax.ejb.EntityContext
 * EntityContext}) informado pelo container EJB quando o <i>bean</i> é inicializado.
 * Este método não está definido na interface {@link javax.ejb.EntityBean
 * EntityBean} e sua implementação é uma necessidade específica da <i>framework</i>.
 * </p>
 * <p>
 * <b>2.</b> O <i>entity bean</i> tem de adicionar o método <tt>create(OT
 * usuario, OT ot)</tt> à sua interface <i>home</i> e os correspondentes
 * <tt>ejbCreate(OT usuario, OT ot)</tt> e <tt>ejbPostCreate(OT
 * usuario, OT ot)</tt> à sua implementação (subclasse de
 * <tt>CadastroEntityLocalImpl</tt>). O método <tt>ejbCreate</tt> pode
 * inicializar propriedades cujos valores não tenham sido informados pelo ot e
 * deve fazer uma chamada a <tt>criar(ot)</tt>. Este último se encarrega
 * de copiar os valores das propriedades do ot para o <i>entity bean</i>. Por exemplo:
 * <pre>
 *  public Long ejbCreate(OT usuario, OT ot) throws CreateException {
 *    try {
 *      ot.set("umaPropriedade", new Long(1));
 *      //setUmaPropriedade(new Long(1));
 *    } catch (Exception e) {
 *      throw new CreateException(e.toString());
 *    }
 *    return (Long)criar(ot);
 *  }
 *
 *  public Long ejbPostCreate(OT usuario, OT ot) {
 *    posCriar(ot);
 *    //ot.setOutraPropriedade(getOutroPropriedade());
 *  }
 *  </pre>
 * No trecho de código acima, o valor default da propriedade 'umaPropriedade' é
 * definido primeiro no ot, para só então ser copiado para o <i>bean</i> através da
 * chamada a <tt>criar</tt>. Isto permite que a informação retorne para
 * o cliente que fez a solicitação de inclusão, o que pode ser útil caso seja
 * preciso mostrar para o usuário da aplicação o valor default assumido para a
 * propriedade. O método <tt>ejbCreate</tt> é chamado pelo método
 * <tt>incluir</tt> e este último retorna o mesmo ot passado inicialmente para
 * <tt>ejbCreate</tt>. Outra possibilidade é a chamada direta ao método
 * <tt>set</tt> da propriedade 'status' do <i>bean</i> já em <tt>ejbCreate</tt> (linha de
 * código comentada no trecho acima).<br>
 * Por fim, a implementação de <tt>ejbPostCreate(OT usuario, OT
 * ot)</tt> deve chamar <tt>posCriar(ot)</tt> para completar o processo
 * de inicialização do <i>bean</i>. Esta é a última chance de se definir o valor de
 * alguma propriedade do ot que deva ser retornada para a aplicação.
 * </p>
 * <p>
 * <b>3.</b> No caso dos relacionamentos 1:1 ou 1:N entre <i>entity beans</i>,
 * o <i>bean</i> que possui a chave extrangeira pode definir uma propriedade para
 * lidar diretamente com o valor da chave e outra para lidar já com a instância do
 * <i>bean</i> relacionado (esta última é a forma padrão de se implementar
 * relacionamentos entre <i>entity beans</i>). Em uma subclasse de
 * <tt>CadastroEntityLocalImpl</tt>, os métodos <tt>get</tt> e <tt>set</tt> de uma
 * propriedade do primeiro tipo devem fazer chamadas respectivamente aos métodos
 * <tt>getRelacionamento</tt> e <tt>setRelacionamento</tt>. Estes últimos, por sua vez,
 * vão fazer referência ao nome da propriedade do segundo tipo que efetivamente
 * implementa o relacionamento. O trecho abaixo ilustra uma parte do codigo do
 * <i>entity bean</i> <tt>Empregado</tt> que se relaciona com um <i>entity bean</i>
 * <tt>Departamento</tt>:
 * <pre>
 *  public class EmpregadoImpl extends CadastroEntityLocalImpl {
 *    ...
 *    //Retorna o id do departamento (chave extrangeira)
 *    public Long getIdDpto() {
 *      DepartamentoLocal dpto = (DepartamentoLocal)getRelacionamento("departamento");
 *      return dpto != null ? dpto.getId() : null;
 *    }
 *
 *    //Define o id do departamento (chave extrangeira)
 *    public void setIdDpto(Long idDpto) throws ErroCadastro {
 *      CadastroEntityLocal cadastro = getCadastroEntityLocal("departamento");
 *      OT chave = FabricaOT.getInstancia(Arrays.asList("id"), Arrays.asList("id"));
 *      chave.set("id", idDpto);
 *      setRelacionamento("departamento", cadastro.consultarEntityPorChavePrimaria(chave));
 *    }
 *
 *    //Retorna os dados do departamento (bean)
 *    public abstract DepartamentoLocal getDepartamento();
 *
 *    //Define os dados do departamento (bean)
 *    public abstract void setDepartamento(DepartamentoLocal dpto);
 *    ...
 *  }
 * </pre>
 * Esta técnica garante a portabilidade do código entre os diferentes
 * servidores de aplicação, mesmo quando o servidor não permite o acesso
 * direto, via propriedade CMP, a uma chave extrangeira.
 * </p>
 * <p>
 * <a name="mapaRelChavesExt">Uma técnica alternativa é redefinir o método {@link CadastroEntityLocal#getMapaRelacionamentosChavesExtrangeiras
 * getMapaRelacionamentosChavesExtrangeiras}, de modo que este retorne um mapeamento
 * dos nomes dos atributos da chave extrangeira (que não precisam mais ser definidos
 * no <i>entity</i>) em uma instancia de {@link InfoRelacionamentoEntity InfoRelacionamentoEntity},
 * com as informações sobre o relacionamento usado para manipular a chave extrangeira.
 * O código abaixo ilustra o uso dessa segunda técnica para o exemplo já
 * citado:
 * <pre>
 *  public class EmpregadoImpl extends CadastroEntityLocalImpl {
 *    ...
 *    public Map<List<String>, InfoRelacionamentoEntity> getMapaRelacionamentosChavesExtrangeiras() {
 *      Map<List<String>, InfoRelacionamentoEntity> mapa = new HashMap<List<String>, InfoRelacionamentoEntity>();
 *      mapa.put(
 *        Arrays.asList("idDepto"),
 *        new InfoRelacionamentoEntity("departamento", "departamento", Arrays.asList("id"))
 *      );
 *      return mapa;
 *    }
 *
 *    //Retorna os dados do departamento (bean)
 *    public abstract DepartamentoLocal getDepartamento();
 *
 *    //Define os dados do departamento (bean)
 *    public abstract void setDepartamento(DepartamentoLocal dpto);
 *    ...
 *  }
 * </pre>
 * </p>
 * <p>
 * <a name="idVersao"><b>4.</b></a> Se a implementação precisar fazer uso de um
 * mecanismo de checagem de concorrência otimista, garantindo que um <i>bean</i>
 * não vai atualizar as suas propriedades quando os valores no banco de dados
 * tiverem sido alterados por outra transação externa, pode ser definida a
 * propriedade <tt>idVersao</tt> nos ots e no cadastro <i>entity bean</i>, e qual deve
 * ser mapeada em um campo da tabela que mantenha um id de versão do registro.
 * Esta propriedade, caso definida, sempre será verificada pelo cadastro antes
 * de fazer uma atualização, que checa se o <b>id de versão</b> do ot é igual
 * ao mantido na tabela e, em caso negativo, cancela a transação gerando um
 * erro do tipo {@link ErroIdVersaoInvalido ErroIdVersaoInvalido}. O id de versão
 * deve ser um campo numérico da tabela que sempre é carregado no ot quando os
 * dados são consultados e sempre é retornado no ot que contém os dados alterados
 * pela aplicação, devendo ser incrementado antes da atualização no banco. Note-se
 * que, para que o mecanismo funcione corretamente, qualquer aplicação externa que
 * também venha a atualizar os dados no banco tem que incrementar o id de versão na
 * tabela após cada atualização. Este <i>pattern</i> é descrito em detalhes no
 * livro <i>EJB Design Patterns</i> de Floyd Marinescu, que discute também as
 * combinações possíveis de níveis de isolamento de transação do banco de
 * dados e do servidor de aplicação para que o mecanismo possa ser
 * implementado com <i>entity beans</i>.
 * </p>
 *
 * Por fim, vale ressaltar que esta classe possui uma implementação inoperante da
 * interface {@link fsiscad.modelo.ot.OT OT}, com excessão dos métodos
 * definidos em {@link fsiscad.beans.AcessoPropriedades AcessoPropriedades}.
 * Isto permite que interfaces locais de cadastros <i>entity bean</i> possam implementar
 * interfaces de ots, evitando a duplicação de métodos de acesso getXXX e setXXX
 * nas interfaces de ots e <i>entity beans</i> associados a um mesmo modelo. Os métodos
 * inoperantes da interface <tt>OT</tt> levantam <tt>UnsupportedOperationException</tt>.
 */

public abstract class CadastroEntityLocalImpl extends CadastroImpl implements OT, CadastroEntityLocal, EntityBean {
    
    private boolean criandoEntity;
    private Map<String, Object> mpPropsPosCriacao;
    
    /**
     * Retorna o cadastro <i>entity bean</i> associado a este modelo.
     *
     * @see CadastroImpl#getCadastro(String)
     */
    protected final static CadastroEntityLocal getCadastroEntityLocal(String modelo)
    throws ErroCadastro {
        return (CadastroEntityLocal)getCadastro(modelo);
    }
    
    /**
     * Retorna o <i>wrapper</i> que implementa a interface <i>home</i>.
     */
    protected final EJBLocalHome getEJBLocalHome() {
        return getEntityContext().getEJBLocalHome();
    }
    
    /**
     * Retorna o <i>wrapper</i> que implementa a interface local.
     */
    protected final EJBLocalObject getEJBLocalObject() {
        return getEntityContext().getEJBLocalObject();
    }
    
    /**
     * Retorna os parâmetros associados à classe do <i>wrapper</i>.
     */
    protected final Map<String, Object> getParametros() {
        CacheModelo cache = CacheModelo.getCacheModelo();
        Map<String, Object> mpParams = (Map)cache.get("PARAMS."+getClass());
        if (mpParams == null) {
            synchronized (cache) {
                mpParams = new HashMap<String, Object>();
                cache.put("PARAMS."+getClass(), mpParams);
            }
        }
        return mpParams;
    }
    
    /**
     * Retorna o contexto do <i>entity bean</i>. Este método tem de ser redefinido nas
     * subclasses de <tt>CadastroEntityLocalImpl</tt>.
     */
    public EntityContext getEntityContext() {
        throw new UnsupportedOperationException("getEntityContext");
    }
    
  /* *******************************************************************
   * INTERFACE HOME
   * ******************************************************************* */
    
    /**
     * Chama o método {@link CadastroEntityLocalImpl#getModelo getModelo}.
     */
    public String ejbHomeGetModelo() {
        return getModelo();
    }
    
    /**
     * Chama o método {@link CadastroEntityLocalImpl#setModelo setModelo}.
     */
    public void ejbHomeSetModelo(String chave) {
        setModelo(chave);
    }
    
    /**
     * Chama o método {@link CadastroEntityLocalImpl#getMapaRelacionamentosChavesExtrangeiras
     * getMapaRelacionamentosChavesExtrangeiras}.
     */
    public Map<List<String>, InfoRelacionamentoEntity> ejbHomeGetMapaRelacionamentosChavesExtrangeiras() {
        return getMapaRelacionamentosChavesExtrangeiras();
    }
    
    /**
     * Chama o método {@link
     * CadastroEntityLocalImpl#consultarEntityPorChavePrimaria(fsiscad.modelo.ot.OT)
     * consultarEntityPorChavePrimaria}.
     */
    public EJBLocalObject ejbHomeConsultarEntityPorChavePrimaria(OT chave)
    throws ErroCadastro {
        return consultarEntityPorChavePrimaria(chave);
    }
    
    /**
     * Chama o método {@link
     * CadastroEntityLocalImpl#consultarTodos(fsiscad.modelo.ot.OT,
     * fsiscad.modelo.ot.OT) consultarTodos}.
     */
    public Collection<OT> ejbHomeConsultarTodos(OT usuario, OT molde)
    throws ErroCadastro {
        return consultarTodos(usuario, molde);
    }
    
    /**
     * Chama o método {@link
     * CadastroEntityLocalImpl#consultarTodos(fsiscad.modelo.ot.OT,
     * java.util.Map) consultarTodos}.
     */
    public Collection<OT> ejbHomeConsultarTodos(OT usuario, Map<String, OT> mpMoldes)
    throws ErroCadastro {
        return consultarTodos(usuario, mpMoldes);
    }
    
    /**
     * Chama o método {@link
     * CadastroEntityLocalImpl#consultarPorChavePrimaria(fsiscad.modelo.ot.OT,
     * fsiscad.modelo.ot.OT,fsiscad.modelo.ot.OT) consultarPorChavePrimaria}.
     */
    public OT ejbHomeConsultarPorChavePrimaria(OT usuario, OT chave, OT molde)
    throws ErroCadastro {
        return consultarPorChavePrimaria(usuario, chave, molde);
    }
    
    /**
     * Chama o método {@link
     * CadastroEntityLocalImpl#consultarPorChavePrimaria(fsiscad.modelo.ot.OT,
     * fsiscad.modelo.ot.OT,java.util.Map) consultarPorChavePrimaria}.
     */
    public OT ejbHomeConsultarPorChavePrimaria(OT usuario, OT chave, Map<String, OT> mpMoldes)
    throws ErroCadastro {
        return consultarPorChavePrimaria(usuario, chave, mpMoldes);
    }
    
    /**
     * Chama o método {@link
     * CadastroEntityLocalImpl#incluir(fsiscad.modelo.ot.OT,
     * fsiscad.modelo.ot.OT) incluir}.
     */
    public OT ejbHomeIncluir(OT usuario, OT ot)
    throws ErroCadastro {
        return incluir(usuario, ot);
    }
    
    /**
     * Chama o método {@link
     * CadastroEntityLocalImpl#alterar(fsiscad.modelo.ot.OT,
     * fsiscad.modelo.ot.OT) alterar}.
     */
    public OT ejbHomeAlterar(OT usuario, OT ot)
    throws ErroCadastro {
        return alterar(usuario, ot);
    }
    
    /**
     * Chama o método {@link
     * CadastroEntityLocalImpl#excluir(fsiscad.modelo.ot.OT,
     * fsiscad.modelo.ot.OT) excluir}.
     */
    public void ejbHomeExcluir(OT usuario, OT ot)
    throws ErroCadastro {
        excluir(usuario, ot);
    }
    
  /* *******************************************************************
   * OPERAÇÕES AUXILIARES (HOME)
   * ******************************************************************* */
    
    private static OT removerChavePrimariaOT(OT ot) {
        if (!ot.getNomesPropriedadesChave().isEmpty()) {
            Collection<String> clNomesProps = new ArrayList<String>(ot.getNomesPropriedades());
            clNomesProps.removeAll(ot.getNomesPropriedadesChave());
            return FabricaOT.getInstancia(ot.get(clNomesProps), ot.getTipoAcessoPropriedades());
        }
        return ot;
    }
    
    private static OT removerChavesExtrangeirasOT(EJBLocalHome home, OT ot) {
        CadastroEntityLocal cadastro = (CadastroEntityLocal)home;
        Map<List<String>, InfoRelacionamentoEntity> mpRelChavesExt = cadastro.getMapaRelacionamentosChavesExtrangeiras();
        if (!mpRelChavesExt.isEmpty()) {
            Collection<List<String>> itens = mpRelChavesExt.keySet();
            Collection<String> clNomesProps = new ArrayList<String>(ot.getNomesPropriedades());
            for (List<String> item : itens) {
                clNomesProps.removeAll(item);
            }
            return FabricaOT.getInstancia(ot.getNomesPropriedadesChave(), ot.get(clNomesProps), ot.getTipoAcessoPropriedades());
        }
        return ot;
    }
    
    private static void consultarChavesExtrangeiras(AcessoPropriedades entity, OT ot)
    throws ErroCadastro {
        CadastroEntityLocal cadastro = (CadastroEntityLocal)((EJBLocalObject)entity).getEJBLocalHome();
        Map<List<String>, InfoRelacionamentoEntity> mpRelChavesExt = cadastro.getMapaRelacionamentosChavesExtrangeiras();
        Collection<Map.Entry<List<String>, InfoRelacionamentoEntity>> itens = mpRelChavesExt.entrySet();
        for(Map.Entry<List<String>, InfoRelacionamentoEntity> item : itens) {
            if (ot.getNomesPropriedades().containsAll(item.getKey())) {
                AcessoPropriedades entityRel = (AcessoPropriedades)entity.get(item.getValue().getNomePropriedade());
                if (entityRel != null) {
                    int cont = 0;
                    for(String nomePropChaveExt : item.getKey()) {
                        List<String> lsPropsChaveModelo = item.getValue().getNomesPropriedadesChaveRelacionada();
                        ot.set(nomePropChaveExt, entityRel.get(lsPropsChaveModelo.get(cont++)));
                    }
                }
            }
        }
    }
    
    private static void atualizarChavesExtrangeiras(AcessoPropriedades entity, OT ot)
    throws ErroCadastro {
        CadastroEntityLocal cadastro = (CadastroEntityLocal)((EJBLocalObject)entity).getEJBLocalHome();
        Map<List<String>, InfoRelacionamentoEntity> mpRelChavesExt = cadastro.getMapaRelacionamentosChavesExtrangeiras();
        Collection<Map.Entry<List<String>, InfoRelacionamentoEntity>> itens = mpRelChavesExt.entrySet();
        for(Map.Entry<List<String>, InfoRelacionamentoEntity> item : itens) {
            try {
                OT chaveExt = FabricaOT.getInstancia(item.getKey(), item.getKey());
                chaveExt.set(ot.get(item.getKey()));
                CadastroEntityLocal cadastroRel = getCadastroEntityLocal(item.getValue().getModeloRelacionado());
                entity.set(item.getValue().getNomePropriedade(), cadastroRel.consultarEntityPorChavePrimaria(chaveExt));
            } catch (ErroPropriedadeOTNaoDefinida e) {}
        }
    }
    
    private static Collection<OT> converterEntityParaOT(Collection<EJBLocalObject> clEntities, Map<String, OT> mpMoldes, String nomePropRel, String conector)
    throws ErroCadastro {
        if (clEntities == null) {
            return null;
        }
        List<OT> ots = new ArrayList<OT>();
        for (EJBLocalObject entity : clEntities) {
            ots.add(converterEntityParaOT(entity, mpMoldes, nomePropRel, conector));
        }
        return ots;
    }
    
    private static OT converterEntityParaOT(EJBLocalObject entity, Map<String, OT> mpMoldes, String nomePropRel, String conector)
    throws ErroCadastro {
        if (entity == null) {
            return null;
        }
        OT molde = mpMoldes.get(nomePropRel);
        OT moldeSemChavesExt = removerChavesExtrangeirasOT(entity.getEJBLocalHome(), molde);
        Map<String, Object> mpProps = new HashMap<String, Object>();
        for (String nome : moldeSemChavesExt.getNomesPropriedades()) {
            String nomeAbs = nomePropRel+conector+nome;
            Object valor = ((AcessoPropriedades)entity).get(nome);
            if (mpMoldes.containsKey(nomeAbs) && valor != null) {
                if (valor instanceof Collection) {
                    valor = converterEntityParaOT((Collection)valor, mpMoldes, nomeAbs, ".");
                } else {
                    if (valor instanceof EJBLocalObject) {
                        valor = converterEntityParaOT((EJBLocalObject)valor, mpMoldes, nomeAbs, ".");
                    } else {
                        throw new ErroExecucao("Não é propriedade de relacionamento: "+nomeAbs);
                    }
                }
            }
            mpProps.put(nome, valor);
        }
        Collection<String> clNomesPropsChave = molde.getNomesPropriedadesChave();
        Collection<String> clNomesProps = molde.getNomesPropriedades();
        Class tipoAcessoProps = molde.getTipoAcessoPropriedades();
        OT ot = FabricaOT.getInstancia(clNomesPropsChave, clNomesProps, tipoAcessoProps);
        ot.set(mpProps);
        consultarChavesExtrangeiras((AcessoPropriedades)entity, ot);
        return ot;
    }
    
    /**
     * Converte esta lista de <i>entities</i> em ots usando este tipo como molde. As
     * propriedades de chave primária não estarão definidas nos ots retornados.
     *
     * @param clEntities lista de <i>entity beans</i> a serem convertidos
     * @param molde tipo dos ots a serem retornados
     *
     * @return lista de ots com os dados dos <i>entities</i>
     *
     * @throws ErroCadastro se algum erro ocorreu durante a operação
     */
    public final static Collection<OT> converterEntityParaOT(Collection<EJBLocalObject> clEntities, Class molde)
    throws ErroCadastro {
        return converterEntityParaOT(clEntities, FabricaOT.getInstancia(molde));
    }
    
    /**
     * Converte este <i>entity bean</i> em um ot usando este tipo como molde. As
     * propriedades de chave primária não estarão definidas no ot retornado.
     *
     * @param entity <i>entity bean</i> a ser convertido
     * @param molde tipo do ot a ser retornado
     *
     * @return ot com os dados do <i>entity</i>
     *
     * @throws ErroCadastro se algum erro ocorreu durante a operação
     */
    public final static OT converterEntityParaOT(EJBLocalObject entity, Class molde)
    throws ErroCadastro {
        return converterEntityParaOT(entity, FabricaOT.getInstancia(molde));
    }
    
    /**
     * Converte esta lista de <i>entities</i> em ots usando este molde.
     *
     * @param clEntities lista de <i>entity beans</i> a serem convertidos
     * @param molde molde para os ots a serem retornados
     *
     * @return lista de ots com os dados dos <i>entities</i>
     *
     * @throws ErroCadastro se algum erro ocorreu durante a operação
     */
    public final static Collection<OT> converterEntityParaOT(Collection<EJBLocalObject> clEntities, OT molde)
    throws ErroCadastro {
        Map<String, OT> mpMoldes = new HashMap<String, OT>();
        mpMoldes.put("", molde);
        return converterEntityParaOT(clEntities, mpMoldes);
    }
    
    /**
     * Converte este <i>entity bean</i> em um ot usando este molde.
     *
     * @param entity <i>entity bean</i> a ser convertido
     * @param molde molde para o ot a ser retornado
     *
     * @return ot com os dados do <i>entity</i>
     *
     * @throws ErroCadastro se algum erro ocorreu durante a operação
     */
    public final static OT converterEntityParaOT(EJBLocalObject entity, OT molde)
    throws ErroCadastro {
        Map<String, OT> mpMoldes = new HashMap<String, OT>();
        mpMoldes.put("", molde);
        return converterEntityParaOT(entity, mpMoldes);
    }
    
    /**
     * Converte esta lista de <i>entities</i> e seus relacionamentos em malhas de
     * ots usando este mapa de moldes.
     *
     * @param clEntities lista de <i>entity bean</i> a serem convertidos
     * @param mpMoldes moldes para os ots a serem retornados
     *
     * @return lista com as raízes das malhas contendo os dados dos <i>entities</i>
     *
     * @throws ErroCadastro se algum erro ocorreu durante a operação
     */
    public final static Collection<OT> converterEntityParaOT(Collection<EJBLocalObject> clEntities, Map<String, OT> mpMoldes)
    throws ErroCadastro {
        return converterEntityParaOT(clEntities, mpMoldes, "", "");
    }
    
    /**
     * Converte este <i>entity bean</i> e seus relacionamentos em uma malha de
     * ots usando este mapa de moldes.
     *
     * @param entity <i>entity bean</i> a ser convertido
     * @param mpMoldes moldes para os ots a serem retornados
     *
     * @return raiz da malha contendo os dados dos <i>entities</i>
     *
     * @throws ErroCadastro se algum erro ocorreu durante a operação
     */
    public final static OT converterEntityParaOT(EJBLocalObject entity, Map<String, OT> mpMoldes)
    throws ErroCadastro {
        return converterEntityParaOT(entity, mpMoldes, "", "");
    }
    
    private Object montarChave(OT ot)
    throws ErroCadastro {
        Object chave;
        Collection clValoresChave = getValoresChaveOT(ot);
        DescritorCadastroEntityLocal info = Cadastros.getDescritorCadastroEntityLocal(getEJBLocalHome());
        /* Chave simples */
        if (clValoresChave.size() == 1) {
            chave = clValoresChave.iterator().next();
            if (!info.getTipoChavePrimaria().isAssignableFrom(chave.getClass())) {
                throw new ErroExecucao("Chave primária do ot incompatível com a chave primária do entity bean: "+clValoresChave);
            }
        } else {
        /* Trata o caso de chave composta procurando um contrutor público na classe
           chave do Entity com parâmetros compatíveis com os da chave do objeto de
           transferência */
            if (info.isTipoChavePrimariaNativo()) {
                throw new ErroExecucao("Chave primária do ot incompatível com a chave primária do entity bean: "+clValoresChave);
            }
            Object[] valsChave = clValoresChave.toArray();
            Class[] tiposValsChave = new Class[clValoresChave.size()];
            for (int i = 0; i < valsChave.length; i++) {
                tiposValsChave[i] = (valsChave[i] != null) ? valsChave[i].getClass() : Object.class;
            }
            Constructor construtorChavePrimaria;
            try {
                construtorChavePrimaria = info.getTipoChavePrimaria().getConstructor(tiposValsChave);
            } catch (NoSuchMethodException e) {
                throw new ErroExecucao("Chave primária do ot incompatível com a chave primária do entity bean: "+clValoresChave);
            }
            try {
                chave = construtorChavePrimaria.newInstance(valsChave);
            } catch (InvocationTargetException ite) {
                throw new ErroCadastro("Erro instanciando chave primária do entity bean", ite.getTargetException());
            } catch (Exception e) {
                throw new ErroCadastro("Erro instanciando chave primária do entity bean", e);
            }
        }
        return chave;
    }
    
  /* *******************************************************************
   * IMPLEMENTAÇÃO DA INTERFACE CadastroEntityLocal (HOME)
   * ******************************************************************* */
    
    /**
     * Consulta <i>entity beans</i> deste modelo a partir desta lista de chaves primárias
     * mantidas em ots. Os nomes das propriedades que compõem as chaves
     * devem ser obtidos através de chamadas ao método
     * <tt>getNomesPropriedadesChave(modelo)</tt>.
     *
     * @param modelo modelo a ser consultado
     * @param clOts objetos de transferência contendo as chaves de
     *        consulta
     *
     * @return lista de <i>entities</i> com o resultado da consulta
     *
     * @throws ErroCadastro se algum erro ocorreu durante a operação
     */
    protected final static Collection<EJBLocalObject> consultarEntitiesPorChavePrimaria(String modelo, Collection<OT> clOts)
    throws ErroCadastro {
        CadastroEntityLocal cadastro = (CadastroEntityLocal)getCadastro(modelo);
        List<EJBLocalObject> entities = new ArrayList<EJBLocalObject>();
        for (OT ot : clOts) {
            entities.add(cadastro.consultarEntityPorChavePrimaria(ot));
        }
        return entities;
    }
    
    /**
     * Consulta um <i>entity bean</i> deste modelo a partir desta ot primária criada
     * como OT. Os nomes das propriedades que compõem a ot devem ser
     * obtidos através de uma chamada a
     * <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
     *
     * @param modelo modelo a ser consultado
     * @param ot ot contendo a chave de consulta
     *
     * @return bean com o resultado da consulta
     *
     * @throws ErroCadastro se algum erro ocorreu durante a operação
     */
    protected final static EJBLocalObject consultarEntityPorChavePrimaria(String modelo, OT ot)
    throws ErroCadastro {
        return ((CadastroEntityLocal)getCadastro(modelo)).consultarEntityPorChavePrimaria(ot);
    }
    
    public final String getModelo() {
        return (String) getParametros().get("modelo");
    }
    
    public final void setModelo(String modelo) {
        getParametros().put("modelo", modelo);
    }
    
    public Map<List<String>, InfoRelacionamentoEntity> getMapaRelacionamentosChavesExtrangeiras() {
        return Collections.<List<String>, InfoRelacionamentoEntity>emptyMap();
    }
    
    public final EJBLocalObject consultarEntityPorChavePrimaria(OT ot)
    throws ErroCadastro {
        try {
            DescritorCadastroEntityLocal info = Cadastros.getDescritorCadastroEntityLocal(getEJBLocalHome());
            if (info.getMetodoConsultaPorChavePrimaria() != null){
                Object chave = montarChave(ot);
                return (EJBLocalObject)info.getMetodoConsultaPorChavePrimaria().invoke(getEJBLocalHome(), new Object[] { chave });
            }
        } catch (InvocationTargetException ite) {
            if (ite.getTargetException() instanceof ObjectNotFoundException) {
                throw new ErroItemNaoEncontrado(ite.getTargetException().getMessage());
            }
            throw new ErroCadastro("Erro consultando entity bean do modelo '"+getModelo()+"'", ite.getTargetException());
        } catch (Exception e) {
            throw new ErroCadastro("Erro consultando entity bean do modelo '"+getModelo()+"'", e);
        }
        throw new UnsupportedOperationException("findByPrimaryKey: modelo '"+getModelo()+"'");
    }
    
    public final Collection<OT> consultarTodos(OT usuario, OT molde)
    throws ErroCadastro {
        Map<String, OT> mpMoldes = new HashMap<String, OT>();
        mpMoldes.put("", molde);
        return consultarTodos(usuario, mpMoldes);
    }
    
    public final Collection<OT> consultarTodos(OT usuario, Map<String, OT> mpMoldes)
    throws ErroCadastro {
        try {
            DescritorCadastroEntityLocal info = Cadastros.getDescritorCadastroEntityLocal(getEJBLocalHome());
            if (info.getMetodoConsultaTodos() != null) {
                Collection<EJBLocalObject> clEntities = (Collection)info.getMetodoConsultaTodos().invoke(getEJBLocalHome(), new Object[] {});
                return converterEntityParaOT(clEntities, mpMoldes);
            }
        } catch (InvocationTargetException ite) {
            throw new ErroCadastro("Erro consultando itens do modelo '"+getModelo()+"'", ite.getTargetException());
        } catch (Exception e) {
            throw new ErroCadastro("Erro consultando itens do modelo '"+getModelo()+"'", e);
        }
        throw new UnsupportedOperationException("findAll: modelo '"+getModelo()+"'");
    }
    
    public final OT consultarPorChavePrimaria(OT usuario, OT chave, OT molde)
    throws ErroCadastro {
        Map<String, OT> mpMoldes = new HashMap<String, OT>();
        mpMoldes.put("", molde);
        return consultarPorChavePrimaria(usuario, chave, mpMoldes);
    }
    
    public final OT consultarPorChavePrimaria(OT usuario, OT chave, Map<String, OT> mpMoldes)
    throws ErroCadastro {
        try {
            EJBLocalObject entity = consultarEntityPorChavePrimaria(chave);
            return converterEntityParaOT(entity, mpMoldes);
        } catch (Exception e) {
            throw new ErroCadastro("Erro consultando item do modelo '"+getModelo()+"' por chave primária", e);
        }
    }
    
    public final OT incluir(OT usuario, OT ot)
    throws ErroCadastro {
        try {
            DescritorCadastroEntityLocal info = Cadastros.getDescritorCadastroEntityLocal(getEJBLocalHome());
            if (info.getMetodoCriacao() != null) {
                OT otSemChavesExt = removerChavesExtrangeirasOT(getEJBLocalHome(), ot);
                AcessoPropriedades entity = (AcessoPropriedades)info.getMetodoCriacao().invoke(getEJBLocalHome(), new Object[] { usuario, otSemChavesExt });
                ot.set(otSemChavesExt.get()); //valores default
                atualizarChavesExtrangeiras(entity, ot);
                return ot;
            }
        } catch (InvocationTargetException ite) {
            try {
                getEntityContext().setRollbackOnly();
            } catch (IllegalStateException ise) {}
            throw new ErroCadastro("Erro incluindo item do modelo '"+getModelo()+"'", ite.getTargetException());
        } catch (Exception e) {
            try {
                getEntityContext().setRollbackOnly();
            } catch (IllegalStateException ise) {}
            throw new ErroCadastro("Erro incluindo item do modelo '"+getModelo()+"'", e);
        }
        throw new UnsupportedOperationException("create(OT, OT): modelo '"+getModelo()+"'");
    }
    
    public final OT alterar(OT usuario, OT ot)
    throws ErroCadastro {
        try {
            AcessoPropriedades entity = (AcessoPropriedades)consultarEntityPorChavePrimaria(ot);
            Collection<String> clNomesPropsChave = ot.getNomesPropriedadesChave();
            Collection<String> clNomesProps = ot.getNomesPropriedades();
            if (clNomesProps.contains("idVersao")) {
                long idVersao = ot.getIdVersao();
                if (idVersao != (Long)entity.get("idVersao")) {
                    throw new ErroIdVersaoInvalido(getClass()+": "+ idVersao);
                }
                ot.setIdVersao(idVersao+1);
            }
            OT otSemChaves = removerChavesExtrangeirasOT(getEJBLocalHome(), removerChavePrimariaOT(ot));
            entity.set(otSemChaves.get());
            atualizarChavesExtrangeiras(entity, ot);
            return ot;
        } catch (Exception e) {
            try {
                getEntityContext().setRollbackOnly();
            } catch (IllegalStateException ise) {}
            throw new ErroCadastro("Erro alterando item do modelo '"+getModelo()+"'", e);
        }
    }
    
    public final void excluir(OT usuario, OT ot)
    throws ErroCadastro {
        try {
            consultarEntityPorChavePrimaria(ot).remove();
        } catch (Exception e) {
            try {
                getEntityContext().setRollbackOnly();
            } catch (IllegalStateException ise) {}
            throw new ErroCadastro("Erro excluindo item do modelo '"+ getModelo()+"'", e);
        }
    }
    
  /* *******************************************************************
   * OPERAÇÕES AUXILIARES (ENTITY)
   * ******************************************************************* */
    
    /**
     * Inicia o processo de criação do <i>entity bean</i>. Define os valores das
     * propriedades do <i>entity bean</i> a partir dos valores contidos neste OT.
     *
     * @param ot ot com os valores das propriedades
     *
     * @throws ErroCadastro se algum erro ocorreu durante a operação
     */
    protected final Object criar(OT ot)
    throws ErroCadastro {
        criandoEntity = true;
        mpPropsPosCriacao = new HashMap<String, Object>();
        set(ot.get());
        return montarChave(ot);
    }
    
    /**
     * Finaliza o processo de criação do <i>entity bean</i>. No caso deste ot trazer o
     * valor de uma ou mais chaves extrangeiras do <i>entity bean</i>, inicializa as
     * propriedades de relacionamento do <i>entity bean</i> a partir dos valores contidos
     * no ot.
     *
     * @param ot ot com os valores das propriedades
     */
    protected final void posCriar(OT ot) {
        Collection<Map.Entry<String, Object>> itens = mpPropsPosCriacao.entrySet();
        for (Map.Entry<String, Object> item : itens) {
            set(item.getKey(), item.getValue());
        }
        mpPropsPosCriacao = null;
        criandoEntity = false;
    }
    
    /**
     * Retorna o valor da propriedade de relacionamento com este nome.
     *
     * @param nome nome da propriedade de relacionamento
     *
     * @return instância(s) à(s) qual(is) este <i>entity bean</i> se relaciona
     */
    protected final Object getRelacionamento(String nome) {
        if (criandoEntity) {
            return mpPropsPosCriacao.get(nome);
        }
        return get(nome);
    }
    
    /**
     * Define o valor da propriedade de relacionamento com este nome.
     *
     * @param nome nome da propriedade de relacionamento
     * @param valor instância(s) à(s) qual(is) este <i>entity bean</i> se relaciona
     */
    protected final void setRelacionamento(String nome, Object valor) {
        if (criandoEntity) {
            mpPropsPosCriacao.put(nome, valor);
            return;
        }
        set(nome, valor);
    }
    
  /* *******************************************************************
   * IMPLEMENTAÇÃO DA INTERFACE AcessoPropriedades (ENTITY)
   * ******************************************************************* */
    
    public final Map<String, Object> get() {
        return Componentes.getValoresPropriedades(getClass(), getEJBLocalObject());
    }
    
    public final Map<String, Object> get(Collection<String> clNames) {
        Map<String, Object> mpProps = new HashMap<String, Object>();
        for (String nome : clNames) {
            mpProps.put(nome, get(nome));
        }
        return mpProps;
    }
    
    public final void set(Map<String, Object> mpProps) {
        try {
            Collection<Map.Entry<String, Object>> itens = mpProps.entrySet();
            for (Map.Entry<String, Object> item : itens) {
                set(item.getKey(), item.getValue());
            }
        } catch (ErroExecucao e) {
            try {
                getEntityContext().setRollbackOnly();
            } catch (IllegalStateException ise) {}
            throw e;
        }
    }
    
    public final Object get(String nome) {
        Map<String, PropertyDescriptor> mpInfoProp = Componentes.getDescritoresPropriedades(getClass());
        PropertyDescriptor infoProp = mpInfoProp.get(nome);
        if (infoProp == null || infoProp.getReadMethod() == null) {
            throw new ErroExecucao("Não existe método de leitura para a propriedade '"+nome+"': "+getClass().getName());
        }
        try {
            return infoProp.getReadMethod().invoke(this, new Object[]{});
        } catch (InvocationTargetException ite) {
            throw new ErroExecucao("Erro lendo propriedade '"+nome+"'", ite.getTargetException());
        } catch (Exception e) {
            throw new ErroExecucao("Erro lendo propriedade '"+nome+"'", e);
        }
    }
    
    public final void set(String nome, Object valor) {
        Map<String, PropertyDescriptor> mpInfoProp = Componentes.getDescritoresPropriedades(getClass());
        PropertyDescriptor infoProp = mpInfoProp.get(nome);
        if (infoProp == null || infoProp.getWriteMethod() == null) {
            throw new ErroExecucao("Não existe método de escrita para a propriedade '"+nome+"': "+getClass().getName());
        }
        try {
            infoProp.getWriteMethod().invoke(this, new Object[]{ valor });
        } catch (InvocationTargetException ite) {
            throw new ErroExecucao("Erro escrevendo propriedade '"+nome+"'", ite.getTargetException());
        } catch (Exception e) {
            throw new ErroExecucao("Erro escrevendo propriedade '"+nome+"'", e);
        }
    }
    
  /* *******************************************************************
   * IMPLEMENTAÇÃO DA INTERFACE OT (ENTITY)
   * ******************************************************************* */
    
    public Collection<String> getNomesPropriedadesChave() {
        throw new UnsupportedOperationException("getNomesPropriedadesChave");
    }
    
    public Collection<String> getNomesPropriedades() {
        throw new UnsupportedOperationException("getNomesPropriedades");
    }
    
    public Class getTipoAcessoPropriedades() {
        throw new UnsupportedOperationException("getTipoAcessoPropriedades");
    }
    
    public long getIdVersao() {
        throw new UnsupportedOperationException("getIdVersao");
    }
    
    public void setIdVersao(long idVersao) {
        throw new UnsupportedOperationException("getIdVersao");
    }
}
