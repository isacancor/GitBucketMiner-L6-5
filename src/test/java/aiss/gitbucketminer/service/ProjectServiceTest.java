package aiss.gitbucketminer.service;

import aiss.gitbucketminer.exception.ProjectNotFoundException;
import aiss.gitbucketminer.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceTest {
    @Value("${gitbucketminer.token}")
    private String token;

    @Value("${gitbucketminer.baseuri}")
    private String baseUri;

    @Value("${gitminer.sincecommits}")
    private int sinceCommitsDefault;

    @Value("${gitminer.sinceissues}")
    private int sinceIssuesDefault;

    @Value("${gitminer.maxpages}")
    private int maxPagesDefault;

    final String owner = "root";
    final String repo = "sample";

    @Autowired
    ProjectService projectService;

    @Test
    void getProject() throws ProjectNotFoundException {
        Project project = projectService.getProject(owner, repo);
        assertEquals(project.id,"0","The id doesn't match");
        assertEquals(project.name,repo,"The name doesn't match");
        assertEquals(project.webUrl, "http://localhost:4040/" + owner + "/" + repo,"The url doesn't match");
        assertNotNull(project.getCommits(), "Commits are null");
        assertNotNull(project.getIssues(), "Issues are null");
    }

    @Test
    void getProjectThatDoesntExist() throws ProjectNotFoundException {
        Project project = projectService.getProject(repo, repo);
    }
}