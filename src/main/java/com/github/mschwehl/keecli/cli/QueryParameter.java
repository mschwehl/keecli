/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.mschwehl.keecli.cli;

/**
 *
 * @author s850
 */
public class QueryParameter {
    
    private String path;
    private String field;
    
    public QueryParameter(String path, String field) {
        this.path = path;
        this.field = field;
    }

    @Override
    public String toString() {
       return getPath() + "/" + getField();
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }
    
    
    
}
