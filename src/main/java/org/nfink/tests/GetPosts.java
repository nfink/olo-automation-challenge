package org.nfink.tests;

import io.restassured.common.mapper.TypeRef;
import org.junit.ClassRule;
import org.junit.Test;
import org.nfink.model.Post;
import org.nfink.rule.PostsClientRule;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.UUID;

public class GetPosts {
    @ClassRule
    public static PostsClientRule postsClientRule = new PostsClientRule();

    @Test
    public void returnsListOfPosts() {
        when()
                .get(postsClientRule.getPostsClient().getGetUrl())
        .then()
                .statusCode(200)
        .extract()
                .as(new TypeRef<List<Post>>() {});
    }

    @Test
    public void returnsExistingPost() {
        // Post post = new Post(null, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1);
        // Post createdPost = postsClientRule.getPostsClient().createPost(post);

        Post post = new Post(100, "at nam consequatur ea labore ea harum", "cupiditate quo est a modi nesciunt soluta\\nipsa voluptas error itaque dicta in\\nautem qui minus magnam et distinctio eum\\naccusamus ratione error aut", 10);

        when()
                .get(postsClientRule.getPostsClient().getGetUrl())
            .then()
                .statusCode(200)
                .body(String.format("find { it.id == %s && it.title == '%s' && it.body == '%s' && it.userId == %s }",
                            post.getId(), post.getTitle(), post.getBody(), post.getUserId()),
                        notNullValue());
    }
}