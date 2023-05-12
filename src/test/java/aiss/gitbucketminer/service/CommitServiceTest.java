package aiss.gitbucketminer.service;

import aiss.gitbucketminer.gitbucketmodel.Commit2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommitServiceTest {

    @Autowired
    CommitService commitService;

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
    @Test
    void getCommits() {
        String uri = baseUri + owner + "/" + repo +  "/commits";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        ResponseEntity<Commit2[]> commits = commitService.getCommits(uri, headers);
        List<Commit2> commitList = Arrays.stream(commits.getBody()).toList();
        assertNotNull(commitList, "The list of commits is null");

        System.out.println(commitList.size());
        commitList.stream().forEach(c -> System.out.println(c + "\n"));
    }
}