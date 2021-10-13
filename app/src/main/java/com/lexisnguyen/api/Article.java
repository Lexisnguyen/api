package com.lexisnguyen.api;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    String _id;
    String title;
    String content;
    String imageUrl;
    String groupName;
    String createAt;
    String updateAt;
    int __v;

    public Article(String _id, String title, String content, String imageUrl, String groupName, String createAt,
                   String updateAt, int __v) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.groupName = groupName;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.__v = __v;
    }

    public Article(String title, String content, String imageUrl, String groupName) {
        this._id = null;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.groupName = groupName;
        this.createAt = null;
        this.updateAt = null;
        this.__v = 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "Article{" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", groupName='" + groupName + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", __v=" + __v +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}