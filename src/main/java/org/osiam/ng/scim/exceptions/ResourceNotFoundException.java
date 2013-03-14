package org.osiam.ng.scim.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends IllegalArgumentException {
    public ResourceNotFoundException(String s) {
        super(s);
    }
}
