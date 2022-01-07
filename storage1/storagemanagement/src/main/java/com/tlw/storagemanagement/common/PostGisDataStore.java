package com.tlw.storagemanagement.common;

import com.tlw.storagemanagement.Properties.PostgisProperties;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class PostGisDataStore {

    private static DataStore PGDataStore;

    public static DataStore getPostGISDS(PostgisProperties config) throws IOException {

        if(PGDataStore==null){
            Map<String,Object> param=new HashMap<String,Object>();
            param.put("dbtype","postgis");
            param.put("host",config.getHost());
            param.put("port",config.getPort());
            param.put("user",config.getUser());
            param.put("passwd",config.getPw());
            param.put("database",config.getDatabase());
            param.put("schema",config.getSchema());
            // PGDataStore= (JDBCDataStore) DataStoreFinder.getDataStore(param);
            PGDataStore= DataStoreFinder.getDataStore(param);
            // DataStore ds=DataStoreFinder.getDataStore(param);
        }
        return PGDataStore;
    }

}
