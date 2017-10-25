package fsiscad.util;

import fsiscad.contexto.*;
import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import javax.rmi.*;

/**
 * Localizador de EJBs (<i>pattern Service Locator</i>). Mant�m cache de interfaces
 * <i>home</i> e possui rotinas para localiza��o, serializa��o e deserializa��o de
 * inst�ncias de EJBs. O localizador � guardado no <a href="../contexto/Contexto.html">
 * contexto de execu��o</a> mantido pela <i>framework</i>.
 *
 * @see Contexto
 */
public final class LocalizadorEJB {
    private InitialContext ic;
    private Map<String, EJBHome> mpHomes = new HashMap<String, EJBHome>();
    private Map<String, EJBLocalHome> mpLocalHomes = new HashMap<String, EJBLocalHome>();
    
    private LocalizadorEJB() throws NamingException {
        ic = new InitialContext();
    }
    
    /**
     * Retorna o gerenciador de EJBs.
     * 
     * 
     * @return gerenciador de EJBs
     * @throws ErroLocalizadorEJB se ocorrer um erro na obten��o do gerenciador
     *         de EJBs
     */
    public static LocalizadorEJB getLocalizadorEJB() throws ErroLocalizadorEJB {
        try {
            Contexto ctx = Contexto.getContextoCarregador();
            if (!ctx.itemDefinido(LocalizadorEJB.class.getName())) {
                try {
                    ctx.definir(LocalizadorEJB.class.getName(), new LocalizadorEJB());
                } catch (ErroItemContextoJaDefinido e) {
                    //ErroVinculoJaDefinido pode ocorrer devido a condi��es de concorr�ncia
                }
            }
            return (LocalizadorEJB)ctx.buscar(LocalizadorEJB.class.getName());
        } catch (Exception e) {
            throw new ErroLocalizadorEJB("Erro instanciando localizador", e);
        }
    }
    
    /**
     * Retorna uma inst�ncia de EJB usando o <tt>EJBHandle</tt> deserializado a
     * partir da representa��o em formato serializado do <i>handle</i> mantida
     * neste array de bytes.
     * 
     * 
     * @return inst�ncia de EJB
     * @throws ErroLocalizadorEJB se ocorrer um erro durante a recupera��o do
     *         EJB
     */
    public EJBObject getServico(byte[] id) throws ErroLocalizadorEJB {
        try {
            InputStream is = new ByteArrayInputStream(id);
            ObjectInputStream ois = new ObjectInputStream(is);
            javax.ejb.Handle handle = (javax.ejb.Handle)ois.readObject();
            return handle.getEJBObject();
        } catch (Exception e) {
            throw new ErroLocalizadorEJB("Erro obtendo servi�o", e);
        }
    }
    
    /**
     * Retorna um array de bytes com a representa��o do <tt>EJBHandle</tt> deste
     * EJB em formato serializado.
     * 
     * 
     * @return array de bytes com o <tt>EJBHandle</tt> em formato serializado
     * @throws ErroLocalizadorEJB se ocorrer um erro durante a serializa��o do
     *         <i>handle</i>
     */
    public byte[] getId(EJBObject ejb) throws ErroLocalizadorEJB {
        try {
            javax.ejb.Handle handle = ejb.getHandle();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(handle);
            oos.flush();
            oos.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new ErroLocalizadorEJB("Erro gerando id para servi�o do tipo '" +
                    ejb.getClass().getName() + "')", e);
        }
    }
    
    /**
     * Retorna a interface <i>home</i> com este nome e deste tipo.
     * 
     * 
     * @param nome nome da interface <i>home</i>
     * @param tipo tipo da interface <i>home</i>
     * @return interface <i>home</i>
     * @throws ErroLocalizadorEJB se ocorrer um erro na recupera��o da interface
     *         <i>home</i>
     */
    public EJBHome getHome(String nome, Class tipo) throws ErroLocalizadorEJB {
        try {
            EJBHome home = mpHomes.get(nome);
            if (home == null) {
                Object objref = ic.lookup(nome);
                home = (EJBHome)PortableRemoteObject.narrow(objref, tipo);
                mpHomes.put(nome, home);
            }
            return home;
        } catch (Exception e) {
            throw new ErroLocalizadorEJB("Erro localizando interface remota '"+nome+"'", e);
        }
    }
    
    /**
     * Retorna a interface <i>home</i> local com este nome.
     * 
     * 
     * @param nome nome da interface <i>home</i> local
     * @return interface <i>home</i> local
     * @throws ErroLocalizadorEJB se ocorrer um erro na recupera��o da interface
     *         <i>home</i> local
     */
    public EJBLocalHome getLocalHome(String nome) throws ErroLocalizadorEJB {
        try {
            EJBLocalHome home = mpLocalHomes.get(nome);
            if (home == null) {
                home = (EJBLocalHome)ic.lookup(nome);
                mpLocalHomes.put(nome, home);
            }
            return home;
        } catch (Exception e) {
            throw new ErroLocalizadorEJB("Erro localizando interface local '"+nome+"'", e);
        }
    }
}
