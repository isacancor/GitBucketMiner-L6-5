package aiss.gitbucketminer.controller;

import aiss.gitbucketminer.exception.ProjectNotFoundException;
import aiss.gitbucketminer.model.Project;
import aiss.gitbucketminer.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Tag(name = "GitBucket Project", description = "GitBucket Project management API")
@RestController
@RequestMapping("/gitbucketminer")
public class GitBucketController {
    @Autowired
    ProjectService projectService;
    @Autowired
    RestTemplate restTemplate;

    // GET /gitbucketminer/{owner}/{repoName}
    @Operation(
            summary = "Retrieve a GitBucket Project",
            description = "Get a GitBucket Project by specifying some parameters",
            tags = { "project", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Project found",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Project not found",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{owner}/{repo}")
    public Project getProject(@Parameter(description = "name of the project owner") @PathVariable String owner,
                              @Parameter(description = "name of the project repository") @PathVariable String repo)
            throws ProjectNotFoundException {
        Project res = projectService.getProject(owner, repo);
        return res;
    }

    // POST /gitbucketminer/{owner}/{repoName}
    @Operation(
            summary = "Send a GitBucket Project to GitMiner",
            description = "Get a GitBucket Project to GitMiner by specifying some parameters",
            tags = { "project", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Sent project",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Project could not be sent",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404",
                    description = "Project not found",
                    content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{owner}/{repo}")
    public Project postProject(@Parameter(description = "name of the project owner") @PathVariable String owner,
                               @Parameter(description = "name of the project repository") @PathVariable String repo)
            throws ProjectNotFoundException {

        String uri = "http://localhost:8080/gitminer/projects";
        Project res = projectService.getProject(owner, repo);

        ResponseEntity<Project> response = restTemplate
                .postForEntity(uri, res, Project.class);

        return response.getBody();
    }
}
