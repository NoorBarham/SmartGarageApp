package com.example.lastgarageapp.itemClasses;

public class newsItem {
    private String textName, textNews, textHour,newsId;

    public newsItem(String textName, String textNews, String textHour,String newsId) {
        this.textName = textName;
        this.textNews = textNews;
        this.textHour = textHour;
        this.newsId=newsId;

    }
    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public String getTextNews() {
        return textNews;
    }

    public void setTextNews(String textMessage) {
        this.textNews = textNews;
    }

    public String getTextHour() {
        return textHour;
    }

    public void setTextHour(String textHour) {
        this.textHour = textHour;
    }
    public String getNewsId() {

        return newsId;
    }

    public void setNewsId(String newsId) {

        this.newsId = newsId;
    }
}
