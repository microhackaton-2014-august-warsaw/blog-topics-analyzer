package com.ofg.blog.controller.topic.extractor
import com.ofg.infrastructure.discovery.ServiceResolver
import com.ofg.infrastructure.web.resttemplate.RestTemplate
import com.ofg.blog.controller.topic.Place
import com.ofg.blog.controller.topic.PlacesJsonBuilder
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@TypeChecked
@Slf4j
@Component
class PlacePropagatingWorker implements PropagationWorker {

    public static final String COLLERATOR_DEPENDENCY_NAME = 'collerator'
    
    private final PlacesJsonBuilder placesJsonBuilder
    private final ServiceResolver serviceResolver
    private final RestTemplate restTemplate
    

    @Autowired
    PlacePropagatingWorker(PlacesJsonBuilder placesJsonBuilder,
                           ServiceResolver serviceResolver,
                           RestTemplate restTemplate) {
        this.placesJsonBuilder = placesJsonBuilder
        this.serviceResolver = serviceResolver
        this.restTemplate = restTemplate
    }

    @Override
    void collectAndPropagate(long pairId, String blogData) {
//        Map<String, Optional<Place>> extractedPlaces = placesExtractor.extractPlacesFrom(blogData)
        String jsonToPropagate = "";//placesJsonBuilder.buildPlacesJson(pairId, extractedPlaces)
        restTemplate.postForObject("${serviceResolver.getUrl(COLLERATOR_DEPENDENCY_NAME).get()}/$pairId", jsonToPropagate, String)
        log.debug("Sent json [$jsonToPropagate] to collerator")
    }
}
