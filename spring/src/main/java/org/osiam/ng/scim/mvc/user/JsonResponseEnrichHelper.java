package org.osiam.ng.scim.mvc.user;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.node.ObjectNode;
import org.osiam.ng.resourceserver.dao.SCIMSearchResult;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.05.13
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
public class JsonResponseEnrichHelper {


   public String getJsonFromSearchResult(SCIMSearchResult resultList, Map<String, Object> parameterMap, Set<String> schemas) {
        String schema = "";
        if (schemas != null && schemas.iterator().hasNext()) {
            schema = schemas.iterator().next();
        }
        return getJsonResponseWithAdditionalFields(resultList, parameterMap, schema);
    }

    private String getJsonResponseWithAdditionalFields(SCIMSearchResult scimSearchResult, Map<String, Object> parameterMap, String schema) {


        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializationConfig().addMixInAnnotations(
                Object.class, PropertyFilterMixIn.class);

        String[] ignorableFieldNames = (String[]) parameterMap.get("attributes");

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("filter properties by name",
                        SimpleBeanPropertyFilter.filterOutAllExcept(
                                ignorableFieldNames));
        ObjectWriter writer = mapper.writer(filters);

        try {
            String jsonString = writer.writeValueAsString(scimSearchResult.getResult());
            JsonNode jsonNode = mapper.readTree(jsonString);
            ObjectNode rootNode = mapper.createObjectNode();
            rootNode.put("totalResults", scimSearchResult.getTotalResult());
            rootNode.put("itemsPerPage", (int)parameterMap.get("count"));
            rootNode.put("startIndex", (int)parameterMap.get("startIndex"));
            rootNode.put("schemas", schema);
            rootNode.put("Resources", jsonNode);

            return rootNode.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
