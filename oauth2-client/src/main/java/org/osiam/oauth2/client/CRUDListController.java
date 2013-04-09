/*
 * Copyright (C) 2013 tarent AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.osiam.oauth2.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scim.schema.v2.MultiValuedAttribute;
import scim.schema.v2.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CRUDListController {
    public enum KnownMultiValueAttributeLists {
        //        ADDRESS(new User.Addresses().getAddress()),
        PHONE(new User.PhoneNumbers()),
        PHOTO(new User.Photos()),
        EMAIL(new User.Emails()),
        IM(new User.Ims()),
        ENTITLEMENT(new User.Entitlements()),
        ROLE(new User.Roles()),
        X509(new User.X509Certificates()),
        GROUP(new User.Groups());

        private final User.ContainsListOfMultiValue set;

        private static Map<String, KnownMultiValueAttributeLists> fromString = new HashMap<>();

        static {
            for (KnownMultiValueAttributeLists k : values()) {
                fromString.put(k.name().toLowerCase(), k);
            }
        }


        private KnownMultiValueAttributeLists(User.ContainsListOfMultiValue set) {
            this.set = set;
        }

        public <T extends User.ContainsListOfMultiValue> T getSet() {
            return (T) set;
        }
    }

    @RequestMapping("/createMultiValueAttribute")
    public String redirectTo(HttpServletRequest req, @RequestParam String used_for, @RequestParam String access_token) throws ServletException, IOException {
        req.setAttribute("access_token", access_token);
        req.setAttribute("used_for", used_for);
        if ("addresses".equals(used_for)) {
            return "create_address";
        }
        return "create_multivalueattribute";
    }

    @RequestMapping("/addListAttribute")
    public String addListAttribute(HttpServletRequest req) throws ServletException, IOException {
        String access_token = req.getParameter("access_token");
        req.setAttribute("access_token", access_token);
        String used_for = req.getParameter("used_for");
        req.setAttribute("used_for", used_for);
        KnownMultiValueAttributeLists k = KnownMultiValueAttributeLists.fromString.get(used_for);
        if (k != null) {
            MultiValuedAttribute attribute =
                    new MultiValuedAttribute.Builder().setDisplay(req.getParameter("display")).setOperation(
                            Boolean.valueOf(req.getParameter("delete")) == true ? "delete" :
                                    null).setPrimary(Boolean.valueOf(req.getParameter("primary"))).setType(req.getParameter("type")).setValue(req.getParameter("value")).build();
            k.set.values().add(attribute);
        }

        return "create_update_user";
    }


}
