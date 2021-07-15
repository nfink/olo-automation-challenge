package org.nfink.tests;

import io.restassured.common.mapper.TypeRef;
import org.nfink.model.Post;
import org.nfink.service.PostsClient;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.List;

public class GetPosts {
    public static void returnsListOfPosts(PostsClient postsClient) {
        when()
                .get(postsClient.getGetUrl())
        .then()
                .statusCode(200)
        .extract()
                .as(new TypeRef<List<Post>>() {});
    }

    public static void returnsExistingPost(PostsClient postsClient, Post post) throws IOException {
        when()
                .get(postsClient.getGetUrl())
        .then()
                .statusCode(200)
                .body(String.format("find { it.id == %s }", post.getId()), equalTo(post.toJsonObject()));
    }
}
