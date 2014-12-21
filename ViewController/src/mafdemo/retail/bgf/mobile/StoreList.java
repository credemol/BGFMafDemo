package mafdemo.retail.bgf.mobile;

import com.sun.util.logging.Level;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.el.ValueExpression;

import mafdemo.retail.bgf.application.DBConnectionFactory;

import mafdemo.retail.bgf.application.LifeCycleListenerImpl;

import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.bindings.dbf.AmxAccessorIteratorBinding;
import oracle.adfmf.bindings.iterator.BasicIterator;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.internal.AdfmfJavaUtilitiesInternal;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;
import oracle.adfmf.util.Utility;
import oracle.adfmf.util.logging.Trace;


// this class is deprecated.
public class StoreList {
    
    
    
    private static final String[] PROVINCES = {
        "경기도", "서울특별시", "경상남도", "강원도", "부산광역시", "충청남도", "경상북도", "인천광역시", "충청북도",
        "전라북도", "제주특별자치도", "대구광역시", "전라남도", "대전광역시", "울산광역시", "세종특별자치시"
    };
    
    private static List provinceList = new ArrayList();
    static {
        for(int i = 0; i < PROVINCES.length; i++) {
            provinceList.add(new Province(PROVINCES[i], PROVINCES[i]));
        }
    }

    private transient ProviderChangeSupport providerChangeSupport = new ProviderChangeSupport(this);
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    
//    private String provinceIndex;
//    private String districtIndex;
    private String provinceCode;
    private String districtCode;
    
    
    
    public StoreList() {
        super();
    }
    
    
    public Province[] getProvinces() {
        return (Province[]) provinceList.toArray(new Province[provinceList.size()]);
    }
    
    public District[] getDistricts() {
        Trace.log(Utility.ApplicationLogger, Level.SEVERE, StoreList.class, "getDistricts",
                  "############## getDestricts() start. provinceCode=[" + this.provinceCode + "]");
        if(this.provinceCode == null) {
            return new District[0];
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        final String sql = "SELECT DISTRICT_CODE from PROVINCE_DISTRICT where PROVINCE_CODE = ?";
        List districts = new ArrayList();
        try {
            conn = DBConnectionFactory.getConnection();
            TraceLog.info(getClass(), "getDistricts", "conn: " + conn);
            stmt = conn.prepareStatement(sql);
            TraceLog.info(getClass(), "getDistricts", "stmt: " + stmt);
            stmt.setString(1, provinceCode);
            
            rs = stmt.executeQuery();
            TraceLog.info(getClass(), "getDistricts", "rs: " + rs);
            while(rs.next()) {
                String district = rs.getString("DISTRICT_CODE");
                districts.add(new District(district, district));
            }
        } catch(SQLException e) {
            Trace.log(Utility.ApplicationLogger, Level.SEVERE, StoreList.class, "getDistricts",
                      "############## Exception:  " + e.getMessage());

        } finally {
            try {
                if(rs != null) {
                    rs.close();
                }
            } catch(Exception e) {
            }
            try {
                if(stmt != null) {
                    stmt.close();
                }
            } catch(Exception e) {
            }
        }

        Trace.log(Utility.ApplicationLogger, Level.SEVERE, StoreList.class, "getDistricts",
                  "############## getDestricts() is completed.  ");

        
        return (District[]) districts.toArray(new District[districts.size()]);
    }
    
    public Store[] findStores(String provinceCode, String districtCode, String storeName, 
                              boolean service01, boolean service02, boolean service03, boolean service04,
                              boolean service05, boolean service06, boolean service07, boolean service08) {
        TraceLog.info(getClass(), "getStores", "start...");
        //String provinceIndex = (String)AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.provinceIndex}");
        //String districtIndex = (String)AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.districtIndex}");
        TraceLog.info(getClass(), "getStores", "provinceCode: " + provinceCode);
        TraceLog.info(getClass(), "getStores", "districtCode: " + districtCode);
        
        if(provinceCode == null || provinceCode.trim().length() == 0) {
            return new Store[0];
        }
        if(districtCode == null || districtCode.trim().length() == 0) {
            return new Store[0];
        }
        
        String sql = "SELECT S.ID, S.NAME, OLD_ADDRESS, NEW_ADDRESS, TEL_NO, SERVICE_01, SERVICE_02, SERVICE_03, " +
                           "SERVICE_04, SERVICE_05, SERVICE_06, SERVICE_07, SERVICE_08 FROM STORE S, PROVINCE_DISTRICT PD " +
                           "WHERE PD.PROVINCE_CODE = ? AND PD.DISTRICT_CODE = ? AND PD.ID = S.PROVINCE_DISTRICT_ID ";
        
        sql += buildWhereClause(storeName, service01, service02, service03, service04, service05, service06, service07, service08);

        TraceLog.info(getClass(), "getStores", "sql: " + sql);
        
        //TraceLog.info(getClass(), "getStores", "provinceIndex: " + provinceIndex);
        //if(provinceCode != null) {
        //    TraceLog.info(getClass(), "getStores", "provinceCode class: " + provinceCode.getClass());
        //}

        //TraceLog.info(getClass(), "getStores", "districtIndex: " + districtIndex);
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List list = new ArrayList();
        try {
            conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, provinceCode);
            stmt.setString(2, districtCode);
            
            rs = stmt.executeQuery();
            while(rs.next()) {
                list.add(new Store(rs.getString("ID"), 
                                   rs.getString("NAME"), 
                                   rs.getString("TEL_NO"), 
                                   rs.getString("OLD_ADDRESS"), 
                                   rs.getString("NEW_ADDRESS"),
                                   "Y".equals(rs.getString("SERVICE_01")),
                                   "Y".equals(rs.getString("SERVICE_02")),
                                   "Y".equals(rs.getString("SERVICE_03")),
                                   "Y".equals(rs.getString("SERVICE_04")),
                                   "Y".equals(rs.getString("SERVICE_05")),
                                   "Y".equals(rs.getString("SERVICE_06")),
                                   "Y".equals(rs.getString("SERVICE_07")),
                                   "Y".equals(rs.getString("SERVICE_08"))));
            }
        } catch(SQLException e) {
            
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch(Exception e) {};
            } 
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(Exception e) {};
            }
        }
        return (Store[]) list.toArray(new Store[list.size()]);
                                  
    }
    
    public Store[] getStores() {
        TraceLog.info(getClass(), "getStores", "start...");
        //String provinceIndex = (String)AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.provinceIndex}");
        //String districtIndex = (String)AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.districtIndex}");
        TraceLog.info(getClass(), "getStores", "provinceCode: " + provinceCode);
        TraceLog.info(getClass(), "getStores", "districtCode: " + districtCode);
        
        if(provinceCode == null || provinceCode.trim().length() == 0) {
            return new Store[0];
        }
        if(districtCode == null || districtCode.trim().length() == 0) {
            return new Store[0];
        }
        
        String sql = "SELECT S.ID, S.NAME, OLD_ADDRESS, NEW_ADDRESS, TEL_NO, SERVICE_01, SERVICE_02, SERVICE_03, " +
                           "SERVICE_04, SERVICE_05, SERVICE_06, SERVICE_07, SERVICE_08 FROM STORE S, PROVINCE_DISTRICT PD " +
                           "WHERE PD.PROVINCE_CODE = ? AND PD.DISTRICT_CODE = ? AND PD.ID = S.PROVINCE_DISTRICT_ID ";
        
        sql += buildWhereClause();
        TraceLog.info(getClass(), "getStores", "sql: " + sql);
        
        //TraceLog.info(getClass(), "getStores", "provinceIndex: " + provinceIndex);
        //if(provinceCode != null) {
        //    TraceLog.info(getClass(), "getStores", "provinceCode class: " + provinceCode.getClass());
        //}

        //TraceLog.info(getClass(), "getStores", "districtIndex: " + districtIndex);
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List list = new ArrayList();
        try {
            conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, provinceCode);
            stmt.setString(2, districtCode);
            
            rs = stmt.executeQuery();
            while(rs.next()) {
                list.add(new Store(rs.getString("ID"), 
                                   rs.getString("NAME"), 
                                   rs.getString("TEL_NO"), 
                                   rs.getString("OLD_ADDRESS"), 
                                   rs.getString("NEW_ADDRESS"),
                                   "Y".equals(rs.getString("SERVICE_01")),
                                   "Y".equals(rs.getString("SERVICE_02")),
                                   "Y".equals(rs.getString("SERVICE_03")),
                                   "Y".equals(rs.getString("SERVICE_04")),
                                   "Y".equals(rs.getString("SERVICE_05")),
                                   "Y".equals(rs.getString("SERVICE_06")),
                                   "Y".equals(rs.getString("SERVICE_07")),
                                   "Y".equals(rs.getString("SERVICE_08"))));
            }
        } catch(SQLException e) {
            
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch(Exception e) {};
            } 
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(Exception e) {};
            }
        }
        return (Store[]) list.toArray(new Store[list.size()]);
        
        //return new Store[] {
        //           new Store("1", "store1", "tel1", "old addr 1", "new addr 1"),
        //            new Store("2", "store2", "tel2", "old addr 2", "new addr 2"),
        //            new Store("3", "store3", "tel3", "old addr 3", "new addr 3")
        //};
    }
    
    private String buildWhereClause(String storeName, boolean service01, boolean service02, boolean service03, boolean service04,
                                    boolean service05, boolean service06, boolean service07, boolean service08) {
        StringBuffer sb = new StringBuffer();
        if(service01) {
            sb.append(" AND SERVICE_01='Y'");
        }
        if(service02) {
            sb.append(" AND SERVICE_02='Y'");
        }
        if(service03) {
            sb.append(" AND SERVICE_03='Y'");
        }
        if(service04) {
            sb.append(" AND SERVICE_04='Y'");
        }
        if(service05) {
            sb.append(" AND SERVICE_05='Y'");
        }
        if(service06) {
            sb.append(" AND SERVICE_06='Y'");
        }
        if(service07) {
            sb.append(" AND SERVICE_07='Y'");
        }
        if(service08) {
            sb.append(" AND SERVICE_08='Y'");
        }
        
       
        if(storeName != null) {
            storeName = storeName.trim();
            if(storeName.length() > 0) {
                sb.append(" AND NAME LIKE '%" + storeName + "%'");
            }
        }
        
        return sb.toString();

    }
    private String buildWhereClause() {
        StringBuffer sb = new StringBuffer();
        if("true".equals(AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.service01}"))) {
            sb.append(" AND SERVICE_01='Y'");
        }
        if("true".equals(AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.service02}"))) {
            sb.append(" AND SERVICE_02='Y'");
        }
        if("true".equals(AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.service03}"))) {
            sb.append(" AND SERVICE_03='Y'");
        }
        if("true".equals(AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.service04}"))) {
            sb.append(" AND SERVICE_04='Y'");
        }
        if("true".equals(AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.service05}"))) {
            sb.append(" AND SERVICE_05='Y'");
        }
        if("true".equals(AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.service06}"))) {
            sb.append(" AND SERVICE_06='Y'");
        }
        if("true".equals(AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.service07}"))) {
            sb.append(" AND SERVICE_07='Y'");
        }
        if("true".equals(AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.service08}"))) {
            sb.append(" AND SERVICE_08='Y'");
        }
        
        String storeName = (String) AdfmfJavaUtilities.getELValue("#{pageFlowScope.StoreSearchBean.storeName}");
        if(storeName != null) {
            storeName = storeName.trim();
            if(storeName.length() > 0) {
                sb.append(" AND NAME LIKE '%" + storeName + "%'");
            }
        }
        
        return sb.toString();
        
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    
    public void addProviderChangeListener(ProviderChangeListener l) {
        providerChangeSupport.addProviderChangeListener(l);
    }

    public void removeProviderChangeListener(ProviderChangeListener l) {
        providerChangeSupport.removeProviderChangeListener(l);
    }


//    public void setProvinceIndex(String provinceIndex) {
//        TraceLog.info(getClass(), "setProviceIndex", "provinceIndex: " + provinceIndex);
//        String oldProvinceIndex = this.provinceIndex;
//        this.provinceIndex = provinceIndex;
//        propertyChangeSupport.firePropertyChange("provinceIndex", oldProvinceIndex, provinceIndex);
//        
//        if("-1".equals(provinceIndex)) {
//            setProvinceCode(null);
//            return;
//        }
//        int index = Integer.parseInt(provinceIndex);
//        setProvinceCode(PROVINCES[index]);
//
//        providerChangeSupport.fireProviderRefresh("districts");
//        ValueExpression ve = AdfmfJavaUtilities.getValueExpression("#{bindings.districtsIterator}", Object.class);
//        AmxAccessorIteratorBinding aib = (AmxAccessorIteratorBinding) ve.getValue(AdfmfJavaUtilities.getAdfELContext());
//        BasicIterator bi = (BasicIterator) aib.getIterator();
//        bi.refresh(true);
//    
//        TraceLog.info(getClass(), "setProviceIndex", "Completed successfully");
//    }
//
//    public String getProvinceIndex() {
//        return provinceIndex;
//    }
//
//    public void setDistrictIndex(String districtIndex) {
//        TraceLog.info(getClass(), "setDistrictIndex", "districtIndex: " + districtIndex);
//        String oldDistrictIndex = this.districtIndex;
//        this.districtIndex = districtIndex;
//        propertyChangeSupport.firePropertyChange("districtIndex", oldDistrictIndex, districtIndex);
//    }
//
//    public String getDistrictIndex() {
//        return districtIndex;
//    }

    public void setProvinceCode(String provinceCode) {
        Trace.log(Utility.ApplicationLogger, Level.INFO, StoreList.class, "setProvinceCode",
                  "############## setProvinceCode() start.  [" + provinceCode + "]");
        String oldProvinceCode = this.provinceCode;
        this.provinceCode = provinceCode;
        propertyChangeSupport.firePropertyChange("provinceCode", oldProvinceCode, provinceCode);
        
        providerChangeSupport.fireProviderRefresh("districts");
        
        Trace.log(Utility.ApplicationLogger, Level.INFO, StoreList.class, "getDistricts",
                  "############## setProvinceCode() is completed.  ");
    }

    
    public String getProvinceCode() {
        return provinceCode;
    }

    public void setDistrictCode(String districtCode) {
        String oldDistrictCode = this.districtCode;
        this.districtCode = districtCode;
        propertyChangeSupport.firePropertyChange("districtCode", oldDistrictCode, districtCode);
    }

    public String getDistrictCode() {
        return districtCode;
    }
    
    //public void selectProvince(ValueChangeEvent valueChangeEvent) {
    //}

}
