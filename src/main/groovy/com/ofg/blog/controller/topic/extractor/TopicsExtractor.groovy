package com.ofg.blog.controller.topic.extractor

import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

@TypeChecked
@Slf4j
@Component
class TopicsExtractor {

     BlogWithTopics extractFrom(String blogData) {
         return new BlogWithTopics([] as Set, "")
     }
}
