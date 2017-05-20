package cn.justwithme.withme.Adapaer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.justwithme.withme.Entity.MessageShow;
import cn.justwithme.withme.R;

/**
 * Created by 桐小目 on 2017/2/18.
 */

public class ChatMessageAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<MessageShow> messageShowList;


    public ChatMessageAdapter(Context context, List<MessageShow> messageShowList){
        layoutInflater = LayoutInflater.from(context);
        this.messageShowList = messageShowList;
    }

    @Override
    public int getCount() {
        return messageShowList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageShowList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if(messageShowList.get(position).getMessageType()==0)
           return 0;
        else
           return 1;
    }
    //Item类型的总数
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageShow messageShow = messageShowList.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            if(messageShow.getMessageType() == 0){
                convertView = layoutInflater.inflate(R.layout.from_message_item,null);
                viewHolder = new ViewHolder();
//                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.from_user_icons);
                viewHolder.time = (TextView) convertView.findViewById(R.id.from_time);
                viewHolder.message = (TextView) convertView.findViewById(R.id.from_message);
            }
            else{
                convertView = layoutInflater.inflate(R.layout.to_message_item,null);
                viewHolder = new ViewHolder();
//                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.to_user_icons);
                viewHolder.time = (TextView) convertView.findViewById(R.id.to_time);
                viewHolder.message = (TextView) convertView.findViewById(R.id.to_message);
            }
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        System.out.println("我到了这里"+position);
        System.out.println("我添加了适配器: "+messageShow.getContents());
//        viewHolder.imageView.setImageResource(R.drawable.photo);
        viewHolder.message.setText(messageShow.getContents());
        viewHolder.time.setText(messageShow.getTime());
        return convertView;
    }

    private final class ViewHolder{
//        ImageView imageView;
        TextView time;
        TextView message;
    }
}
