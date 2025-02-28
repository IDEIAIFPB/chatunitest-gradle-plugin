package org.ifpb.ideia;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedDependency;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GradleDependencyBuilder {

    /**
     * Returns a list of absolute paths for the project's jar file and all artifacts in the compileClasspath.
     */
    public static List<String> listClassPaths(Project project) {
        List<String> classPaths = new ArrayList<>();

        File jarFile = project.file("build/libs/" + project.getName() + ".jar");
        if (jarFile.exists()) {
            classPaths.add(jarFile.getAbsolutePath());
        } else {
            throw new RuntimeException("Jar file not found at " + jarFile.getAbsolutePath() + ". Build the project first.");
        }

        ResolvedConfiguration resolvedConfig = project.getConfigurations()
                .getByName("compileClasspath")
                .getResolvedConfiguration();

        Set<ResolvedDependency> firstLevelDeps = resolvedConfig.getFirstLevelModuleDependencies();
        Set<ResolvedDependency> visited = new HashSet<>();

        for (ResolvedDependency dep : firstLevelDeps) {
            collectDependencyArtifacts(dep, visited, classPaths);
        }
        return classPaths;
    }

    /**
     * Recursively collects artifact files from the dependency graph.
     */
    private static void collectDependencyArtifacts(ResolvedDependency dependency, Set<ResolvedDependency> visited, List<String> classPaths) {
        if (!visited.add(dependency)) {
            return;
        }

        for (ResolvedArtifact artifact : dependency.getModuleArtifacts()) {
            File file = artifact.getFile();
            if (file.exists()) {
                classPaths.add(file.getAbsolutePath());
            }
        }

        for (ResolvedDependency child : dependency.getChildren()) {
            collectDependencyArtifacts(child, visited, classPaths);
        }
    }
}
