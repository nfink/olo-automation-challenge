package org.nfink.pact.util;

import au.com.dius.pact.consumer.dsl.*;
import org.nfink.model.Post;

public class PostUtil {
    public static DslPart buildBody(Post post) {
        PactDslJsonBody body = new PactDslJsonBody();
        if (post.getId() != null) {
            body = body.integerType("id", post.getId());
        }
        body = body.stringType("title", post.getTitle());
        body = body.stringType("body", post.getBody());
        if (post.getUserId() != null) {
            body = body.integerType("userId", post.getUserId());
        }
        return body;
    }

    public static DslPart buildArrayBody(Post post) {
        return PactDslJsonArray.arrayMinLike(1)
                .integerType("id", post.getId())
                .stringType("title", post.getTitle())
                .stringType("body", post.getBody())
                .integerType("userId", post.getUserId())
                .closeObject();
    }
}
