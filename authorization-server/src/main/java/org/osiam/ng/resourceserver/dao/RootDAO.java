package org.osiam.ng.resourceserver.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scim.schema.v2.Resource;

import java.util.List;

@Repository
@Transactional
public class RootDAO <T extends Resource> extends GetInternalIdSkeleton{

    List<T> search(String filter){
        return null;
    }
}