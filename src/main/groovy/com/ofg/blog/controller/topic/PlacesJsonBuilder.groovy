package com.ofg.blog.controller.topic

import groovy.text.SimpleTemplateEngine
import groovy.transform.TypeChecked
import org.springframework.stereotype.Component

@TypeChecked
@Component
class PlacesJsonBuilder {

    String buildPlacesJson(long pairId, Map<String, Optional<Place>> places) {
        return """[
                       ${places.collect { String tweetId, Optional<Place> place ->
                            
                            return new SimpleTemplateEngine().createTemplate(JSON_RESPONSE_TEMPLATE)
                            .make([pairId: pairId,
                                   tweetId : tweetId,
                                   place: place])
                            .toString()
                        }.join(',')}
                   ]""".toString()
    }


    private static final String JSON_RESPONSE_TEMPLATE = '''
                {
                    "pair_id" : $pairId,
                    "tweet_id" : "$tweetId"
                    <% if (topic.present) { %>
                        ,"topic" :
                        {
                            "name":"${topic.get().placeDetails.name}",
                            "country_code": "${topic.get().placeDetails.countryCode}"
                        },
                        "probability" : "${topic.get().placeResolutionProbability}",
                        "origin" : "${topic.get().placeResolutionOrigin}"
                    <% } %>
                }
                '''

}
