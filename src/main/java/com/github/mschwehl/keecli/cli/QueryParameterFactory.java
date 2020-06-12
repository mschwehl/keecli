/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.mschwehl.keecli.cli;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.IStringConverterFactory;

/**
 *
 * @author s850
 */
public class QueryParameterFactory  implements IStringConverterFactory {
  public Class<? extends IStringConverter<?>> getConverter(Class forType) {
    if (forType.equals(QueryParameter.class)) return QueryParameterConverter.class;
    else return null;
  }
}