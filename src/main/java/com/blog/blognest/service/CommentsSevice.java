package com.blog.blognest.service;

import com.blog.blognest.entity.Comments;

import java.util.List;

public interface CommentsSevice {

    Comments createComment(Comments comments);

    Comments editComment(Comments comments, Long id);

    String deleteComment(Long id);

    List<Comments> findByBlogId(Long blogId);
}
