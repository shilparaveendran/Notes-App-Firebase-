package com.example.firebasedemo;


public class Notes {
    String Title,Content;


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "Title='" + Title + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }
}
