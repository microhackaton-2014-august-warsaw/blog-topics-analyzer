package com.ofg.blog.controller.topic

import groovy.transform.ToString

@ToString
class Place {
    final PlaceDetails placeDetails
    final String placeResolutionOrigin

    Place(PlaceDetails placeDetails, String placeResolutionOrigin) {
        this.placeDetails = placeDetails
        this.placeResolutionOrigin = placeResolutionOrigin
    }

    @ToString
    static class PlaceDetails {
        final String name
        final String countryCode

        PlaceDetails(String name, String countryCode) {
            this.name = name
            this.countryCode = countryCode
        }
    }

}
