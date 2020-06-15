package com.github.mschwehl.keecli;


import com.github.mschwehl.keecli.cli.QueryParameterFactory;
import com.github.mschwehl.keecli.cli.QueryParameter;
import com.beust.jcommander.JCommander;
import com.github.mschwehl.keecli.api.EKeeField;
import com.github.mschwehl.keecli.cli.Arguments;
import org.linguafranca.pwdb.Database;
import org.linguafranca.pwdb.Entry;
import org.linguafranca.pwdb.kdbx.KdbxCreds;
import org.linguafranca.pwdb.kdbx.simple.SimpleDatabase;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Keecli
 *
 */
public class Keecli {

    public static void main(String[] args) throws Exception {

        final Arguments keeArgs = new Arguments();

            JCommander.newBuilder()
                .addObject(keeArgs)
                .addConverterFactory(new QueryParameterFactory())
                .build()
                .parse(args);

        // Graal-native: No required-check is applied
        if (keeArgs == null
                || keeArgs.isVersion() 
                || keeArgs.getMainParameter() != null
                || keeArgs.getDbPassword() == null
                || keeArgs.getDbPath() == null
                || keeArgs.getTarget() == null) {
            System.out.println("usage: java -jar keecli.jar -q username:test -o field:password -t clipboard -dbPassword xxx -dbPath /usr/kee.kdbx");
            System.out.println("       java -jar keecli.jar -o path:username -dbPassword xxx -dbPath /usr/kee.kdbx");
            System.out.println("       -q query: filter elements by paht or attribute -o: outputfilter e.g. by fieldname, -t console or clipboard");
            System.out.println("version: " + getVersionNameFromManifest());
            
            return; 
        }
        
        

        KdbxCreds credentials = new KdbxCreds(keeArgs.getDbPassword().getBytes());

        Path path = Paths.get(keeArgs.getDbPath());

        // in memory, sicherer für read-only operationen
        ByteArrayInputStream bis = new ByteArrayInputStream(Files.readAllBytes(path));

        try (InputStream inputStream = bis;) {

            Database database = SimpleDatabase.load(credentials, inputStream);

            try {

                Function<Entry, Boolean> query = buildQueryFunction(keeArgs.getQuery());

                java.util.List<Entry> list = database.findEntries(query::apply);

                Function<Entry, String> collector = buildOutputConsumer(keeArgs);

                if (keeArgs.isOutputPassword() && keeArgs.isTargetClipboard() && list.size() != 1) {
                    throw new IllegalStateException("Query does not deliver exact one element, cannot copy to clipboard: " + list.size());
                }

                String result = list.stream().map(collector).collect(Collectors.joining(System.lineSeparator()));

                switch (keeArgs.getTarget()) {

                    case clipboard:

                        StringSelection selection = new StringSelection(result);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(selection, selection);

                        System.out.println("content copied to clipboard");
                        
                        break;

                    case console:

                        System.out.println(result);
                        break;

                }

            } catch (RuntimeException re) {
                re.printStackTrace();
            }

        } // end try // end try

    }

    private static Function<Entry, Boolean> buildQueryFunction(QueryParameter query) {

        if (query == null) {
            return (Entry entry) -> {
                return true;
            };
        }

        // username
        Function<Entry, String> rval = buildMapField(query.getPath());

        Function<Entry, Boolean> path = (Entry e) -> {
            return rval.apply(e).equals(query.getField());
        };

        return path;

    }

    private static Function<Entry, String> buildOutputConsumer(Arguments arguments) {

        Function<Entry, String> rval = buildMapField(arguments.getOutput().getField());

        if (arguments.getOutput().getPath().equals("field")) {
            return rval;
        }

        if (arguments.getOutput().getPath().equals("path")) {

            Function<Entry, String> path = (Entry e) -> {
                return e.getPath() + "/" + rval.apply(e);
            };

            return path;

        }

        return (Entry e) -> {
            return e.getPath();
        };

    }

    private static Function<Entry, String> buildMapField(String property) {

        if (EKeeField.password.toString().equals(property)) {
            return (Entry e) -> {
                return e.getPassword();
            };
        }

        if (EKeeField.username.toString().equals(property)) {
            return (Entry e) -> {
                return e.getUsername();
            };
        }

        if (EKeeField.url.toString().equals(property)) {
            return (Entry e) -> {
                return e.getUrl();
            };
        }

        if (EKeeField.title.toString().equals(property)) {
            return (Entry e) -> {
                return e.getTitle();
            };
        }

        if (EKeeField.uuid.toString().equals(property)) {
            return (Entry e) -> {
                return e.getUuid().toString();
            };
        }

        throw new IllegalStateException("field not mapped: " + property);

    }

    public static UUID makeUuid(String uuidString) {

        UUID uuid = new UUID(
                new BigInteger(uuidString.substring(0, 16), 16).longValue(),
                new BigInteger(uuidString.substring(16), 16).longValue());

        return uuid;

    }
    
    static String getVersionNameFromManifest() throws IOException {

        Properties p = new Properties();
        p.load( Keecli.class.getClassLoader().getResourceAsStream("META-INF/maven/com.github.mschwehl/keecli/pom.properties") );
        return p.getProperty("version");
        
    }
        

}
