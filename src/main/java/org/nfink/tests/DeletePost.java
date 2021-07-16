package org.nfink.tests;

import org.nfink.service.PostsClient;

import static io.restassured.RestAssured.*;

public class DeletePost {
    public static void validId_returns200(PostsClient postsClient, String id) {
        when()
                .delete(postsClient.getDeleteUrl(id))
        .then()
                .statusCode(200);
    }

    public static void postDoesNotExist_Returns404(PostsClient postsClient, String id) {
        when()
                .delete(postsClient.getDeleteUrl(id))
        .then()
                .statusCode(404);
    }
}
