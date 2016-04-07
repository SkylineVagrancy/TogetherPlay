package com.pzh.yiqiplay.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
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
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by pzh on 16/3/28.
 */
public class ContactUI extends BaseUI {

    private ListView contactList;
    private ArrayList<String> lists;
    private LayoutInflater inflater;
    private ContactAdapter adapter;
    private HashMap<String, ArrayList<ContactBean>> contactMap;
    private int count = 0;
    private TextView tv_back, tv_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactui_layout);
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
        contactMap = new HashMap<>();
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
        cursor = resolver.query(CallLog.Calls.CONTENT_URI, new String[]{
                        CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,
                        CallLog.Calls.TYPE, CallLog.Calls.DATE}, null, null,
                CallLog.Calls.DEFAULT_SORT_ORDER);
        try {
            if (cursor == null) {
                Toast.makeText(this, "没查询到记录", Toast.LENGTH_SHORT).show();
                return;
            }
            while (cursor.moveToNext()) {
                ContactBean bean = new ContactBean();
                String num = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                bean.date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                bean.last = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                bean.name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                bean.num = num;
                if (bean.name == null) {
                    bean.isSelect = true;
                    bean.name = "";
                }
                if (contactMap.containsKey(num)) {
                    contactMap.get(num).add(bean);
                } else {
                    ArrayList<ContactBean> list = new ArrayList<>();
                    list.add(bean);
                    count++;
                    contactMap.put(num, list);
                }
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            Iterator<String> keys = contactMap.keySet().iterator();
            while (keys.hasNext()) {
                String tem = keys.next();
                lists.add(tem);
            }
            adapter.notifyDataSetChanged();
            tv_count.setText(count + "");
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

            holder.select.setChecked(contactMap.get(lists.get(position)).get(0).isSelect);
            holder.select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactMap.get(lists.get(position)).get(0).isSelect = !contactMap.get(lists.get(position)).get(0).isSelect;
                    if (contactMap.get(lists.get(position)).get(0).isSelect) {
                        count++;
                    } else {
                        count--;
                    }
                    tv_count.setText(count + "");
                }
            });

            holder.tv_name.setText(contactMap.get(lists.get(position)).get(0).name + " " + contactMap.get(lists.get(position)).size());
            holder.tv_num.setText(contactMap.get(lists.get(position)).get(0).num);
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
            holder.tv_last.setText(sim.format(contactMap.get(lists.get(position)).get(0).date));
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
