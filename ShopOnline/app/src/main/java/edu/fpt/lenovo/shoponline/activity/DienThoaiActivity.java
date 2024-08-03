package edu.fpt.lenovo.shoponline.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.fpt.lenovo.shoponline.adapter.DiethoaiAdapter;
import edu.fpt.lenovo.shoponline.model.Sanpham;
import edu.fpt.lenovo.shoponline.ultil.CheckConnetion;
import edu.fpt.lenovo.shoponline.ultil.server;
import lenovo.shoponline.R;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    DiethoaiAdapter diethoaiAdapter;
    ArrayList<Sanpham> mangdienthoai;
    int idDienThoai = 0;
    int page=1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        Anhxa();//anh xa cac id
        ActionToolbarDienThoai();//xu ly click item tren toolbar
        GetIDLoaiSanPham();
        GetData(page);//lay du lieu
    }
    ///
    void Anhxa()
    {
        toolbar = findViewById(R.id.toolbardienthoai);
        listView = findViewById(R.id.listviewdienthoai);
        mangdienthoai = new ArrayList<>();
        diethoaiAdapter = new DiethoaiAdapter(getApplicationContext(),mangdienthoai);
        listView.setAdapter(diethoaiAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent  = new Intent(getApplicationContext(),ChiTietSanPham.class);
//                intent.putExtra("thongtinsanpham",mangdienthoai.get(i));
//                startActivity(intent);
            }
        });
    }
    void ActionToolbarDienThoai()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    void GetIDLoaiSanPham(){
        idDienThoai = getIntent().getIntExtra("idloaidsanpham",-1);
    }
    void GetData(int p)
    {
        //b1: su dung thu vien volley
       final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        //b2.Tao request
        //StringRequest(phuongThuc,DuongDan,ThanhCong,ThatBai)
        //chu ys: truyen tham so cho post
        String path = server.Duongdandienthoai+String.valueOf(p);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                path, new Response.Listener<String>() {
            //neu thanh cong
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tendt="";
                int Giadt = 0;
                String Hinhanhdt="";
                String Mota="";
                int IdsanphamDT=0;
                if(response!=null)
                {
                    try {
                        //chuyen ket qua ve mang cac doi tuong
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++)//doc mang bang vong lap
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);//lay ve doi tuong i
                            id=jsonObject.getInt("id");//lay tung truong du lieu
                            Tendt = jsonObject.getString("tensp");
                            Giadt = jsonObject.getInt("giasp");
                            Hinhanhdt = jsonObject.getString("hinhanhsp");
                            Mota = jsonObject.getString("motasp");
                            IdsanphamDT = jsonObject.getInt("idsanpham");
                            //dua doi tuong vao mamg
                            mangdienthoai.add(new Sanpham(id,Tendt,Giadt,Hinhanhdt,Mota,IdsanphamDT));
                            diethoaiAdapter.notifyDataSetChanged();

                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    CheckConnetion.ShowToastLong(getApplicationContext(),"Khong co du lieu");
                }
            }
            //neu that bai
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            //truyen tham so

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("idsanpham",String.valueOf(idDienThoai));
                return param;
            }
        };
        //b3. xu ly request
        requestQueue.add(stringRequest);

    }
    ////
}
