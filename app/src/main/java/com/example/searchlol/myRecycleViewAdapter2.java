package com.example.searchlol;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

class myRecyclerViewAdapter2 extends RecyclerView.Adapter {
    String TAG = "RecyclerViewAdapter2";

    //리사이클러뷰에 넣을 데이터 리스트
    ArrayList<gameMatchData> dataModels;
    Context context;

    //생성자를 통하여 데이터 리스트 context를 받음
    public myRecyclerViewAdapter2(Context context, ArrayList<gameMatchData> dataModels){
        this.dataModels = dataModels;
        this.context = context;
    }

    public int getItemCount() {
        //데이터 리스트의 크기를 전달해주어야 함
        return dataModels.size();
    }


    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreateViewHolder");

        //자신이 만든 itemview를 inflate한 다음 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item2,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);


        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG,"onBindViewHolder");

        MyViewHolder myViewHolder = (MyViewHolder)holder;

        myViewHolder.championImg.setImageBitmap(dataModels.get(position).getChampionBit());
        myViewHolder.spell1Image.setImageBitmap(dataModels.get(position).getSpell1Bit());
        myViewHolder.spell2Image.setImageBitmap(dataModels.get(position).getSpell2Bit());
        myViewHolder.rune1Image.setImageBitmap(dataModels.get(position).getRune1Bit());
        myViewHolder.rune2Image.setImageBitmap(dataModels.get(position).getRune2Bit());

        if(dataModels.get(position).getWinsLosses())
        {
            myViewHolder.bar.setBackgroundColor(Color.parseColor("#50bcdf"));
            myViewHolder.item_cardView.setBackgroundColor(Color.parseColor("#50bcdf"));
            myViewHolder.winslosseswhat.setText("승리\n" + dataModels.get(position).getQueueType());
        }else { myViewHolder.bar.setBackgroundColor(Color.parseColor("#b22222"));
                myViewHolder.item_cardView.setBackgroundColor(Color.parseColor("#b22222"));
                myViewHolder.winslosseswhat.setText("패배\n" + dataModels.get(position).getQueueType());
        }
        Log.d("matchwpqkf", String.valueOf(dataModels.get(position).getItemImg()));
        for(int i=0; i<myViewHolder.item.length; i++)
        {
            Log.d("matchTest", String.valueOf(dataModels.get(position).getItemImg()[i]));
            myViewHolder.item[i].setImageBitmap(dataModels.get(position).getItemImg()[i]);
        }
        double kdaAver = ((double)Integer.parseInt(dataModels.get(position).getKda()[0]) +
                (double)Integer.parseInt(dataModels.get(position).getKda()[2]))/(double)Integer.parseInt(dataModels.get(position).getKda()[1]);
        String kdaAverToString = String.format("%.2f", kdaAver);
        if(kdaAverToString.equals("Infinity")) { kdaAverToString = "Perfect"; }
        myViewHolder.kda.setText(kdaAverToString + "\n" + dataModels.get(position).getKda()[0] + " / "
        + dataModels.get(position).getKda()[1] + " / "
        + dataModels.get(position).getKda()[2]
        );
        myViewHolder.wardgold.setText("골드 " + dataModels.get(position).getRecyclerViewRight()[3] +"\n"
        + "와드 " + dataModels.get(position).getRecyclerViewRight()[0] + " " +dataModels.get(position).getRecyclerViewRight()[1] + " " + dataModels.get(position).getRecyclerViewRight()[2]
        + "\n" + "cs  " + dataModels.get(position).getRecyclerViewRight()[4]);


    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView championImg, spell1Image, spell2Image, rune1Image, rune2Image;
        ImageView item[] = new ImageView[6];
        ConstraintLayout bar;
        CardView item_cardView;
        TextView winslosseswhat, kda, wardgold;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
                championImg = itemView.findViewById(R.id.championImg);
                spell1Image = itemView.findViewById(R.id.spell1Image);
                spell2Image = itemView.findViewById(R.id.spell2Image);
                rune1Image = itemView.findViewById(R.id.rune1Image);
                rune2Image = itemView.findViewById(R.id.rune2Image);
                bar = itemView.findViewById(R.id.bar);
                item_cardView = itemView.findViewById(R.id.item_cardview);
                winslosseswhat = itemView.findViewById(R.id.winslosseswhat);
                kda = itemView.findViewById(R.id.kda);
                wardgold= itemView.findViewById(R.id.wardgold);
                
                item[0] = itemView.findViewById(R.id.item1);
                item[1] = itemView.findViewById(R.id.item2);
                item[2] = itemView.findViewById(R.id.item3);
                item[3] = itemView.findViewById(R.id.item4);
                item[4] = itemView.findViewById(R.id.item5);
                item[5] = itemView.findViewById(R.id.item6);

        }
    }

}
