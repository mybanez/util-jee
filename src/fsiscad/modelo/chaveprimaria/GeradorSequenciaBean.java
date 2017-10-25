package fsiscad.modelo.chaveprimaria;

import fsiscad.modelo.*;
import fsiscad.util.*;
import javax.ejb.*;
import javax.naming.*;

/**
 * Componente <i>session bean</i> que gera e mant�m em cache blocos com os valores 
 * das seq��ncias obtidos atrav�s do <i>entity bean</i> {@link SequenciaBean SequenciaBean}. O 
 * cache ajuda a minimizar os acessos ao banco de dados, garantindo boa performance
 * na manipula��o das seq��ncias. Implementa a t�cnica descrita no livro
 * <i>EJB Design Patterns</i> de Floyd Marinescu.  Esta classe procura no 
 * contexto JNDI 'java:comp/env/fsc' os seguintes par�metros:
 * <p>
 * <b>1.</b><tt>ChavesModelo.BLOCO_SEQUENCIA</tt>: tamanho do bloco de valores
 * que deve ser mantido em cache. Se n�o for informado, o valor 10 � assumido
 * por default.
 * </p>
 * <p>
 * <b>2.</b><tt>ChavesModelo.TENTATIVAS_SEQUENCIA</tt>: n�mero de m�ximo de
 * tentativas para se obter o pr�ximo bloco de valores de uma seq��ncia.
 * M�ltiplas tentativas podem ser necess�rias em  fun��o de poss�veis
 * conflitos decorrentes de mecanismos de concorr�ncia otimista (ver livro
 * <i>EJB Design Patterns</i>). Se n�o for informado, o valor 3 � assumido por
 * default.
 * </p>
 * <p>
 * <b>3.</b><tt>ChavesModelo.EJB_SEQUENCIA</tt>: nome JNDI registrado na
 * aplica��o para a interface <i>home</i> do <i>entity bean</i> que mant�m as 
 * sequ�ncias.
 * </p>
 *
 * @see GeradorChavePrimaria
 */
public class GeradorSequenciaBean implements SessionBean {
    private SessionContext context;
    
    private int tamBloco;
    private int numTentativas;
    private String nomeEJB;
    
    public void ejbCreate() throws CreateException {
        try {
            Context ctx = new InitialContext();
            try {
                tamBloco = (Integer)ctx.lookup(ChavesModelo.CONTEXTO_JNDI+ChavesModelo.BLOCO_SEQUENCIA);
            } catch (NamingException e) {
                tamBloco = 10;
            }
            try {
                numTentativas = (Integer)ctx.lookup(ChavesModelo.CONTEXTO_JNDI+ChavesModelo.TENTATIVAS_SEQUENCIA);
            } catch (NamingException e) {
                numTentativas = 3;
            }
            try {
                nomeEJB = (String)ctx.lookup(ChavesModelo.CONTEXTO_JNDI+ChavesModelo.EJB_SEQUENCIA);
            } catch (NamingException e) {
                nomeEJB = SequenciaLocalHome.class.getName();
            }
        } catch (NamingException e) {
            throw new CreateException("Erro criando gerador de seq��ncias: "+e);
        }
    }
    
    public void ejbActivate() {}
    
    public void ejbPassivate() {}
    
    public void ejbRemove() {}
    
    public SessionContext getSessionContext() {
        return context;
    }
    
    public void setSessionContext(SessionContext context) {
        this.context = context;
    }
    
    /**
     * Retorna o pr�ximo valor desta seq��ncia.
     *
     * @return pr�ximo valor da seq��ncia
     *
     * @throws ErroSequencia se ocorrer um erro durante a gera��o do pr�ximo
     *         valor da seq��ncia
     */
    public long getProximoValorSequencia(String nomeSeq)
    throws ErroGeradorSequencia {
        try {
            MapaSequencias mpSeqs = MapaSequencias.getMapaSequencias();
            InfoSequencia info = mpSeqs.get(nomeSeq);
            if (info == null) {
                info = new InfoSequencia();
                SequenciaLocalHome home = null;
                try {
                    LocalizadorEJB gerEJB = LocalizadorEJB.getLocalizadorEJB();
                    home = (SequenciaLocalHome)gerEJB.getLocalHome(nomeEJB);
                    info.setSequencia(home.findByPrimaryKey(nomeSeq));
                } catch (FinderException fe) {
                    info.setSequencia(home.create(nomeSeq));
                }
                mpSeqs.put(nomeSeq, info);
            }
            if ((info.getValorAtual() % tamBloco) == 0) {
                for (int i = 0; true; i++) {
                    try {
                        info.setValorAtual(info.getSequencia().getProximoValor(tamBloco));
                        break;
                    } catch (TransactionRolledbackLocalException e) {
                        if (i < numTentativas) {
                            continue;
                        } else {
                            throw new ErroGeradorSequencia("M�ximo de tentativas para obten��o do pr�ximo valor da seq��ncia excedido: "
                                    +numTentativas, e);
                        }
                    }
                }
            }
            return info.incValorAtual();
        } catch (Exception e) {
            throw new ErroGeradorSequencia("Erro obtendo pr�ximo valor da seq��ncia '"+nomeSeq+"'", e);
        }
    }
}
