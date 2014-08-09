package com.ofg.blog.controller
import com.ofg.blog.controller.topic.extractor.PropagationWorker
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.constraints.NotNull
import java.util.concurrent.Callable

import static org.springframework.web.bind.annotation.RequestMethod.PUT

@Slf4j
@RestController
@RequestMapping('/api')
@Api(value = "pairId", description = "Collects topics from blog data and propagates them to Collerators")
class PairIdController {

    @Autowired private PropagationWorker propagationWorker

    @RequestMapping(
            value = '{pairId}',
            method = PUT,
            consumes = 'application/vnd.com.ofg.blog-topics-analyzer.v1+json',
            produces = 'application/vnd.com.ofg.blog-topics-analyzer.v1+json')
    @ApiOperation(value = "Async collecting and propagating of blog data for a given pairId",
            notes = "This will handle topic extracting and pass results to Correlators")
    Callable<Void> getTopicsFromBlogData(@PathVariable @NotNull long pairId, @RequestBody @NotNull String blogData) {
        propagationWorker.collectAndPropagate(pairId, blogData)
    }

}
