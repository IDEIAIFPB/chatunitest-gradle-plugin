package org.ifpb.ideia;

import lombok.Getter;
import lombok.Setter;
import org.gradle.api.tasks.TaskAction;
import zju.cst.aces.api.Task;
import zju.cst.aces.api.impl.RunnerImpl;

@Getter
@Setter
public class ChatUniTestProjectTask extends ChatUniTestBaseTask {

    @TaskAction
    public void run() {
        this.init();
        this.getGradleLogger().info("==========================");
        this.getGradleLogger().info(
                "[" + this.extension.getPhaseType() + "] Generating tests for project " +
                        this.getMavenProject().getBasedir().getName() + " ..."
        );
        this.getGradleLogger().warn(
                "[" + this.extension.getPhaseType() + "] It may consume a significant number of tokens!"
        );
        try {
            new Task(this.config, new RunnerImpl(this.config)).startProjectTask();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
