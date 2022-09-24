package com.example.searchlol;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myRecyclerViewAdapter extends RecyclerView.Adapter {
    int cnt;
    String TAG = "RecyclerViewAdapter";

    //리사이클러뷰에 넣을 데이터 리스트
    ArrayList<featuredGamesInfo> dataModels;
    ArrayList<featuredGamesInfo2> dataModels2;
    Context context;

    //생성자를 통하여 데이터 리스트 context를 받음
    public myRecyclerViewAdapter(Context context, ArrayList<featuredGamesInfo> dataModels, ArrayList<featuredGamesInfo2> dataModels2){
        this.dataModels = dataModels;
        this.dataModels2 = dataModels2;
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
                .inflate(R.layout.recycler_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);


        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG,"onBindViewHolder");

        MyViewHolder myViewHolder = (MyViewHolder)holder;
        Log.d("test1", String.valueOf(dataModels.get(position).getBit()));
        Log.d("test1", String.valueOf(dataModels2.get(position).getBit()));
        myViewHolder.friendImage.setImageBitmap(dataModels.get(position).getBit());
        myViewHolder.enemyImage.setImageBitmap(dataModels2.get(position).getBit());

        myViewHolder.spell1Image.setImageBitmap(dataModels.get(position).getSpell1Bit());
        myViewHolder.spell2Image.setImageBitmap(dataModels.get(position).getSpell2Bit());
        myViewHolder.spell1Image2.setImageBitmap(dataModels2.get(position).getSpell1Bit());
        myViewHolder.spell2Image2.setImageBitmap(dataModels2.get(position).getSpell2Bit());

        myViewHolder.rune1Image.setImageBitmap(dataModels.get(position).getRune1Bit());
        myViewHolder.rune2Image.setImageBitmap(dataModels.get(position).getRune2Bit());
        myViewHolder.rune1Image2.setImageBitmap(dataModels2.get(position).getRune1Bit());
        myViewHolder.rune2Image2.setImageBitmap(dataModels2.get(position).getRune2Bit());

        myViewHolder.blueUserName.setText(dataModels.get(position).getUserName());
        myViewHolder.redUserName.setText(dataModels2.get(position).getUserName());

        myViewHolder.blueTier.setText(dataModels.get(position).getTier());
        myViewHolder.redTier.setText(dataModels2.get(position).getTier());
        patchTextView(myViewHolder);
        patchTextView2(myViewHolder);
    }
    //티어에따라 배경색을 다르게함
    public void patchTextView(MyViewHolder myViewHolder)
    {
        if(myViewHolder.blueTier.getText().equals("C"))
        {
            myViewHolder.blueTier.setBackgroundColor(Color.parseColor("#cc0000"));
        }else if(myViewHolder.blueTier.getText().equals("GM"))
        {
            myViewHolder.blueTier.setBackgroundColor(Color.parseColor("#cc0000"));
        }else if(myViewHolder.blueTier.getText().equals("M"))
        {
            myViewHolder.blueTier.setBackgroundColor(Color.parseColor("#cc0000"));
        }else if(myViewHolder.blueTier.getText().equals("D1") ||
                myViewHolder.blueTier.getText().equals("D2") ||
                myViewHolder.blueTier.getText().equals("D3") ||
                myViewHolder.blueTier.getText().equals("D4"))
        {
            myViewHolder.blueTier.setBackgroundColor(Color.parseColor("#0081cc"));
        }else if(myViewHolder.blueTier.getText().equals("P1") ||
                myViewHolder.blueTier.getText().equals("P2") ||
                myViewHolder.blueTier.getText().equals("P3") ||
                myViewHolder.blueTier.getText().equals("P4"))
        {
            myViewHolder.blueTier.setBackgroundColor(Color.parseColor("#008080"));
        }else if(myViewHolder.blueTier.getText().equals("G1") ||
                myViewHolder.blueTier.getText().equals("G2") ||
                myViewHolder.blueTier.getText().equals("G3") ||
                myViewHolder.blueTier.getText().equals("G4"))
        {
            myViewHolder.blueTier.setBackgroundColor(Color.parseColor("#FFD700"));
        }else if(myViewHolder.blueTier.getText().equals("S1") ||
                myViewHolder.blueTier.getText().equals("S2") ||
                myViewHolder.blueTier.getText().equals("S3") ||
                myViewHolder.blueTier.getText().equals("S4"))
        {
            myViewHolder.blueTier.setBackgroundColor(Color.parseColor("#C0C0C0"));
        }else if(myViewHolder.blueTier.getText().equals("B1") ||
                myViewHolder.blueTier.getText().equals("B2") ||
                myViewHolder.blueTier.getText().equals("B3") ||
                myViewHolder.blueTier.getText().equals("B4"))
        {
            myViewHolder.blueTier.setBackgroundColor(Color.parseColor("#624637"));
        }else if(myViewHolder.blueTier.getText().equals("I1") ||
                myViewHolder.blueTier.getText().equals("I2") ||
                myViewHolder.blueTier.getText().equals("I3") ||
                myViewHolder.blueTier.getText().equals("I4"))
        {
            myViewHolder.blueTier.setBackgroundColor(Color.parseColor("#ADA6A2"));
        }
    }public void patchTextView2(MyViewHolder myViewHolder)
    {
        if(myViewHolder.redTier.getText().equals("C"))
        {
            myViewHolder.redTier.setBackgroundColor(Color.parseColor("#cc0000"));
        }else if(myViewHolder.redTier.getText().equals("GM"))
        {
            myViewHolder.redTier.setBackgroundColor(Color.parseColor("#cc0000"));
        }else if(myViewHolder.redTier.getText().equals("M"))
        {
            myViewHolder.redTier.setBackgroundColor(Color.parseColor("#cc0000"));
        }else if(myViewHolder.redTier.getText().equals("D1") ||
                myViewHolder.redTier.getText().equals("D2") ||
                myViewHolder.redTier.getText().equals("D3") ||
                myViewHolder.redTier.getText().equals("D4"))
        {
            myViewHolder.redTier.setBackgroundColor(Color.parseColor("#0081cc"));
        }else if(myViewHolder.redTier.getText().equals("P1") ||
                myViewHolder.redTier.getText().equals("P2") ||
                myViewHolder.redTier.getText().equals("P3") ||
                myViewHolder.redTier.getText().equals("P4"))
        {
            myViewHolder.redTier.setBackgroundColor(Color.parseColor("#008080"));
        }else if(myViewHolder.redTier.getText().equals("G1") ||
                myViewHolder.redTier.getText().equals("G2") ||
                myViewHolder.redTier.getText().equals("G3") ||
                myViewHolder.redTier.getText().equals("G4"))
        {
            myViewHolder.redTier.setBackgroundColor(Color.parseColor("#FFD700"));
        }else if(myViewHolder.redTier.getText().equals("S1") ||
                myViewHolder.redTier.getText().equals("S2") ||
                myViewHolder.redTier.getText().equals("S3") ||
                myViewHolder.redTier.getText().equals("S4"))
        {
            myViewHolder.redTier.setBackgroundColor(Color.parseColor("#C0C0C0"));
        }else if(myViewHolder.redTier.getText().equals("B1") ||
                myViewHolder.redTier.getText().equals("B2") ||
                myViewHolder.redTier.getText().equals("B3") ||
                myViewHolder.redTier.getText().equals("B4"))
        {
            myViewHolder.redTier.setBackgroundColor(Color.parseColor("#624637"));
        }else if(myViewHolder.redTier.getText().equals("I1") ||
                myViewHolder.redTier.getText().equals("I2") ||
                myViewHolder.redTier.getText().equals("I3") ||
                myViewHolder.redTier.getText().equals("I4"))
        {
            myViewHolder.redTier.setBackgroundColor(Color.parseColor("#ADA6A2"));
        }
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView friendImage, enemyImage, spell1Image, spell2Image, spell1Image2, spell2Image2, rune1Image, rune2Image, rune1Image2, rune2Image2;
        TextView blueUserName, redUserName, blueTier, redTier;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            friendImage = itemView.findViewById(R.id.freindChampion);
            enemyImage = itemView.findViewById(R.id.enemyChampion);
            spell1Image = itemView.findViewById(R.id.spell1Image);
            spell2Image = itemView.findViewById(R.id.spell2Image);
            spell1Image2 = itemView.findViewById(R.id.spell1Image2);
            spell2Image2 = itemView.findViewById(R.id.spell2Image2);
            rune1Image = itemView.findViewById(R.id.rune1Image);
            rune2Image = itemView.findViewById(R.id.rune2Image);
            rune1Image2 = itemView.findViewById(R.id.rune1Image2);
            rune2Image2 = itemView.findViewById(R.id.rune2Image2);

            blueUserName = itemView.findViewById(R.id.blueUserName);
            redUserName = itemView.findViewById(R.id.redUserName);

            blueTier = itemView.findViewById(R.id.blueTier);
            redTier = itemView.findViewById(R.id.redTier);

        }
    }

}
