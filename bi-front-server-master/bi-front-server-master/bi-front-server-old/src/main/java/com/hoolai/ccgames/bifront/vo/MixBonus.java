package com.hoolai.ccgames.bifront.vo;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hoolai on 2016/8/29.
 */
public class MixBonus {
    public long gold = 0;
    public long exp = 0;
    public long hp = 0;
    public Map< Long, Long > items = new TreeMap<>();


    public static void main( String[] args ) {
        MixBonus bonus = new MixBonus().parseFrom( "-1:2;3:5;" )
                .mergeFromString( "-1:2;3:5" )
                .addItem( 3, 4 )
                .addItem( 4, 4 );
        System.out.println( "gold:" + bonus.gold );
        System.out.println( "items:" + bonus.items.size() );
        System.out.println( bonus.toString() );
    }

    /**
     * @param s
     * @param splitter1 分割不同子项
     * @param splitter2 分割子项内部
     * @return
     */
    public static Map< Long, Long > splitMap( String s, String splitter1, String splitter2 ) {
        Map< Long, Long > rv = new TreeMap<>();
        if( s.contains( splitter1 ) ) {
            String[] s1 = s.split( splitter1 );
            for( String s2 : s1 ) {
                if( s2.contains( splitter2 ) ) {
                    String[] s3 = s2.split( splitter2 );
                    rv.put( Long.parseLong( s3[0] ), Long.parseLong( s3[1] ) );
                }
            }
        }
        else if( s.contains( splitter2 ) ) {
            String[] s3 = s.split( splitter2 );
            rv.put( Long.parseLong( s3[0] ), Long.parseLong( s3[1] ) );
        }
        return rv;
    }

    public static long getLong( Map< Long, Long > map, long key ) {
        Long val = map.get( key );
        return val == null ? 0L : val;
    }

    public long getItemCount( long itemId ) {
        return getLong( this.items, itemId );
    }

    public static void mergeMap( Map< Long, Long > from, Map< Long, Long > to ) {
        from.forEach( ( k, v ) -> to.compute( k, ( k2, v2 ) -> v2 == null ? v : v + v2 ) );
    }

    public MixBonus setGold( long gold ) {
        this.gold = gold;
        return this;
    }

    public MixBonus setExp( long exp ) {
        this.exp = exp;
        return this;
    }

    public MixBonus setHp( long hp ) {
        this.hp = hp;
        return this;
    }

    public MixBonus addItem( long itemId, long itemCount ) {
        this.items.compute( itemId, ( k, v ) -> v == null ? itemCount : v + itemCount );
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder( 64 );
        if( gold != 0 ) sb.append( "-1:" ).append( gold ).append( ';' );
        if( hp != 0 ) sb.append( "-2:" ).append( hp ).append( ';' );
        if( exp != 0 ) sb.append( "-3:" ).append( exp ).append( ';' );
        items.forEach( ( k, v ) -> {
            sb.append( k ).append( ":" ).append( v ).append( ';' );
        } );
        return sb.toString();
    }

    public MixBonus parseFrom( String s ) {
        Map< Long, Long > map = splitMap( s, ";", ":" );
        this.gold = getLong( map, -1L );
        this.hp = getLong( map, -2L );
        this.exp = getLong( map, -3L );
        map.forEach( ( k, v ) -> {
            if( k > 0 ) items.put( k, v );
        } );

        return this;
    }

    public MixBonus merge( MixBonus other ) {
        this.gold += other.gold;
        this.hp += other.hp;
        this.exp += other.exp;
        mergeMap( other.items, this.items );
        return this;
    }

    public MixBonus mergeFromString( String s ) {
        return merge( new MixBonus().parseFrom( s ) );
    }

}
