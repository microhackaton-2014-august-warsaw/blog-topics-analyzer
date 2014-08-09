package com.ofg.blog.controller.topic.extractor

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

@Slf4j
@Component
class TopicsExtractor {

    BlogWithTopics extractFrom(String blogData) {
        def data = new JsonSlurper().parseText(blogData)
        String blogId = data.rssUrl
        Set<String> topics = extractAndChooseKeywords(data.titles, data.posts)
        return new BlogWithTopics(topics, blogId)
    }

    Set<String> extractAndChooseKeywords(List<String> titles, List<String> posts) {
        Map<String, Integer> titlesRatings = extractKeywords(titles)
        Map<String, Integer> postsRatings = extractKeywords(posts)
        def newTitlesRatings = [:]
        titlesRatings.each {
            k,v -> newTitlesRatings[k] = v.size() * 5
        }
        def retMap = newTitlesRatings
        postsRatings.each { k,v -> retMap[k] = retMap[k] ? retMap[k] + v.size() : v.size() }
        def list = []
        retMap.each { k,v ->
            list += [[v, k]]
        }
        return list.sort{ a,b -> b[0] - a[0] }.take(5).collect { k -> k[1] }
    }

    Map<String, Integer> extractKeywords(List<String> strings) {
        def tokens = strings.join('#').toLowerCase().replaceAll('[^A-Za-z0-9]', '#').tokenize('#')
        return tokens.groupBy { it }
    }
}
