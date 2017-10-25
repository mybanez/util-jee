package fsiscad.modelo.cadastro.beans;

import fsiscad.util.*;
import fsiscad.beans.*;
import fsiscad.modelo.ot.*;
import java.beans.*;
import java.lang.reflect.*;
import javax.ejb.*;

/**
 * Classe com rotinas para a manipulação de componentes de cadastro via
 * introspecção.
 */
public final class Cadastros {
    
    /**
     * Retorna os metadados relativos a interface <i>home</i> deste cadastro 
     * <i>entity bean</i>.
     */
    public static DescritorCadastroEntityLocal getDescritorCadastroEntityLocal(EJBLocalHome home) {
        CacheMetaDados cache = CacheMetaDados.getCacheMetaDados();
        Class tipoHome = home.getClass();
        DescritorCadastroEntityLocal info = (DescritorCadastroEntityLocal)cache.get("HOME."+tipoHome.getName());
        if (info == null) {
            synchronized (cache) {
                try {
                    MethodDescriptor[] infoMetodo = Introspector.getBeanInfo(tipoHome).getMethodDescriptors();
                    //Tipo da chave pimária
                    Class tipoChavePrimaria = null;
                    //Método "create"
                    Method metodoCriacao = null;
                    //Método "findByPrimaryKey"
                    Method metodoConsultaPorChavePrimaria = null;
                    //Método "findAll"
                    Method metodoConsultaTodos = null;
                    for (int i = 0; i < infoMetodo.length; i++) {
                        Method metodo = infoMetodo[i].getMethod();
                        if (metodo.getName().equals("create") &&
                                metodo.getParameterTypes().length == 2 &&
                                OT.class.isAssignableFrom(metodo.getParameterTypes()[0]) &&
                                OT.class.isAssignableFrom(metodo.getParameterTypes()[1])) {
                            metodoCriacao = metodo;
                        } else if (infoMetodo[i].getName().equals("findByPrimaryKey")) {
                            tipoChavePrimaria = metodo.getParameterTypes()[0];
                            metodoConsultaPorChavePrimaria = metodo;
                        } else if (infoMetodo[i].getName().equals("findAll")) {
                            metodoConsultaTodos = metodo;
                        }
                    }
                    //Constroi mapeamento
                    info = new DescritorCadastroEntityLocal(tipoHome, metodoCriacao, tipoChavePrimaria, metodoConsultaPorChavePrimaria, metodoConsultaTodos);
                    cache.put("HOME."+tipoHome.getName(), info);
                } catch(IntrospectionException ie) {
                    throw new ErroExecucao("Erro obtendo metadados", ie);
                }
            }
        }
        return info;
    }
}
