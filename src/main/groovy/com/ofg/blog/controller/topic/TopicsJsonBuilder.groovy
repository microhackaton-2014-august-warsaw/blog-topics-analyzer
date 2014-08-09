package com.ofg.blog.controller.topic

import groovy.text.SimpleTemplateEngine
import groovy.transform.TypeChecked
import org.springframework.stereotype.Component

@TypeChecked
@Component
class TopicsJsonBuilder {

    String buildTopicsJson(long pairId, String blogId, Set<String> topics) {
        return """
                {
                    "pairId" : $pairId,
                    "analyzerType" : "blog"
                    "analyzerId" : "$blogId"
                    "topics" : [
                       ${topics.collect { String topic ->
                            return new SimpleTemplateEngine().createTemplate(JSON_TOPIC_TEMPLATE)
                            .make([topic: topic])
                            .toString()
                        }.join(",")}
                    ]
                }""".toString()
    }

    private static final String JSON_TOPIC_TEMPLATE = '''
                {
                    "name" : $topic
                }
                '''

}
