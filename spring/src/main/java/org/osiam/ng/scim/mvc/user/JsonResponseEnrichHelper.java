package org.osiam.ng.scim.mvc.user;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
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

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode;
        ObjectNode objectNode = objectMapper.convertValue(scimSearchResult.getResult(), ObjectNode.class);

        Set<String> attributes = (Set<String>) parameterMap.get("attributes");
        while (objectNode.getFieldNames().hasNext()) {
            String test = objectNode.getFieldNames().next();
            if(!attributes.contains(test)) {
                attributes.remove(test);
            }
        }

        objectNode.remove(attributes);

        rootNode = objectMapper.createObjectNode();
        rootNode.put("totalResults", scimSearchResult.getTotalResult());
        rootNode.put("itemsPerPage", (int)parameterMap.get("count"));
        rootNode.put("startIndex", (int)parameterMap.get("startIndex"));
        rootNode.put("schemas", schema);
        rootNode.put("Resources", objectNode);

        return rootNode.toString();
    }
}
