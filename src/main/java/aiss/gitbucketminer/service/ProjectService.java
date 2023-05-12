package aiss.gitbucketminer.service;

import aiss.gitbucketminer.exception.ProjectNotFoundException;
import aiss.gitbucketminer.gitbucketmodel.Project2;
import aiss.gitbucketminer.model.Project;
import aiss.gitbucketminer.utils.ParsingModels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${gitbucketminer.baseuri}")
    private String baseUri;

    @Value("${gitbucketminer.token}")
    private String token;

    @Value("${gitminer.sincecommits}")
    private int sinceCommitsDefault;

    @Value("${gitminer.sinceissues}")
    private int sinceIssuesDefault;

    @Value("${gitminer.maxpages}")
    private int maxPagesDefault;

    private static Logger logger = LoggerFactory
            .getLogger(ProjectService.class);

    public Project getProject(String owner, String repo) throws ProjectNotFoundException {
        HttpHeaders headers = new HttpHeaders();

        // Setting token header
        if (token != "") {
            headers.set("Authorization", "token " + token);
        }

        // Send request
        HttpEntity<Project2[]> request = new HttpEntity<>(null, headers);

        String uri = baseUri + owner + "/" + repo;

        ResponseEntity<Project2> projectRE;
        try {
            projectRE = restTemplate
                    .exchange(uri, HttpMethod.GET, request, Project2.class);
        } catch (Exception e) {
            throw new ProjectNotFoundException();
        }


        Project2 oldProject = projectRE.getBody();
        Project project = ParsingModels.parseProject(oldProject);

        return project;
    }

}
