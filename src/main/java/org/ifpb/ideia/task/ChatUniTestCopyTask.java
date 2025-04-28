package org.ifpb.ideia.task;

import lombok.Getter;
import lombok.Setter;
import org.gradle.api.tasks.TaskAction;
import zju.cst.aces.util.TestCompiler;
import java.io.File;

@Getter
@Setter
public class ChatUniTestCopyTask extends ChatUniTestBaseTask {
    @TaskAction
    public void run() {
        this.init();
        this.getGradleLogger().info("\n==========================\n[ChatUniTest] Copying tests ...");

        TestCompiler.srcTestFolder = new File(config.getProject().getBasedir(), "src/test/java");
        TestCompiler.testBackupFolder = new File(config.getProject().getBasedir(), "src/backup");

        try {
            TestCompiler compiler = new TestCompiler(config.getTestOutput(), config.getCompileOutputPath(),
                    config.getProject().getBasedir().toPath().resolve("build"), config.getClassPaths());
            compiler.copyAndBackupTestFolder();
            compiler.copyAndBackupCompiledTest();
        } catch (Exception e) {
            this.getGradleLogger().error("Failed to copy test folder, please try again.");
            throw new RuntimeException(e);
        }

        this.getGradleLogger().info("\n==========================\n[ChatUniTest] Finished");
    }
}
