package org.osiam.ng.test

import org.osiam.ng.test.actors.ClientTestActor

import spock.lang.Specification

/**
 * Abstract super specification for all system tests.
 *
 * @author Michael Kutz, tarent Solutions GmbH, 28.02.2013
 */
abstract class AbstractSystemSpec extends Specification {

    ClientTestActor client = new ClientTestActor()

    def setup() {
    }

    def clearnup() {
    }
}
