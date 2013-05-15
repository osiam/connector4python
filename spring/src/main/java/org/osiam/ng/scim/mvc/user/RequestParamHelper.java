package org.osiam.ng.scim.mvc.user;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.05.13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public class RequestParamHelper {

    public Map<String,Object> getRequestParameterValues(HttpServletRequest request) {

        Map<String,Object> parameterMap = new HashMap<>();

        parameterMap.put("filter", request.getParameter("filter"));
        parameterMap.put("sortBy", request.getParameter("sortBy") != null ? request.getParameter("sortBy") : "internal_id");
        parameterMap.put("sortOrder", request.getParameter("sortOrder") != null ? request.getParameter("sortOrder") : "ascending");
        parameterMap.put("startIndex", request.getParameter("startIndex") != null ? Integer.parseInt(request.getParameter("startIndex")) : 1);

        int count = request.getParameter("count") != null ? Integer.parseInt(request.getParameter("count")): 100;
        if (count <= 0)
            throw new RuntimeException("Negative count values are not allowed");

        parameterMap.put("count", count);

        return parameterMap;
    }
}
