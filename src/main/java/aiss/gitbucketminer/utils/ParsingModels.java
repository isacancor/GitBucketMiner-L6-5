package aiss.gitbucketminer.utils;

import aiss.gitbucketminer.gitbucketmodel.*;
import aiss.gitbucketminer.model.*;

public class ParsingModels {

    public static Project parseProject(Project2 oldProject){
        Project newProject = new Project();
        newProject.setId(oldProject.getId());
        newProject.setName(oldProject.getName());
        newProject.setWebUrl(oldProject.getWebUrl());

        return newProject;
    }

}
