package org.ifpb.ideia.extension;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatUniTestExtension {
    private Boolean stopWhenSuccess = true;
    private Boolean noExecution = false;
    private Boolean enableMultithreading = false;
    private Boolean enableRuleRepair = true;
    private Boolean enableObfuscate = false;
    private Boolean enableMerge = true;
    private Integer maxThreads = 0;
    private Integer testNumber = 5;
    private Integer maxRounds = 5;
    private Integer maxPromptTokens = 2600;
    private Integer minErrorTokens = 500;
    private Integer maxResponseTokens = 1024;
    private Integer sleepTime = 0;
    private Integer dependencyDepth = 1;
    private Integer topP = 1;
    private Integer frequencyPenalty = 0;
    private Integer presencePenalty = 0;
    private Double temperature = 0.5;
    private String testOutput;
    private String tmpOutput;
    private String promptPath;
    private String examplePath;
    private String proxy = "null:-1";
    private String phaseType = "COVERUP";
    private String smartUnitTest_path = "D:\\APP\\IdeaProjects\\chatunitest-maven-plugin-corporation\\src\\main\\resources\\smartut-master-1.1.0.jar";
    private String coverageAnalyzerJarPath;
    private String model = "qwen2.5-coder:14b";
    private String url = "http://localhost:11434/v1/chat/completions";
    private String[] obfuscateGroupIds;
    private String[] apiKeys = {"xxx"};
}
