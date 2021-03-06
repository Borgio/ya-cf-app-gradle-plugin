package io.pivotal.services.plugin.tasks;

import io.pivotal.services.plugin.CfProperties;
import io.pivotal.services.plugin.tasks.helper.CfPushDelegate;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.gradle.api.tasks.TaskAction;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Responsible for cf push.
 *
 * @author Biju Kunjummen
 */
public class CfPushTask extends AbstractCfTask {

    private CfPushDelegate pushTaskDelegate = new CfPushDelegate();

    @TaskAction
    public void push() {
        CfProperties cfProperties = getCfProperties();
        LOGGER.info("About to call Push task : {} ", cfProperties.toString());

        CloudFoundryOperations cfOperations = getCfOperations();

        Mono<Void> resp = pushTaskDelegate.push(cfOperations, cfProperties);
        resp.block(Duration.ofMillis(defaultWaitTimeout));

    }

    @Override
    public String getDescription() {
        return "Pushes an Application to Cloud Foundry";
    }
}
