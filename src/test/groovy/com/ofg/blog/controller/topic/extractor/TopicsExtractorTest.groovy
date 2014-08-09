package com.ofg.blog.controller.topic.extractor

import spock.lang.Specification

class TopicsExtractorTest extends Specification {

    def 'should extract topics'() {
        given:
            TopicsExtractor topicsExtractor = new TopicsExtractor()
            String blogData = '''{
                "rssUrl" : "http://tomaszdziurko.pl",
                "pairId" : 1,
                "posts" : ["Java jest super, piszemy dalej...", " Scala jest lepsza. Java ssie."],
                "titles" : ["Co robic, jak zyc?", "Java, Scala i kamieni kupa"]
            }'''
        when:
            BlogWithTopics extracted = topicsExtractor.extractFrom(blogData)
        then:
            extracted.blogId == "http://tomaszdziurko.pl"
            extracted.topics.size() == 5
            extracted.topics.contains("java")
            extracted.topics.contains("scala")
            extracted.topics.contains("robic")
    }
}