package com.telstra.codechallenge.github;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service
public class HottestRepositoriesService
{
  @Autowired
  private RestTemplate restTemplate;

  @Value("${gitHub.api.url}")
  private String urlGitHubApi;

  @Value("${noOfDays}")
  private Integer noOfDays;

  /**
   * Returns a List of TrendingRepos. Taken from https://api.github.com/search/repositories?q=created:>tillDate&sort=stars&order=desc
   *
   * @return - list of {TrendingRepos}
   */
  public List<TrendingRepos> getTrendingRepos( Integer repoCount){

    LocalDate currentDate = LocalDate.now();
    
    //Date Till Repos Needed
    LocalDate lastReposDate = currentDate.minusDays(noOfDays);
    
    urlGitHubApi = urlGitHubApi.replaceFirst("tillDate",
                                             lastReposDate.toString());

    ResponseEntity<JsonNode> response = null;

    //Handle any exception/error that will happen during the call to the Api
    try
    {
      response = restTemplate.exchange(urlGitHubApi,
                                       HttpMethod.GET,
                                       HttpEntity.EMPTY,
                                       JsonNode.class);
    }
    catch(HttpStatusCodeException e)
    {
      ResponseEntity.status(e.getRawStatusCode())
          .headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
    }
    catch(RestClientException e)
    {
      ResponseEntity.status(HttpStatus.valueOf(e.getMessage()))
          .header(e.getMessage()).body(e.getMessage());
    }

    //It's easier to get the response as JsonNode object to handle it rapidly.
    JsonNode repoResults = response.getBody().findValue("items");

    List<TrendingRepos> list = new ArrayList<TrendingRepos>();
    //Check if the GitHub Repos API returns some result
    if(repoResults != null)
    {
      list = processResults(repoResults);
    }
    else
    {
      System.out.println("The response from the GitHub API Call is Null");
    }
    if(repoCount != null)
    {
      if(list.size() > repoCount)
      {
        //Showing only the list needed for
        return list.subList(0, repoCount);
      }
    }
    else
    {
      return list;
    }
    return list;
  
  }
  
  private List<TrendingRepos> processResults( JsonNode root)
  {

    List<TrendingRepos> list = new ArrayList<TrendingRepos>();

    //We do the same process for All the elements/nodes of the JsonNode, for that we use it as an ArrayNode
    if(root.isArray())
    {
      ArrayNode arrayNode = (ArrayNode) root;
      for(int i = 0; i < arrayNode.size(); i++)
      {
        JsonNode arrayElement = arrayNode.get(i);
        if(arrayElement.isObject())
        {

          TrendingRepos aTrendingRepos = new TrendingRepos();

          aTrendingRepos.setHtmlUrl(arrayElement.get("html_url").asText());
          aTrendingRepos
              .setDescription(arrayElement.get("description").asText());
          aTrendingRepos.setLanguage(arrayElement.get("language").asText());
          aTrendingRepos
              .setWatchersCount(arrayElement.get("watchers_count").asInt());
          aTrendingRepos.setName(arrayElement.get("name").asText());

          list.add(aTrendingRepos);
        }
      }

    }
    return list;
  }

}
