package edu.fpt.lenovo.shoponline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.fpt.lenovo.shoponline.model.Sanpham;
import lenovo.shoponline.R;

public class DiethoaiAdapter extends BaseAdapter {
    ArrayList<Sanpham> sanphamArrayList;
    Context context;
    public DiethoaiAdapter(Context context,ArrayList<Sanpham> arr)
    {
        this.sanphamArrayList = arr;
        this.context =context;
    }
    @Override
    public int getCount() {
        return sanphamArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return sanphamArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolderDT {
        public TextView txttendienthoai,txtgiadienthoai,txtmotadienthoai;
        public ImageView imgdienthoai;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderDT viewHolderDT = null;
        if(view==null)//chua ton tai view => tao view
        {
            viewHolderDT = new ViewHolderDT();
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_dienthoai,null);
            viewHolderDT.txttendienthoai=(TextView)view
                    .findViewById(R.id.textviewdienthoai);
            viewHolderDT.txtgiadienthoai = (TextView)view
                    .findViewById(R.id.textviewgiadienthoai);
            viewHolderDT.txtmotadienthoai = (TextView)view
                    .findViewById(R.id.textviewmotadienthoai);
            viewHolderDT.imgdienthoai = (ImageView)view
                    .findViewById(R.id.imageviewdienthoai);
            view.setTag(viewHolderDT);//tao template
        }
        else //neu da ton tai view => lay ve view
        {
            viewHolderDT = (ViewHolderDT)view.getTag();
        }
        //xu ly du lieu
        Sanpham sanpham = (Sanpham)getItem(i);
        viewHolderDT.txttendienthoai.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat(("###,###,###"));
        viewHolderDT.txtgiadienthoai.setText("Gia: "
                +decimalFormat.format(sanpham.getGiasanpham()));
        viewHolderDT.txtmotadienthoai.setMaxLines(2);
        viewHolderDT.txtmotadienthoai.setText(sanpham.getMotasanpham());
        //lay anh mang
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.home)
                .error(R.drawable.erro)
                .into(viewHolderDT.imgdienthoai);
        return view;
    }
}
