package de.luc.weinbrecht.esperKafkaExample.utils;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.common.client.module.Module;
import com.espertech.esper.common.client.module.ParseException;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.DeploymentOptions;
import com.espertech.esper.runtime.client.EPDeployException;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class EPLUtil {
    private static final Logger log = LoggerFactory.getLogger(EPLUtil.class);

    public static EPCompiled compileEPL(Configuration configuration, String statement, String name) {
        log.info("Compiling EPL from statement param ({}:{})", name, statement);
        String finalStatement;
        if (name != null) {
            finalStatement = "@name(" + name + ") " + statement;
        } else {
            finalStatement = statement;
        }

        EPCompiled compiled;
        try {
            compiled = EPCompilerProvider.getCompiler().compile(finalStatement, new CompilerArguments(configuration));
        } catch (EPCompileException ex) {
            throw new RuntimeException(ex);
        }

        return compiled;
    }

    public static EPCompiled loadQueries(Configuration configuration) {
        URL url = EPLUtil.class.getResource("/queries/queries.epl");
        Module queries = null;
        try {
            queries = EPCompilerProvider.getCompiler().readModule(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return compileModule(queries, configuration);
    }

    public static EPCompiled loadQueriesByFile(Configuration configuration, File file) {
        Module queries = null;
        try {
            queries = EPCompilerProvider.getCompiler().readModule(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return compileModule(queries, configuration);
    }

    private static EPCompiled compileModule(Module module, Configuration configuration) {
        try {
            return EPCompilerProvider.getCompiler().compile(module, new CompilerArguments(configuration));
        } catch (EPCompileException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static EPDeployment deploy(EPRuntime runtime, EPCompiled compiled) {
        EPDeployment deployment;
        try {
            deployment = runtime.getDeploymentService().deploy(compiled, new DeploymentOptions().setDeploymentId(UUID.randomUUID().toString()));
        } catch (EPDeployException ex) {
            throw new RuntimeException(ex);
        }
        return deployment;
    }
}
