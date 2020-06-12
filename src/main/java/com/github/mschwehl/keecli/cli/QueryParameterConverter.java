/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.mschwehl.keecli.cli;

import com.beust.jcommander.IStringConverter;

/**
 *
 * @author s850
 */
public class QueryParameterConverter implements IStringConverter<QueryParameter> {
  @Override
  public QueryParameter convert(String value) {
      
    if (value.contains(":") ) {
        String[] s = value.split(":");
        return new QueryParameter(s[0], s[1]);
    } else {
       return new QueryParameter("", value);

    }
  }
}