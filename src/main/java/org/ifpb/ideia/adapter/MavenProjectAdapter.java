package org.ifpb.ideia.adapter;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.project.MavenProject;
import org.gradle.api.Project;
import java.io.File;

public class MavenProjectAdapter {
    /**
     * Converts a Gradle project to a minimal MavenProject.
     * Adjust this method to populate additional fields if needed.
     */
    public static MavenProject fromGradleProject(Project gradleProject) {
        Model model = new Model();

        model.setGroupId(gradleProject.getGroup().toString());
        model.setArtifactId(gradleProject.getName());
        model.setVersion(gradleProject.getVersion().toString());

        model.setPackaging("jar");

        Build build = new Build();
        build.setSourceDirectory(gradleProject.getProjectDir().getPath() + "/src/main/java");
        model.setBuild(build);

        MavenProject mavenProject = new MavenProject(model);
        mavenProject.setFile(new File(gradleProject.getProjectDir(), "java"));

        if (gradleProject.getParent() != null) {
            mavenProject.setParent(fromGradleProject(gradleProject.getParent()));
        }

        return mavenProject;
    }
}
