package com.pzh.yiqiplay.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pzh.yiqiplay.R;
import com.pzh.yiqiplay.bean.ContactBean;
import com.pzh.yiqiplay.common.BaseUI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by pzh on 16/3/28.
 */
public class PhoneBook extends BaseUI {

    private ListView contactList;
    private ArrayList<ContactBean> lists;
    private LayoutInflater inflater;
    private ContactAdapter adapter;

    private TextView tv_back, tv_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_book_layout);
        inflater = LayoutInflater.from(this);
        contactList = (ListView) findViewById(R.id.contactList);
        tv_back = (TextView) findViewById(R.id.back);
        tv_count = (TextView) findViewById(R.id.count);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lists = new ArrayList<>();
        adapter = new ContactAdapter();
        contactList.setAdapter(adapter);
        getData();
    }


    private void getData() {
        ContentResolver resolver = getContentResolver();
        Cursor cursor = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
//             here to request the missing permissions, and then overriding
//               public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                                      int[] grantResults);
//             to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null,null);
        try {
            if (cursor == null) {
                Toast.makeText(this, "没查询到记录", Toast.LENGTH_SHORT).show();
                return;
            }
            while (cursor.moveToNext()) {
                ContactBean bean = new ContactBean();
                bean.name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                bean.num=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                bean.date=cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LAST_TIME_CONTACTED));
                lists.add(bean);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            adapter.notifyDataSetChanged();
        }
    }

    public class ContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.contact_item, null);
                holder.tv_num = (TextView) convertView.findViewById(R.id.num);
                holder.tv_name = (TextView) convertView.findViewById(R.id.name);
                holder.tv_last = (TextView) convertView.findViewById(R.id.last);
                holder.select = (CheckBox) convertView.findViewById(R.id.select);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_name.setText(lists.get(position).name);
            holder.tv_num.setText(lists.get(position).num);
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
            holder.tv_last.setText(sim.format(lists.get(position).date));
            return convertView;
        }
    }

    public class ViewHolder {
        TextView tv_name;
        TextView tv_num;
        TextView tv_last;
        CheckBox select;
    }

}
