package org.nfink.tests;

import io.restassured.common.mapper.TypeRef;
import org.nfink.model.Comment;
import org.nfink.service.CommentsClient;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class GetComments {
    public static void returnsListOfComments(CommentsClient commentsClient) {
        when()
                .get(commentsClient.getGetUrl())
        .then()
                .statusCode(200)
        .extract()
                .as(new TypeRef<List<Comment>>() {});
    }

    public static void returnsExistingComment(CommentsClient commentsClient, Comment comment) throws IOException {
        when()
                .get(commentsClient.getGetUrl())
        .then()
                .statusCode(200)
                .body(String.format("find { it.id == %s }", comment.getId()), equalTo(comment.toJsonObject()));
    }

    public static void validPostId_ReturnsListOfComments(CommentsClient commentsClient, Integer postId) {
        when()
                .get(commentsClient.getGetUrl(postId.toString()))
        .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<Comment>>() {});
    }

    public static void validPostId_ReturnsExistingComment(CommentsClient commentsClient, Comment comment) throws IOException {
        when()
                .get(commentsClient.getGetUrl(comment.getPostId().toString()))
        .then()
                .statusCode(200)
                .body(String.format("find { it.id == %s }", comment.getId()), equalTo(comment.toJsonObject()));
    }

    public static void postDoesNotExist_Returns404(CommentsClient commentsClient, Integer postId) {
        when()
                .get(commentsClient.getGetUrl(postId.toString()))
        .then()
                .statusCode(404);
    }

    public static void postDoesNotExist_WithParameter_ReturnsEmptyList(CommentsClient commentsClient, Integer postId) {
        when()
                .get(commentsClient.getGetByParameterUrl(postId.toString()))
        .then()
                .statusCode(200)
                .body("$", equalTo(Collections.emptyList()));
    }
}
