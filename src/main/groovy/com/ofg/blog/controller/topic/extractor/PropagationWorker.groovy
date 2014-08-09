package com.ofg.blog.controller.topic.extractor

interface PropagationWorker {
    void collectAndPropagate(long pairId, String tweets)
}