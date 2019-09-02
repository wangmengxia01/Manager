/**
 *
 */
package com.hoolai.ccgames.bifront.item;

import com.hoolai.ccgames.skeleton.utils.PropUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.util.Map;


public class ItemRegistry {

    public static final Logger logger = LoggerFactory.getLogger( ItemRegistry.class );

    private Map< Long, String > mapping = new HashMap< Long, String >();

    public String get( long itemId ) {
        return mapping.get( itemId );
    }

    public String getOrId( long itemId ) {
        String name = mapping.get( itemId );
        return name == null ? String.valueOf( itemId ) : name;
    }

    public String put( Long itemId, String itemName ) {
        return mapping.put( itemId, itemName );
    }

    public void init( String filePath ) throws Exception {

        SAXParserFactory.newInstance().newSAXParser()
                .parse( PropUtil.getInputStream( filePath ), new ItemLoader( this ) );

        logger.debug( "Load {} items", mapping.size() );
    }

    public static void main( String[] args ) {
        String a = "(12,3,";
        System.out.println( a.replaceFirst( ",$", ")" ) );
    }

}
