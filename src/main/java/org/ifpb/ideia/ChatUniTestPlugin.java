package org.ifpb.ideia;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.ifpb.ideia.adapter.MavenProjectAdapter;
import org.ifpb.ideia.extension.ChatUniTestExtension;

import java.util.Objects;

public class ChatUniTestPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getExtensions().create("chatunitest", ChatUniTestExtension.class);

        project.getTasks().register("project", ChatUniTestProjectTask.class, task -> {
            task.setClassPaths(GradleDependencyBuilder.listClassPaths(project));
            task.setExtension(project.getExtensions().findByType(ChatUniTestExtension.class));
            task.setMavenProject(MavenProjectAdapter.fromGradleProject(project));
        });

        project.getTasks().register("method", ChatUniTestMethodTask.class, task -> {
            task.setClassPaths(GradleDependencyBuilder.listClassPaths(project));
            task.setExtension(project.getExtensions().findByType(ChatUniTestExtension.class));
            task.setMavenProject(MavenProjectAdapter.fromGradleProject(project));
            if (project.hasProperty("selectMethod")) {
                task.setSelectMethod(Objects.requireNonNull(project.property("selectMethod")).toString());
            }
        });
    }
}