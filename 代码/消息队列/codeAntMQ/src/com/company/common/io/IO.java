package com.company.common.io;

import com.company.common.entity.IORequest;
import com.company.common.entity.IOResponse;

import java.io.IOException;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.9.24
 */
public interface IO {
    public IOResponse send(IORequest request) throws IOException, ClassNotFoundException;

    public IORequest receive() throws IOException, ClassNotFoundException;
}
