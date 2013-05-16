package org.osiam.ng.scim.mvc.user;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import scim.schema.v2.Group;
import scim.schema.v2.Resource;
import scim.schema.v2.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.05.13
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
public class JsonResponseEnrichHelper {

    public String getJsonUserResponseWithAdditionalFields(List<User> resultList, Map<String, Object> parameterMap) {
        String schema = "";
        if (resultList.size() != 0 && resultList.get(0).getSchemas() != null) {
            schema = (String)resultList.get(0).getSchemas().toArray()[0];
        }
        return getJsonResponseWithAdditionalFields(resultList, parameterMap, schema);
    }

    public String getJsonGroupResponseWithAdditionalFields(List<Group> resultList, Map<String, Object> parameterMap) {
        String schema = "";
        if (resultList.size() != 0 && resultList.get(0).getSchemas() != null) {
            schema = (String)resultList.get(0).getSchemas().toArray()[0];
        }
        return getJsonResponseWithAdditionalFields(resultList, parameterMap, schema);
    }

    public String getJsonRootResponseWithAdditionalFields(List<Resource> resultList, Map<String, Object> parameterMap) {
        String schema = "";
        if (resultList.size() != 0 && resultList.get(0).getSchemas() != null) {
            schema = (String)resultList.get(0).getSchemas().toArray()[0];
        }
        return getJsonResponseWithAdditionalFields(resultList, parameterMap, schema);
    }

    private String getJsonResponseWithAdditionalFields(List resultList, Map<String, Object> parameterMap, String schema) {

        String finalJson;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResultList = objectMapper.writeValueAsString(resultList);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode origNode = mapper.readTree(jsonResultList);

            ObjectNode rootNode = mapper.createObjectNode();
            rootNode.put("totalResults", 1000);
            rootNode.put("itemsPerPage", (int)parameterMap.get("count"));
            rootNode.put("startIndex", (int)parameterMap.get("startIndex"));
            rootNode.put("schemas", schema);
            rootNode.put("Resources", origNode);

            finalJson = rootNode.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return finalJson;
    }
}
