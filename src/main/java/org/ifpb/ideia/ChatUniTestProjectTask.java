package org.ifpb.ideia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import org.apache.maven.project.MavenProject;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.ifpb.ideia.extension.ChatUniTestExtension;
import org.ifpb.ideia.logger.GradleLogger;
import zju.cst.aces.api.Task;
import zju.cst.aces.api.config.Config;
import zju.cst.aces.api.impl.ProjectImpl;
import zju.cst.aces.api.impl.RunnerImpl;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Getter
@Setter
public class ChatUniTestProjectTask extends DefaultTask {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    @Input
    @Optional
    protected String testOutput;

    @Input
    @Optional
    protected String tmpOutput;

    @Input
    @Optional
    protected String promptPath;

    @Input
    @Optional
    protected String examplePath;

    @Input
    @Optional
    protected Boolean stopWhenSuccess = true;

    @Input
    @Optional
    protected Boolean noExecution = false;

    @Input
    @Optional
    protected Boolean enableMultithreading = false;

    @Input
    @Optional
    protected Boolean enableRuleRepair = true;

    @Input
    @Optional
    protected Boolean enableObfuscate = false;

    @Input
    @Optional
    protected Boolean enableMerge = true;

    @Input
    @Optional
    protected String[] obfuscateGroupIds;

    @Input
    @Optional
    protected Integer maxThreads = 0;

    @Input
    @Optional
    protected Integer testNumber = 5;

    @Input
    @Optional
    protected Integer maxRounds = 5;

    @Input
    @Optional
    protected Integer maxPromptTokens = 2600;

    @Input
    @Optional
    protected Integer minErrorTokens = 500;

    @Input
    @Optional
    protected Integer maxResponseTokens = 1024;

    @Input
    @Optional
    protected Integer sleepTime = 0;

    @Input
    @Optional
    protected Integer dependencyDepth = 1;

    @Input
    @Optional
    protected Double temperature = 0.5;

    @Input
    @Optional
    protected Integer topP = 1;

    @Input
    @Optional
    protected Integer frequencyPenalty = 0;

    @Input
    @Optional
    protected Integer presencePenalty = 0;

    @Input
    @Optional
    protected String proxy = "null:-1";

    @Input
    @Optional
    protected String phaseType = "COVERUP";

    @Input
    @Optional
    protected String smartUnitTest_path = "D:\\APP\\IdeaProjects\\chatunitest-maven-plugin-corporation\\src\\main\\resources\\smartut-master-1.1.0.jar";

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

    @TaskAction
    public void runProjectTest() {
        init();
        this.gradleLogger.info("==========================");
        this.gradleLogger.info("[" + this.phaseType + "] Generating tests for project " + this.mavenProject.getBasedir().getName() + " ...");
        this.gradleLogger.warn("[" + this.phaseType + "] It may consume a significant number of tokens!");
        try {
            new Task(this.config, new RunnerImpl(this.config)).startProjectTask();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void init() {
        if (this.tmpOutput == null) {
            this.tmpOutput = this.mavenProject.getBasedir() + "/tmp/chatunitest-info";
        }
        if (this.examplePath == null) {
            this.examplePath = this.mavenProject.getBasedir() + "/exampleUsage.json";
        }

        ProjectImpl myProject = new ProjectImpl(this.mavenProject, this.classPaths);

        this.config = new Config.ConfigBuilder(myProject)
                .logger(this.gradleLogger)
                .promptPath(this.promptPath == null ? null : new File(this.promptPath))
                .examplePath(Paths.get(this.examplePath))
                .apiKeys(this.extension.getApiKeys())
                .enableMultithreading(this.enableMultithreading)
                .enableRuleRepair(this.enableRuleRepair)
                .tmpOutput(Paths.get(this.tmpOutput))
                .testOutput(this.testOutput == null ? null : Paths.get(this.testOutput))
                .stopWhenSuccess(this.stopWhenSuccess)
                .noExecution(this.noExecution)
                .enableObfuscate(this.enableObfuscate)
                .enableMerge(this.enableMerge)
                .obfuscateGroupIds(this.obfuscateGroupIds)
                .maxThreads(this.maxThreads)
                .testNumber(this.testNumber)
                .maxRounds(this.maxRounds)
                .sleepTime(this.sleepTime)
                .dependencyDepth(this.dependencyDepth)
                .model(this.extension.getModel())
                .maxResponseTokens(this.maxResponseTokens)
                .url(this.extension.getUrl())
                .temperature(this.temperature)
                .topP(this.topP)
                .frequencyPenalty(this.frequencyPenalty)
                .presencePenalty(this.presencePenalty)
                .proxy(this.proxy)
                .phaseType(this.phaseType)
                .build();

        this.config.setPluginSign(this.phaseType);
        this.config.print();
    }
}
