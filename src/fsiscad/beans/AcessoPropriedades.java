package fsiscad.beans;

import java.io.*;
import java.util.*;

/**
 * Define métodos genéricos para acesso às propriedades de um componente JavaBean, 
 * tratado aqui como um simples repositório de propriedades. A chave de acesso é 
 * sempre uma String contendo o nome da propriedade, que deve começar com letra 
 * minúscula. 
 */
public interface AcessoPropriedades extends Serializable
{
  /**
   * Retorna todas as propriedades do JavaBean.
   *
   * @return mapa com os valores de todas as propriedades
   */
  Map<String, Object> get();

  /**
     * Retorna os valores destas propriedades.
     * 
     * @param clNames nomes das propriedades selecionadas
     *
     * @return mapa com os valores das propriedades selecionadas
     */
  Map<String, Object> get(Collection<String> clNames);

  /**
     * Define os valores destas propriedades.
     * 
     * @param mpValues mapa com os valores das propriedades selecionadas
     */
  void set(Map<String, Object> mpValues);

  /**
   * Retorna o valor desta propriedade.
   *
   * @param name nome da propriedade
   *
   * @return valor da propriedade
   */
  Object get(String name);

  /**
   * Define o valor desta propriedade.
   *
   * @param name nome da propriedade
   * @param value valor da propriedade
   */
  void set(String name, Object value);
}
