package org.nfink.tests;

import io.restassured.http.ContentType;
import org.nfink.model.Post;
import org.nfink.service.PostsClient;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdatePost {
    public static Post validBody_UpdatesPost(PostsClient postsClient, Post body) throws IOException {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .put(postsClient.getPutUrl(body.getId().toString()))
        .then()
                .statusCode(200)
                .body("$", equalTo(body.toJsonObject()))
        .extract()
                .as(Post.class);
    }

    public static void postDoesNotExist_Returns409(PostsClient postsClient, Post body) {
        given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .put(postsClient.getPutUrl(body.getId().toString()))
        .then()
                .statusCode(409);
    }
}
