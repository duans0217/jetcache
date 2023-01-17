package com.alicp.jetcache.support;

import com.alibaba.fastjson.JSON;

/**
 * @author smartduan
 */
public class FastjsonValueDecoder extends AbstractValueDecoder {

    public static final FastjsonValueDecoder INSTANCE = new FastjsonValueDecoder(true);

    public FastjsonValueDecoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    public Object doApply(byte[] buffer) {
        if (useIdentityNumber) {
            byte[] bs = new byte[buffer.length - 4];
            System.arraycopy(buffer, 4, bs, 0, bs.length);
            return JSON.parse(bs);
        } else {
            return JSON.parse(buffer);
        }
    }
}