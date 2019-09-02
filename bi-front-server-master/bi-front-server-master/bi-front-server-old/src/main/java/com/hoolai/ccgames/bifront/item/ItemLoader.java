package com.hoolai.ccgames.bifront.item;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ItemLoader extends DefaultHandler {

    private Long key = null;
    private String name = null;
    private String tagName = null;
    private StringBuilder data = null;
    private ItemRegistry registry = null;

    public ItemLoader( ItemRegistry reg ) {
        this.registry = reg;
    }

    public void startElement( String uri, String localName, String qName,
                              Attributes attributes ) throws SAXException {
        this.tagName = qName;
        data = new StringBuilder();
    }

    @Override
    public void endElement( String uri, String localName, String qName )
            throws SAXException {
        if( qName.equals( "Item" ) ) {
            if( registry.put( key, name ) != null ) {
                throw new RuntimeException( "duplicate for item " + key );
            }
        }
        this.tagName = null;
    }

    @Override
    public void characters( char[] ch, int start, int length )
            throws SAXException {
        if( this.tagName != null ) {
            data.append( ch, start, length );
            if( this.tagName.equals( "ID" ) ) {
                this.key = Long.parseLong( data.toString().trim() );
            }
            else if( this.tagName.equals( "Name" ) ) {
                this.name = data.toString().trim();
            }
            data.setLength( 0 );
        }
    }
}
