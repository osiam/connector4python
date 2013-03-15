package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
class X509CertificateEntitySpec extends Specification {

    X509CertificateEntity certificateEntity = new X509CertificateEntity()


    def "setter and getter for the Id should be present"() {
        when:
        certificateEntity.setId(123456)

        then:
        certificateEntity.getId() == 123456
    }

    def "setter and getter for the value should be present"(){
        when:
        certificateEntity.setValue("someValue")

        then:
        certificateEntity.getValue() == "someValue"
    }
}