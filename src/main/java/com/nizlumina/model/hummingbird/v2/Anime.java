package com.nizlumina.model.hummingbird.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Anime
{

    @Expose
    private int id;
    @Expose
    private Titles titles;
    @Expose
    private String slug;
    @Expose
    private String synopsis;
    @SerializedName("started_airing_date")
    @Expose
    private String startedAiringDate;
    @SerializedName("finished_airing_date")
    @Expose
    private String finishedAiringDate;
    @SerializedName("youtube_video_id")
    @Expose
    private String youtubeVideoId;
    @SerializedName("age_rating")
    @Expose
    private String ageRating;
    @SerializedName("episode_count")
    @Expose
    private int episodeCount;
    @SerializedName("episode_length")
    @Expose
    private int episodeLength;
    @SerializedName("show_type")
    @Expose
    private String showType;
    @SerializedName("poster_image")
    @Expose
    private String posterImage;
    @SerializedName("cover_image")
    @Expose
    private String coverImage;
    @SerializedName("community_rating")
    @Expose
    private double communityRating;
    @Expose
    private List<String> genres = new ArrayList<String>();
    @Expose
    private List<String> producers = new ArrayList<String>();
    @SerializedName("bayesian_rating")
    @Expose
    private double bayesianRating;
    @Expose
    private Links links;

    /**
     * @return The id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return The titles
     */
    public Titles getTitles()
    {
        return titles;
    }

    /**
     * @param titles The titles
     */
    public void setTitles(Titles titles)
    {
        this.titles = titles;
    }

    /**
     * @return The slug
     */
    public String getSlug()
    {
        return slug;
    }

    /**
     * @param slug The slug
     */
    public void setSlug(String slug)
    {
        this.slug = slug;
    }

    /**
     * @return The synopsis
     */
    public String getSynopsis()
    {
        return synopsis;
    }

    /**
     * @param synopsis The synopsis
     */
    public void setSynopsis(String synopsis)
    {
        this.synopsis = synopsis;
    }

    /**
     * @return The startedAiringDate
     */
    public String getStartedAiringDate()
    {
        return startedAiringDate;
    }

    /**
     * @param startedAiringDate The started_airing_date
     */
    public void setStartedAiringDate(String startedAiringDate)
    {
        this.startedAiringDate = startedAiringDate;
    }

    /**
     * @return The finishedAiringDate
     */
    public String getFinishedAiringDate()
    {
        return finishedAiringDate;
    }

    /**
     * @param finishedAiringDate The finished_airing_date
     */
    public void setFinishedAiringDate(String finishedAiringDate)
    {
        this.finishedAiringDate = finishedAiringDate;
    }

    /**
     * @return The youtubeVideoId
     */
    public String getYoutubeVideoId()
    {
        return youtubeVideoId;
    }

    /**
     * @param youtubeVideoId The youtube_video_id
     */
    public void setYoutubeVideoId(String youtubeVideoId)
    {
        this.youtubeVideoId = youtubeVideoId;
    }

    /**
     * @return The ageRating
     */
    public String getAgeRating()
    {
        return ageRating;
    }

    /**
     * @param ageRating The age_rating
     */
    public void setAgeRating(String ageRating)
    {
        this.ageRating = ageRating;
    }

    /**
     * @return The episodeCount
     */
    public int getEpisodeCount()
    {
        return episodeCount;
    }

    /**
     * @param episodeCount The episode_count
     */
    public void setEpisodeCount(int episodeCount)
    {
        this.episodeCount = episodeCount;
    }

    /**
     * @return The episodeLength
     */
    public int getEpisodeLength()
    {
        return episodeLength;
    }

    /**
     * @param episodeLength The episode_length
     */
    public void setEpisodeLength(int episodeLength)
    {
        this.episodeLength = episodeLength;
    }

    /**
     * @return The showType
     */
    public String getShowType()
    {
        return showType;
    }

    /**
     * @param showType The show_type
     */
    public void setShowType(String showType)
    {
        this.showType = showType;
    }

    /**
     * @return The posterImage
     */
    public String getPosterImage()
    {
        return posterImage;
    }

    /**
     * @param posterImage The poster_image
     */
    public void setPosterImage(String posterImage)
    {
        this.posterImage = posterImage;
    }

    /**
     * @return The coverImage
     */
    public String getCoverImage()
    {
        return coverImage;
    }

    /**
     * @param coverImage The cover_image
     */
    public void setCoverImage(String coverImage)
    {
        this.coverImage = coverImage;
    }

    /**
     * @return The communityRating
     */
    public double getCommunityRating()
    {
        return communityRating;
    }

    /**
     * @param communityRating The community_rating
     */
    public void setCommunityRating(double communityRating)
    {
        this.communityRating = communityRating;
    }

    /**
     * @return The genres
     */
    public List<String> getGenres()
    {
        return genres;
    }

    /**
     * @param genres The genres
     */
    public void setGenres(List<String> genres)
    {
        this.genres = genres;
    }

    /**
     * @return The producers
     */
    public List<String> getProducers()
    {
        return producers;
    }

    /**
     * @param producers The producers
     */
    public void setProducers(List<String> producers)
    {
        this.producers = producers;
    }

    /**
     * @return The bayesianRating
     */
    public double getBayesianRating()
    {
        return bayesianRating;
    }

    /**
     * @param bayesianRating The bayesian_rating
     */
    public void setBayesianRating(double bayesianRating)
    {
        this.bayesianRating = bayesianRating;
    }

    /**
     * @return The links
     */
    public Links getLinks()
    {
        return links;
    }

    /**
     * @param links The links
     */
    public void setLinks(Links links)
    {
        this.links = links;
    }

}
