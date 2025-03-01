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
public class ChatUniTestClassTask extends ChatUniTestBaseTask {
    @Input
    @Optional
    public String selectClass;

    @TaskAction
    public void run() {
        this.init();
        if (this.selectClass == null) {
            throw new IllegalArgumentException("Invalid selectClass format. Expected 'ClassName'.");
        }

        this.getGradleLogger().info("==========================");
        this.getGradleLogger().info(
                "[" + this.extension.getPhaseType() + "] Generating tests for class " + this.selectClass
        );
        try {
            new Task(this.config, new RunnerImpl(this.config)).startClassTask(this.selectClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
