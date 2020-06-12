package com.github.mschwehl.keecli.cli;

import com.beust.jcommander.Parameter;
import com.github.mschwehl.keecli.api.EKeeField;
import com.github.mschwehl.keecli.api.ETarget;


public class Arguments {

    @Parameter(names = {"--query","-q" })
    private QueryParameter query;

    @Parameter(names = {"--output","-o" })
    private QueryParameter output = new QueryParameter("path", "username");

    @Parameter(names = {"--target","-t"} )
    private ETarget target = ETarget.console;
    
    @Parameter(names = "-dbPath", description = "Database-Path" , required = true  )
    private String dbPath;

    @Parameter(names = "-dbPassword", description = "DB Password" , required = true, password = true)
    private String dbPassword;
    
    @Parameter(names = {"--version","-v" ,"-h" , "--help"} , help = true)
    private boolean version;

    /**
     * @return the query
     */
    public QueryParameter getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(QueryParameter query) {
        this.query = query;
    }

    /**
     * @return the output
     */
    public QueryParameter getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(QueryParameter output) {
        this.output = output;
    }

    /**
     * @return the target
     */
    public ETarget getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(ETarget target) {
        this.target = target;
    }

    /**
     * @return the dbPath
     */
    public String getDbPath() {
        return dbPath;
    }

    /**
     * @param dbPath the dbPath to set
     */
    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    /**
     * @return the dbPassword
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * @param dbPassword the dbPassword to set
     */
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    /**
     * @return the version
     */
    public boolean isVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(boolean version) {
        this.version = version;
    }
    

    public boolean isOutputPassword() {
        return output != null && output.getField().equals(EKeeField.password.toString());
    }
    
    public boolean isTargetConsole() {
        return ETarget.console.equals(target);
    }
    
    public boolean isTargetClipboard() {
        return ETarget.clipboard.equals(target);
    }
        
}
