package plugin;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.List;

/**
 * @Classname DependencyCounterMojo
 * @Description TODO
 * @Date 2022/5/25
 * @Created by zengzg
 */
@Mojo(name = "run-readme-build", defaultPhase = LifecyclePhase.COMPILE)
public class DependencyCounterMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("start build readme doc");
        List<Dependency> dependencies = project.getDependencies();

        project.get

        long numDependencies = dependencies.stream().count();
        getLog().info("Number of dependencies: " + numDependencies);
    }
}
