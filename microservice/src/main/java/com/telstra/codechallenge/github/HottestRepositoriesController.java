package com.telstra.codechallenge.github;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gitHub")
public class HottestRepositoriesController
{

  @Autowired
  private HottestRepositoriesService hottestRepositoriesService;

  @GetMapping("/trendingRepos")
  public List<TrendingRepos> trendingRepos( @RequestParam(value = "repoCount", required = false) Integer repoCount)
  {
    return hottestRepositoriesService.getTrendingRepos(repoCount);
  }

}
