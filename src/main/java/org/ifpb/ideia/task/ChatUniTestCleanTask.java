package org.ifpb.ideia.task;

import lombok.Getter;
import lombok.Setter;
import org.gradle.api.tasks.TaskAction;
import org.codehaus.plexus.util.FileUtils;
import zju.cst.aces.util.TestCompiler;

@Getter
@Setter
public class ChatUniTestCleanTask extends ChatUniTestBaseTask {
    @TaskAction
    public void run() {
        this.init();
        this.getGradleLogger().info(
                "\n==========================\n[ChatUniTest] Cleaning project " +
                        this.getMavenProject().getBasedir().getName() + " ..."
        );
        this.getGradleLogger().info(
                "\n==========================\n[ChatUniTest] Cleaning output directory "
                + this.config.getTmpOutput() + " and " + this.config.getTestOutput() + " ..."
        );
        this.getGradleLogger().info(
                "\n==========================\n[ChatUniTest] Restoring backup folder ..."
        );

        try {
            FileUtils.deleteDirectory(config.getTmpOutput().toFile());
            FileUtils.deleteDirectory(config.getTestOutput().toFile());
            TestCompiler compiler = new TestCompiler(config.getTestOutput(), config.getCompileOutputPath(),
                    config.getProject().getBasedir().toPath().resolve("target"), config.getClassPaths());
            compiler.restoreBackupFolder();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.getGradleLogger().info("\n==========================\n[ChatUniTest] Finished");
    }
}

