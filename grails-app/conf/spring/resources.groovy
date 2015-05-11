import demo.marshallers.*
import grails.converters.JSON
import org.grails.web.converters.configuration.ObjectMarshallerRegisterer

beans = {
    personJsonMarshaller(ObjectMarshallerRegisterer) {
        marshaller = new PersonMarshallerJson()
        converterClass = JSON
        priority = 1
    }
}
