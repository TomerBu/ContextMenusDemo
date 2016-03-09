package tomerbu.edu.contextmenusdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PopUpListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private List<String> data;
    private LayoutInflater inflater;

    public PopUpListAdapter(Context context, List<String> data) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_pop_item, parent, false);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvItemTitle);
        ImageView ivItemImage = (ImageView) convertView.findViewById(R.id.fbSpeak);

        String item = data.get(position);
        tvTitle.setText(item);
        ivItemImage.setImageResource(position % 2 == 0 ? R.drawable.ic_flight : R.drawable.ic_motorcycle);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, data.get(position), Toast.LENGTH_SHORT).show();
    }
}
