package org.ifpb.ideia;

import lombok.Getter;
import lombok.Setter;
import org.apache.maven.project.MavenProject;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Internal;
import org.ifpb.ideia.extension.ChatUniTestExtension;
import org.ifpb.ideia.logger.GradleLogger;
import zju.cst.aces.api.config.Config;
import zju.cst.aces.api.impl.ProjectImpl;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Getter
@Setter
public class ChatUniTestBaseTask extends DefaultTask {

    @Internal
    protected Config config;

    @Internal
    protected ChatUniTestExtension extension;

    @Internal
    private MavenProject mavenProject;

    @Internal
    private List<String> classPaths;

    @Internal
    private GradleLogger gradleLogger = new GradleLogger(getLogger());

    protected void init() {
        ProjectImpl myProject = new ProjectImpl(this.mavenProject, this.classPaths);

        this.config = new Config.ConfigBuilder(myProject)
                .logger(this.gradleLogger)
                .promptPath(this.extension.getPromptPath() == null ? null : new File(this.extension.getPromptPath()))
                .examplePath(
                        this.extension.getExamplePath() == null ?
                                Paths.get(this.mavenProject.getBasedir() + "/exampleUsage.json") :
                                Paths.get(this.extension.getExamplePath())
                )
                .apiKeys(this.extension.getApiKeys())
                .enableMultithreading(this.extension.getEnableMultithreading())
                .enableRuleRepair(this.extension.getEnableRuleRepair())
                .tmpOutput(
                        this.extension.getTmpOutput() == null ?
                                Paths.get(this.mavenProject.getBasedir() + "/tmp/chatunitest-info") :
                                Paths.get(this.extension.getTmpOutput())
                )
                .testOutput(this.extension.getTestOutput() == null ? null : Paths.get(this.extension.getTestOutput()))
                .stopWhenSuccess(this.extension.getStopWhenSuccess())
                .noExecution(this.extension.getNoExecution())
                .enableObfuscate(this.extension.getEnableObfuscate())
                .enableMerge(this.extension.getEnableMerge())
                .obfuscateGroupIds(this.extension.getObfuscateGroupIds())
                .maxThreads(this.extension.getMaxThreads())
                .testNumber(this.extension.getTestNumber())
                .maxRounds(this.extension.getMaxRounds())
                .sleepTime(this.extension.getSleepTime())
                .dependencyDepth(this.extension.getDependencyDepth())
                .model(this.extension.getModel())
                .maxResponseTokens(this.extension.getMaxResponseTokens())
                .url(this.extension.getUrl())
                .temperature(this.extension.getTemperature())
                .topP(this.extension.getTopP())
                .frequencyPenalty(this.extension.getFrequencyPenalty())
                .presencePenalty(this.extension.getPresencePenalty())
                .proxy(this.extension.getProxy())
                .phaseType(this.extension.getPhaseType())
                .build();

        this.config.setPluginSign(this.extension.getPhaseType());
        this.config.print();
    }
}
