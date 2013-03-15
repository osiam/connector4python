package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
class PhotoEntitySpec extends Specification {

    PhotoEntity photoEntity = new PhotoEntity()

    def "setter and getter for the Id should be present"() {
        when:
        photoEntity.setId(123456)

        then:
        photoEntity.getId() == 123456
    }

    def "setter and getter for the value should be present"() {
        when:
        photoEntity.setValue("https://photos.example.com/profilephoto/72930000000Ccne/T")

        then:
        photoEntity.getValue() == "https://photos.example.com/profilephoto/72930000000Ccne/T"
    }

    def "setter and getter for the type should be present"() {
        when:
        photoEntity.setType("thumbnail")

        then:
        photoEntity.getType() == "thumbnail"
    }
}