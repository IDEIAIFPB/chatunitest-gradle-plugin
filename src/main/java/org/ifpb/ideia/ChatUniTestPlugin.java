package org.ifpb.ideia;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.ifpb.ideia.adapter.MavenProjectAdapter;
import org.ifpb.ideia.extension.ChatUniTestExtension;
import org.ifpb.ideia.task.ChatUniTestBaseTask;
import org.ifpb.ideia.task.ChatUniTestClassTask;
import org.ifpb.ideia.task.ChatUniTestMethodTask;
import org.ifpb.ideia.task.ChatUniTestProjectTask;

import java.util.Objects;

public class ChatUniTestPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getExtensions().create("chatunitest", ChatUniTestExtension.class);

        Action<ChatUniTestBaseTask> baseConfig = task -> {
            task.setExtension(project.getExtensions().findByType(ChatUniTestExtension.class));
            task.setMavenProject(MavenProjectAdapter.fromGradleProject(project));
            task.setClassPaths(GradleDependencyBuilder.listClassPaths(project));
        };

        project.getTasks().register("chatunitest.project", ChatUniTestProjectTask.class, baseConfig);

        project.getTasks().register("chatunitest.class", ChatUniTestClassTask.class, task -> {
            baseConfig.execute(task);

            if (project.hasProperty("selectClass")) {
                task.setSelectClass(Objects.requireNonNull(project.property("selectClass")).toString());
            }
        });

        project.getTasks().register("chatunitest.method", ChatUniTestMethodTask.class, task -> {
            baseConfig.execute(task);

            if (project.hasProperty("selectMethod")) {
                task.setSelectMethod(Objects.requireNonNull(project.property("selectMethod")).toString());
            }
        });
    }
}
