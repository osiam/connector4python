package org.osiam.ng.resourceserver.bridges;

import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;


/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 06.05.13
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
public class NameFieldsClassBridge implements FieldBridge {

    @Override
    public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
