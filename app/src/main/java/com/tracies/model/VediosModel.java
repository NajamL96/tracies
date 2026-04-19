package com.tracies.model;

public class VediosModel {

    String thumbnailUrl;
    String videoTitle;
    String videoUrl;
    String videoID;

    public VediosModel(String videoID) {
        this.videoID = videoID;
    }
    public VediosModel(){

    }

    public VediosModel(String thumbnailUrl, String videoTitle, String videoUrl) {
        this.thumbnailUrl = thumbnailUrl;
        this.videoTitle = videoTitle;
        this.videoUrl = videoUrl;

    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}


