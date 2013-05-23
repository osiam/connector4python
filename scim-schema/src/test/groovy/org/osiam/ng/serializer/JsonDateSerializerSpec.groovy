package org.osiam.ng.serializer

import org.codehaus.jackson.JsonGenerator
import org.codehaus.jackson.map.SerializerProvider
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 23.05.13
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
class JsonDateSerializerSpec extends Specification {

    def jsonDateSerializer = new JsonDateSerializer()
    def jsonGeneratorMock = Mock(JsonGenerator)
    def serializerProviderMock = Mock(SerializerProvider)

    def "should serialize dates to ISO value"() {
        given:
        def date = new DateTime(2013, 5, 23, 11, 57, 00, DateTimeZone.forID("CET"))

        when:
        jsonDateSerializer.serialize(date, jsonGeneratorMock, serializerProviderMock)

        then:
        1 * jsonGeneratorMock.writeString(_)
    }
}
