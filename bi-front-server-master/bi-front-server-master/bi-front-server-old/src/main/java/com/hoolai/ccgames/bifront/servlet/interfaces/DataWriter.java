package com.hoolai.ccgames.bifront.servlet.interfaces;

import java.io.PrintWriter;

/**
 * Created by hoolai on 2016/7/12.
 */
public interface DataWriter< T > {
    public void write( T data, PrintWriter pw );
}
