package org.ifpb.ideia.adapter;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.project.MavenProject;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.bundling.Jar;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        SourceSetContainer sourceSets = gradleProject.getExtensions().getByType(SourceSetContainer.class);
        SourceSet mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
        List<String> sourcePaths = mainSourceSet
                .getJava()
                .getSrcDirs()
                .stream()
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());

        build.setSourceDirectory(sourcePaths.get(0));
        Set<File> classDirs = mainSourceSet.getOutput().getClassesDirs().getFiles();
        File firstDir = classDirs.iterator().next();
        build.setOutputDirectory(firstDir.getAbsolutePath());

        build.setDirectory(gradleProject.getLayout().getBuildDirectory().getAsFileTree().getAsPath());

        String baseName = gradleProject.getTasks().named("jar", Jar.class).get().getArchiveBaseName().get();
        String projectVersion = gradleProject.getVersion().equals("unspecified") ? "" : "-" + gradleProject.getVersion();
        build.setFinalName(baseName + projectVersion);

        model.setBuild(build);

        MavenProject mavenProject = new MavenProject(model);
        mavenProject.setFile(new File(gradleProject.getProjectDir(), "java"));

        sourcePaths.forEach(mavenProject::addCompileSourceRoot);

        if (gradleProject.getParent() != null) {
            mavenProject.setParent(fromGradleProject(gradleProject.getParent()));
        }

        return mavenProject;
    }
}
