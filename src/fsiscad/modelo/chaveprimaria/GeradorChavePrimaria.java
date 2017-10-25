package fsiscad.modelo.chaveprimaria;

import fsiscad.contexto.*;
import fsiscad.util.*;
import fsiscad.modelo.*;
import javax.naming.*;

/**
 * Gerador de chaves prim�rias para <i>entity beans</i>. Disponibiliza as t�cnicas de
 * gera��o de UUID (<i>Universally Unique IDentifier</i>) em mem�ria,
 * implementada na classe {@link GeradorUUID GeradorUUID}, de gera��o de
 * sequ�ncias armazenadas no banco de dados, implementada nos EJBs {@link
 * GeradorSequenciaBean GeradorSequenciaBean} e {@link SequenciaBean
 * SequenciaBean}. Esta classe procura o nome JNDI do <i>session bean</i> que 
 * implementa o gerador de sequ�ncias no contexto JNDI 'java:comp/env/fsc', 
 * usanda a chave <tt>ChavesModelo.EJB_GERADOR_SEQUENCIA</tt>.
 */
public final class GeradorChavePrimaria {
    private String nomeEJBGeradorSeq;
    private byte[] idEJBGeradorSeq;
    
    public GeradorChavePrimaria() {
        try {
            Context ctx = new InitialContext();
            nomeEJBGeradorSeq = (String)ctx.lookup(ChavesModelo.CONTEXTO_JNDI+ChavesModelo.EJB_GERADOR_SEQUENCIA);
        } catch (NamingException ex) {
            nomeEJBGeradorSeq = GeradorSequenciaHome.class.getName();
        }
    }
    
    /**
     * Retorna o gerador de chaves prim�rias.
     *
     * @return gerador de chaves prim�rias
     */
    public static GeradorChavePrimaria getGeradorChavePrimaria() throws ErroModelo {
        return (GeradorChavePrimaria) FabricaObjetoModelo.getInstanciaEmCache(GeradorChavePrimaria.class.getName(), GeradorChavePrimaria.class.getName());
    }
    
    /**
     * Retorna um identificador universal �nico.
     *
     * @return identificador universal �nico
     *
     * @throws ErroGeradorChavePrimaria se ocorrer um erro durante a gera��o do
     *         UUID
     */
    public String getUUID() throws ErroGeradorChavePrimaria {
        try {
            return GeradorUUID.getGeradorUUID().getUUID();
        } catch (Exception e) {
            throw new ErroGeradorChavePrimaria("Erro gerando UUID", e);
        }
    }
    
    /**
     * Retorna o pr�ximo valor desta seq��ncia.
     *
     * @return pr�ximo valor da seq��ncia
     *
     * @throws ErroGeradorChavePrimaria se ocorrer um erro durante a gera��o do
     *         pr�ximo valor da seq��ncia
     */
    public long getProximoValorSequencia(String nomeSeq)
    throws ErroGeradorChavePrimaria {
        try {
            GeradorSequencia cache;
            LocalizadorEJB gerEJB = LocalizadorEJB.getLocalizadorEJB();            
            if (idEJBGeradorSeq == null) {
                GeradorSequenciaHome home = (GeradorSequenciaHome)gerEJB.getHome(nomeEJBGeradorSeq, GeradorSequenciaHome.class);
                cache = home.create();
                idEJBGeradorSeq = gerEJB.getId(cache);
            } else {
                cache = (GeradorSequencia)gerEJB.getServico(idEJBGeradorSeq);
            }
            return cache.getProximoValorSequencia(nomeSeq);
        } catch (Exception e) {
            throw new ErroGeradorChavePrimaria("Erro gerando pr�ximo valor da seq��ncia '"+nomeSeq+"'", e);
        }
    }
}
