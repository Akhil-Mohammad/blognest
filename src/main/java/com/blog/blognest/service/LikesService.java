package com.blog.blognest.service;

import com.blog.blognest.entity.Likes;

public interface LikesService {

    Likes createLike(Likes likes);

    String deleteLike(Long id);

    Long getLikesCountByBlogId(Long blogId);
}
