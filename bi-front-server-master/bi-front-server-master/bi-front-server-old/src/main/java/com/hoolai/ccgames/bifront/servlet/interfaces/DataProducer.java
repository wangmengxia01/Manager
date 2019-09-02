package com.hoolai.ccgames.bifront.servlet.interfaces;

import com.hoolai.ccgames.bifront.net.BiClient;

/**
 * Created by hoolai on 2016/7/12.
 */

@FunctionalInterface
public interface DataProducer< T > {
    public T produce( long beg, long end, BiClient c );
}
