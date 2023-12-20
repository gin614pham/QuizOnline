package com.quiz.model.data;

import java.io.Serializable;

public class Option implements Serializable {

    private int id;
    private String content;

    public Option(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public Option() {
        this(-1, "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
