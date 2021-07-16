package org.nfink.pact.util;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import org.nfink.model.Comment;

public class CommentUtil {
    public static DslPart buildBody(Comment comment) {
        PactDslJsonBody body = new PactDslJsonBody();
        if (comment.getPostId() != null) {
            body = body.integerType("postId", comment.getPostId());
        }
        if (comment.getId() != null) {
            body = body.integerType("id", comment.getId());
        }
        body = body.stringType("name", comment.getName());
        body = body.stringType("email", comment.getEmail());
        body = body.stringType("body", comment.getBody());
        return body;
    }
}
