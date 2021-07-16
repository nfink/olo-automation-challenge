package org.nfink.tests.shared;

import org.nfink.model.Post;
import org.nfink.service.PostsClient;

import java.io.IOException;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class GetPost {
    public static void validId_ReturnsPost(PostsClient postsClient, Post post) throws IOException {
        when()
                .get(postsClient.getGetUrl(post.getId().toString()))
        .then()
                .statusCode(200)
                .body("$", equalTo(post.toJsonObject()));
    }

    public static void noData_Returns404(PostsClient postsClient, String noDataId) {
        when()
                .get(postsClient.getGetUrl(noDataId))
        .then()
                .statusCode(404);
    }

    public static void invalidId_Returns404(PostsClient postsClient, String invalidId) {
        when()
                .get(postsClient.getGetUrl(invalidId))
        .then()
                .statusCode(404);
    }
}
