package org.nfink.service;

import io.restassured.response.Response;
import io.restassured.specification.RequestSenderOptions;
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

    public String getGetUrl() {
        return this.baseUrl + path;
    }

    public String getGetUrl(String id) {
        return this.baseUrl + path + "/" + id;
    }

    public String getPostUrl() {
        return this.baseUrl + path;
    }

    public String getPutUrl(String id) {
        return this.baseUrl + path + "/" + id;
    }

    public String getDeleteUrl(String id) {
        return this.baseUrl + path + "/" + id;
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
        delete(getDeleteUrl(post.getId().toString()))
                .then()
                    .statusCode(200);
    }
}
