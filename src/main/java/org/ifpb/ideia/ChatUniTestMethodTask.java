package org.ifpb.ideia;

import lombok.Getter;
import lombok.Setter;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import zju.cst.aces.api.Task;
import zju.cst.aces.api.impl.RunnerImpl;

@Getter
@Setter
public class ChatUniTestMethodTask extends ChatUniTestBaseTask {
    @Input
    @Optional
    public String selectMethod;

    @TaskAction
    public void run() {
        this.init();
        if (this.selectMethod == null || !this.selectMethod.contains("#")) {
            throw new IllegalArgumentException("Invalid selectMethod format. Expected 'ClassName#MethodName'.");
        }

        String[] parts = this.selectMethod.split("#");
        String className = parts[0];
        String methodName = parts[1];

        this.getGradleLogger().info("==========================");
        this.getGradleLogger().info(
                "[" + this.extension.getPhaseType() + "] Generating tests for method " +
                        methodName + "in class" + className + "..."
        );
        try {
            new Task(this.config, new RunnerImpl(this.config)).startMethodTask(className, methodName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
