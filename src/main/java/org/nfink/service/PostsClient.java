package org.nfink.service;

import org.nfink.model.Post;

import static io.restassured.RestAssured.*;

public class PostsClient {
    private static final String path = "/posts";

    private String baseUrl;

    public PostsClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static String getPath() {
        return path;
    }

    public static String getGetPath() {
        return path;
    }

    public static String getPostPath() {
        return path;
    }

    public static String getPutPath(String id) {
        return path + "/" + id;
    }

    public static String getDeletePath(String id) {
        return path + "/" + id;
    }

    public String getGetUrl() {
        return this.baseUrl + getGetPath();
    }

    public String getGetUrl(String id) {
        return this.baseUrl + getGetPath() + "/" + id;
    }

    public String getPostUrl() {
        return this.baseUrl + getPostPath();
    }

    public String getPutUrl(String id) {
        return this.baseUrl + getPutPath(id);
    }

    public String getDeleteUrl(String id) {
        return this.baseUrl + getDeletePath(id);
    }

    public Post createPost(Post post) {
        return given()
                    .body(post)
                .when()
                    .post(getPostUrl())
                .then()
                    .statusCode(201)
                .extract()
                    .as(Post.class);
    }

    public Post updatePost(Post post) {
        return given()
                    .body(post)
                .when()
                    .put(getPutUrl(post.getId().toString()))
                .then()
                    .statusCode(200)
                .extract()
                    .as(Post.class);
    }

    public void deletePost(Post post) {
        delete(getDeleteUrl(post.getId().toString()));
    }
}
