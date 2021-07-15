package org.nfink.rule;

import org.junit.rules.ExternalResource;
import org.nfink.Config;
import org.nfink.service.PostsClient;

public class PostsClientRule extends ExternalResource {
    private final PostsClient postsClient;

    public PostsClientRule() {
        postsClient = new PostsClient(Config.getProperty("jsonplaceholder.url"));
    }

    public PostsClient getPostsClient() {
        return postsClient;
    }
}
