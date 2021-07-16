package org.nfink.service;

public class CommentsClient {
    private static final String commentsPath = "/comments";

    private static final String postCommentsPath = "/posts/%s/comments";

    private String baseUrl;

    public CommentsClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static String getGetPath() {
        return commentsPath;
    }

    public static String getGetPath(String postId) {
        return String.format(postCommentsPath, postId);
    }

    public static String getGetByParameterPath(String postId) {
        return commentsPath + "?postId=" + postId;
    }

    public static String getPostPath(String postId) {
        return String.format(postCommentsPath, postId);
    }

    public String getGetUrl() {
        return baseUrl + getGetPath();
    }

    public String getGetUrl(String postId) {
        return baseUrl + getGetPath(postId);
    }

    public String getGetByParameterUrl(String postId) {
        return baseUrl + getGetByParameterPath(postId);
    }

    public String getPostUrl(String postId) {
        return baseUrl + getPostPath(postId);
    }
}
