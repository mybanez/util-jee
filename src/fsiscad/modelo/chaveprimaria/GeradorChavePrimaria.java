package fsiscad.modelo.chaveprimaria;

import fsiscad.contexto.*;
import fsiscad.util.*;
import fsiscad.modelo.*;
import javax.naming.*;

/**
 * Gerador de chaves primárias para <i>entity beans</i>. Disponibiliza as técnicas de
 * geração de UUID (<i>Universally Unique IDentifier</i>) em memória,
 * implementada na classe {@link GeradorUUID GeradorUUID}, de geração de
 * sequências armazenadas no banco de dados, implementada nos EJBs {@link
 * GeradorSequenciaBean GeradorSequenciaBean} e {@link SequenciaBean
 * SequenciaBean}. Esta classe procura o nome JNDI do <i>session bean</i> que 
 * implementa o gerador de sequências no contexto JNDI 'java:comp/env/fsc', 
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
     * Retorna o gerador de chaves primárias.
     *
     * @return gerador de chaves primárias
     */
    public static GeradorChavePrimaria getGeradorChavePrimaria() throws ErroModelo {
        return (GeradorChavePrimaria) FabricaObjetoModelo.getInstanciaEmCache(GeradorChavePrimaria.class.getName(), GeradorChavePrimaria.class.getName());
    }
    
    /**
     * Retorna um identificador universal único.
     *
     * @return identificador universal único
     *
     * @throws ErroGeradorChavePrimaria se ocorrer um erro durante a geração do
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
     * Retorna o próximo valor desta seqüência.
     *
     * @return próximo valor da seqüência
     *
     * @throws ErroGeradorChavePrimaria se ocorrer um erro durante a geração do
     *         próximo valor da seqüência
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
            throw new ErroGeradorChavePrimaria("Erro gerando próximo valor da seqüência '"+nomeSeq+"'", e);
        }
    }
}
