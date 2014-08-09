package com.ofg.blog.controller.topic.extractor

import groovy.transform.ToString

@ToString
class BlogWithTopics {
    final Set<String> topics
    final String blogId

    BlogWithTopics(Set<String> topics, String blogId) {
        this.topics = topics
        this.blogId = blogId
    }
}
