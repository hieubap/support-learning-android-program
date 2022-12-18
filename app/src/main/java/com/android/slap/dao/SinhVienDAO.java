package com.android.slap.dao;

import com.google.firebase.firestore.DocumentSnapshot;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.HashMap;
import java.util.Map;

public class SinhVienDAO {
    public Integer key;
    public String fullname;
    public String name;
    public String mssv = "Chưa có";
    public Double giuaKy = 0.0;
    public Double cuoiKy = 0.0;
    public Integer diemDanh;
    public Map<String,Boolean> checks = new HashMap<>();

    public SinhVienDAO(){

    }

    public SinhVienDAO(DocumentSnapshot doc){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.map(doc.getData(),this);
        this.key = Integer.parseInt(doc.getId());
        this.diemDanh = checks.size();
    }

    public Boolean isCheckIn(int sessionId){
        Boolean b = checks.get(sessionId+"");
        return b == null ? false : b;
    }
}
