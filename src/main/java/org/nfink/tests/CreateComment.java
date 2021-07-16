package org.nfink.tests;

import io.restassured.http.ContentType;
import org.nfink.model.Comment;
import org.nfink.service.CommentsClient;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateComment {
    public static Comment validBody_CreatesComment(CommentsClient commentsClient, Comment body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post(commentsClient.getPostUrl(body.getPostId().toString()))
        .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("postId", equalTo(body.getPostId()))
                .body("name", equalTo(body.getName()))
                .body("email", equalTo(body.getEmail()))
                .body("body", equalTo(body.getBody()))
        .extract()
                .as(Comment.class);
    }

    public static void postDoesNotExist_Returns404(CommentsClient commentsClient, Comment body) {
        given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post(commentsClient.getPostUrl(body.getPostId().toString()))
        .then()
                .statusCode(404);
    }
}
