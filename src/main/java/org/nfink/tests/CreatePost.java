package org.nfink.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.nfink.model.Post;
import org.nfink.service.PostsClient;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreatePost {
    public static Post validBody_CreatesPost(PostsClient postsClient, Post body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post(postsClient.getPostUrl())
        .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo(body.getTitle()))
                .body("body", equalTo(body.getBody()))
                .body("userId", equalTo(body.getUserId()))
        .extract()
                .as(Post.class);
    }

    public static void incorrectContentType_Returns415(PostsClient postsClient, Post body) throws IOException {
        // We have to convert to a json string since we can't rely on rest-assured serialization - it uses the content type to determine how.
        String bodyJson = new ObjectMapper().writeValueAsString(body);

        given()
                .contentType(ContentType.TEXT)
                .body(bodyJson)
        .when()
                .post(postsClient.getPostUrl())
        .then()
                .statusCode(415);
    }

    public static void missingUserId_Returns400(PostsClient postsClient, Post body) {
        given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post(postsClient.getPostUrl())
        .then()
                .statusCode(400);
    }

}
