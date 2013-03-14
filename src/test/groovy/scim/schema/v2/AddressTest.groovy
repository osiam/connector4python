package scim.schema.v2

import spock.lang.Specification

class AddressTest extends Specification {
    def "should generate an address from builder"() {

        given:
        def builder = new Address.Builder().setCountry("country")
                .setFormatted("formatted")
                .setLocality("locality")
                .setPostalCode("code")
                .setRegion("region")
                .setStreetAddress("street")

        when:
        def address = builder.build()
        then:

        address.country == builder.country
        address.formatted == builder.formatted
        address.locality == builder.locality
        address.postalCode == builder.postalCode
        address.region == builder.region
        address.streetAddress == builder.streetAddress
    }


}
