package com.telstra.codechallenge.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrendingRepos
{

  @JsonProperty("html_url")
  private String htmlUrl;

  @JsonProperty("watchers_count")
  private Integer watchersCount;

  private String language;

  private String description;

  private String name;

  public String getHtmlUrl()
  {
    return htmlUrl;
  }

  public void setHtmlUrl( String htmlUrl)
  {
    this.htmlUrl = htmlUrl;
  }

  public Integer getWatchersCount()
  {
    return watchersCount;
  }

  public void setWatchersCount( Integer watchersCount)
  {
    this.watchersCount = watchersCount;
  }

  public String getLanguage()
  {
    return language;
  }

  public void setLanguage( String language)
  {
    this.language = language;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription( String description)
  {
    this.description = description;
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name)
  {
    this.name = name;
  }

}
