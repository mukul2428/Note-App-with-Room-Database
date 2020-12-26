package com.techexpert.quixotetask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    //initialize variable
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;
//    private static ClickListener clickListener;

    //create constructor
    public MainAdapter(Activity context, List<MainData> dataList)
    {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //initialize main data
        MainData data = dataList.get(position);
        //initialize database
        //database = RoomDB.getInstance(context);
        database = Room.databaseBuilder(context, RoomDB.class, AppConfig.DB_NAME)
                .allowMainThreadQueries()
                .build();
        //Set text on textView
        holder.textView1.setText(data.getText1());
        holder.textView2.setText(data.getText2());
        Bitmap photo=BitmapManager.base64ToBitmap(data.getImg());
        holder.pic.setImageBitmap(photo);
//        holder.btEdit.setOnClickListener(view ->
//        {
//            //initialize main data
//            MainData d = dataList.get(holder.getAdapterPosition());
//            //Get id
//            int sID = d.getID();
//            //Get text
//            String sText = d.getText1();
//
//            //create dialog
//            Dialog dialog = new Dialog(context);
//            //set context view
//            dialog.setContentView(R.layout.dialog_update);
//            //initialize width
//            int width = WindowManager.LayoutParams.MATCH_PARENT;
//            //initialize height
//            int height = WindowManager.LayoutParams.WRAP_CONTENT;
//            //Set Layout
//            dialog.getWindow().setLayout(width,height);
//            //show dialog
//            dialog.show();
//
//            //initialize and assign variable
//            EditText editText = dialog.findViewById(R.id.edit_text);
//            Button btUpdate = dialog.findViewById(R.id.bt_update);
//
//            //set text on edit text
//            editText.setText(sText);
//
//            btUpdate.setOnClickListener(view1 ->
//            {
//                //Dismiss dialog
//                dialog.dismiss();
//                //get updated text from edit text
//                String uText = editText.getText().toString().trim();
//                //Update text in database
//                database.mainDao().update(sID,uText);
//                //notify when data is updated
//                dataList.clear();
//                dataList.addAll(database.mainDao().getAll());
//                notifyDataSetChanged();
//
//            });
//        });

        holder.btDelete.setOnClickListener(view ->
        {

            //initialize main data
            MainData d = dataList.get(holder.getAdapterPosition());
            //delete text from database
            database.mainDao().delete(d);
            //notify when data is deleted
            int p = holder.getAdapterPosition();
            dataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,dataList.size());


        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //initialize variable
        TextView textView1,textView2;
        ImageView btDelete,pic;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textView1 = itemView.findViewById(R.id.text_view1);
            textView2 = itemView.findViewById(R.id.text_view2);
            btDelete = itemView.findViewById(R.id.bt_delete);
            pic = itemView.findViewById(R.id.pic);

            itemView.setOnClickListener(view ->
            {

                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("data1",""+dataList.get(itemPosition).getText1());
                intent.putExtra("data2",""+dataList.get(itemPosition).getText2());
                intent.putExtra("data3",""+dataList.get(itemPosition).getImg());
                context.startActivity(intent);

            });

        }
    }
}
