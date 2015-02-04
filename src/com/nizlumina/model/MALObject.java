package com.nizlumina.model;

/**
 * MAL Object as returned by MAL
 */
public class MALObject
{
    //As returned by the search API
    public static final String IDENTIFIER = "anime";
    public static final String ENTRY_ARRAY_IDENTIFIER = "entry";
    private int id;
    private String endDate;
    private String title;
    private String status;
    private float score;
    private String image;
    private String synopsis;
    private String synonyms;
    private String type;
    private String startDate;
    private int episodes;
    private String english;

    public MALObject() {}

    public MALObject(int id, String endDate, String title, String status, float score, String image, String synopsis, String synonyms, String type, String startDate, int episodes, String english)
    {
        this.id = id;
        this.endDate = endDate;
        this.title = title;
        this.status = status;
        this.score = score;
        this.image = image;
        this.synopsis = synopsis;
        this.synonyms = synonyms;
        this.type = type;
        this.startDate = startDate;
        this.episodes = episodes;
        this.english = english;
    }

    public int getId()
    {
        return id;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public String getTitle()
    {
        return title;
    }

    public String getStatus()
    {
        return status;
    }

    public float getScore()
    {
        return score;
    }

    public String getImage()
    {
        return image;
    }

    public String getSynopsis()
    {
        return synopsis;
    }

    public String getSynonyms()
    {
        return synonyms;
    }

    public String getType()
    {
        return type;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public int getEpisodes()
    {
        return episodes;
    }

    public String getEnglish()
    {
        return english;
    }

    public static class Builder
    {

        private int id;
        private String endDate;
        private String title;
        private String status;
        private float score;
        private String image;
        private String synopsis;
        private String synonyms;
        private String type;
        private String startDate;
        private int episodes;
        private String english;

        public Builder setId(int id)
        {
            this.id = id;
            return this;
        }

        public Builder setEndDate(String endDate)
        {
            this.endDate = endDate;
            return this;
        }

        public Builder setTitle(String title)
        {
            this.title = title;
            return this;
        }

        public Builder setStatus(String status)
        {
            this.status = status;
            return this;
        }

        public Builder setScore(float score)
        {
            this.score = score;
            return this;
        }

        public Builder setImage(String image)
        {
            this.image = image;
            return this;
        }

        public Builder setSynopsis(String synopsis)
        {
            this.synopsis = synopsis;
            return this;
        }

        public Builder setSynonyms(String synonyms)
        {
            this.synonyms = synonyms;
            return this;
        }

        public Builder setType(String type)
        {
            this.type = type;
            return this;
        }

        public Builder setStartDate(String startDate)
        {
            this.startDate = startDate;
            return this;
        }

        public Builder setEpisodes(int episodes)
        {
            this.episodes = episodes;
            return this;
        }

        public Builder setEnglish(String english)
        {
            this.english = english;
            return this;
        }

        public MALObject createMALObject()
        {
            return new MALObject(id, endDate, title, status, score, image, synopsis, synonyms, type, startDate, episodes, english);
        }
    }
}
