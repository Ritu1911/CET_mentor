package com.cet.alokrabi.allgovtjobs_ssc_rrb_mentor_tutor;

public class Video
{
  private String videoId;
  private String videoThumbnail;
  private String videoTitle;
  
  public Video() {}
  
  public Video(String paramString1, String paramString2, String paramString3)
  {
    this.videoTitle = paramString1;
    this.videoThumbnail = paramString2;
    this.videoId = paramString3;
  }
  
  public String getVideoId()
  {
    return this.videoId;
  }
  
  public String getVideoThumbnail()
  {
    return this.videoThumbnail;
  }
  
  public String getVideoTitle()
  {
    return this.videoTitle;
  }
  
  public void setVideoId(String paramString)
  {
    this.videoId = paramString;
  }
  
  public void setVideoThumbnail(String paramString)
  {
    this.videoThumbnail = paramString;
  }
  
  public void setVideoTitle(String paramString)
  {
    this.videoTitle = paramString;
  }
  
  public String toString()
  {
    return "Video [videoTitle=" + this.videoTitle + ", videoThumbnail=" + this.videoThumbnail + ", videoId=" + this.videoId + "]";
  }
}



/* Location:           C:\Users\Kamala\Desktop\GetSourceCode\dex2jar-0.0.9.15\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.ace.knowledge.videos.Video

 * JD-Core Version:    0.7.0.1

 */