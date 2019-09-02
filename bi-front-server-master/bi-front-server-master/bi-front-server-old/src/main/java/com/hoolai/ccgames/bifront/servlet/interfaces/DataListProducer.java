package com.hoolai.ccgames.bifront.servlet.interfaces;

import com.hoolai.ccgames.bifront.net.BiClient;

import java.util.List;

/**
 * Created by hoolai on 2016/7/12.
 */

@FunctionalInterface
public interface DataListProducer< T > {
    public List< T > produce( long beg, long end, BiClient c );
}
