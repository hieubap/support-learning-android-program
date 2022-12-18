package com.android.slap.ui.ds_lop;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.slap.MainActivity;
import com.android.slap.R;
import com.android.slap.dao.SinhVienDAO;
import com.android.slap.databinding.FragmentDsLopBinding;
import com.android.slap.model.SinhVienModel;
import com.android.slap.event.SinhVienModelEvent;
import com.android.slap.ui.ds_lop.ui.SinhVienAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DsLopFragment extends Fragment implements SinhVienModelEvent {

    private FragmentDsLopBinding binding;
    private SinhVienModel sinhVienModel;
    private SinhVienAdapter sinhVienAdapter;
    private GridView gridView;
    private Dialog dialogUpdate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sinhVienModel = new SinhVienModel(this);
        sinhVienModel.getData();

        binding = FragmentDsLopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = binding.dsSvGrid;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onSuccessIn(SinhVienDAO sinhVienModel) {

    }

    @Override
    public void onSuccessOut(SinhVienDAO sinhVienModel) {

    }

    @Override
    public void afterGetData(List<SinhVienDAO> list) {
        sinhVienAdapter = new SinhVienAdapter(getView().getContext(), R.layout.sinh_vien_item, list,this);
        gridView.setAdapter(sinhVienAdapter);
    }

    @Override
    public void afterSave() {
        sinhVienModel.getData();
        dialogUpdate.hide();
    }

    @Override
    public void onTouchItem(SinhVienDAO sv) {
        if(!MainActivity.THAY_TUAN) return;
        dialogUpdate = new Dialog(getContext());
        dialogUpdate.setCancelable(true);
        dialogUpdate.setContentView(R.layout.input_point_layout);
        dialogUpdate.show();
        Button btn = dialogUpdate.findViewById(R.id.save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText pointGk = dialogUpdate.findViewById(R.id.point_giua_ky);
                EditText pointCk = dialogUpdate.findViewById(R.id.point_cuoi_ky);

                sv.giuaKy = Double.valueOf(String.valueOf(pointGk.getText()));
                sv.cuoiKy = Double.valueOf(String.valueOf(pointCk.getText()));

                sinhVienModel.save(sv);
            }
        });
    }
}