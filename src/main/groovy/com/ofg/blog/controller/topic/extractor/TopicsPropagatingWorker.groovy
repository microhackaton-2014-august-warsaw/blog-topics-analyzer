package com.ofg.blog.controller.topic.extractor

import com.ofg.infrastructure.discovery.ServiceResolver
import com.ofg.infrastructure.web.resttemplate.RestTemplate
import com.ofg.blog.controller.topic.TopicsJsonBuilder
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@TypeChecked
@Slf4j
@Component
class TopicsPropagatingWorker implements PropagationWorker {

    public static final String COLLERATOR_DEPENDENCY_NAME = 'collerator'

    private final TopicsExtractor topicsExtractor
    private final TopicsJsonBuilder topicsJsonBuilder
    private final ServiceResolver serviceResolver
    private final RestTemplate restTemplate

    @Autowired
    TopicsPropagatingWorker(
            TopicsExtractor topicsExtractor,
            TopicsJsonBuilder topicsJsonBuilder,
            ServiceResolver serviceResolver,
            RestTemplate restTemplate) {
        this.topicsExtractor = topicsExtractor
        this.topicsJsonBuilder = topicsJsonBuilder
        this.serviceResolver = serviceResolver
        this.restTemplate = restTemplate
    }

    @Override
    void collectAndPropagate(long pairId, String blogData) {
        BlogWithTopics extractedData = topicsExtractor.extractFrom(blogData)
        String jsonToPropagate = topicsJsonBuilder.buildTopicsJson(pairId, extractedData.blogId, extractedData.topics)
        restTemplate.postForObject("${serviceResolver.getUrl(COLLERATOR_DEPENDENCY_NAME).get()}/correlations",
                jsonToPropagate, String)
        log.debug("Sent json [$jsonToPropagate] to collerator")
    }
}
