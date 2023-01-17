package com.alicp.jetcache.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author smartduan
 */
public class FastjsonValueEncoder extends AbstractValueEncoder {

    public static final FastjsonValueEncoder INSTANCE = new FastjsonValueEncoder(true);

    public static int IDENTITY_NUMBER = 0x4A953A81;

    public FastjsonValueEncoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    public byte[] apply(Object value) {
        try {
            byte[] bs1 = JSON.toJSONBytes(value, SerializerFeature.WriteClassName);

            if (useIdentityNumber) {
                byte[] bs2 = new byte[bs1.length + 4];
                writeHeader(bs2, IDENTITY_NUMBER);
                System.arraycopy(bs1, 0, bs2, 4, bs1.length);
                return bs2;
            } else {
                return bs1;
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Fastjson Encode error. ");
            sb.append("msg=").append(e.getMessage());
            throw new CacheEncodeException(sb.toString(), e);
        }
    }

    private void writeHeader(byte[] buf, int header) {
        buf[0] = (byte) (header >> 24 & 0xFF);
        buf[1] = (byte) (header >> 16 & 0xFF);
        buf[2] = (byte) (header >> 8 & 0xFF);
        buf[3] = (byte) (header & 0xFF);
    }
}