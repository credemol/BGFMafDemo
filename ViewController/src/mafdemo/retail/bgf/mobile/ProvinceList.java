package mafdemo.retail.bgf.mobile;

import java.util.ArrayList;
import java.util.List;

public class ProvinceList {
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


    public ProvinceList() {
        super();
    }
    
    public Province[] getProvinces() {
        return (Province[]) provinceList.toArray(new Province[provinceList.size()]);
    }

}
