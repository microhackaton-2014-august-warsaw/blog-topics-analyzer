package com.ofg.blog.controller.topic

import spock.lang.Specification

class TopicsJsonBuilderTest extends Specification {

    def "should build json"() {
        given:
            TopicsJsonBuilder jsonBuilder = new TopicsJsonBuilder()
        when:
            String json = jsonBuilder.buildTopicsJson(1, 'http://tomaszdziurko.pl', ['Java', 'Scala'] as Set)
        then:
            assert json == '''
                {
                    "pairId" : 1,
                    "analyzerType" : "blog"
                    "analyzerId" : "http://tomaszdziurko.pl"
                    "topics" : [
                       {"name" : Java},{"name" : Scala}
                    ]
                }'''
    }
}
