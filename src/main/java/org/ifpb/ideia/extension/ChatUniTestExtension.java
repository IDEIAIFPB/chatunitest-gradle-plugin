package org.ifpb.ideia.extension;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatUniTestExtension {
    private String coverageAnalyzerJarPath;
    private String[] apiKeys = {"xxx"};
    private String model = "qwen2.5-coder:14b";
    private String url = "http://localhost:11434/v1/chat/completions";
}
