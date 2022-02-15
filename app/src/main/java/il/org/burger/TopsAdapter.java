package il.org.burger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TopsAdapter extends ArrayAdapter {

    public TopsAdapter(@NonNull Context context, ArrayList<BurgerTopItem> ballItems) {
        super(context, 0,ballItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,parent,false);
        }
        ImageView ballImage = convertView.findViewById(R.id.top_image);
        TextView ballName = convertView.findViewById(R.id.top_name);

        BurgerTopItem currentBall = (BurgerTopItem) getItem(position);
        if(currentBall != null){
            ballImage.setImageResource(currentBall.getTopImage());
            ballName.setText(currentBall.getTopName());
        }
        return convertView;
    }
}
