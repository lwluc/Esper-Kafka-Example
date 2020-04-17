package de.luc.weinbrecht.esperKafkaExample;


import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPRuntimeProvider;
import com.espertech.esper.runtime.client.EPStatement;
import de.luc.weinbrecht.esperKafkaExample.config.EsperConfig;
import de.luc.weinbrecht.esperKafkaExample.utils.EPLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.luc.weinbrecht.esperKafkaExample.config.EsperConfig.RUNTIME_URI;

public class EsperMain {

	private static final Logger log = LoggerFactory.getLogger(EsperMain.class);

	public void run() {
		Configuration configuration = EsperConfig.getConfiguration();

		log.info("Compiling statement");
		EPCompiled compiled = EPLUtil.loadQueries(configuration);

		log.info("Setting up runtime");
		EPRuntime runtime = EPRuntimeProvider.getRuntime(RUNTIME_URI, configuration);

		log.info("Deploying compiled EPL");
		EPDeployment deployment = EPLUtil.deploy(runtime, compiled);

		listenToSampleStatement(runtime, deployment);

		log.info("Keeping the Application running");
		while (true) {}
	}

	private void listenToSampleStatement(EPRuntime runtime, EPDeployment deployment) {
		log.info("Listening to statement");
		EPStatement statement = runtime.getDeploymentService().getStatement(deployment.getDeploymentId(), "sampleQuery");

		statement.addListener((newData, oldData, sta, run) -> {
			for (EventBean nd : newData) {
				log.info("Read {} ({}) from Kafka", nd.get("word"), nd.get("len"));
			}
		});
	}
}
